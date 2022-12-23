package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.stage.StageDTO;
import com.example.accountingsystem.entities.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/stages")
public class StagesController {
    private final StageService stageService;

    @Autowired
    public StagesController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping(path = "/{id}")
    public List<StageDTO> showStageById(@PathVariable("id") String id, HttpServletResponse response) {
        List<StageDTO> list = stageService.getStagesByContractId(Long.parseLong(id));
        if (list == null) {
            response.setStatus(404);
        }
        return list;
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public void addContract(@RequestBody StageDTO dto, HttpServletResponse response) {
        if (!stageService.addStage(dto)) {
            response.setStatus(403);
        }
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public void updateStage(@RequestBody StageDTO dto, HttpServletResponse response) {
        if (!stageService.updateStage(dto)) {
            response.setStatus(403);
        }
    }

    @DeleteMapping(path = "/delete/stage_id={id}", consumes = {"application/json"})
    public void deleteStage(@PathVariable Long id, HttpServletResponse response) {
        if (!stageService.deleteStage(id)) {
            response.setStatus(404);
        }
    }
}
