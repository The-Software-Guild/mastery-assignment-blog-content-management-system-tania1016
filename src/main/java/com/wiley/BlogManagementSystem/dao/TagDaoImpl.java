package com.wiley.BlogManagementSystem.dao;

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
public class TagDaoImpl implements TagDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        final String sql = "INSERT INTO tag( tag_name) " + "VALUES(?)";

        jdbc.update(sql, tag.getTag_name());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        tag.setTag_id(newId);
        return tag;
    }

    @Override
    public List<Tag> getAllTags() {
        final String sql = "SELECT * FROM tag";
        return jdbc.query(sql, new TagMapper());
    }

    @Override
    public Tag getTagById(int tag_id) {
        try {
            final String sql = "SELECT * FROM tag WHERE tag_id = ?";
            return jdbc.queryForObject(sql, new TagMapper(), tag_id);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateTag(Tag tag) {
        final String sql = "UPDATE tag SET "
                + "tag_name = ? "
                + "WHERE tag_id = ?;";
        jdbc.update(sql, tag.getTag_name(), tag.getTag_id());
    }

    @Override
    @Transactional
    public void deleteTagById(int tag_id) {
        //UPDATE Blog_Tag SET tag_id = NULL WHERE tag_id = ?;
        final String UPDATE_BLOG_TAG = "UPDATE Blog_Tag SET tag_id = NULL WHERE tag_id = ?;";
        jdbc.update(UPDATE_BLOG_TAG, tag_id);

        //DELETE FROM tag WHERE tag_id = ?;
        final String DELETE_TAG = "DELETE FROM tag WHERE tag_id = ?;";
        jdbc.update(DELETE_TAG, tag_id);
    }

    @Override
    public List<Tag> getTagsByBlogId(int blog_id) {
        try {
            final String sql = "SELECT * FROM tag JOIN blog_tag ON tag.tag_id = blog_tag.tag_id WHERE blog_id = ?";
            return jdbc.query(sql, new TagMapper(), blog_id);

        } catch (DataAccessException ex) {
            return null;
        }
    }

    public static final class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int index) throws SQLException {
            Tag tag = new Tag();
            tag.setTag_id(rs.getInt("tag_id"));
            tag.setTag_name(rs.getString("tag_name"));
            return tag;
        }
    }
}
