package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(path = "")
    public List<Contract> showContracts(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContracts();
    }

    @PostMapping(path = "") //postman
    public List<Contract> addContract(@RequestBody @Valid Contract contract, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.addContract(contract);
        return contractService.getContracts();
    }

    @GetMapping(path = "/{id}")
    public Contract showContractsById(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContractById(Long.parseLong(id));
    }

    @PutMapping(path = "/{id}/update", consumes = {"application/json"})
    public void updateContract(@RequestBody Contract contract, @PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.updateContract(Long.parseLong(id), contract);
    }
}
