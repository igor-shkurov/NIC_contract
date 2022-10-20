package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(path = "/contracts")
    public List<Contract> testPage() {
        return contractService.getContracts();
    }

    @PostMapping(path = "/contracts")
    public List<Contract> addContract(@RequestBody Contract contract) {
        contractService.addContract(contract);
        return contractService.getContracts();
    }
}
