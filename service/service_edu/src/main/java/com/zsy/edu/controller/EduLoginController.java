package com.zsy.edu.controller;

import com.zsy.commonutils.ResModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/edulogin/user")
public class EduLoginController {

    @PostMapping("login")
    public ResModel login(){
        return ResModel.success().data("token","admin");
    }

    @GetMapping("info")
    public ResModel info(){
        Map<String,Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("name","werner");
        map.put("avatar","https://picsum.photos/200/200");
        return ResModel.success().data(map);
    }
}
