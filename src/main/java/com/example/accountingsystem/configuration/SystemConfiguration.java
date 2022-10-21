package com.example.accountingsystem.configuration;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class SystemConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            User administrator = new User();
            administrator.setFIO("Shkurov Igor Olegovich");
            administrator.setUsername("admin");
            BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
            administrator.setPassword(cryptPasswordEncoder.encode("root"));
            administrator.setRole(User.Role.ADMIN);
//            administrator.setExpirationDate(LocalDateTime.MAX);

            userDetailsService.saveUser(administrator);
        };
    }
}
