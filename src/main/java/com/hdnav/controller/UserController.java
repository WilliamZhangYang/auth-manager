package com.hdnav.controller;

import com.hdnav.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping("/login")
    public String login(){
        return "login,OK";
    }

    @GetMapping("/register")
    public String register(User user){
        if (user != null){
            logger.info("get user success!");
        }
        return "register,OK";
    }

    @PostMapping("/getToken")
    public String getToken(@RequestParam("userName") String userName, @RequestParam("phone") String phone){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName",userName);
        map.put("phone",phone);
        String token = "1";
        return token;
    }

    @GetMapping("/test")
    public String test(){

        return "test,OK";
    }
}
