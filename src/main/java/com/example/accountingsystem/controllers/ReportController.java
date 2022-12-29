package com.example.accountingsystem.controllers;

import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.time.LocalDate;
@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ExcelExportService excelExportService;
    private final ContractService contractService;

    @Autowired
    public ReportController(ExcelExportService excelExportService, ContractService contractService) {
        this.excelExportService = excelExportService;
        this.contractService = contractService;
    }

    @PostMapping(path = "")
    public void getContractsExcel(HttpServletResponse response, @RequestParam String beginDate,
                                  @RequestParam String endDate) {
        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment; filename=contracts.xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");

        excelExportService.exportContractsByGivenPeriod(response, LocalDate.parse(beginDate), LocalDate.parse(endDate));
    }

    @GetMapping(path = "/{contractId}")
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
}
