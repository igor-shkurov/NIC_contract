package com.example.accountingsystem.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.accountingsystem.configuration.AlgorithmBuilder;
import com.example.accountingsystem.configuration.JWTUtils;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.utility.ContractExcelExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final ContractService contractService;
    private final CustomUserDetailsService userService;

    @Autowired
    public ContractController(ContractService contractService, CustomUserDetailsService userService) {
        this.contractService = contractService;
        this.userService = userService;
    }

    @GetMapping(path = "/contracts")
    public List<Contract> showContracts() {
        return contractService.getContracts();
    }

    @PostMapping(path = "/contracts")
    public List<Contract> addContract(@RequestBody Contract contract) {
        contractService.addContract(contract);
        return contractService.getContracts();
    }

    @GetMapping(path = "/contracts.xlsx")
    public void getFile(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerVal = "attachment; filename = contracts.xlsx";

        List<Contract> list = contractService.getContracts();
        ContractExcelExporter exporter = new ContractExcelExporter(list);
        try {
            exporter.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authHeader.substring("Bearer ".length());
                Algorithm algorithm = AlgorithmBuilder.algorithmInstance;
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                JWTUtils.writeTokensToJSON(response, access_token, refresh_token);
            }
            catch (Exception exception) {
                JWTUtils.writeExceptionToJSON(response, exception);
            }
        }
        else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @GetMapping(path = "/users")
    public List<User> showUsers() {
        return userService.getUsers();
    }
}
