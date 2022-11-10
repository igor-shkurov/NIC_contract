package com.example.accountingsystem.entities.counterparty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterpartyService {
    private final CounterpartyRepo counterpartyRepo;

    @Autowired
    public CounterpartyService(CounterpartyRepo counterpartyRepo) {
        this.counterpartyRepo = counterpartyRepo;
    }

    public List<Counterparty> getCounterparties() {
        return counterpartyRepo.findAll();
    }
}
