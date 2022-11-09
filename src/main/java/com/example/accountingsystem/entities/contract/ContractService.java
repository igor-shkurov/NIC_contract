package com.example.accountingsystem.entities.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepo contractRepo;

    @Autowired
    public ContractService(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
    }

    public List<Contract> getContracts() {
        return contractRepo.findAll();
    }

    public void addContract(Contract contract) {
        contractRepo.save(contract);
    }

    public Contract getContractById(Long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        return opt.orElse(null);
    }
}
