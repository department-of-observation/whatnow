package com.sp.whatnow;

public class Forum {
    private String id = "";
    private String forumtitle = "";
    private String forumuser = "";
    private String forumcontent = "";
    private String forumdate = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return forumtitle;
    }

    public void setTitle(String forumtitle) {
        this.forumtitle = forumtitle;
    }

    public String getUser() {
        return forumuser;
    }

    public void setUser(String forumuser) {
        this.forumuser = forumuser;
    }

    public String getContent() {
        return forumcontent;
    }

    public void setContent(String forumcontent) {
        this.forumcontent = forumcontent;
    }

    public String getDate() {
        return forumdate;
    }

    public void setDate(String forumdate) {
        this.forumdate = forumdate;
    }

    @Override
    public String toString() {
        return (getTitle());
    }

}
