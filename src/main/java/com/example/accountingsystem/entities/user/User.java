package com.example.accountingsystem.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table
@JsonPropertyOrder({"id", "fio", "username", "password"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "varchar(60)")
    private String FIO;
    @Column(columnDefinition = "varchar(20)")
    private String username;
    @Column(columnDefinition = "varchar(255)")
    private String password;
    @Column(columnDefinition = "datetime")
    private LocalDateTime expirationDate;

    public enum Role { // @todo: Подумать как лучше можно хранить роли (или и так норм)
        USER, ADMIN;
    }

    @Enumerated(EnumType.STRING)
    public Role role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @JsonIgnore
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    @JsonIgnore
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /* todo: Подумать как булевские методы должны работать (осмысленно)  */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return expirationDate.isAfter(LocalDateTime.now());
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));
    }

    @JsonIgnore
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
