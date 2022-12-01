package com.example.accountingsystem.entities.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
         if (userDetailsRepo.findUserByUsername(user.getUsername()) != null)   {
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
//            log.error("User not found");
            throw new UsernameNotFoundException("User not found in db");
        }
        else {
//            log.info("User found");
        }
        return user;
    }

    public User getUserById(long id) {
        Optional<User> opt = userDetailsRepo.findById(id);
        return opt.orElse(null);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return (User) loadUserByUsername(currentUserName);
        }
        throw new UsernameNotFoundException("No one is logged in");
    }

    public List<User> getUsers() {
        return userDetailsRepo.findAll();
    }
}
