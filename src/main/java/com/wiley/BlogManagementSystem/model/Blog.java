package com.wiley.BlogManagementSystem.model;

import org.attoparser.dom.Text;

import java.util.Date;
import java.util.Objects;

public class Blog {
    private int blog_id;
    private String title;
    private Date date;
    //idk about this
    private String body;
    private boolean isApproved;


    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean approved) {
        isApproved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blog)) return false;
        Blog blog = (Blog) o;
        return getBlog_id() == blog.getBlog_id() && isApproved == blog.isApproved && getTitle().equals(blog.getTitle()) && getDate().equals(blog.getDate()) && getBody().equals(blog.getBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlog_id(), getTitle(), getDate(), getBody(), isApproved);
    }
}

