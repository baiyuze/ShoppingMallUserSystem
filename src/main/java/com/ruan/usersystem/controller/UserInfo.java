package com.ruan.usersystem.controller;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruan.usersystem.config.LoginRequired;
import com.ruan.usersystem.entity.Response;
import com.ruan.usersystem.entity.User;
import com.ruan.usersystem.service.UserService;
import com.ruan.usersystem.utils.TokenUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;


//RestController responseBody和controller的结合

@RestController
public class UserInfo {
    @Autowired
    UserService service;
    @Value("${jwt.secretString}")
    private String secretString;
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
        res.setResult(null);
        return res;
    }

    /**
     * 获取账号信息
     * @param account
     * @return
     */
    @RequestMapping("/getUserInfo")
    @LoginRequired
    public Response queryByUsername(HttpServletRequest request,String account) {
        Response res = new Response();

        if(account == null || account == "") {
            res.setMsg("account参数不能为空");
            res.setCode(400);
            return res;
        }
        Object userInfo = request.getAttribute("userInfo");
        System.out.println(userInfo + "============");
        List <User> users = service.queryByUserInfo(account);

        res.getUserList(200,(List) users,"获取成功");
        return res;
    }

    /***
     * 登录
     * @param person
     * @return
     */

    @RequestMapping("/login")
    public Response login(@RequestBody Map<String, String> person) throws JSONException {

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
                User userObj = userList.get(0);
                String psw = userObj.getPassword();
                String act = userObj.getAccount();
                String name = userObj.getName();
                int userId = userObj.getId();
                System.out.println(userObj);
                if(psw.equals(password) && act.equals(account)) {
                    // 生成token 信息
                    TokenUtils utils = new TokenUtils();
                    String token = utils.createJwtToken(name,account,userId);
                    res.setMsg("登录成功");
                    res.setCode(200);
                    res.getTokenResult(token,userObj);
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
