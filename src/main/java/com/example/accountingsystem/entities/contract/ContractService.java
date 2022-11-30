package com.example.accountingsystem.entities.contract;

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

    private final ContractMapper mapper;

    @Autowired
    public ContractService(ContractRepo contractRepo) {
        this.contractRepo = contractRepo;
        this.mapper = Mappers.getMapper(ContractMapper.class);
    }

    public List<ContractDTO> getContracts() {
        List<Contract> entities = contractRepo.findAll();
        return mapper.toListOfDTO(entities);
    }

    public void addContract(Contract contract) {
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
