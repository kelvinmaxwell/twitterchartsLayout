// Post.java
package com.example.twitterchartslayout;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String username;
    private String content;
    private List<Comment> comments;

    public Post(String username, String content) {
        this.username = username;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
