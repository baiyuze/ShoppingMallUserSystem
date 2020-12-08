package com.ruan.usersystem.config;

import com.ruan.usersystem.entity.User;
import com.ruan.usersystem.service.UserService;
import com.ruan.usersystem.utils.TokenUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Autowired
    StringRedisTemplate redisTemplate;
    long time =  60 * 60 * 2 * 1000;
    @Override
    //在Controller执行之前调用，如果返回false，controller不执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if(methodAnnotation != null) {
            String token = request.getHeader("Authorization");
            TokenUtils utils = new TokenUtils();
            Map userInfo = utils.decryptJwt(token);
            //            token 校验失败
            if (userInfo == null) {
                reponeseData(response,1);
                return false;
            } else {
                // token校验成功，读取用户数据
                String userId = userInfo.get("userId").toString();
                String act = userInfo.get("account").toString();
                String userTokenExp = act + userId + "exp";
                String userToken = redisTemplate.opsForValue().get(act + userId);
                long currentTime = new Date().getTime();
                String userExpTimeRedis = redisTemplate.opsForValue().get(userTokenExp);
                if(userExpTimeRedis == null || userToken == null || userToken.isEmpty()) {
                    reponeseData(response, 2);
                    return false;
                } else {
                    long userExpTime = Long.parseLong(userExpTimeRedis);
                    System.out.println(currentTime+"====="+userExpTime+"currentTime - userExpTime) <= 60 * 1000");
                    if((userExpTime - currentTime) <= 60 * 1000) {
                        Date date = new Date();
                        long t = date.getTime() + time;
                        // 更新redis过期时间
                        redisTemplate.opsForValue().set(userTokenExp, Long.toString(t),time, TimeUnit.MILLISECONDS);
                    }
                }
                //  过期时间，如果还有一分钟即将过期，则更新过期时间


                request.setAttribute("userInfo", userInfo);
                return true;
            }
        }
        return true;
    }

    /***
     * 响应数据给接口
     * @param response
     */
    public void reponeseData(HttpServletResponse response, int type) throws IOException, JSONException {
        String msg = (type == 1 ? "token校验失败，请检查token信息": "token已经失效，请重新登录");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject res = new JSONObject();
        res.put("status",400);
        res.put("msg",msg);
        res.put("result",org.json.JSONObject.NULL);
        //8kb缓存，不满不响应数据
        PrintWriter out = null ;
        // getWriter要写在utf8前面
        out = response.getWriter();
        // write 写入数据到接口中
        out.write(res.toString());
        // 不调用flush则不会返回数据
        out.flush();
        // 结束响应
        out.close();
    }


    //controller执行之后，且页面渲染之前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("---------postHandle--------");
    }

    //页面渲染之后调用，一般用于资源清理操作
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("---------afterCompletion--------");
    }

}
