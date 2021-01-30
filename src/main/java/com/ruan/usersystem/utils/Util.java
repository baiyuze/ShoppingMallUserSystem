package com.ruan.usersystem.utils;

public class Util {
    /**
     * 赋值默认值
     * */
    public int AssignDefaultValue(Integer val, int defaultVal) {
        if(val != null) {
            return val;
        }
        return defaultVal;
    }

    public String AssignDefaultValue(String val, String defaultVal) {
        if(val != null) {
            return val;
        }
        return defaultVal;
    }
}
