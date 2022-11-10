package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.Stage;
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

    ContractService contractService;
    CounterpartyContractService counterpartyContractService;
    StageService stageService;

    @Autowired
    public ExcelExportService(ContractService contractService, CounterpartyContractService counterpartyContractService, StageService stageService) {
        this.contractService = contractService;
        this.counterpartyContractService = counterpartyContractService;
        this.stageService = stageService;
    }

    public void writeHeaderRow(Class<?> cl) {
        if (cl != Stage.class && !ExportableContract.class.isAssignableFrom(cl)) {
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

        if (ExportableContract.class.isAssignableFrom(cl)) {
            cell = row.createCell(6);
            cell.setCellValue("Тип договора");

            cell = row.createCell(7);
            cell.setCellValue("Сумма договора");

            cell = row.createCell(8);
            cell.setCellValue("Основной/номер основного");
        }

        if (cl == Stage.class) {
            cell = row.createCell(6);
            cell.setCellValue("Доход");

            cell = row.createCell(7);
            cell.setCellValue("Расход");
        }

        setRowAlignment(row);
    }

    private void writeTableRowsForContracts(List<? extends ExportableContract> list) {
        for (ExportableContract contract : list) {
            Row row = sheet.createRow(currentRow++);

            Long id = contract.getId();

            CellStyle cellStyle = book.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell cell = row.createCell(0);
            if (contract.getClass() == Contract.class) {
                cell.setCellValue(id);
            }

            cell = row.createCell(1);
            cell.setCellValue(contract.getApproxBeginDate().toString());

            cell = row.createCell(2);
            cell.setCellValue(contract.getApproxEndDate().toString());

            cell = row.createCell(3);
            cell.setCellValue(contract.getBeginDate().toString());

            cell = row.createCell(4);
            cell.setCellValue(contract.getEndDate().toString());

            cell = row.createCell(5);
            cell.setCellValue(contract.getName());

            cell = row.createCell(6);
            String str;
            switch (contract.getContractType()) {
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
            cell.setCellValue(contract.getSum());

            cell = row.createCell(8);

            if (contract.getClass() == Contract.class) {
                cell.setCellValue("Основной");
                writeTableRowsForContracts(counterpartyContractService.getCounterpartyContractsByContractId(id));
            }
            else {
                cell.setCellValue(((CounterpartyContract) contract).getContract().getId());
            }

            setRowAlignment(row);
        }
    }

    private void writeTableRowsForStages(List<Stage> list) {
        for (Stage stage : list) {
            Row row = sheet.createRow(currentRow++);

            Long id = stage.getId();

            Cell cell = row.createCell(0);
            cell.setCellValue(id);

            cell = row.createCell(1);
            cell.setCellValue(stage.getApproxBeginDate().toString());

            cell = row.createCell(2);
            cell.setCellValue(stage.getApproxEndDate().toString());

            cell = row.createCell(3);
            cell.setCellValue(stage.getBeginDate().toString());

            cell = row.createCell(4);
            cell.setCellValue(stage.getEndDate().toString());

            cell = row.createCell(5);
            cell.setCellValue(stage.getName());

            cell = row.createCell(6);
            cell.setCellValue(stage.getCredit());

            cell = row.createCell(7);
            cell.setCellValue(stage.getDebit());

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

    public void exportContracts(HttpServletResponse response, LocalDate beginDate, LocalDate endDate) {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Contracts");

        List<Contract> contracts = contractService.getContractsByGivenPeriod(beginDate, endDate);
        writeHeaderRow(Contract.class);
        writeTableRowsForContracts(contracts);

        exportBook(response);
    }

    public void exportStagesByContractId(HttpServletResponse response, Long id) {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Stages");

        List<Stage> stages = stageService.getStagesByContractId(id);
        writeHeaderRow(Stage.class);
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
