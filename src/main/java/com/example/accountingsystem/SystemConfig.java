package com.example.accountingsystem;

import com.example.accountingsystem.entities.contract.ContractService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemConfig {

    @Bean
    CommandLineRunner commandLineRunner(ContractService contractService) {
        return args -> {

        };
    }
}
