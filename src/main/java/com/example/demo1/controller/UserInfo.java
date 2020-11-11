package com.example.demo1.controller;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo1.entity.Response;
import com.example.demo1.entity.User;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

//RestController responseBody和controller的结合

@RestController
public class UserInfo {
    @Autowired
    UserService service;

    /***
     * 注册
     * @param person
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Response inSertUser(@RequestBody Map<String, String> person) {
        String account = person.get("account");
        String name = person.get("name");
        String password = person.get("password");
        String msg = "注册成功";
        int code = 200;
        Response res = new Response();

        if(account != null && account != ""  && name != null && password != null) {
            List<User> users = service.queryByUserInfo(account);
            if(users.size() >0) {
                msg = "当前账号已经使用，请重新输入";
                code = 400;
            } else {
                service.inSertUser(account,name,password);
            }
        } else {
            msg = "账号或用户名和密码不能为空";
            code = 400;
        }

        res.setMsg(msg);
        res.setCode(code);
        System.out.println(person);
        return res;
    }

    /**
     * 获取账号信息
     * @param account
     * @return
     */
    @RequestMapping("/getUserInfo")
    public Response queryByUsername(String account) {
        Response res = new Response();
        if(account == null || account == "") {
            res.setMsg("account参数不能为空");
            res.setCode(400);
            return res;
        }
        List<User> users = service.queryByUserInfo(account);
        res.getUserList(200,users,"获取成功");
        return res;
    }

    /***
     * 登录
     * @param person
     * @return
     */
    @RequestMapping("/login")
    public Response login(@RequestBody Map<String, String> person) {
        Response res = new Response();
        String account = person.get("account");
        String password = person.get("password");
        if(account == null || account.isEmpty() || password == null || password .isEmpty()) {
            res.setMsg("用户名或密码不能为空");
            res.setCode(400);
            return res;
        }
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$";
        boolean isMatch = Pattern.matches(pattern, password);
        if(isMatch) {
            List <User> userList = service.queryByUserInfoAndPw(account);
            if(userList != null && userList.size() > 0) {
                User userInfo = userList.get(0);
                String psw = userInfo.getPassword();
                String act = userInfo.getAccount();
                System.out.println(psw + " " + password +"密码");

                if(psw.equals(password) && act.equals(account)) {
                    res.setMsg("登录成功");
                    res.setCode(200);
                    // 生成token 信息
                    res.setResult("akskakakjfakjlas");
                    return res;
                }
            }
            res.setMsg("密码错误");
            res.setCode(400);
            return res;
        } else {
            res.setMsg("密码错误，至少8-16个字符，至少1个大写字母，1个小写字母和1个数字");
            res.setCode(400);
            return res;
        }

    }
}
