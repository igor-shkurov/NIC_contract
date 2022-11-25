package com.example.accountingsystem.entities.contract;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.time.LocalDate;
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

    public List<Contract> getContractsByGivenPeriod(LocalDate beginDate, LocalDate endDate) {
        return contractRepo.getContractsByGivenPeriod(Date.valueOf(beginDate), Date.valueOf(endDate));
    }

    public void updateContract(long id, Contract updatingContract) {
        Contract contractToBeUpdated = getContractById(id);
        if (contractToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(contractToBeUpdated, updatingContract);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            contractToBeUpdated.setId(id);
            contractRepo.save(contractToBeUpdated);
        }
        else {
            contractRepo.save(updatingContract);
        }
    }
}
