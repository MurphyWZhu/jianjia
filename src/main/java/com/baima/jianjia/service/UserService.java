package com.baima.jianjia.service;

import com.baima.jianjia.pojo.User;

import java.util.List;

public interface UserService {
    List<User> queryUserList();

    List<String> queryUserNameList();

    User queryUserByName(String name);

    void addUser(User user);

    void updatePassword(String username,String password);
}
