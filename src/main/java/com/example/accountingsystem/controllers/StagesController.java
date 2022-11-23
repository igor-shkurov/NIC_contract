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
@RequestMapping("/api")
public class StagesController {
    private final StageService stageService;
    private final ContractService contractService;


    @Autowired
    public StagesController(StageService stageService, ContractService contractService) {
        this.stageService = stageService;
        this.contractService = contractService;
    }

    //get all stages
    @GetMapping(path = "/stages")
    public List<Stage> showStages(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return stageService.getStages();
    }

    // get all stages of contract
    @GetMapping(path = "/stages/{contractId}")
    public List<Stage> showStageById(@PathVariable("contractId") String contractId, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return stageService.getStagesByContractId(Long.parseLong(contractId));
    }

    //не проверено
    @PostMapping(path = "/stages/{id}")
    public List<Stage> addContract(@RequestBody @Valid Stage stage, @PathVariable("id") String contractId) {
        //нужно?
        stage.setContract(contractService.getContractById(Long.parseLong(contractId)));
        stageService.addStage(stage);
        return stageService.getStages();
    }
}
