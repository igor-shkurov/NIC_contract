package com.example.accountingsystem.entities.stage;

import com.example.accountingsystem.entities.contract.ContractService;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class StageService {
    private final StageRepo stageRepo;
    private final ContractService contractService;

    private final StageMapper mapper;

    @Autowired
    public StageService(StageRepo stageRepo, ContractService contractService) {
        this.stageRepo = stageRepo;
        this.contractService = contractService;
        mapper = Mappers.getMapper(StageMapper.class);
    }

    public boolean addStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(dto.getContractId());
        if (!Objects.equals(contract.getAssociatedUser().getId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return false;
        }
        Stage entity = mapper.DTOtoStage(dto);
        entity.setContract(contractService.getContractById(dto.getContractId()));

        stageRepo.save(entity);
    }

    public Stage getStageById(long id) { return stageRepo.findById(id).orElse(null); }

    public List<StageDTO> getStagesByContractId(long id) {
        List<Stage> entities = stageRepo.getStagesByContractId(id);
        return mapper.toListOfDTO(entities);
    }

    public boolean updateStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        Stage updatingStage = mapper.DTOtoStage(dto);
        updatingStage.setContract(contractService.getContractById(dto.getContractId()));

        Stage stageToBeUpdated = getStageById(id);
        if (stageToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(stageToBeUpdated, updatingStage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            stageToBeUpdated.setId(id);
            stageRepo.save(stageToBeUpdated);
        }
        else {
            stageRepo.save(updatingStage);
        }
    }

    public void deleteStage(long id) {
        stageRepo.deleteById(id);
    }
}
