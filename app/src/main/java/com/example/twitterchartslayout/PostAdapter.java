// PostAdapter.java
package com.example.twitterchartslayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts;
    private OnPostClickListener onPostClickListener;

    public interface OnPostClickListener {
        void onPostClick(int position);
    }

    public PostAdapter(List<Post> posts, OnPostClickListener onPostClickListener) {
        this.posts = posts;
        this.onPostClickListener = onPostClickListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameTextView;
        private TextView contentTextView;
        private RecyclerView commentsRecyclerView;
        private CommentAdapter commentAdapter;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);

            // Set up click listener for the entire post item
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onPostClickListener.onPostClick(position);
                    toggleCommentsVisibility();
                }
            });
        }

        public void bind(Post post) {
            usernameTextView.setText(post.getUsername());
            contentTextView.setText(post.getContent());

            // Initially hide the comments
            commentsRecyclerView.setVisibility(View.GONE);

            // Set up comments RecyclerView
            commentAdapter = new CommentAdapter(post.getComments());
            commentsRecyclerView.setAdapter(commentAdapter);
            commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));

            commentAdapter.setOnItemClickListener(position -> {
                // Forward the click event to the PostAdapter's listener
                if (onPostClickListener != null) {
                    onPostClickListener.onPostClick(getAdapterPosition());
                }
            });

        }

        private void toggleCommentsVisibility() {
            // Toggle visibility of nested comments
            if (commentsRecyclerView.getVisibility() == View.VISIBLE) {
                commentsRecyclerView.setVisibility(View.GONE);
                toggleNestedCommentsVisibility(commentsRecyclerView);

                notifyDataSetChanged();
            } else {
                commentsRecyclerView.setVisibility(View.VISIBLE);
            }
        }

        private void toggleNestedCommentsVisibility(View view) {
            // Recursively toggle visibility of nested comments
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View childView = recyclerView.getChildAt(i);
                    if (childView.getTag() != null && childView.getTag() instanceof CommentAdapter.CommentViewHolder) {
                        CommentAdapter.CommentViewHolder commentViewHolder = (CommentAdapter.CommentViewHolder) childView.getTag();
                        commentViewHolder.toggleNestedCommentsVisibility();
                    }
                }
            }
        }
    }
}