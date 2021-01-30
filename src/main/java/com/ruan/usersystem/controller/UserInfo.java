package com.ruan.usersystem.controller;

//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruan.usersystem.config.LoginRequired;
import com.ruan.usersystem.entity.Response;
import com.ruan.usersystem.entity.User;
import com.ruan.usersystem.service.UserService;
import com.ruan.usersystem.utils.TokenUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;


//RestController responseBody和controller的结合

@RestController
public class UserInfo {
    @Autowired
    UserService service;
    @Autowired
    StringRedisTemplate redisTemplate;
    long time = 60 * 60 * 2 * 1000;
    private static Logger logger = LogManager.getLogger(UserInfo.class.getName());
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
            List<User> users = service.queryByUserInfo(account,10,1);
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
    public Response queryByUsername(HttpServletRequest request,String account, Integer pageSize, Integer pageNum) {
        if(pageSize == null) {
            pageSize = 20;
        }
        if(pageNum == null) {
            pageNum = 1;
        }
        Response res = new Response();
        Object userInfo = request.getAttribute("userInfo");
        List <User> users = service.queryByUserInfo(account,pageSize,pageNum);
        Integer total = service.getTotal(account);
        res.setList(200,(List) users,"获取成功", pageNum, pageSize,total);
        return res;
    }

    /***
     * 登录
     * @param person
     * @return
     */

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody Map<String, String> person) throws JSONException {
        Date date = new Date();
        long t = date.getTime() + 60 * 60 * 2 * 1000;

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
        if(password != null && !password.isEmpty()) {
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
                    String userString = act + userId;
                    String userTokenExp = userString + "exp";
                    redisTemplate.opsForValue().set(userString, token, 30, TimeUnit.MINUTES);
                    redisTemplate.opsForValue().set(userTokenExp, Long.toString(t),time,TimeUnit.MILLISECONDS);
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

    /***
     * 登出接口--post
     * @param token
     * @return
     */
    @RequestMapping(value = "/loginOut",method=RequestMethod.POST)
    public Response loginOut(@RequestHeader("Authorization") String token) {
        TokenUtils utils = new TokenUtils();
        Response res = new Response();
        Map userInfo = utils.decryptJwt(token);
//            token 校验失败
        if (userInfo == null) {
            res.setCode(400);
            res.setMsg("token已经失效，请直接登录");
            res.setResult(null);
        } else {
            String userId = userInfo.get("userId").toString();
            String account = userInfo.get("account").toString();
            String redisToken = redisTemplate.opsForValue().get(account + userId);

            if(redisToken == null) {
                res.setCode(400);
                res.setMsg("token已经失效，请直接登录");
                res.setResult(null);
            } else {

                Boolean s = redisTemplate.delete(account + userId);
                if(s) {
                    res.setCode(200);
                    res.setMsg("退出成功");
                    res.setResult(null);
                } else {
                    res.setCode(400);
                    res.setMsg("退出失败，redis删除失败");
                    res.setResult(null);
                }

            }
        }
        return res;
    }

    /***
     * 删除用户，更新用户节点status状态，1--已删除，1--账号删除
     * @param body
     * @return
     */
    @RequestMapping(value = "/removeUser", method = RequestMethod.DELETE)
    @LoginRequired
    public Response removeUser(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        Response res = new Response();
        int code = 200;
        String msg = "删除成功";

        if(userId == null) {
            code = 400;
            msg = "userId不能为null";
        }
        try {
            int val = service.setUserStatus(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        res.setResult(null);
        res.setMsg(msg);
        res.setCode(code);
        return res;
    }
}
