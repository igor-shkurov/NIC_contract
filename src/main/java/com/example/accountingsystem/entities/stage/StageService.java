package com.example.accountingsystem.entities.stage;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageRepo;
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

}
