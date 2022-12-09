package com.wiley.BlogManagementSystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlogManyTags {

    private List<Tag> tags = new ArrayList<>();

    public BlogManyTags() {
    }

    public BlogManyTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogManyTags that = (BlogManyTags) o;

        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return tags != null ? tags.hashCode() : 0;
    }
}
