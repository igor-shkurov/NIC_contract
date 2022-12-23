package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CounterpartyContractService {
    private final CounterpartyContractRepo counterpartyContractRepo;
    private final ContractService contractService;
    private final CounterpartyService counterpartyService;
    private final CustomUserDetailsService userDetailsService;

    private final CounterpartyContractMapper mapper;

    @Autowired
    public CounterpartyContractService(CounterpartyContractRepo counterpartyContractRepo, ContractService contractService, CounterpartyService counterpartyService, CustomUserDetailsService userDetailsService) {
        this.counterpartyContractRepo = counterpartyContractRepo;
        this.contractService = contractService;
        this.counterpartyService = counterpartyService;
        this.userDetailsService = userDetailsService;
        this.mapper = Mappers.getMapper(CounterpartyContractMapper.class);
    }

    public List<CounterpartyContract> getContracts() {
        return counterpartyContractRepo.findAll();
    }

    public boolean addCounterpartyContract(CounterpartyContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(dto.contractId);
        if (!Objects.equals(contract.getAssociatedUser().getId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return false;
        }
        CounterpartyContract counterpartyContract = mapper.DTOtoCounterpartyContract(dto);
        counterpartyContract.setContract(contract);
        counterpartyContract.setCounterparty(counterpartyService.getCounterpartyById(dto.counterpartyId));

        counterpartyContractRepo.save(counterpartyContract);
        return true;
    }

    public CounterpartyContract getCounterpartyContractById(long id) {
        Optional<CounterpartyContract> opt = counterpartyContractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<CounterpartyContractDTO> getCounterpartyContractsByContractId(Long id) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(id);
        List<CounterpartyContract> entities = null;
        if (Objects.equals(currentUser.getId(), contract.getAssociatedUser().getId()) || currentUser.getRole() == User.Role.ADMIN) {
             entities = counterpartyContractRepo.getCounterpartyContractsByContractId(id);
        }
        return mapper.toListOfDTO(entities);
    }

    public boolean updateContract(CounterpartyContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.id;
        CounterpartyContract updatingContract = mapper.DTOtoCounterpartyContract(dto);
        updatingContract.setContract(contractService.getContractById(dto.contractId));
        updatingContract.setCounterparty(counterpartyService.getCounterpartyById(dto.counterpartyId));

        CounterpartyContract contractToBeUpdated = getCounterpartyContractById(id);

        if (!Objects.equals(contractToBeUpdated.getContract().getAssociatedUser().getId(), currentUser.getId()) ||
            !Objects.equals(updatingContract.getContract().getAssociatedUser().getId(), currentUser.getId()) &&
                currentUser.getRole() != User.Role.ADMIN)
        {
            return false;
        }

        if (contractToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(contractToBeUpdated, updatingContract);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            contractToBeUpdated.setId(id);
            counterpartyContractRepo.save(contractToBeUpdated);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteContract(long id) {
        Optional<CounterpartyContract> opt = counterpartyContractRepo.findById(id);
        User currentUser = userDetailsService.getCurrentUser();
        if (opt.isPresent()) {
            if (currentUser.getRole() != User.Role.ADMIN) {
                if (!Objects.equals(opt.get().getContract().getAssociatedUser().getId(), currentUser.getId())) {
                    return false;
                }
            }
            counterpartyContractRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
