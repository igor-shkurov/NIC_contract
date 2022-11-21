package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepo contractRepo;
    private final StageService stageService;
    private final CounterpartyContractService counterpartyContractService;

    @Autowired
    public ContractService(ContractRepo contractRepo, StageService stageService, CounterpartyContractService counterpartyContractService) {
        this.contractRepo = contractRepo;
        this.stageService = stageService;
        this.counterpartyContractService = counterpartyContractService;
    }

    public List<Contract> getContracts() {
        return contractRepo.findAll();
    }

    public List<Contract> getContractsByGivenPeriod(LocalDate beginDate, LocalDate endDate) {
        return contractRepo.getContractsByGivenPeriod(Date.valueOf(beginDate), Date.valueOf(endDate));
    }

    public void addContract(Contract contract) {
        contractRepo.save(contract);
    }

    public Contract getContractById(Long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<Contract> getContractsByUserId(Long id) {
        return contractRepo.getContractsByUserId(id);
    }

    public void deleteById(Long id) {
        counterpartyContractService.deleteCounterpartyContractByContractId(id);
        stageService.getStageByContractId(id);
        contractRepo.deleteById(id);
    }

    //возможны проблемы с удалением связаннных контрактов
    public void deleteContractsByUserId(Long userId) {
        List<Contract> userContracts = getContractsByUserId(userId);

        for (Contract contract : userContracts) {
            deleteById(contract.getId());

        }
    }

    //не проверяет корректность. Если пользователя, который привязан к контракту нет, то создаёт этого пользователя
    public void saveContract(Contract contract) {
        contractRepo.save(contract);
    }
}
