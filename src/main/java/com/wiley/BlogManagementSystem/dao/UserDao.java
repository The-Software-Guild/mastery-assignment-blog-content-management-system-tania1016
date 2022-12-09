package com.wiley.BlogManagementSystem.dao;

import com.wiley.BlogManagementSystem.model.User;

import java.util.List;

public interface UserDao {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(int user_id);
    void deleteUserById(int user_id);
    void updateUser(User user);
//    //this is trying to add to memberorg
//    void addMemberToOrg(Member member, Organization organization);
//    void updateMemberOrg(Member member, Organization organization);
}
