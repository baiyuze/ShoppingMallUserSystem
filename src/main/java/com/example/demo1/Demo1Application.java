package com.example.demo1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

//@RestController
@SpringBootApplication
public class Demo1Application {
    @Value("${name}")
    private String myName;
//    @RequestMapping(value="test", method = RequestMethod.GET)
//    String home(String name) {
//        Login val = new Login();
//        val.test();
//        return "<script>window.a = {a: \"+name+\"};alert('"+myName+"');</script>";
//    }
    public static void main(String[] args) {
        System.out.println("猪猪测试122121" ); // 打印 Hello World
        System.out.println(args); // 打印 Hello World
        SpringApplication.run(Demo1Application.class, args);
    }

}
