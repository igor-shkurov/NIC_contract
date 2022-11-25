package com.example.accountingsystem.entities.stage;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class StageService {
    private final StageRepo stageRepo;

    @Autowired
    public StageService(StageRepo stageRepo) {
        this.stageRepo = stageRepo;
    }

    public List<Stage> getStages() {
        return stageRepo.findAll();
    }

    public void addStage(Stage stage) {
        stageRepo.save(stage);
    }

    public Stage getStageById(long id) { return stageRepo.findById(id).orElse(null); }

    public List<Stage> getStagesByContractId(Long id) {
        return stageRepo.getStagesByContractId(id);
    }

    public void updateStage(long id, Stage updatingStage) {
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

}
