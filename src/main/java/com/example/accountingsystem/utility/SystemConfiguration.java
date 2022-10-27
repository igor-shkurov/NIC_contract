package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Configuration
public class SystemConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

            User administrator = new User();
            administrator.setFIO("Shkurov Igor Olegovich");
            administrator.setUsername("admin");
            administrator.setPassword(cryptPasswordEncoder.encode("root"));
            administrator.setRole(User.Role.ADMIN);

            userDetailsService.saveUser(administrator);

            User user = new User();
            user.setFIO("Pyatizbyantsev Ilya Andreevich");
            user.setUsername("aboba");
            user.setPassword(cryptPasswordEncoder.encode("snusik"));
            user.setRole(User.Role.USER);
            userDetailsService.saveUser(user);
        };
    }
}
