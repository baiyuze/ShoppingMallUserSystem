package com.example.demo1.entity;
import java.util.List;

public class Response {
    String msg;
    int code;
    Object result;

    public Object getResult() {
        return result;
    }

    public void setResult(String result) {
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

    public void getUserList(int code, Object result, String msg) {
        this.code = code;
        this.result = result;
        this.msg = msg;
    }


}
