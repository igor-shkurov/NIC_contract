package com.example.accountingsystem.entities.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDTO {
    @Null
    public long id;
    @NotBlank
    @Size(min = 4, max = 40)
    public String FIO;
    @NotBlank
    @Size(min = 3, max = 50)
    public String username;
    @NotBlank
    @Size(min = 3, max = 50)
    public String password;
    public LocalDateTime expirationDate;
    public User.Role role;
}
