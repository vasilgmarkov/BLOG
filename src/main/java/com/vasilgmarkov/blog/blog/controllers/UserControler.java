package com.vasilgmarkov.blog.blog.controllers;


import com.vasilgmarkov.blog.blog.entities.Role;
import com.vasilgmarkov.blog.blog.entities.User;
import com.vasilgmarkov.blog.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserControler {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping(value = "/register")
    public String register(@RequestBody UserRegistration userRegistration){
        if(!userRegistration.getPassword().equals(userRegistration.getPasswordConfirmation())){
            return "Password do not match";
        }else if(userService.getUser(userRegistration.getUsername())!= null){
            return "User already exists";
            //input sanitization
        }
        System.out.println(userService.getUser(userRegistration.getUsername())!= null);
        System.out.println(userRegistration.getUsername());
        System.out.println(userService.getUser(userRegistration.getUsername()));
        userService.save(new User(userRegistration.getUsername(), userRegistration.getPassword(), Arrays.asList(new Role("USER"))));

        return "User created!";

    }

    @GetMapping(value = "/checkusers")
    public List<User> users(){
       return userService.getAllUsers();
    }

        @GetMapping(value = "/logouts")
    public void logout(@RequestParam (value = "access_token") String accessToken, @RequestParam (value = "refresh_token") String refreshToken){
        tokenStore.removeAccessToken(tokenStore.readAccessToken(accessToken));
        tokenStore.removeRefreshToken(tokenStore.readRefreshToken(refreshToken));
    }

    @GetMapping(value ="/getUsername")
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
