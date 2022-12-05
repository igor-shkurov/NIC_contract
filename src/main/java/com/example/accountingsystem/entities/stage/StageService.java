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

    public void addStage(StageDTO dto) {
        Stage entity = mapper.DTOtoStage(dto);
        stageRepo.save(entity);
    }

    public Stage getStageById(long id) { return stageRepo.findById(id).orElse(null); }

    public List<StageDTO> getStagesByContractId(long id) {
        List<Stage> entities = stageRepo.getStagesByContractId(id);
        return mapper.toListOfDTO(entities);
    }

    public void updateStage(StageDTO dto) {
        long id = dto.id;
        Stage updatingStage = mapper.DTOtoStage(dto);
        updatingStage.setContract(contractService.getContractById(dto.contractId));
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
