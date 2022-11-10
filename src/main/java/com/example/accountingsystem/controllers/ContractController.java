package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.StageService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import com.example.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.time.LocalDate;
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

    @PostMapping(path = "/contracts.xlsx")
    public void getContractsExcel(HttpServletResponse response, @RequestParam String beginDate,
                                                                @RequestParam String endDate) {
        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment; filename=contracts.xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");

        excelExportService.exportContractsByGivenPeriod(response, LocalDate.parse(beginDate), LocalDate.parse(endDate));
    }

    @GetMapping(path = "/stages{contractId}.xlsx")
    public void getStagesExcel(HttpServletResponse response, @PathVariable String contractId) throws FileNotFoundException {
        Long id = Long.parseLong(contractId);
        if (contractService.getContractById(id) == null) {
            throw new FileNotFoundException("Can not create a file for this contract: No contract with such id");
        }

        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment; filename=stages[" + id + "].xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");

        excelExportService.exportStagesByContractId(response, id);
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
