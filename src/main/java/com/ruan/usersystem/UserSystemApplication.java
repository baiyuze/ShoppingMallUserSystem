package com.ruan.usersystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserSystemApplication {


    public static void main(String[] args) {
//        添加环境变量
        String relativelyPath=System.getProperty("user.dir");
        System.setProperty("project", relativelyPath);
        SpringApplication.run(UserSystemApplication.class, args);
    }

}
