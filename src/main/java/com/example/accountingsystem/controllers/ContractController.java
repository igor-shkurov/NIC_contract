package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final StageService stageService;
    private final ContractService contractService;
    private final CustomUserDetailsService userService;
    private final ExcelExportService excelExportService;

    @Autowired
    public ContractController(StageService stageService, ContractService contractService,
                              CustomUserDetailsService userService, CounterpartyContractService counterpartyContractService,
                              ExcelExportService excelExportService) {
        this.stageService = stageService;
        this.contractService = contractService;
        this.userService = userService;
        this.excelExportService = excelExportService;
    }

    @GetMapping(path = "/contracts")
    public List<Contract> showContracts() {
        return contractService.getContracts();
    }

    @PostMapping(path = "/contracts")
    public List<Contract> addContract(@RequestBody Contract contract) {
        contractService.addContract(contract);

        return contractService.getContracts();
    }

    @GetMapping(path = "/contracts.xlsx")
    public void getFile(HttpServletResponse response) {
//        response.setContentType("application/octet-stream");
//        try {
//            excelExportService.exportContracts(response);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        response.setContentType("application/octet-stream");
        try {
            excelExportService.exportStagesByContractId(response, 1L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(path = "/users")
    public List<User> showUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "/contracts/{id}")
    public Contract showCounterContracts(@PathVariable("id") String contractId) {

        return contractService.getContractById(Long.parseLong(contractId));
    }
}
