package com.example.accountingsystem.entities.user;

import java.time.LocalDateTime;

public class UserDTO {
    public long id;
    public String FIO;
    public String username;
    public String password;
    public LocalDateTime expirationDate;
    public User.Role role;
}
