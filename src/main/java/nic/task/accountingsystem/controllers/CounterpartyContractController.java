package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import nic.task.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Pair<List<CounterpartyContractDTO>, HttpStatus> pair = counterpartyContractService.getCounterpartyContractsByContractId(id);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Validated({CounterpartyContractDTO.New.class}) CounterpartyContractDTO dto) {
        return new ResponseEntity<>(counterpartyContractService.addCounterpartyContract(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateContract(@RequestBody @Validated(CounterpartyContractDTO.Modify.class)  CounterpartyContractDTO dto) {
        return new ResponseEntity<>(counterpartyContractService.updateContract(dto));
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id) {
        return new ResponseEntity<>(counterpartyContractService.deleteContract(id));
    }
}
