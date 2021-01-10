package com.example.JAR;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.JAR.databinding.ActivityLoginBinding;

import net.dean.jraw.RedditClient;
import net.dean.jraw.oauth.StatefulAuthHelper;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final StatefulAuthHelper helper = JRAW.getLoginHelper();
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        WebView login = binding.webview;
        login.getSettings().setUserAgentString(App.getUserAgent().toString());
        login.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (helper.isFinalRedirectUrl(url)) {
                    view.stopLoading();
                    Log.d("JAR",url);
                    Background.execute(()->{
                        RedditClient reddit = helper.onUserChallenge(url);


                        JRAW.setINSTANCE(reddit);
                        NavigationHandler.openFrontpage(LoginActivity.this);
                    });


                }
            }
        });
        login.loadUrl(helper.getAuthorizationUrl(false,
                true,
                "read", "vote","identity"
        ));

    }

    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.login);
    }
}
