package com.example.demo1.service;

import com.example.demo1.controller.UserInfo;
import com.example.demo1.entity.User;

import java.util.List;


public interface UserService {
   public void inSertUser(String account, String name, String password);
   List<User> queryByUserInfo(String account);
   List<User> queryByUserInfoAndPw(String account);
}



