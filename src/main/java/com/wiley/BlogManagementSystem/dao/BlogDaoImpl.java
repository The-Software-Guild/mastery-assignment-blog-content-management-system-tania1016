package com.wiley.BlogManagementSystem.dao;


import com.wiley.BlogManagementSystem.model.Blog;
import com.wiley.BlogManagementSystem.model.BlogTagDetail;
import com.wiley.BlogManagementSystem.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class BlogDaoImpl implements BlogDao{


    @Autowired
    JdbcTemplate jdbc;


    @Transactional
    @Override
    public Blog add(Blog blog) {
        final String sql = "INSERT INTO blog( title, date, body, isApproved) " + "VALUES(?,?,?,?)";

        jdbc.update(sql, blog.getTitle(), blog.getDate(), blog.getBody(), blog.getIsApproved());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        blog.setBlog_id(newId);
        return blog;
    }

    @Override
    public List<Blog> getAllBlog() {
        final String sql = "SELECT * FROM blog";
        return jdbc.query(sql, new BlogMapper());
    }

    @Override
    public List<BlogTagDetail> getAllBlogTags(){
        final String sql = "SELECT blog.blog_id, blog.title, blog.date, blog.body, blog.isApproved, tag.tag_name, tag.tag_id FROM blog " +
                "JOIN blog_tag on blog.blog_id=blog_tag.blog_id " +
                "JOIN tag on blog_tag.tag_id=tag.tag_id ORDER BY blog_id ASC";
        return jdbc.query(sql, new BlogMapper2());
    }

    @Override
    public BlogTagDetail getSingleBlogTags(int blog_id){
        final String sql = "SELECT blog.blog_id, blog.title, blog.date, blog.body, blog.isApproved, tag.tag_name, tag.tag_id FROM blog " +
                "JOIN blog_tag on blog.blog_id=blog_tag.blog_id " +
                "JOIN tag on blog_tag.tag_id=tag.tag_id WHERE blog.blog_id = ?";
//                " blog.blog_id, blog.title, blog.date, blog.body, blog.isApproved, tag.tag_name, tag.tag_id FROM blog " +
//                "JOIN blog_tag on blog.blog_id=blog_tag.blog_id " +
//                "JOIN tag on blog_tag.tag_id=tag.tag_id WHERE blog_id = ?";
        return jdbc.queryForObject(sql, new BlogMapper2(), blog_id);
    }
    //add a get all blogs that are approved
    @Override
    public List<Blog> getAllApprovedBlog() {
        final String sql = "SELECT * FROM blog WHERE isApproved = 1";
        return jdbc.query(sql, new BlogMapper());
    }

//    @Override
//    public List<BlogTagDetail> getAllBlogTagDetails(){
//        final String sql = "SELECT blog.blog_id,blog.title,blog.date,blog.body,blog.isApproved " +
//                "tag.tag_name FROM blog " +
//                "JOIN blog_tag ON blog.blog_id=blog_tag.blog_id " +
//                "JOIN tag ON tag.tag_id=blog_tag.tag_id ORDER BY blog_id DESC";
//        return jdbc.query(sql, new BlogMapper2());
//
//    }

    @Override
    public List<Blog> getAllActiveBlogs() {
        final String sql = "SELECT * FROM blog WHERE isApproved = 1";
        return jdbc.query(sql, new BlogMapper());
    }

    @Override
    public List<Blog> getRecentBlogs() {
        final String sql = "SELECT * FROM blog WHERE isApproved = 1 ORDER BY date";
        return jdbc.query(sql, new BlogMapper());
    }

    @Override
    public Blog getBlogById(int blog_id) {
        try {
            final String sql = "SELECT * FROM blog WHERE blog_id = ?";
            return jdbc.queryForObject(sql, new BlogMapper(), blog_id);

        } catch (DataAccessException ex) {
            return null;
        }
    }


    @Override
    public void updateBlog(Blog blog) {
        final String sql = "UPDATE blog SET "
                + "title = ?, "
                + "date = ?, "
                + "body = ?, "
                + "isApproved = ? "
                + "WHERE blog_id = ?;";
        jdbc.update(sql, blog.getTitle(), blog.getDate(), blog.getBody(), blog.getIsApproved(), blog.getBlog_id());
    }

    @Override
    public void updateBlogTag(Blog blog, Tag tag){
        final String sql = "UPDATE blog_tag SET tag_id = ? " +
                "WHERE blog_id = ?";
        jdbc.update(sql,
                tag.getTag_id(),
                blog.getBlog_id());
    }


    @Override
    @Transactional
    public void deleteBlogById(int blog_id) {

        //delete from the bridge table first
        final String DELETE_BLOG_TAG_BLOGS = "DELETE FROM blog_tag WHERE blog_id = ?";
        jdbc.update(DELETE_BLOG_TAG_BLOGS, blog_id);

        //then we can delete the actual blog
        final String DELETE_BLOG = "DELETE FROM blog WHERE blog_id = ?";
        jdbc.update(DELETE_BLOG, blog_id);
    }
    @Override
    public void addBlogTag(Blog blog, Tag tag){
        final String sql = "INSERT INTO blog_tag(blog_id, tag_id) "
                + "values(?,?)";
        jdbc.update(sql,
                blog.getBlog_id(),
                tag.getTag_id());
    }
    @Override
    public List<Blog> getLastThreeBlogs(){
        final String sql = "SELECT * FROM blog ORDER BY blog_id DESC LIMIT 3";
        return jdbc.query(sql, new BlogMapper());
    }

    public static final class BlogMapper implements RowMapper<Blog> {

        @Override
        public Blog mapRow(ResultSet rs, int index) throws SQLException {
            Blog blog = new Blog();
            blog.setBlog_id(rs.getInt("blog_id"));
            blog.setTitle(rs.getString("title"));
            blog.setDate(rs.getDate("date"));
            blog.setBody(rs.getString("body"));
            blog.setIsApproved(rs.getBoolean("isApproved"));
            return blog;
        }
    }

    public static final class BlogMapper2 implements RowMapper<BlogTagDetail> {

        @Override
        public BlogTagDetail mapRow(ResultSet rs, int index) throws SQLException {
            BlogTagDetail btd = new BlogTagDetail();
            btd.setBlog_id(rs.getInt("blog_id"));
            btd.setTitle(rs.getString("title"));
            btd.setDate(rs.getDate("date"));
            btd.setBody(rs.getString("body"));
            btd.setApproved(rs.getBoolean("isApproved"));
            btd.setTag_name(rs.getString("tag_name"));
            return btd;
        }
    }
}

