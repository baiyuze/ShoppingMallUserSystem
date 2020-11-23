package com.ruan.usersystem.service.impl;

import com.ruan.usersystem.entity.User;
import com.ruan.usersystem.mapper.UserMapper;
import com.ruan.usersystem.service.UserService;
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
    public List <User> queryByUserInfo(String account, Integer pageSize,Integer pageNum) {
        List <User> userList = userMapper.queryByUserInfo(account,pageSize,pageNum);
        return userList;
    }

    public Integer getTotal(String tableName){
        Integer total = userMapper.getTotal(tableName);
        return total;
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

