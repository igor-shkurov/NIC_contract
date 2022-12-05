package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.counterparty.Counterparty;
import com.example.accountingsystem.entities.counterparty.CounterpartyDTO;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/counterparties")
public class CounterpartiesController {

    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartiesController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
        HiddenHttpMethodFilter filter;
    }

    @GetMapping(path = "")
    public List<CounterpartyDTO> showCounterparties(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return counterpartyService.getCounterparties();
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateCounterparty(@RequestBody CounterpartyDTO counterpartyContract, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        counterpartyService.updateCounterparty(counterpartyContract);
    }
}
