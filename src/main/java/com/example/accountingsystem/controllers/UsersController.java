package com.example.accountingsystem.controllers;


import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {


    private final CustomUserDetailsService userService;

    @Autowired
    public UsersController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public List<User> showUsers(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return userService.getUsers();
    }

    @GetMapping(path = "/current_user")
    public User showMe(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return userService.getCurrentUser();
    }
}
