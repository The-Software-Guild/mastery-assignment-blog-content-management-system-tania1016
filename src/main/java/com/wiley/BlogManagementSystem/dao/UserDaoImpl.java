package com.wiley.BlogManagementSystem.dao;

import com.wiley.BlogManagementSystem.model.User;
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
public class UserDaoImpl implements UserDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public User addUser(User user){
        final String sql = "INSERT INTO user(role, username, password) "
                + "VALUES(?,?,?)";
        jdbc.update(sql,
                user.getRole(),
                user.getUsername(),
                user.getPassword());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        user.setUser_id(newId);
        return user;
    }

    @Override
    public List<User> getAllUsers(){
        final String sql = "SELECT * FROM user";
        return jdbc.query(sql, new UserMapper());
    }

    @Override
    public User getUserById(int user_id){
        try{
            final String sql = "SELECT * FROM user WHERE user_id = ?";
            return jdbc.queryForObject(sql, new UserMapper(), user_id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void deleteUserById(int user_id){
        final String sql = "DELETE FROM user WHERE user_id = ?";
        jdbc.update(sql, user_id);
    }

    @Override
    public void updateUser(User user){
        final String sql = "UPDATE user SET role = ?, username = ?, password = ? " +
                "WHERE user_id = ?";
        jdbc.update(sql,
                user.getRole(),
                user.getUsername(),
                user.getPassword(),
                user.getUser_id());
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setRole(rs.getString("role"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
