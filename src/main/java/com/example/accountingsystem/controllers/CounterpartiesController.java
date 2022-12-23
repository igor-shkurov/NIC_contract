package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.counterparty.CounterpartyDTO;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/counterparties")
public class CounterpartiesController {

    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartiesController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @GetMapping(path = "")
    public List<CounterpartyDTO> showCounterparties() {
        return counterpartyService.getCounterparties();
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public void addCounterparty(@RequestBody CounterpartyDTO dto, HttpServletResponse response) {
        counterpartyService.addCounterparty(dto);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateCounterparty(@RequestBody CounterpartyDTO counterpartyContract, HttpServletResponse response) {
        counterpartyService.updateCounterparty(counterpartyContract);
    }

    @DeleteMapping(path = "/delete/counterparty_id={id}")
    public void deleteCounterparty(@PathVariable Long id, HttpServletResponse response) {
        counterpartyService.deleteCounterparty(id);
    }
}
