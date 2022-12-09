package com.wiley.BlogManagementSystem.dao;

import com.wiley.BlogManagementSystem.model.Tag;

import java.util.List;

public interface TagDao {
    Tag add(Tag tag);

    List<Tag> getAllTags();

    Tag getTagById(int tag_id);

    void updateTag(Tag tag);

    void deleteTagById(int tag_id);
    List<Tag> getTagsByBlogId(int blog_id);
}
