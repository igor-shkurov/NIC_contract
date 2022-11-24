package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.ContractType;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.Superuser;
import com.example.accountingsystem.entities.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Slf4j
@Configuration
public class SystemConfiguration {
    @Bean
    CommandLineRunner commandLineRunner(CustomUserDetailsService userDetailsService, ContractService contractService) {
        return args -> {
            BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();


            User administrator =  Superuser.INSTANCE;
            userDetailsService.saveUser(administrator);


            User user = new User();
            user.setFIO("Pyatizbyantsev Ilya Andreevich");
            user.setUsername("aboba");
            user.setPassword(cryptPasswordEncoder.encode("snusik"));
            user.setRole(User.Role.USER);
            userDetailsService.saveUser(user);


            User user1 = new User();
            user1.setFIO("Pyatizbyantsev Ilya Andreevich");
            user1.setUsername("aboba2");
            user1.setPassword(cryptPasswordEncoder.encode("snusik"));
            user1.setRole(User.Role.USER);
            userDetailsService.saveUser(user1);

            Contract contract = new Contract("String name", ContractType.WORK, LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-10"),
                    LocalDate.parse("2022-10-10"), LocalDate.parse("2022-10-10"), 200L);

            contract.setAssociatedUser(user1);
            contract.setId(10L);
            // contractService.saveContract(contract);

            System.out.println(Superuser.INSTANCE.getId());
        };
    }
}
