package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/counterparty_contracts")
public class CounterpartyContractController {

    private final CounterpartyContractService counterpartyContractService;

    @Autowired
    public CounterpartyContractController(CounterpartyContractService counterpartyContractService) {
        this.counterpartyContractService = counterpartyContractService;
    }

    @GetMapping(path = "/contract_id={id}", produces = {"application/json"})
    public ResponseEntity<List<CounterpartyContractDTO>> showContractsById(@PathVariable("id") Long id) {
        List<CounterpartyContractDTO> list = counterpartyContractService.getCounterpartyContractsByContractId(id);
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Validated({CounterpartyContractDTO.New.class}) CounterpartyContractDTO dto) {
        boolean status = counterpartyContractService.addCounterpartyContract(dto);
        return new ResponseEntity<>(status ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateContract(@RequestBody @Validated(CounterpartyContractDTO.Modify.class)  CounterpartyContractDTO dto) {
        boolean status = counterpartyContractService.updateContract(dto);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id) {
        boolean status = counterpartyContractService.deleteContract(id);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }
}
