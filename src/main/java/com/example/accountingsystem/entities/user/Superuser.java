package com.example.accountingsystem.entities.user;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

public class Superuser extends User {
    @Transient
    public static final Superuser INSTANCE = new Superuser();

    public Superuser getINSTANCE() {
        return INSTANCE;
    }

    private Superuser() {
        super("Shkurov Igor Olegovich", "admin", new BCryptPasswordEncoder().encode("root"));
        this.role = Role.HOST;
    }



    @Override
    public void setRole(Role role) {
        throw new AccessDeniedException("Can't set host's Role!");
    }

}
