package com.example.accountingsystem.entities.user;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "FIO should not be empty")
    @Size(max = 60, message = "Name should not be more then 60 letters")
    @Column(columnDefinition = "varchar(60)")
    //только буквы?
    private String FIO;
    @NotBlank(message = "FIO should not be empty")
    @Size(max = 20, message = "Login should not be more then 20 letters")
    @Column(columnDefinition = "varchar(20)")
    private String username;
    @Column(columnDefinition = "varchar(255)")
    private String password;
    @Column(columnDefinition = "datetime")
    private LocalDateTime expirationDate;

    public User() {

    }


    public enum Role { // @todo: Подумать как лучше можно хранить роли (или и так норм)
        USER, ADMIN, HOST;
    }

    @Enumerated(EnumType.STRING)
    protected Role role;

    private void notHostCheck() {
        if (role == Role.HOST) {
            throw new IllegalArgumentException("user can't be host!");
        }
    }


    public User(String FIO, String username, String password, LocalDateTime expirationDate, Role role) {
        this.FIO = FIO;
        this.username = username;
        this.password = password;
        this.expirationDate = expirationDate;
        this.role = role;
        notHostCheck();
    }

    public User(String FIO, String username, String password, Role role) {
        this.FIO = FIO;
        this.username = username;
        this.password = password;
        this.role = role;
        notHostCheck();
    }

    public User(String FIO, String username, String password) {
        this.FIO = FIO;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    // public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    /* todo: Подумать как булевские методы должны работать (осмысленно)  */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + getRole().toString()));
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        notHostCheck();
    }
}
