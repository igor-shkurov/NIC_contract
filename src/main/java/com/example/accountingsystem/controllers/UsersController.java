package com.example.accountingsystem.controllers;


import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.entities.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final CustomUserDetailsService userService;

    @Autowired
    public UsersController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "")
    public List<UserDTO> showUsers(HttpServletResponse response) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return userService.getUsers();
    }

//    @GetMapping(path = "/current")
//    public UserDTO showMe(HttpServletResponse response) {
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
//        return userService.getCurrentUser();
//    }

    @PostMapping(path = "")
    public void addUser(HttpServletResponse response, UserDTO dto) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        userService.saveUser(dto);
    }

    @PutMapping(path = "/update")
    public void updateUser(HttpServletResponse response, UserDTO dto) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        userService.saveUser(dto);
    }

    @DeleteMapping(path = "/delete")
    public void deleteUser(HttpServletResponse response, @RequestParam long id) {
        userService.deleteUser(id);
    }
}
