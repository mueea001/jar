package com.example.JAR;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import net.dean.jraw.android.AndroidHelper;
import net.dean.jraw.android.AppInfo;
import net.dean.jraw.android.AppInfoProvider;
import net.dean.jraw.android.SharedPreferencesTokenStore;
import net.dean.jraw.android.SimpleAndroidLogAdapter;
import net.dean.jraw.http.LogAdapter;
import net.dean.jraw.http.SimpleHttpLogger;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.AccountHelper;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class App extends Application {
    private static AccountHelper accountHelper;
    private static SharedPreferencesTokenStore tokenStore;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInfoProvider provider = new AppInfoProvider() {
            @NotNull
            @Override
            public AppInfo provide() {
                Context context = getApplicationContext();
                UserAgent ua = new UserAgent("android", context.getPackageName(),context.getString(R.string.app_version),"JARForReddit");
                return new AppInfo("fxPqP8pMEuu-Tw",
                        "http://localhost:8080/",
                        ua
                );
            }
        };
        UUID uuid = UUID.randomUUID();

        // Store our access tokens and refresh tokens in shared preferences
        tokenStore = new SharedPreferencesTokenStore(getApplicationContext());
        // Load stored tokens into memory
        tokenStore.load();
        // Automatically save new tokens as they arrive
        tokenStore.setAutoPersist(true);

        // An AccountHelper manages switching between accounts and into/out of userless mode.
        accountHelper = AndroidHelper.accountHelper(provider, uuid, tokenStore);

        // Every time we use the AccountHelper to switch between accounts (from one account to
        // another, or into/out of userless mode), call this function
        accountHelper.onSwitch(redditClient -> {
            // By default, JRAW logs HTTP activity to System.out. We're going to use Log.i()
            // instead.
            LogAdapter logAdapter = new SimpleAndroidLogAdapter(Log.INFO);

            // We're going to use the LogAdapter to write down the summaries produced by
            // SimpleHttpLogger
            redditClient.setLogger(
                    new SimpleHttpLogger(SimpleHttpLogger.DEFAULT_LINE_LENGTH, logAdapter));

            // If you want to disable logging, use a NoopHttpLogger instead:
            // redditClient.setLogger(new NoopHttpLogger());

            return null;
        });

    }
    public static AccountHelper getAccountHelper() { return accountHelper; }
    public static SharedPreferencesTokenStore getTokenStore() { return tokenStore; }
}