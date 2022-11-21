package com.example.accountingsystem.entities.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {
    private final StageRepo stageRepo;

    @Autowired
    public StageService(StageRepo stageRepo) {
        this.stageRepo = stageRepo;
    }

    public List<Stage> getStagesByContractId(Long id) {
        return stageRepo.getStagesByContractId(id);
    }

    public List<Stage> getStages() {
        return stageRepo.findAll();
    }

    public void addStage(Stage stage) {
        stageRepo.save(stage);
    }

    public void deleteById(Long id) {
        stageRepo.deleteById(id);
    }

    public List<Stage> getStageByContractId(Long id) {
        return stageRepo.getStagesByContractId(id);
    }

    public void deleteCounterpartyContractByContractId(Long contractId) {
        List<Stage> contractStages = getStageByContractId(contractId);

        for (Stage stage : contractStages) {
            deleteById(stage.getId());
        }
    }

}
