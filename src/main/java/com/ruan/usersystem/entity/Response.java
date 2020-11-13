package com.ruan.usersystem.entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    String msg;
    int code;
    Map result= new HashMap();

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

    public void getUserList(int code, Map result, String msg) {
        this.code = code;
        this.result = result;
        this.msg = msg;
    }

    public void getTokenResult(String token) {
        System.out.println(token);
        this.result.put("token",token);
    }


}
