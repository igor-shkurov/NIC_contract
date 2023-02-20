package nic.task.accountingsystem.utility;

import lombok.extern.slf4j.Slf4j;
import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.User;
import nic.task.accountingsystem.entities.user.UserDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SystemConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService) {
        return args -> {
            UserDTO administrator = new UserDTO();
            administrator.setFIO("Shkurov Igor Olegovich");
            administrator.setUsername("admin");
            administrator.setPassword("root");
            administrator.setRole(User.Role.ADMIN);

            userDetailsService.saveUser(administrator, false);
        };
    }
}
