package com.example.twitterchartslayout;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twitterchartslayout.databinding.ActivityMainBinding;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  PostAdapter.OnPostClickListener{



    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts;
    private int openPostPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);

        // Generate sample data
        posts = generateSampleData();

        // Set up RecyclerView and adapter
        postAdapter = new PostAdapter(posts,this);



        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Post> generateSampleData() {
        List<Post> sampleData = new ArrayList<>();

        Post post1 = new Post("User1", "This is the first post.");
        Comment post1Comment1 = new Comment("User2", "Reply to post1.");
        post1Comment1.addReply(new Comment("User6", "Reply to reply1."));
        Comment reply2Comment1 = new Comment("User7", "Another reply to reply1..");
        reply2Comment1.addReply(new Comment("User7", "Another reply to reply1."));
        post1Comment1.addReply(reply2Comment1);
        Comment post1Comment12 = new Comment("User2", "Reply to post1.");
        post1.addComment(post1Comment1);
        post1.addComment(post1Comment12);

        Comment post1Comment2 = new Comment("User2", "Reply to post1.");
        post1Comment2.addReply(new Comment("User3", "Another reply to post1."));
        post1.addComment(post1Comment2);
        sampleData.add(post1);

        Post post2 = new Post("User4", "Another post here.");
        post2.addComment(new Comment("User5", "Reply to post2."));
        post2.addComment(new Comment("User8", "Another reply to post2."));
        sampleData.add(post2);

        // Add more posts and comments as needed

        return sampleData;
    }


    @Override
    public void onPostClick(Post post) {
        Toast.makeText(this, post.getUsername(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCommentClick(Comment comment) {
        Toast.makeText(this, comment.getUsername(), Toast.LENGTH_SHORT).show();
    }


}