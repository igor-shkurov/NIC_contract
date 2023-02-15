package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.stage.StageDTO;
import nic.task.accountingsystem.entities.stage.StageService;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stages")
public class StageController {
    private final StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping(path = "/contract_id={id}", produces = {"application/json"})
    public ResponseEntity<List<StageDTO>> showStageById(@PathVariable("id") Long id) {
        Pair<List<StageDTO>, HttpStatus> pair = stageService.getStagesByContractId(id);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Validated({StageDTO.New.class}) StageDTO dto) {
        return new ResponseEntity<>(stageService.addStage(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateStage(@RequestBody @Validated({StageDTO.Modify.class}) StageDTO dto) {
        return new ResponseEntity<>(stageService.updateStage(dto));
    }

    @DeleteMapping(path = "/delete/stage_id={id}")
    public ResponseEntity<Object> deleteStage(@PathVariable Long id) {
        return new ResponseEntity<>(stageService.deleteStage(id));
    }
}
