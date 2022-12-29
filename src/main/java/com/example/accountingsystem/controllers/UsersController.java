package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<UserDTO> list = userService.getUsers();
        if (list == null) {
            response.setStatus(403);
        }
        return list;
    }

    @PostMapping(path = "/add")
    public void addUser(UserDTO dto, HttpServletResponse response) {
        response.setStatus(userService.saveUser(dto) ? 200 : 409);

    }

    @PutMapping(path = "/update")
    public void updateUser(UserDTO dto, HttpServletResponse response) {
        if (!userService.updateUser(dto)) {
            response.setStatus(403);
        }
    }

    @DeleteMapping(path = "/delete/user_id={id}")
    public void deleteUser(@PathVariable("id") Long id, HttpServletResponse response) {
        if (!userService.deleteUser(id)) {
            response.setStatus(404);
        }
    }
}
