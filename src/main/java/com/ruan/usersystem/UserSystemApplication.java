package com.ruan.usersystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserSystemApplication {
    public static void main(String[] args) {
        System.out.println(args);
        SpringApplication.run(UserSystemApplication.class, args);
    }

}
