package com.example.accountingsystem.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.accountingsystem.security.jwt.JWTUtils;
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

import static com.example.accountingsystem.security.jwt.AlgorithmBuilder.algorithmInstance;
import static com.example.accountingsystem.security.jwt.JWTUtils.JWT_ACCESS_DURATION;
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

        List<Contract> list = contractService.getContracts();
        ContractExcelExporter exporter = new ContractExcelExporter(list);
        try {
            exporter.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/users")
    public List<User> showUsers() {
        return userService.getUsers();
    }
}
