package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractController {

    private final ContractService contractService;
    private final ExcelExportService excelExportService;


    @Autowired
    public ContractController(ContractService contractService, ExcelExportService excelExportService) {
        this.contractService = contractService;
        this.excelExportService = excelExportService;
    }

    @GetMapping(path = "/contracts")
    public List<Contract> showContracts(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        return contractService.getContracts();
    }

    @PostMapping(path = "/contracts") //postman
    public List<Contract> addContract(@RequestBody @Valid Contract contract) {
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

    @GetMapping(path = "/contracts/{id}")
    public Contract showContractsById(@PathVariable("id") String contractId) {
        return contractService.getContractById(Long.parseLong(contractId));
    }


}
