package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
    public List<Contract> addContract(@RequestBody @Valid Contract contract) {
        contractService.addContract(contract);

        return contractService.getContracts();
    }

    @GetMapping(path = "/{id}")
    public Contract showContractsById(@PathVariable("id") String contractId) {
        return contractService.getContractById(Long.parseLong(contractId));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public Contract patchContract(@PathVariable("id") String contractId, @RequestBody JsonPatch patch) {
        Contract contract = contractService.getContractById(Long.parseLong(contractId));
        try {
            Contract patchedContract = contractService.applyPatch(patch, contract);
        } catch (JsonPatchException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
