package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractDTO;
import com.example.accountingsystem.entities.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @GetMapping(path = "")
    public List<ContractDTO> showContractsForUser(HttpServletResponse response) {
        List<ContractDTO> list = contractService.getContractsForUser();
        if (list == null) {
            response.setStatus(404);
        }
        return list;
    }

    @GetMapping(path = "/contract_id={id}")
    public ResponseEntity<ContractDTO> showContractById(@PathVariable("id") Long id) {
        ContractDTO dto = contractService.getContractDtoById(id);
        return new ResponseEntity<>(dto, (dto == null) ? HttpStatus.NOT_FOUND : HttpStatus.OK); // @todo: change all HttpServletResponse parameters to ResponseEntity<> return val
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public void addContract(@RequestBody ContractDTO dto, HttpServletResponse response) {
        if (!contractService.addContract(dto)) {
            response.setStatus(403);
        }
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateContract(@RequestBody ContractDTO dto, HttpServletResponse response) {
        if (!contractService.updateContract(dto)) {
            response.setStatus(403);
        }
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public void deleteContract(@PathVariable("id") Long id, HttpServletResponse response) {
        if (!contractService.deleteContract(id)) {
            response.setStatus(404);
        }
    }
}
