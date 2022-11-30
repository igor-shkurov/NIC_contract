package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractMapper;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/contract_counterparties")
public class CounterpartyContractsController {

    private final CounterpartyContractService counterpartyContractService;

    @Autowired
    public CounterpartyContractsController(CounterpartyContractService counterpartyContractService) {
        this.counterpartyContractService = counterpartyContractService;
    }

    @GetMapping(path = "/{id}")
    public List<CounterpartyContractDTO> showContractsById(@PathVariable("id") String pathId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(pathId));
    }

    @PostMapping(path = "/{id}", consumes = {"application/json"})
    public void addContract(@RequestBody @Valid CounterpartyContractDTO dto,
                                                     @PathVariable("id") String pathId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        dto.contract_id = Long.parseLong(pathId);
        counterpartyContractService.addCounterpartyContract(dto);
    }

    @PutMapping(path = "/{id}/update", consumes = {"application/json"})
    public void updateContract(@RequestBody @Valid CounterpartyContractDTO dto,
                                                     @PathVariable("id") String pathId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        dto.contract_id = Long.parseLong(pathId);
        counterpartyContractService.updateContract(dto);
    }
}
