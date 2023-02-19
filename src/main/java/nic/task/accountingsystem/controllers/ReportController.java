package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.contract.ContractService;
import nic.task.accountingsystem.utility.DatesDTO;
import nic.task.accountingsystem.utility.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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

    @PostMapping(path = "", consumes = {"application/json"})
    public void getContractsExcel(HttpServletResponse response, @RequestBody DatesDTO dates) {
        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment; filename=contracts.xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");
        excelExportService.exportContractsByGivenPeriod(response, dates.getBeginDate(), dates.getEndDate());
    }

    @GetMapping(path = "/contract_id={id}")
    public void getStagesExcel(HttpServletResponse response, @PathVariable Long id) {
        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment; filename=stages[" + id + "].xlsx");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "no-store");
        response.addHeader("Cache-Control", "max-age=0");

        excelExportService.exportStagesByContractId(response, id);
    }
}
