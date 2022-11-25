package com.example.accountingsystem.entities.counterparty_contract;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("DuplicatedCode")
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

    public void addCounterpartyContract(CounterpartyContract counterpartyContract) {
        counterpartyContractRepo.save(counterpartyContract);
    }

    public CounterpartyContract getCounterpartyContractById(long id) {
        Optional<CounterpartyContract> opt = counterpartyContractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<CounterpartyContract> getCounterpartyContractsByContractId(Long id) {
        return counterpartyContractRepo.getCounterpartyContractsByContractId(id);
    }

    public void updateContract(long id, CounterpartyContract updatingContract) {
        CounterpartyContract contractToBeUpdated = getCounterpartyContractById(id);
        if (contractToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(contractToBeUpdated, updatingContract);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            contractToBeUpdated.setId(id);
            counterpartyContractRepo.save(contractToBeUpdated);
        }
        else {
            counterpartyContractRepo.save(updatingContract);
        }
    }

}
