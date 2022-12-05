package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
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
    private final CustomUserDetailsService userDetailsService;

    private final ContractMapper mapper;

    @Autowired
    public ContractService(ContractRepo contractRepo, CustomUserDetailsService userDetailsService) {
        this.contractRepo = contractRepo;
        this.userDetailsService = userDetailsService;
        this.mapper = Mappers.getMapper(ContractMapper.class);
    }

    public List<ContractDTO> getContractsForUser(long id) {
        User user = userDetailsService.getUserById(id);
        if (user == null) {
            return null;
        }
        if (user.getRole() == User.Role.ADMIN) {
            return mapper.toListOfDTO(contractRepo.findAll());
        }
        List<Contract> entities = contractRepo.getContractByAssociatedUserId(id);
        return mapper.toListOfDTO(entities);
    }

    public void addContract(ContractDTO dto) {
        Contract contract = mapper.DTOtoContract(dto);
        contract.setAssociatedUser(userDetailsService.getCurrentUser());
        contractRepo.save(contract);
    }

    public ContractDTO getContractDtoById(long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        ContractDTO dto = null;
        if (opt.isPresent()) {
            dto = mapper.contractContractToDTO(opt.get());
        }
        return dto;
    }

    public Contract getContractById(long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<ContractDTO> getContractsByGivenPeriod(LocalDate beginDate, LocalDate endDate) {
        List<Contract> entities = contractRepo.getContractsByGivenPeriod(Date.valueOf(beginDate), Date.valueOf(endDate));
        return mapper.toListOfDTO(entities);
    }

    public void updateContract(ContractDTO dto) {
        long id = dto.id;
        Contract updatingContract = mapper.DTOtoContract(dto);
        updatingContract.setAssociatedUser(userDetailsService.getUserById(dto.userId));
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

    public void deleteContract(long id) {
        contractRepo.deleteById(id);
    }
}
