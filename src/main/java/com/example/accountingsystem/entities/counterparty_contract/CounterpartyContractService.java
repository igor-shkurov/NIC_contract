package com.example.accountingsystem.entities.counterparty_contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterpartyContractService {
    private final CounterpartyContractRepo counterpartyContractRepo;

    @Autowired
    public CounterpartyContractService(CounterpartyContractRepo counterpartyContractRepo) {
        this.counterpartyContractRepo = counterpartyContractRepo;
    }

    public List<CounterpartyContract> getContracts() {
        return counterpartyContractRepo.findAll();
    }

    public List<CounterpartyContract> getCounterpartyContractsByContractId(Long id) {
        return counterpartyContractRepo.getCounterpartyContractsByContractId(id);
    }
}
