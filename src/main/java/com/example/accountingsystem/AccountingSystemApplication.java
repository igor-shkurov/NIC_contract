package com.example.accountingsystem;

import com.example.accountingsystem.utility.LoginHistoryRecorder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;

@SpringBootApplication
public class AccountingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingSystemApplication.class, args);
    }

    @PreDestroy
    public void onExit() {
    }
}
