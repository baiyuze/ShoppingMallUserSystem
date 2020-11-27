package com.ruan.usersystem.mapper;

import com.ruan.usersystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    /***
     * 注册插入信息
     * @param user
     * @return
     */
    int inSertUser(User user);

    /***
     * 获取不含有密码的用户信息
     * @param account
     * @return
     */
    List<User> queryByUserInfo(String account, Integer pageSize, Integer pageNum);

    Integer getTotal(@Param("account") String account);

//    List <User> queryByUserInfo(@Param("account") String account );
    /***
     * 获取含有密码的账户
     * @param account
     * @return
     */
    List <User> queryByUserInfoAndPw(String account);

    Integer setUserStatus(@Param("userId") Integer userId);

}


