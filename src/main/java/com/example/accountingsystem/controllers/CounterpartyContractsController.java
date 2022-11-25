package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contract_counterparties")
public class CounterpartyContractsController {

    private final CounterpartyContractService counterpartyContractService;

    private final ContractService contractService;

    @Autowired
    public CounterpartyContractsController(CounterpartyContractService counterpartyContractService, ContractService contractService) {
        this.counterpartyContractService = counterpartyContractService;
        this.contractService = contractService;
    }

    @GetMapping(path = "/{id}")
    public List<CounterpartyContract> showCounterpartyContractById(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(id));
    }

    @PostMapping(path = "/{id}")
    public List<CounterpartyContract> addContract(@RequestBody @Valid CounterpartyContract counterpartyContract,
                                                  @PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        counterpartyContract.setContract(contractService.getContractById(Long.parseLong(id)));
        counterpartyContractService.addCounterpartyContract(counterpartyContract);
        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(id));
    }

    @PutMapping(path = "/{id}/update", consumes = {"application/json"})
    public void updateContract(@RequestBody @Valid CounterpartyContract counterpartyContract,
                                                     @PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        counterpartyContractService.updateContract(Long.parseLong(id), counterpartyContract);
    }
}
