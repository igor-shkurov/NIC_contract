package com.example.accountingsystem.controllers;


import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.counterparty.Counterparty;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CounterpartiesController {

    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartiesController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @GetMapping(path = "/counterparties")
    public List<Counterparty> showCounterpartyContractById() {
        return counterpartyService.getCounterparties();
    }
}
