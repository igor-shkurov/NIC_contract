package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageDTO;
import com.example.accountingsystem.entities.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        List<StageDTO> list = stageService.getStagesByContractId(id);
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Validated({StageDTO.New.class}) StageDTO dto) {
        boolean status = stageService.addStage(dto);
        return new ResponseEntity<>(status ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateStage(@RequestBody @Validated({StageDTO.Modify.class}) StageDTO dto) {
        boolean status = stageService.updateStage(dto);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/stage_id={id}")
    public ResponseEntity<Object> deleteStage(@PathVariable Long id) {
        boolean status = stageService.deleteStage(id);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }
}
