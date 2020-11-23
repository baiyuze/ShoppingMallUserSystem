package com.ruan.usersystem.service;

import com.ruan.usersystem.controller.UserInfo;
import com.ruan.usersystem.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserService {
   public void inSertUser(String account, String name, String password);
   public Integer getTotal(String tableName);
   List<User> queryByUserInfo(@Param("account") String account,@Param("pageSize") Integer pageSize,@Param("pageNum") Integer pageNum);
   List<User> queryByUserInfoAndPw(String account);
}



