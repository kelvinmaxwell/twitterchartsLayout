// PostAdapter.java
package com.example.twitterchartslayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> implements CommentAdapter.OnItemClickListenerCommets {

    private List<Post> posts;
    private OnPostClickListener onPostClickListener;
    private CommentAdapter.OnItemClickListenerCommets onItemClickListenerComments;
    private int openPosition = RecyclerView.NO_POSITION;


    public PostAdapter(List<Post> posts, OnPostClickListener onPostClickListener) {
        this.posts = posts;
        this.onPostClickListener = onPostClickListener;
        this.onItemClickListenerComments = this;

    }


    @Override
    public void onItemClick(Comment comment) {
        if (onPostClickListener != null) {
            onPostClickListener.onCommentClick(comment);
        }
    }

    public interface OnPostClickListener {
        void onPostClick(Post post);
        void onCommentClick(Comment comment);
    }



    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(posts.get(position),position);
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
                    onPostClickListener.onPostClick(posts.get(position));
                    if(commentAdapter.getItemCount()>0) {
                        Log.d("child count", String.valueOf(getBindingAdapterPosition()));

                        toggleCommentsVisibility(position);
                    }

                }
            });
        }

        public void bind(Post post,int position) {
            usernameTextView.setText(post.getUsername());
            contentTextView.setText(post.getContent());

            // Initially hide the comments
            commentsRecyclerView.setVisibility(position == openPosition ? View.VISIBLE : View.GONE);


            // Set up comments RecyclerView
            commentAdapter = new CommentAdapter(post.getComments(), onItemClickListenerComments);
            commentsRecyclerView.setAdapter(commentAdapter);
            commentsRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));



        }
        private void toggleCommentsVisibility(int position) {
            // Toggle visibility of nested comments
            if (openPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(openPosition);
            }

            openPosition = (position == openPosition) ? RecyclerView.NO_POSITION : position;
            notifyItemChanged(openPosition);
        }
    }

//        public void toggleCommentsVisibility() {
//            // Toggle visibility of nested comments
//            if (commentsRecyclerView.getVisibility() == View.VISIBLE) {
//                commentsRecyclerView.setVisibility(View.GONE);
//                //toggleNestedCommentsVisibility(commentsRecyclerView);
//
//
//            } else {
//                commentsRecyclerView.setVisibility(View.VISIBLE);
//            }
//        }



}