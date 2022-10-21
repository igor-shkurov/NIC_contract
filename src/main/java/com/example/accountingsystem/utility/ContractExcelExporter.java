package com.example.accountingsystem.utility;

import com.example.accountingsystem.entities.contract.Contract;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class ContractExcelExporter {
    private final XSSFWorkbook book;
    private final XSSFSheet sheet;

    private final List<Contract> listContracts;

    public ContractExcelExporter(List<Contract> contracts) {
        listContracts = contracts;
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
    }

    private void writeTableRows() {
        int i = 1;
        for (Contract contract : listContracts) {
            Row row = sheet.createRow(i++);

            Cell cell = row.createCell(0);
            cell.setCellValue(contract.getId());

            cell = row.createCell(1);
            cell.setCellValue(contract.getName());

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

            CellStyle cellStyle = book.createCellStyle();

            cell = row.createCell(3);
            cell.setCellValue(contract.getApproxBeginDate().toString());

            cell = row.createCell(4);
            cell.setCellValue(contract.getApproxEndDate().toString());

            cell = row.createCell(5);
            cell.setCellValue(contract.getBeginDate().toString());

            cell = row.createCell(6);
            cell.setCellValue(contract.getEndDate().toString());

            cell = row.createCell(7);
            cell.setCellValue(contract.getSum());
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
        writeHeaderRow();
        writeTableRows();
        autoSizeColumns(book);

        ServletOutputStream outputStream = response.getOutputStream();
        book.write(outputStream);
        book.close();
        outputStream.close();
    }
}
