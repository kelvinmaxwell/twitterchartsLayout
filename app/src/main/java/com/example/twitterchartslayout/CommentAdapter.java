// CommentAdapter.java
package com.example.twitterchartslayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;
    private OnItemClickListenerCommets onItemClickListener;
    public interface OnItemClickListenerCommets {
        void onItemClick(Comment comment);
    }




    public CommentAdapter(List<Comment> comments, OnItemClickListenerCommets onItemClickListener) {
        this.comments = comments;
        this.onItemClickListener=onItemClickListener;

    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private TextView usernameTextView;
        private TextView contentTextView;
        private RecyclerView nestedRecyclerView;
        private CommentAdapter nestedAdapter;



        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            nestedRecyclerView = itemView.findViewById(R.id.nestedRecyclerView);

            nestedAdapter = new CommentAdapter(new ArrayList<>(),onItemClickListener);
            nestedRecyclerView.setAdapter(nestedAdapter);
            nestedRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            nestedAdapter.getItemCount();



                    itemView.setOnClickListener(v -> {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                        onItemClickListener.onItemClick(comments.get(position));
                        if(nestedAdapter.getItemCount()>0) {
                            Log.d("child count", String.valueOf(getBindingAdapterPosition()));

                                toggleNestedCommentsVisibility();
                            }
                        }
                    });


            }

//            itemView.setOnClickListener(v -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
//                    onItemClickListener.onItemClick(position);
//                }
//            });




        public void bind(Comment comment) {
            usernameTextView.setText(comment.getUsername());
            contentTextView.setText(comment.getContent());

            // Initially hide the nested comments
            nestedRecyclerView.setVisibility(View.GONE);

            // Set up nested comments RecyclerView
            nestedAdapter.setComments(comment.getReplies());
            nestedAdapter.notifyDataSetChanged();
        }


        public void toggleNestedCommentsVisibility() {
            // Toggle visibility of nested comments
            if (nestedRecyclerView.getVisibility() == View.VISIBLE) {
                nestedRecyclerView.setVisibility(View.GONE);
            } else {
                nestedRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }




}
