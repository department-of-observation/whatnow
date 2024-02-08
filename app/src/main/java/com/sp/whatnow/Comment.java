package com.sp.whatnow;

public class Comment {
    private String forum_id = "";
    private String comment_id = "";
    private String comment_user = "";
    private String comment_content = "";

    public String getId() {
        return forum_id;
    }

    public void setId(String forum_id) {
        this.forum_id = forum_id;
    }

    public String getCId() {
        return comment_id;
    }

    public void setCId(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getUser() {
        return comment_user;
    }

    public void setUser(String comment_user) {
        this.comment_user = comment_user;
    }

    public String getContent() {
        return comment_content;
    }

    public void setContent(String content) {
        this.comment_content = comment_content;
    }


}
