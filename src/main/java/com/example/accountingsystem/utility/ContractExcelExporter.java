package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Component
public class ContractExcelExporter {
    private final XSSFWorkbook book;
    private final XSSFSheet sheet;
    private static int currentRow = 1;

    ContractService contractService;
    CounterpartyContractService counterpartyContractService;

    @Autowired
    public ContractExcelExporter(ContractService contractService, CounterpartyContractService counterpartyContractService) {
        this.contractService = contractService;
        this.counterpartyContractService = counterpartyContractService;
        book = new XSSFWorkbook();
        sheet = book.createSheet("Contracts");
    }

    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Id договора");

        cell = row.createCell(1);
        cell.setCellValue("Название");

        cell = row.createCell(2);
        cell.setCellValue("Тип договора");

        cell = row.createCell(3);
        cell.setCellValue("Срок начала (план)");

        cell = row.createCell(4);
        cell.setCellValue("Срок окончания (план)");

        cell = row.createCell(5);
        cell.setCellValue("Срок начала (факт)");

        cell = row.createCell(6);
        cell.setCellValue("Срок окончания (факт)");

        cell = row.createCell(7);
        cell.setCellValue("Сумма договора");

        cell = row.createCell(8);
        cell.setCellValue("Основной/номер основного");
    }

    private void writeTableRows(List<? extends ExportableContract> list) {
        for (ExportableContract contract : list) {
            Row row = sheet.createRow(currentRow++);

            Long id = contract.getId();

            CellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);

            Cell cell = row.createCell(0);
            if (contract.getClass() == Contract.class) {
                cell.setCellValue(id);
                cell.setCellStyle(cellStyle);
            }

            cell = row.createCell(1);
            cell.setCellValue(contract.getName());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(2);
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
                    // @todo: Че тут сделать, может на  if поменять (?)
            }
            cell.setCellValue(str);
            cell.setCellStyle(cellStyle);

            cell = row.createCell(3);
            cell.setCellValue(contract.getApproxBeginDate().toString());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(4);
            cell.setCellValue(contract.getApproxEndDate().toString());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(5);
            cell.setCellValue(contract.getBeginDate().toString());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(6);
            cell.setCellValue(contract.getEndDate().toString());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(7);
            cell.setCellValue(contract.getSum());
            cell.setCellStyle(cellStyle);

            cell = row.createCell(8);
            cell.setCellStyle(cellStyle);

            if (contract.getClass() == Contract.class) {
                cell.setCellValue("Основной");
                writeTableRows(counterpartyContractService.getCounterpartyContractsByContractId(id));
            }
            else {
                cell.setCellValue(((CounterpartyContract) contract).getContract().getId());
            }
        }
    }

    private void autoSizeColumns(XSSFWorkbook book) {
        if (sheet.getPhysicalNumberOfRows() > 0) {
            Row row = sheet.getRow(sheet.getFirstRowNum());
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                sheet.autoSizeColumn(columnIndex);
            }
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        List<Contract> contracts = contractService.getContracts();
        writeHeaderRow();
        writeTableRows(contracts);
        autoSizeColumns(book);

        ServletOutputStream outputStream = response.getOutputStream();
        book.write(outputStream);
        book.close();
        outputStream.close();
    }
}
