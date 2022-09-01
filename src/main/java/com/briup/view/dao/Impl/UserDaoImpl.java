package com.briup.view.dao.Impl;


import com.briup.view.dao.UserDao;
import com.briup.view.pojo.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
    private String driver="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost:3306/env";
    private String user="root";
    private String passwd="root";
    private Connection connection = null;
    public UserDaoImpl(){
        try {
            connection= DriverManager.getConnection(url,user,passwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void save(User user)  {
        String sql = "insert into user(username,pwd,gender,info) values(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPwd());
            ps.setString(3,user.getGender());
            ps.setString(4,user.getInfo());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public User find(String name)  {
        String sql = "select * from user where username=?";
        PreparedStatement ps = null;
        User user = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPwd(rs.getString("pwd"));
                user.setGender(rs.getString("gender"));
                user.setInfo(rs.getString("info"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
