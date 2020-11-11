package com.example.demo1.service.impl;

import com.example.demo1.entity.User;
import com.example.demo1.mapper.UserMapper;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public void inSertUser(String account, String name, String password) {
        User user = new User(account,name,password);
        userMapper.inSertUser(user);
    }
    public List <User> queryByUserInfo(String account) {
        List <User> userList = userMapper.queryByUserInfo(account);
        return userList;
    }

    /***
     * 获取用户信息
     * @param account
     * @return
     */
    public List <User> queryByUserInfoAndPw(String account) {
        List <User> userList = userMapper.queryByUserInfoAndPw(account);
        return userList;
    }
}

