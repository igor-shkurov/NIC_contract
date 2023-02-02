package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.counterparty.CounterpartyDTO;
import nic.task.accountingsystem.entities.counterparty.CounterpartyService;
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
        List<CounterpartyDTO> list = counterpartyService.getCounterparties();
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addCounterparty(@RequestBody @Validated({CounterpartyDTO.New.class}) CounterpartyDTO dto) {
        boolean status = counterpartyService.addCounterparty(dto);
        return new ResponseEntity<>(status ? HttpStatus.CREATED : HttpStatus.FORBIDDEN);
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateCounterparty(@RequestBody @Validated({CounterpartyDTO.Modify.class}) CounterpartyDTO dto) {
        boolean status = counterpartyService.updateCounterparty(dto);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/delete/counterparty_id={id}")
    public ResponseEntity<Object> deleteCounterparty(@PathVariable Long id) {
        boolean status = counterpartyService.deleteCounterparty(id);
        return new ResponseEntity<>(status ? HttpStatus.ACCEPTED : HttpStatus.FORBIDDEN);
    }
}
