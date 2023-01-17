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

    public boolean addCounterpartyContract(CounterpartyContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(dto.getContractId());
        if (!Objects.equals(contract.getAssociatedUser().getId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return false;
        }
        CounterpartyContract counterpartyContract = mapper.DTOtoCounterpartyContract(dto);
        counterpartyContract.setContract(contract);
        counterpartyContract.setCounterparty(counterpartyService.getCounterpartyById(dto.getCounterpartyId()));
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

    public boolean updateContract(CounterpartyContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        CounterpartyContract updatingContract = mapper.DTOtoCounterpartyContract(dto);
        updatingContract.setContract(contractService.getContractById(dto.getContractId()));
        updatingContract.setCounterparty(counterpartyService.getCounterpartyById(dto.getCounterpartyId()));

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

    public void deleteContract(long id) {
        counterpartyContractRepo.deleteById(id);
    }

}
