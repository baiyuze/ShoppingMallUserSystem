package com.ruan.usersystem.entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    String msg;
    int code;
    Map<String, Object> result= new HashMap();

    public Map getResult() {
        return result;
    }

    public void setResult(Map result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void getUserList(int code, List result, String msg) {
        this.code = code;
        this.result.put("data",result);
        this.msg = msg;
    }

    public void getTokenResult(String token, User userObject) {
        String name = userObject.getName();
        String account = userObject.getAccount();
        this.result.put("token",token);
        this.result.put("name", name);
        this.result.put("account", account);
    }


}
