package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CounterpartyContractService {
    private final CounterpartyContractRepo counterpartyContractRepo;
    private final ContractService contractService;
    private final CounterpartyService counterpartyService;

    private final CounterpartyContractMapper mapper;

    @Autowired
    public CounterpartyContractService(CounterpartyContractRepo counterpartyContractRepo, ContractService contractService, CounterpartyService counterpartyService) {
        this.counterpartyContractRepo = counterpartyContractRepo;
        this.contractService = contractService;
        this.counterpartyService = counterpartyService;
        this.mapper = Mappers.getMapper(CounterpartyContractMapper.class);
    }

    public List<CounterpartyContract> getContracts() {
        return counterpartyContractRepo.findAll();
    }

    public void addCounterpartyContract(CounterpartyContractDTO dto) {
        CounterpartyContract counterpartyContract = mapper.DTOtoCounterpartyContract(dto);
        counterpartyContract.setContract(contractService.getContractById(dto.contract_id));
        counterpartyContract.setCounterparty(counterpartyService.getCounterpartyById(dto.counterparty_id));
        counterpartyContractRepo.save(counterpartyContract);
    }

    public CounterpartyContract getCounterpartyContractById(long id) {
        Optional<CounterpartyContract> opt = counterpartyContractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<CounterpartyContractDTO> getCounterpartyContractsByContractId(Long id) {
        List<CounterpartyContract> entities = counterpartyContractRepo.getCounterpartyContractsByContractId(id);
        return mapper.toListOfDTO(entities);
    }

    public void updateContract(CounterpartyContractDTO dto) {
        long id = dto.id;
        CounterpartyContract updatingContract = mapper.DTOtoCounterpartyContract(dto);
        updatingContract.setContract(contractService.getContractById(dto.contract_id));
        updatingContract.setCounterparty(counterpartyService.getCounterpartyById(dto.counterparty_id));
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
