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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);

        // Generate sample data
        posts = generateSampleData();

        // Set up RecyclerView and adapter
        postAdapter = new PostAdapter(posts, position -> {
            // Toggle visibility of comments on post click
            if (recyclerView != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder instanceof PostAdapter.PostViewHolder) {
                    PostAdapter.PostViewHolder postViewHolder = (PostAdapter.PostViewHolder) viewHolder;

                }
            }
        });

        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Post> generateSampleData() {
        List<Post> sampleData = new ArrayList<>();

        Post post1 = new Post("User1", "This is the first post.");
        Comment post1Comment1 = new Comment("User2", "Reply to post1.");
        post1Comment1.addReply(new Comment("User6", "Reply to reply1."));
        post1Comment1.addReply(new Comment("User7", "Another reply to reply1."));
        post1.addComment(post1Comment1);
        post1.addComment(new Comment("User3", "Another reply to post1."));
        sampleData.add(post1);

        Post post2 = new Post("User4", "Another post here.");
        post2.addComment(new Comment("User5", "Reply to post2."));
        post2.addComment(new Comment("User8", "Another reply to post2."));
        sampleData.add(post2);

        // Add more posts and comments as needed

        return sampleData;
    }




}