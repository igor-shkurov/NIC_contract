package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/stages")
public class StagesController {
    private final StageService stageService;
    private final ContractService contractService;

    @Autowired
    public StagesController(StageService stageService, ContractService contractService) {
        this.stageService = stageService;
        this.contractService = contractService;
    }

    @GetMapping(path = "")
    public List<Stage> showStages(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return stageService.getStages();
    }

    @GetMapping(path = "/{id}")
    public List<Stage> showStageById(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return stageService.getStagesByContractId(Long.parseLong(id));
    }

    @PostMapping(path = "/{id}")
    public List<Stage> addContract(@RequestBody @Valid Stage stage, @PathVariable("id") String id,
                                   HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        stage.setContract(contractService.getContractById(Long.parseLong(id)));
        stageService.addStage(stage);
        return stageService.getStagesByContractId(Long.parseLong(id));
    }

    @PutMapping(path = "/{id}/update")
    public void updateStage(@RequestBody Stage stage, @PathVariable("id") String id,
                               HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        stageService.updateStage(Long.parseLong(id), stage);
    }
}
