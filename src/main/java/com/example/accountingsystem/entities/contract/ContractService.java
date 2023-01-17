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
    
    public boolean addContract(ContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        if (!Objects.equals(dto.getUserId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return false;
        }
        Contract contract = mapper.DTOtoContract(dto);
        contract.setAssociatedUser(userDetailsService.getCurrentUser());
        contractRepo.save(contract);
    }

    public ContractDTO getContractDtoById(long id) {
        User currentUser = userDetailsService.getCurrentUser();
        Optional<Contract> opt = contractRepo.findById(id);
        ContractDTO dto = null;
        if (opt.isPresent()) {
            Contract contract = opt.get();
            if (!Objects.equals(contract.getAssociatedUser().getId(), userDetailsService.getCurrentUser().getId()) && currentUser.getRole() != User.Role.ADMIN ) {
                return null;
            }
            dto = mapper.contractContractToDTO(contract);
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

    public boolean updateContract(ContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        Contract updatingContract = mapper.DTOtoContract(dto);
        updatingContract.setAssociatedUser(userDetailsService.getUserById(dto.getUserId()));
        Contract contractToBeUpdated = getContractById(id);

        if (currentUser.getRole() != User.Role.ADMIN) {
            if (!Objects.equals(dto.getUserId(), contractToBeUpdated.getAssociatedUser().getId()) || !Objects.equals(dto.getUserId(), currentUser.getId())) {
                return false;
            }
        }
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
