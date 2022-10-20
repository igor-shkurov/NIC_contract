package com.example.accountingsystem.configuration;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            User admiministrator = new User();
            admiministrator.setFIO("Shkurov Igor Olegovich");
            admiministrator.setUsername("admin");
            admiministrator.setPassword("root");
            admiministrator.setRole(User.Role.ADMIN);
//            admiministrator.setExpirationDate(LocalDateTime.MAX);

            userDetailsService.saveUser(admiministrator);
        };
    }
}
