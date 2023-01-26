package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.entities.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Slf4j
@Configuration
public class SystemConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

            UserDTO administrator = new UserDTO();
            administrator.setFIO("Shkurov Igor Olegovich");
            administrator.setUsername("admin");
            administrator.setPassword("root");
            administrator.setRole(User.Role.ADMIN);
            administrator.setExpirationDate(LocalDateTime.now().plusMonths(6));

            userDetailsService.saveUser(administrator);

            UserDTO user = new UserDTO();
            user.setFIO("Pyatizbyantsev Ilya Andreevich");
            user.setUsername("aboba");
            user.setPassword("snusik");
            user.setRole(User.Role.USER);
            user.setExpirationDate(LocalDateTime.now().plusMonths(6));

            userDetailsService.saveUser(user);
        };
    }
}
