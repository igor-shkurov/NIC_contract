package com.example.accountingsystem.entities.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public CustomUserDetailsService(UserDetailsRepo userRepo) {
        this.userDetailsRepo = userRepo;
    }

    public boolean saveUser(User user) {
         if (userDetailsRepo.existsByUsername(user.getUsername()))   {
             return false;
         }
         else {
             userDetailsRepo.save(user);
             return true;
         }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepo.findUserByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in db");
        }
        else {
            log.info("User found");
        }
        return user;
    }

    public User getUser(String username) {
        User user = userDetailsRepo.findUserByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in db");
        }
        else {
            log.info("User found");
        }
        return user;
    }

    public List<User> getUsers() {
        return userDetailsRepo.findAll();
    }

}
