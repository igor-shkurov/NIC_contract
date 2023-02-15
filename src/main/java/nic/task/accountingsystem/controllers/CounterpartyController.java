package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.counterparty.CounterpartyDTO;
import nic.task.accountingsystem.entities.counterparty.CounterpartyService;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/counterparties")
public class CounterpartyController {

    private final CounterpartyService counterpartyService;

    @Autowired
    public CounterpartyController(CounterpartyService counterpartyService) {
        this.counterpartyService = counterpartyService;
    }

    @GetMapping(path = "", produces = {"application/json"})
    public ResponseEntity<List<CounterpartyDTO>> showCounterparties() {
        Pair<List<CounterpartyDTO>, HttpStatus> pair = counterpartyService.getCounterparties();
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addCounterparty(@RequestBody @Validated({CounterpartyDTO.New.class}) CounterpartyDTO dto) {
        return new ResponseEntity<>(counterpartyService.addCounterparty(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateCounterparty(@RequestBody @Validated({CounterpartyDTO.Modify.class}) CounterpartyDTO dto) {
        return new ResponseEntity<>(counterpartyService.updateCounterparty(dto));
    }

    @DeleteMapping(path = "/delete/counterparty_id={id}")
    public ResponseEntity<Object> deleteCounterparty(@PathVariable Long id) {
        return new ResponseEntity<>(counterpartyService.deleteCounterparty(id));
    }
}
