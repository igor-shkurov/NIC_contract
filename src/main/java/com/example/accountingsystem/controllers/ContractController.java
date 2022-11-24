package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(path = "/contracts")
    public List<Contract> showContracts(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContracts();
    }

    @PostMapping(path = "/contracts") //postman
    public List<Contract> addContract(@RequestBody @Valid Contract contract) {
        contractService.addContract(contract);

        return contractService.getContracts();
    }

    @GetMapping(path = "/contracts/{id}")
    public Contract showContractsById(@PathVariable("id") String contractId) {
        return contractService.getContractById(Long.parseLong(contractId));
    }

    @PatchMapping(path = "/contracts/{id}")
    public Contract patchContract(@PathVariable("id") String contractId) {
        return contractService.getContractById(Long.parseLong(contractId));
    }
}
