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
}
