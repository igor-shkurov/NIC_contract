package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractDTO;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.Stage;
import com.example.accountingsystem.entities.stage.StageDTO;
import com.example.accountingsystem.entities.stage.StageService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelExportService {
    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private static int currentRow = 1;

    private final ContractService contractService;
    private final CounterpartyContractService counterpartyContractService;
    private final StageService stageService;
    private final CounterpartyService counterpartyService;

    @Autowired
    public ExcelExportService(ContractService contractService, CounterpartyContractService counterpartyContractService, StageService stageService, CounterpartyService counterpartyService) {
        this.contractService = contractService;
        this.counterpartyContractService = counterpartyContractService;
        this.stageService = stageService;
        this.counterpartyService = counterpartyService;
    }

    public void writeHeaderRow(Class<?> cl) {
        if (cl != StageDTO.class && cl != ExportableContract.class) {
            throw new IllegalArgumentException(cl + " class passed, but Contract/CounterpartyContract/Stage class expected");
        }
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Id");

        cell = row.createCell(1);
        cell.setCellValue("Срок начала (план)");

        cell = row.createCell(2);
        cell.setCellValue("Срок окончания (план)");

        cell = row.createCell(3);
        cell.setCellValue("Срок начала (факт)");

        cell = row.createCell(4);
        cell.setCellValue("Срок окончания (факт)");

        cell = row.createCell(5);
        cell.setCellValue("Название");

        cell = row.createCell(6);
        cell.setCellValue("Сумма");

        if (ExportableContract.class.isAssignableFrom(cl)) {
            cell = row.createCell(7);
            cell.setCellValue("Тип договора");

            cell = row.createCell(8);
            cell.setCellValue("Основной/номер основного");

            cell = row.createCell(9);
            cell.setCellValue("Организация-контрагент");
        }

        if (cl == StageDTO.class) {

            cell = row.createCell(7);
            cell.setCellValue("Расход на зарплату (план)");

            cell = row.createCell(8);
            cell.setCellValue("Расход на материалы (план)");

            cell = row.createCell(9);
            cell.setCellValue("Расход на зарплату (факт)");

            cell = row.createCell(10);
            cell.setCellValue("Расход на материалы (факт)");
        }

        setRowAlignment(row);
    }

    private void writeTableRowsForContracts(List<? extends ExportableContract> list) {
        for (ExportableContract contract : list) {
            Row row = sheet.createRow(currentRow++);

            long id = contract.id;

            CellStyle cellStyle = book.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell cell = row.createCell(0);
            if (contract.getClass() == ContractDTO.class) {
                cell.setCellValue(id);
            }

            cell = row.createCell(1);
            cell.setCellValue(contract.name);

            cell = row.createCell(2);
            cell.setCellValue(contract.approxBeginDate);

            cell = row.createCell(3);
            cell.setCellValue(contract.approxEndDate);

            cell = row.createCell(4);
            cell.setCellValue(contract.beginDate);

            cell = row.createCell(5);
            cell.setCellValue(contract.endDate);

            cell = row.createCell(6);
            String str;
            switch (contract.contractType) {
                case SUPPLY:
                    str = "Поставка";
                    break;
                case PURCHASE:
                    str = "Закупка";
                    break;
                case WORK:
                    str = "Работы";
                    break;
                default:
                    str = "";
                    break;
            }
            cell.setCellValue(str);

            cell = row.createCell(7);
            cell.setCellValue(contract.sum);

            cell = row.createCell(8);
            Cell cellCounterparty = row.createCell(9);

            if (contract.getClass() == ContractDTO.class) {
                cell.setCellValue("Основной");
                cellCounterparty.setCellValue("-");
                writeTableRowsForContracts(counterpartyContractService.getCounterpartyContractsByContractId(id));
            }
            else {
                cell.setCellValue(((CounterpartyContractDTO) contract).contractId);
                long counterpartyId = ((CounterpartyContractDTO) contract).counterpartyId;
                cellCounterparty.setCellValue(counterpartyService.getCounterpartyById(counterpartyId).getName());
            }

            setRowAlignment(row);
        }
    }

    private void writeTableRowsForStages(List<StageDTO> list) {
        for (StageDTO stage : list) {
            Row row = sheet.createRow(currentRow++);

            long id = stage.id;

            Cell cell = row.createCell(0);
            cell.setCellValue(id);

            cell = row.createCell(1);
            cell.setCellValue(stage.approxBeginDate);

            cell = row.createCell(2);
            cell.setCellValue(stage.approxEndDate);

            cell = row.createCell(3);
            cell.setCellValue(stage.beginDate);

            cell = row.createCell(4);
            cell.setCellValue(stage.endDate);

            cell = row.createCell(5);
            cell.setCellValue(stage.name);

            cell = row.createCell(6);
            cell.setCellValue(stage.sum);

            cell = row.createCell(7);
            cell.setCellValue(stage.salary);

            cell = row.createCell(8);
            cell.setCellValue(stage.credit);

            cell = row.createCell(9);
            cell.setCellValue(stage.approxSalary);

            cell = row.createCell(10);
            cell.setCellValue(stage.approxCredit);

            setRowAlignment(row);
        }
    }

    private void autoSizeColumns() {
        Row row = sheet.getRow(sheet.getFirstRowNum());
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            int columnIndex = cell.getColumnIndex();
            sheet.autoSizeColumn(columnIndex);
        }
    }

    private void setRowAlignment(Row row) {
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            cell.setCellStyle(cellStyle);
        }
    }

    public void exportContractsByGivenPeriod(HttpServletResponse response, LocalDate beginDate, LocalDate endDate) {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Contracts");

        List<ContractDTO> contracts = contractService.getContractsByGivenPeriod(beginDate, endDate);
        writeHeaderRow(ExportableContract.class);
        writeTableRowsForContracts(contracts);

        exportBook(response);
    }

    public void exportStagesByContractId(HttpServletResponse response, Long id) {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Stages");

        List<StageDTO> stages = stageService.getStagesByContractId(id);
        writeHeaderRow(StageDTO.class);
        writeTableRowsForStages(stages);

        exportBook(response);
    }

    private void exportBook(HttpServletResponse response) {
        currentRow = 1;
        autoSizeColumns();

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            book.write(outputStream);
            book.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
