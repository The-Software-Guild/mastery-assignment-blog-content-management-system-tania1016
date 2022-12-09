package com.wiley.BlogManagementSystem.dao;


import com.wiley.BlogManagementSystem.model.Blog;
import com.wiley.BlogManagementSystem.model.BlogTagDetail;
import com.wiley.BlogManagementSystem.model.Tag;

import java.util.List;

public interface BlogDao {

    Blog add(Blog blog);

    List<Blog> getAllBlog();

    Blog getBlogById(int blog_id);

    List<BlogTagDetail> getAllBlogTags();
//    List<BlogTagDetail> getAllBlogTagDetails()
    BlogTagDetail getSingleBlogTags(int blog_id);
    List<Blog> getAllApprovedBlog();
//    List<BlogTagDetail> getAllBlogTagDetails();

    void updateBlog(Blog blog);
    void updateBlogTag(Blog blog, Tag tag);

    void deleteBlogById(int blog_id);

    void addBlogTag(Blog blog, Tag tag);

    List<Blog> getAllActiveBlogs();

    List<Blog> getRecentBlogs();
    List<Blog> getLastThreeBlogs();
}
