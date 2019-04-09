package com.vasilgmarkov.blog.blog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserTestController {

    @GetMapping(value = "/free")
    public String free(){return "Hey you are guest";}

    @GetMapping(value = "/private")
    public String privateArea(){
        return "private area";
    }
}
