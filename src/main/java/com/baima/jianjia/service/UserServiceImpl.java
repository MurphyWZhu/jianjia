package com.baima.jianjia.service;

import com.baima.jianjia.mapper.UserMapper;
import com.baima.jianjia.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        return userMapper.queryUserList();
    }

    @Override
    public List<String> queryUserNameList() {
        return userMapper.queryUserNameList();
    }

    @Override
    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user.username, user.password);
    }

    @Override
    public void updatePassword(String username,String password){
        userMapper.updatePassword(username, password);
    }
}
