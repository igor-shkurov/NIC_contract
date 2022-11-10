package com.example.accountingsystem.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AlgorithmBuilder {
    private static Environment env;
    public static Algorithm algorithmInstance;

    @Autowired
    public void setEnvironment(Environment env) {
        AlgorithmBuilder.env = env;
    }

    @Bean
    public static void buildAlgorithm() {
        String secret = env.getProperty("jwt-secret");
        algorithmInstance = Algorithm.HMAC256(Objects.requireNonNull(secret).getBytes());
    }
}
