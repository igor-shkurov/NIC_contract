package com.example.accountingsystem.entities.counterparty_contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterpartyContractService {
    private final CounterpartyContractRepo counterpartyContractRepo;

    @Autowired
    public CounterpartyContractService(CounterpartyContractRepo counterpartyContractRepo) {
        this.counterpartyContractRepo = counterpartyContractRepo;
    }
}
