package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.stage.StageDTO;
import com.example.accountingsystem.entities.stage.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @GetMapping(path = "/contract_id={id}", produces = {"application/json"})
    public ResponseEntity<List<StageDTO>> showStageById(@PathVariable("id") Long id) {
        List<StageDTO> list = stageService.getStagesByContractId(id);
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addContract(@RequestBody @Valid StageDTO dto) {
        boolean status = stageService.addStage(dto);
        return new ResponseEntity<>(status ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateStage(@RequestBody @Valid StageDTO dto) {
        boolean status = stageService.updateStage(dto);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/stage_id={id}")
    public ResponseEntity<Object> deleteStage(@PathVariable Long id) {
        boolean status = stageService.deleteStage(id);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }
}
