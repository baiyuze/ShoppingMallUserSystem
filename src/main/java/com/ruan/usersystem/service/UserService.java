package com.ruan.usersystem.service;

import com.ruan.usersystem.controller.UserInfo;
import com.ruan.usersystem.entity.User;

import java.util.List;


public interface UserService {
   public void inSertUser(String account, String name, String password);
   List<User> queryByUserInfo(String account);
   List<User> queryByUserInfoAndPw(String account);
}



