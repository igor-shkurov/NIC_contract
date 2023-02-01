package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Document(collection = "login_history")
public class LoginRecord {
    @Id
    private String id;
    private String username;
    private String loginTime;
    private User.Role role;

    public static LoginRecord construct(User user) {
        ZoneId z = ZoneId.of("Europe/Moscow");
        LoginRecord record = new LoginRecord();
        record.setLoginTime(ZonedDateTime.now(z).toString());
        record.setRole(user.getRole());
        record.setUsername(user.getUsername());
        return record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
