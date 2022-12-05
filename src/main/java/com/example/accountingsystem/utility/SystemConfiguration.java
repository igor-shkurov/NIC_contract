package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.entities.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
@Configuration
public class SystemConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

            UserDTO administrator = new UserDTO();
            administrator.FIO = "Shkurov Igor Olegovich";
            administrator.username = "admin";
            administrator.password = cryptPasswordEncoder.encode("root");
            administrator.role = User.Role.ADMIN;
            administrator.expirationDate = LocalDateTime.now().plusMonths(6);

            userDetailsService.saveUser(administrator);

            UserDTO user = new UserDTO();
            user.FIO = "Pyatizbyantsev Ilya Andreevich";
            user.username = "aboba";
            user.password = cryptPasswordEncoder.encode("snusik");
            user.role = User.Role.USER;
            user.expirationDate = LocalDateTime.now().minusDays(6);

            userDetailsService.saveUser(user);
        };
    }
}
