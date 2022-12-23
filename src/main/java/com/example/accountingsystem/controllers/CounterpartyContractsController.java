package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/contract_counterparties")
public class CounterpartyContractsController {

    private final CounterpartyContractService counterpartyContractService;

    @Autowired
    public CounterpartyContractsController(CounterpartyContractService counterpartyContractService) {
        this.counterpartyContractService = counterpartyContractService;
    }

    @GetMapping(path = "/contract_id={id}")
    public List<CounterpartyContractDTO> showContractsById(@PathVariable("id") String pathId, HttpServletResponse response) {
        List<CounterpartyContractDTO> list = counterpartyContractService.getCounterpartyContractsByContractId(Long.parseLong(pathId));
        if (list == null) {
            response.setStatus(404);
        }
        return list;
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public void addContract(@RequestBody CounterpartyContractDTO dto, HttpServletResponse response) {
        if (counterpartyContractService.addCounterpartyContract(dto)) {
            response.setStatus(403);
        }
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateContract(@RequestBody CounterpartyContractDTO dto, HttpServletResponse response) {
        if (!counterpartyContractService.updateContract(dto)) {
            response.setStatus(403);
        }
    }

    @DeleteMapping(path = "/delete/contract_id={id}")
    public void deleteContract(@PathVariable Long id, HttpServletResponse response) {
        if (!counterpartyContractService.deleteContract(id)) {
            response.setStatus(404);
        }
    }
}
