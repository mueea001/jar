package com.example.JAR;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class submissionView extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);
    }


    /*public void getComments()
            // https://mattbdean.gitbooks.io/jraw/content/cookbook.html
    {
        RootCommentNode comment = redditClient.submission("need to find a way to pull the post id");
        // This line is used to retrieve the comments from the post
        Iterator<CommentNode<PublicContribution<?>>> it = root.walkTree().iterator();

        while (it.hasNext()) {
            PublicContribution<?> thing = it.next().getSubject();
            System.out.println(thing.getBody());
        }
    }*/
}
