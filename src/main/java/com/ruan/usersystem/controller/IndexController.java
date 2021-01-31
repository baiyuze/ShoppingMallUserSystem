package com.ruan.usersystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    //RestController responseBody和controller的结合
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
