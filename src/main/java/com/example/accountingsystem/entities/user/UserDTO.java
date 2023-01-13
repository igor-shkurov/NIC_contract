package com.example.accountingsystem.entities.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDTO {
    public interface New {}
    public interface Modify {}

    @Null(groups = {New.class})
    private long id;

    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 4, max = 40, groups = {New.class, Modify.class})
    private String FIO;

    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 3, max = 50, groups = {New.class, Modify.class})
    private String username;

    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 3, max = 50, groups = {New.class, Modify.class})
    private String password;

    private LocalDateTime expirationDate;
    private User.Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
