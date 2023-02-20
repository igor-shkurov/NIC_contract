package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.contract.ContractDTO;
import nic.task.accountingsystem.entities.contract.ContractService;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Pair<List<ContractDTO>, HttpStatus> pair = contractService.getContractsForUser();
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping(path = "/contract_id={id}", produces = {"application/json"})
    public ResponseEntity<ContractDTO> showContractById(@PathVariable("id") Long id) {
        Pair<ContractDTO, HttpStatus> pair = contractService.getContractDtoById(id);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<HttpStatus> addContract(@RequestBody @Validated({ContractDTO.New.class}) ContractDTO dto) {
        return new ResponseEntity<>(contractService.addContract(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<HttpStatus> updateContract(@RequestBody @Validated({ContractDTO.Modify.class}) ContractDTO dto) {
        return new ResponseEntity<>(contractService.updateContract(dto));
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public ResponseEntity<HttpStatus> deleteContract(@PathVariable("id") Long id) {
        return new ResponseEntity<>(contractService.deleteContract(id));
    }
}
