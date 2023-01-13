package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.contract.ContractDTO;
import com.example.accountingsystem.entities.contract.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping(path = "", produces = {"application/json"})
    public ResponseEntity<List<ContractDTO>> showContractsForUser() {
        List<ContractDTO> list = contractService.getContractsForUser();
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/contract_id={id}", produces = {"application/json"})
    public ResponseEntity<ContractDTO> showContractById(@PathVariable("id") Long id) {
        ContractDTO dto = contractService.getContractDtoById(id);
        return new ResponseEntity<>(dto, (dto != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Validated({ExportableContract.New.class}) ContractDTO dto) {
        boolean status = contractService.addContract(dto);
        return new ResponseEntity<>(status ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateContract(@RequestBody @Validated({ExportableContract.Modify.class}) ContractDTO dto) {
        boolean status = contractService.updateContract(dto);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public ResponseEntity<Object> deleteContract(@PathVariable("id") Long id) {
        boolean status = contractService.deleteContract(id);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }
}
