package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractDTO;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping(path = "/user_id={user_id}")
    public List<ContractDTO> showContractsForUser(HttpServletResponse response, @PathVariable("user_id") Long userId) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        List<ContractDTO> list = contractService.getContractsForUser(userId);
        if (list == null) {
            response.setStatus(404);
        }
        return list;
    }

    @PostMapping(path = "", consumes = {"application/json"}) //postman
    public void addContract(@RequestBody ContractDTO dto, HttpServletResponse response) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.addContract(dto);
    }

    @GetMapping(path = "/contract_id={contract_id}")
    public ContractDTO showContractById(HttpServletResponse response, @PathVariable("contract_id") Long contractId) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContractDtoById(contractId);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateContract(@RequestBody ContractDTO dto, HttpServletResponse response) {
        //response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        contractService.updateContract(dto);
    }
}
