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
    private final ContractService contractService;
    private final CounterpartyService counterpartyService;

    private final CounterpartyContractMapper mapper;

    @Autowired
    public CounterpartyContractsController(CounterpartyContractService counterpartyContractService, ContractService contractService, CounterpartyService counterpartyService) {
        this.counterpartyContractService = counterpartyContractService;
        this.contractService = contractService;
        this.counterpartyService = counterpartyService;

        this.mapper = Mappers.getMapper(CounterpartyContractMapper.class);
    }

    @GetMapping(path = "/{id}")
    public List<CounterpartyContract> showCounterpartyContractById(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(id));
    }

    @PostMapping(path = "/{id}", consumes = {"application/json"})
    public void addContract(@RequestBody @Valid CounterpartyContractDTO dto,
                                                  @PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        CounterpartyContract counterpartyContract = mapper.DTOtoCounterpartyContract(dto);
        counterpartyContract.setContract(contractService.getContractById(Long.parseLong(id)));
        counterpartyContract.setCounterparty(counterpartyService.getCounterpartyById(dto.counterparty_id));
        counterpartyContractService.addCounterpartyContract(counterpartyContract);
//        return counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(id));
    }

    @PutMapping(path = "/{id}/update", consumes = {"application/json"})
    public void updateContract(@RequestBody @Valid CounterpartyContract counterpartyContract,
                                                     @PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        counterpartyContractService.updateContract(Long.parseLong(id), counterpartyContract);
    }
}
