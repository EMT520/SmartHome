package com.briup.view.pojo;

/*
create table user(
id int primary key auto_increment,
username varchar(20),
pwd varchar(20),
gender varchar(20),
info varchar(200)
);
 */
public class User {
    //用户id
    private Integer id;
    //用户姓名
    private String username;
    //登录密码
    private String pwd;
    //性别
    private String gender;
    //个人描述信息
    private String info;
    public User() {}
    public User(Integer id, String username, String pwd, String gender,
                String info) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.gender = gender;
        this.info = info;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", pwd=" + pwd
                + ", gender=" + gender + ", info=" + info + "]";
    }

}
