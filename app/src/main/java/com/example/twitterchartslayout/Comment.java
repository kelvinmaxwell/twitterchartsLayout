// Comment.java
package com.example.twitterchartslayout;

import java.util.ArrayList;
import java.util.List;

public class Comment {
    private String username;
    private String content;
    private List<Comment> replies;

    public Comment(String username, String content) {
        this.username = username;
        this.content = content;
        this.replies = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }
}
