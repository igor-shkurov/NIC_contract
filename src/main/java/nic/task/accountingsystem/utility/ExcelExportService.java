package nic.task.accountingsystem.utility;

import nic.task.accountingsystem.entities.ExportableContractDTO;
import nic.task.accountingsystem.entities.contract.ContractDTO;
import nic.task.accountingsystem.entities.contract.ContractService;
import nic.task.accountingsystem.entities.counterparty.CounterpartyService;
import nic.task.accountingsystem.entities.counterparty_contract.CounterpartyContractDTO;
import nic.task.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import nic.task.accountingsystem.entities.stage.StageDTO;
import nic.task.accountingsystem.entities.stage.StageService;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
        if (cl != StageDTO.class && cl != ExportableContractDTO.class) {
            throw new IllegalArgumentException(cl + " class passed, but Contract/CounterpartyContract/Stage class expected");
        }
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Срок начала (план)");

        cell = row.createCell(1);
        cell.setCellValue("Срок окончания (план)");

        cell = row.createCell(2);
        cell.setCellValue("Срок начала (факт)");

        cell = row.createCell(3);
        cell.setCellValue("Срок окончания (факт)");

        cell = row.createCell(4);
        cell.setCellValue("Название");

        cell = row.createCell(5);
        cell.setCellValue("Сумма");

        if (ExportableContractDTO.class.isAssignableFrom(cl)) {
            cell = row.createCell(6);
            cell.setCellValue("Тип договора");

            cell = row.createCell(7);
            cell.setCellValue("Основной/номер основного");

            cell = row.createCell(8);
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

    private void writeTableRowsForContracts(List<? extends ExportableContractDTO> list) {
        for (ExportableContractDTO contract : list) {
            Row row = sheet.createRow(currentRow++);

            CreationHelper createHelper = book.getCreationHelper();
            short format = createHelper.createDataFormat().getFormat("d.m.yy");
            CellStyle dateStyle = book.createCellStyle();
            dateStyle.setDataFormat(format);

            long id = contract.getId();

            Cell cell = row.createCell(0);
            cell.setCellValue(contract.getApproxBeginDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(1);
            cell.setCellValue(contract.getApproxEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(2);
            cell.setCellValue(contract.getBeginDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(3);
            cell.setCellValue(contract.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(4);
            cell.setCellValue(contract.getName());

            cell = row.createCell(5);
            cell.setCellValue(contract.getSum().floatValue());

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
            Cell cellCounterparty = row.createCell(8);

            if (contract.getClass() == ContractDTO.class) {
                cell.setCellValue("Основной");
                cellCounterparty.setCellValue("-");
                writeTableRowsForContracts(counterpartyContractService.getCounterpartyContractsByContractId(id).getFirst());
            }
            else {
                long contractId = ((CounterpartyContractDTO) contract).getContractId();
                cell.setCellValue(contractService.getContractById(contractId).getName());
                long counterpartyId = ((CounterpartyContractDTO) contract).getCounterpartyId();
                cellCounterparty.setCellValue(counterpartyService.getCounterpartyById(counterpartyId).getName());
            }

            setRowAlignment(row);
        }
    }

    private void writeTableRowsForStages(List<StageDTO> list) {
        for (StageDTO stage : list) {
            Row row = sheet.createRow(currentRow++);

            long id = stage.getId();

            Cell cell = row.createCell(0);
            cell.setCellValue(stage.getApproxBeginDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(1);
            cell.setCellValue(stage.getApproxEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(2);
            cell.setCellValue(stage.getBeginDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(3);
            cell.setCellValue(stage.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));

            cell = row.createCell(4);
            cell.setCellValue(stage.getName());

            cell = row.createCell(5);
            cell.setCellValue(stage.getSum().floatValue());

            cell = row.createCell(6);
            cell.setCellValue(stage.getSalary().floatValue());

            cell = row.createCell(7);
            cell.setCellValue(stage.getCredit().floatValue());

            cell = row.createCell(8);
            cell.setCellValue(stage.getApproxSalary().floatValue());

            cell = row.createCell(9);
            cell.setCellValue(stage.getApproxCredit().floatValue());

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
        if (beginDate == null) { beginDate = LocalDate.MIN; }
        if (endDate == null) { endDate = LocalDate.MAX; }

        List<ContractDTO> contracts = contractService.getContractsByGivenPeriod(beginDate, endDate);
        writeHeaderRow(ExportableContractDTO.class);
        writeTableRowsForContracts(contracts);

        exportBook(response);
    }

    public void exportStagesByContractId(HttpServletResponse response, Long id) {
        book = new XSSFWorkbook();
        sheet = book.createSheet("Stages");

        Pair<List<StageDTO>, HttpStatus> pair = stageService.getStagesByContractId(id);
        if (pair.getSecond() != HttpStatus.OK) {
            return;
        }
        List<StageDTO> stages = pair.getFirst();

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
