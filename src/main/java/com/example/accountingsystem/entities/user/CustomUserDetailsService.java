package com.example.accountingsystem.entities.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public CustomUserDetailsService(UserDetailsRepo userRepo) {
        this.userDetailsRepo = userRepo;
    }

    public boolean saveUser(User user) {
         if (userDetailsRepo.findUserByUsername(user.getUsername()).isPresent()) {
             return false;
         }
         else {
             userDetailsRepo.save(user);
             return true;
         }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt = userDetailsRepo.findUserByUsername(username);
        return opt.get(); // @todo: Сюда исключение или что-то еще
    }
}
