package com.example.accountingsystem.entities.counterparty;

import com.example.accountingsystem.entities.contract.Contract;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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

    public Counterparty getCounterpartyById(long id) {
        return counterpartyRepo.findById(id).orElse(null);
    }

    public void updateCounterparty(long id, Counterparty updatingCp) {
        Counterparty cpToBeUpdated = getCounterpartyById(id);
        if (cpToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(cpToBeUpdated, updatingCp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            cpToBeUpdated.setId(id);
            counterpartyRepo.save(cpToBeUpdated);
        }
        else {
            counterpartyRepo.save(updatingCp);
        }
    }
}
