package com.wiley.BlogManagementSystem.model;

import java.util.Date;
import java.util.Objects;

public class BlogTagDetail {
    private int blog_id;
    private String title;
    private Date date;
    private String body;
    private boolean isApproved;
    private String tag_name;

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

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogTagDetail)) return false;
        BlogTagDetail that = (BlogTagDetail) o;
        return getBlog_id() == that.getBlog_id() && isApproved() == that.isApproved() && getTitle().equals(that.getTitle()) && Objects.equals(getDate(), that.getDate()) && getBody().equals(that.getBody()) && Objects.equals(getTag_name(), that.getTag_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlog_id(), getTitle(), getDate(), getBody(), isApproved(), getTag_name());
    }
}
