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
@RequestMapping("/api")
public class CounterpartyContractsController {

    private final CounterpartyContractService counterpartyContractService;

    private final ContractService contractService;

    @Autowired
    public CounterpartyContractsController(CounterpartyContractService counterpartyContractService, ContractService contractService) {
        this.counterpartyContractService = counterpartyContractService;
        this.contractService = contractService;
    }

    @GetMapping(path = "/contract_counterparties/{id}")
    public List<CounterpartyContract> showCounterpartyContractById(@PathVariable("id") String contractId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(contractId));
    }

    //не проверено/ Почему передаётся только айди контракта без айди контрагента
    @PostMapping(path = "/contract_counterparties/{id}")
    public List<CounterpartyContract> addContract(@RequestBody @Valid CounterpartyContract counterpartyContract, @PathVariable("id") String contractId) {
        //нужно?
        counterpartyContract.setContract(contractService.getContractById(Long.parseLong(contractId)));
        counterpartyContractService.addCounterpartyContract(counterpartyContract);
        return counterpartyContractService.getContracts();
    }
}
