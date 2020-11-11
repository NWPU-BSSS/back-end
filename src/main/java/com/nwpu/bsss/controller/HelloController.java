package com.nwpu.bsss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
