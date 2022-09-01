package com.briup.view.dao;


import com.briup.view.pojo.User;

public interface UserDao {
    void save(User user);
    User find(String name);
}
