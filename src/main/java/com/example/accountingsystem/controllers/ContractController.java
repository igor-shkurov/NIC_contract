package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractDTO;
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
    public List<ContractDTO> showContracts(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContracts();
    }

    @PostMapping(path = "", consumes = {"application/json"}) //postman
    public void addContract(@RequestBody @Valid Contract contract, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.addContract(contract);
    }

    @GetMapping(path = "/{id}")
    public ContractDTO showContractById(@PathVariable("id") String pathId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContractDtoById(Long.parseLong(pathId));
    }

    @PutMapping(path = "/{id}/update", consumes = {"application/json"})
    public void updateContract(@RequestBody ContractDTO dto, @PathVariable("id") String pathId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.updateContract(dto);
    }
}
