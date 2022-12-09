package com.wiley.BlogManagementSystem.dao;

import com.wiley.BlogManagementSystem.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BlogRepository extends JpaRepository <Blog, Integer> {


    @Query("SELECT b FROM blog" +
            "JOIN blog_tag ON blog.blog_id = blog_tag.blog_id " +
            "JOIN tag ON blog_tag.tag_id = tag.tag_id " +
            "WHERE tag.tag_name = ?1 " ) // blog.blog_id, blog.body, blog.date, blog.title FROM blog
    List<Blog> searchbyTag(String tag_name);
}
