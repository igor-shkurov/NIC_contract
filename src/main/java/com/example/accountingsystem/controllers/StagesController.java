package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageDTO;
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

    @GetMapping(path = "/{id}")
    public List<StageDTO> showStageById(@PathVariable("id") String id, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return stageService.getStagesByContractId(Long.parseLong(id));
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public void addContract(@RequestBody @Valid StageDTO dto, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        stageService.addStage(dto);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateStage(@RequestBody StageDTO dto, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        stageService.updateStage(dto);
    }
}
