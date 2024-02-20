package kr.co.hs.project.service;

import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.project.dto.DayProjectDTO;
import kr.co.hs.project.dto.DayProjectItemDTO;
import kr.co.hs.project.dto.ProjectMainDTO;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;
import java.util.List;
import java.util.Locale;

@Service
public class ExcelExport {

    @Autowired
    ServletContext context;

    @Autowired
    private MessageSource messageSource;

    // start export excel
    public String exportExcelFile(EmpDTO empDTO, List projectMainYDTO, List projectMainDTO, String startDate, String endDate) throws IOException {
        File destFiles = new File(context.getRealPath("resources/Upload/excel_file/"+ empDTO.getEmpCd()));
        //check file exit
        if (!destFiles.exists()){
            destFiles.mkdirs();
        }
        cellStyleFormatNumber = null;
        String fileName = empDTO.getEmpCd() + "/"+ empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        String excelFilePath = destFiles.getPath() + "/" +empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        writeExcel(projectMainYDTO, projectMainDTO, excelFilePath, startDate, endDate);
        return fileName;
    }

    public void writeExcel(List<ProjectMainDTO> projectMainYDTOS, List<ProjectMainDTO> projectMainDTOS, String excelFilePath, String startDate, String endDate) throws IOException{
        try {
            // Create Workbook
            Workbook workbook = getWorkbook(excelFilePath);
            // Create sheet
            Sheet sheet = workbook.createSheet("MainProject"); // Create sheet with sheet name

            sheet.setColumnWidth(1, 10275);

            String langCd = LocaleContextHolder.getLocale().toString();
            Locale loc = new Locale(langCd, "MESSAGES");

            if (startDate.equals("") || startDate == null){
                startDate = "2022-01-01";
                endDate = java.time.LocalDate.now().toString();
            }

            String strStart = messageSource.getMessage("project.list.search", null, loc).toUpperCase(Locale.ROOT)
                    + ": " + startDate.substring(0, 4)
                    + messageSource.getMessage("project.list.year", null, loc).toUpperCase(Locale.ROOT)
                    + " " + startDate.substring(5, 7)
                    + messageSource.getMessage("project.list.month", null, loc).toUpperCase(Locale.ROOT)
                    + " " + startDate.substring(8, 10)
                    + messageSource.getMessage("project.list.day", null, loc).toUpperCase(Locale.ROOT);

            String strEnd = strStart
                    + ": " + endDate.substring(0, 4)
                    + messageSource.getMessage("project.list.year", null, loc).toUpperCase(Locale.ROOT)
                    + " " + endDate.substring(5, 7)
                    + messageSource.getMessage("project.list.month", null, loc).toUpperCase(Locale.ROOT)
                    + " " + endDate.substring(8, 10)
                    + messageSource.getMessage("project.list.day", null, loc).toUpperCase(Locale.ROOT);

            String titleProject = messageSource.getMessage("project.list.project", null, loc).toUpperCase(Locale.ROOT);

            String projectStatus = messageSource.getMessage("project.list.processing", null, loc).toUpperCase(Locale.ROOT);

            writeTitle(sheet, 0, titleProject, 0, 0, 0, 15, 24, "P");
            writeTitle(sheet, 1, strEnd, 1, 1, 0, 15, 14, "D");
            writeTitle(sheet, 2, projectStatus, 2, 2, 0, 15, 14, "S");
            int rowIndex = 3;

            // Write header
            writeHeader(sheet, rowIndex);

            // Write data
            rowIndex++;
            for (ProjectMainDTO book : projectMainDTOS) {

                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(book, row, sheet);
                rowIndex++;
            }

            projectStatus = messageSource.getMessage("project.list.finished", null, loc).toUpperCase(Locale.ROOT);
            writeTitle(sheet, rowIndex, projectStatus, rowIndex, rowIndex, 0, 15, 14, "S");

            rowIndex++;
            for (ProjectMainDTO book : projectMainYDTOS) {

                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBook(book, row, sheet);
                rowIndex++;
            }
            // Write footer
            //writeFooter(sheet, rowIndex);

            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();

            sheet.autoSizeColumn(numberOfColumn);
            autosizeColumn(sheet, numberOfColumn);

            // Create file excel
            createOutputFile(workbook, excelFilePath);
            System.out.println("Done!!!");
        } catch (Exception e){
            System.out.println("DXD: "+e);
        }
    }

    public static Workbook getWorkbook(String excelFilePath) throws IOException{
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")){
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }

    private static CellStyle cellStyleFormatNumber = null;

    public void writeTitle(Sheet sheet, int rowIndex, String code, int a, int b, int c, int d, int fontSize, String type) {

        ///////////
        // create CellStyle
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        font.setFontHeightInPoints((short) fontSize); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        if (type.equals("P")){
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }if (type.equals("D")){
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        }if (type.equals("S")){
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.LEFT);
        }if (type.equals("B")){
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
        }


        // Create row
        Row row = sheet.createRow(rowIndex);
        // Create cells

        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(code);

        for (int i = 1; i <= d; i++){
            cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
        }

        sheet.addMergedRegion(new CellRangeAddress(a,b,c,d));

    }

    public void writeHeader(Sheet sheet, int rowIndex) {

        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");

        ///////////
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.stt", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.nameproject", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.dept", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.position", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.pic", null, loc).toUpperCase(Locale.ROOT));

        //////

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.dept", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.position", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.pic.accounting", null, loc).toUpperCase(Locale.ROOT));

        //////

        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.advancemoney", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(9);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.usagemoney", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(10);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.cashinreturn", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(11);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.approve", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(12);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.translationstatus", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(13);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.status", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(14);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.startdate", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(15);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("project.list.enddate", null, loc).toUpperCase(Locale.ROOT));

    }

    private CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("맑은 고딕");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.DARK_TEAL.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //sheet.setAutoFilter(CellRangeAddress.valueOf("A1:K1"));

        return cellStyle;
    }

    private CellStyle createStyleForBody(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("맑은 고딕");

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }

    public String unescapeHtml3( String str ) {
        try {
            HTMLDocument doc = new HTMLDocument();
            new HTMLEditorKit().read( new StringReader( "<html><body>" + str ), doc, 0 );
            return doc.getText( 1, doc.getLength() ).substring(0, doc.getText( 1, doc.getLength() ).length() - 1);
        } catch( Exception ex ) {
            return str;
        }
    }

    // Write data
    private void writeBook(ProjectMainDTO projectMainDTO, Row row, Sheet sheet){
        if (cellStyleFormatNumber == null){
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();

            Font font = sheet.getWorkbook().createFont();
            font.setFontName("맑은 고딕");
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setFont(font);
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setDataFormat(format);
            cellStyleFormatNumber.setWrapText(true);
            cellStyleFormatNumber.setAlignment(HorizontalAlignment.CENTER);
            cellStyleFormatNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");



        if (projectMainDTO.getType().equals("translate")){
            Cell cell = row.createCell(0);
            cell.setCellValue(messageSource.getMessage("emp.profile.translation", null, loc).toUpperCase(Locale.ROOT));
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String title = unescapeHtml3(projectMainDTO.getTitle());
            cell = row.createCell(1);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellStyle(cellStyleFormatNumber);

            //

            cell = row.createCell(5);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyleFormatNumber);
            //

            cell = row.createCell(8);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(9);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(10);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(11);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(12);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(13);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(14);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(15);
            cell.setCellStyle(cellStyleFormatNumber);

        }else {
            Cell cell = row.createCell(0);
            cell.setCellValue(projectMainDTO.getType());
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String title = unescapeHtml3(projectMainDTO.getTitle());
            cell = row.createCell(1);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);
            cell = row.createCell(2);
            cell.setCellValue(projectMainDTO.getDeptName().replace("<br>", "\n"));
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellValue(projectMainDTO.getPositionName());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellValue(projectMainDTO.getLeaderProjectName());
            cell.setCellStyle(cellStyleFormatNumber);

            //

            cell = row.createCell(5);
            cell.setCellValue(projectMainDTO.getDeptAccountingName().replace("<br>", "\n"));
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellValue(projectMainDTO.getPositionAccountingName());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellValue(projectMainDTO.getLeaderAccountingName());
            cell.setCellStyle(cellStyleFormatNumber);

            //

            cell = row.createCell(8);
            cell.setCellValue(projectMainDTO.getAdvanceAmount());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(9);
            cell.setCellValue(projectMainDTO.getUseAmount());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(10);
            cell.setCellValue(projectMainDTO.getRemainAmount());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(11);
            cell.setCellValue(projectMainDTO.getApproval());
            cell.setCellStyle(cellStyleFormatNumber);

            String translationStatus = "";
            if (projectMainDTO.getTranslationStatus().equals("1"))
                translationStatus = messageSource.getMessage("work.detail.finish", null, loc).toUpperCase(Locale.ROOT);
            else
                translationStatus = messageSource.getMessage("work.detail.notfinish", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(12);
            cell.setCellValue(translationStatus);
            cell.setCellStyle(cellStyleFormatNumber);

            String projectStatus = "";

            if (projectMainDTO.getProjectStatus().equals("Y"))
                projectStatus = messageSource.getMessage("project.type.approved", null, loc).toUpperCase(Locale.ROOT);
            else
                projectStatus = messageSource.getMessage("project.type.not.approve", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(13);
            cell.setCellValue(projectStatus);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(14);
            cell.setCellValue(projectMainDTO.getProjectStartDate());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(15);
            cell.setCellValue(projectMainDTO.getProjectEndDate());
            cell.setCellStyle(cellStyleFormatNumber);
        }
    }

    // Write footer
    private void writeFooter(Sheet sheet, int rowIndex) {
        // Create row
        Row row = sheet.createRow(rowIndex);
        Cell cell = row.createCell(0, CellType.FORMULA);
        cell.setCellFormula("SUM(E2:E6)");
    }

    // Auto resize column width
    private void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            if (columnIndex != 1)
                sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
    }

    // start excel for day project

    public String exportExcelFileDayProject(EmpDTO empDTO, List<DayProjectDTO> dayProjectDTOS, String startDate, String endDate, ProjectMainDTO projectMainDTO) throws IOException {
        File destFiles = new File(context.getRealPath("resources/Upload/excel_file/"+ empDTO.getEmpCd()));
        //check file exit
        if (!destFiles.exists()){
            destFiles.mkdirs();
        }
        cellStyleFormatNumber = null;
        String fileName = empDTO.getEmpCd() + "/"+ empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        String excelFilePath = destFiles.getPath() + "/" +empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        writeExcelDayProject(dayProjectDTOS, excelFilePath, startDate, endDate,  projectMainDTO);
        return fileName;
    }

    public void writeExcelDayProject(List<DayProjectDTO> dayProjectDTOS, String excelFilePath, String startDate, String endDate, ProjectMainDTO projectMainDTO) throws IOException{
        try {
            // Create Workbook
            Workbook workbook = getWorkbook(excelFilePath);
            // Create sheet
            Sheet sheet = workbook.createSheet("DayProject"); // Create sheet with sheet name

            sheet.setColumnWidth(1, 10275);

            String langCd = LocaleContextHolder.getLocale().toString();
            Locale loc = new Locale(langCd, "MESSAGES");

            if (startDate.equals("") || startDate == null){
                startDate = "2022-01-01";
                endDate = java.time.LocalDate.now().toString();
            }

            String strStart = messageSource.getMessage("project.list.search", null, loc).toUpperCase(Locale.ROOT)
                    + ": " + startDate.substring(0, 4)
                    + messageSource.getMessage("project.list.year", null, loc).toUpperCase(Locale.ROOT)
                    + " " + startDate.substring(5, 7)
                    + messageSource.getMessage("project.list.month", null, loc).toUpperCase(Locale.ROOT)
                    + " " + startDate.substring(8, 10)
                    + messageSource.getMessage("project.list.day", null, loc).toUpperCase(Locale.ROOT);

            String strEnd = strStart
                    + ": " + endDate.substring(0, 4)
                    + messageSource.getMessage("project.list.year", null, loc).toUpperCase(Locale.ROOT)
                    + " " + endDate.substring(5, 7)
                    + messageSource.getMessage("project.list.month", null, loc).toUpperCase(Locale.ROOT)
                    + " " + endDate.substring(8, 10)
                    + messageSource.getMessage("project.list.day", null, loc).toUpperCase(Locale.ROOT);

            String titleProject = messageSource.getMessage("dayproject.dayproject.exceltitle", null, loc).toUpperCase(Locale.ROOT);

            String title = unescapeHtml3(projectMainDTO.getTitle());
            String headerTitle = "(" + title + ")" + titleProject;

            String leaderProject = messageSource.getMessage("project.add.pic", null, loc).toUpperCase(Locale.ROOT);

            String leaderAccounting = messageSource.getMessage("project.list.pic.accounting", null, loc).toUpperCase(Locale.ROOT);

            String bottomTitle = leaderProject + ": " + projectMainDTO.getLeaderProjectName() + " - "
                    + leaderAccounting + ": " + projectMainDTO.getLeaderAccountingName();

            writeTitle(sheet, 0, headerTitle, 0, 0, 0, 9, 24, "P");
            writeTitle(sheet, 1, strEnd, 1, 1, 0, 9, 14, "D");
            writeTitle(sheet, 2, bottomTitle, 2, 2, 0, 9, 14, "B");

            int rowIndex = 3;

            // Write header
            writeHeaderDayProject(sheet, rowIndex);

            // Write data
            rowIndex++;
            for (DayProjectDTO book : dayProjectDTOS) {

                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBookDayProject(book, row, sheet);
                rowIndex++;
            }

            // Write footer
            //writeFooter(sheet, rowIndex);

            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();

            sheet.autoSizeColumn(numberOfColumn);
            autosizeColumn(sheet, numberOfColumn);

            // Create file excel
            createOutputFile(workbook, excelFilePath);
            System.out.println("Done!!!");
        } catch (Exception e){
            System.out.println("DXD: "+e);
        }

    }


    public void writeHeaderDayProject(Sheet sheet, int rowIndex) {

        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");

        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.stt", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.daytitle", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.spentstatus", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.advanceamount", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.itemnumber", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.projectprice", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.remainamount", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.createdate", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.approve", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(9);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayproject.dayproject.translation", null, loc).toUpperCase(Locale.ROOT));

    }

    // Write data
    private void writeBookDayProject(DayProjectDTO dayProjectDTO, Row row, Sheet sheet){
        if (cellStyleFormatNumber == null){
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            Workbook workbook = row.getSheet().getWorkbook();
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setDataFormat(format);
            cellStyleFormatNumber.setWrapText(true);
            cellStyleFormatNumber.setAlignment(HorizontalAlignment.CENTER);
            cellStyleFormatNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");



        if (dayProjectDTO.getType().equals("translate")){
            Cell cell = row.createCell(0);
            cell.setCellValue(messageSource.getMessage("emp.profile.translation", null, loc).toUpperCase(Locale.ROOT));
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String title = unescapeHtml3(dayProjectDTO.getTitle());
            cell = row.createCell(1);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellStyle(cellStyleFormatNumber);


            cell = row.createCell(5);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(8);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(9);
            cell.setCellStyle(cellStyleFormatNumber);
        }else {
            Cell cell = row.createCell(0);
            cell.setCellValue(dayProjectDTO.getType());
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String title = unescapeHtml3(dayProjectDTO.getTitle());
            cell = row.createCell(1);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellValue(dayProjectDTO.getSpentType());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellValue(dayProjectDTO.getAdvanceAmount());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellValue(dayProjectDTO.getNumOfItem());
            cell.setCellStyle(cellStyleFormatNumber);

            if (dayProjectDTO.getSpentType().equals("Chi tiêu") || dayProjectDTO.getSpentType().equals("spent") || dayProjectDTO.getSpentType().equals("예산지출")){
                dayProjectDTO.setProjectPrice(dayProjectDTO.getProjectPrice()*(-1));
            }

            cell = row.createCell(5);
            cell.setCellValue(dayProjectDTO.getProjectPrice());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellValue(dayProjectDTO.getRemainAmount());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellValue(dayProjectDTO.getRegDt());
            cell.setCellStyle(cellStyleFormatNumber);

            String datProjectStatus = "";

            if (dayProjectDTO.getDayProjectStatus().equals("Y"))
                datProjectStatus = messageSource.getMessage("project.type.approved", null, loc).toUpperCase(Locale.ROOT);
            else
                datProjectStatus = messageSource.getMessage("project.type.not.approve", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(8);
            cell.setCellValue(datProjectStatus);
            cell.setCellStyle(cellStyleFormatNumber);

            String translationStatus = "";
            if (dayProjectDTO.getTranslationStatus().equals("1"))
                translationStatus = messageSource.getMessage("work.detail.finish", null, loc).toUpperCase(Locale.ROOT);
            else
                translationStatus = messageSource.getMessage("work.detail.notfinish", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(9);
            cell.setCellValue(translationStatus);
            cell.setCellStyle(cellStyleFormatNumber);
        }
    }

    // end

    //start export excel for item day project

    public String exportExcelFileDayProjectItem(EmpDTO empDTO, List<DayProjectItemDTO> dayProjectItemDTOS, ProjectMainDTO projectMainDTO, DayProjectDTO dayProjectDTO) throws IOException {
        File destFiles = new File(context.getRealPath("resources/Upload/excel_file/"+ empDTO.getEmpCd()));
        //check file exit
        if (!destFiles.exists()){
            destFiles.mkdirs();
        }
        cellStyleFormatNumber = null;
        String fileName = empDTO.getEmpCd() + "/"+ empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        String excelFilePath = destFiles.getPath() + "/" +empDTO.getEmpCd()  + "-" + java.time.LocalDate.now().toString()
                + "-" +java.time.LocalTime.now().toString().substring(0,8).replace(":", "-") + ".xls";
        writeExcelDayProjectItem(dayProjectItemDTOS, excelFilePath, projectMainDTO, dayProjectDTO);
        return fileName;
    }

    public void writeExcelDayProjectItem(List<DayProjectItemDTO> dayProjectItemDTOS, String excelFilePath, ProjectMainDTO projectMainDTO, DayProjectDTO dayProjectDTO) throws IOException{
        try {
            // Create Workbook
            Workbook workbook = getWorkbook(excelFilePath);
            // Create sheet
            Sheet sheet = workbook.createSheet("ItemDayProject"); // Create sheet with sheet name

            sheet.setColumnWidth(1, 10275);
            sheet.setColumnWidth(8, 3000);

            String langCd = LocaleContextHolder.getLocale().toString();
            Locale loc = new Locale(langCd, "MESSAGES");

            String headerTitle = dayProjectDTO.getRegDt().substring(0, 4)
                    + messageSource.getMessage("project.list.year", null, loc).toUpperCase(Locale.ROOT)
                    + " " + dayProjectDTO.getRegDt().substring(5, 7)
                    + messageSource.getMessage("project.list.month", null, loc).toUpperCase(Locale.ROOT)
                    + " " + dayProjectDTO.getRegDt().substring(8, 10)
                    + messageSource.getMessage("project.list.day", null, loc).toUpperCase(Locale.ROOT)
                    + "(" + unescapeHtml3(dayProjectDTO.getTitle()) + ") "
                    + messageSource.getMessage("projectdetail.list.exceltile", null, loc).toUpperCase(Locale.ROOT);


            writeTitle(sheet, 0, headerTitle, 0, 0, 0, 8, 18, "B");

            String leaderProject = messageSource.getMessage("project.add.pic", null, loc).toUpperCase(Locale.ROOT);

            String leaderAccounting = messageSource.getMessage("project.list.pic.accounting", null, loc).toUpperCase(Locale.ROOT);

            String bottomTitle = leaderProject + ": " + projectMainDTO.getLeaderProjectName() + " - "
                    + leaderAccounting + ": " + projectMainDTO.getLeaderAccountingName();

            writeTitle(sheet, 1, bottomTitle, 1, 1, 0, 8, 14, "B");

            int rowIndex = 2;

            // Write header
            writeHeaderDayProjectItem(sheet, rowIndex);

            // Write data
            rowIndex++;
            for (DayProjectItemDTO book : dayProjectItemDTOS) {

                // Create row
                Row row = sheet.createRow(rowIndex);
                // Write data on row
                writeBookDayProjectItem(book, row, sheet);
                rowIndex++;
            }

            // Write footer
            //writeFooter(sheet, rowIndex);

            // Auto resize column witdth
            int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();

            sheet.autoSizeColumn(numberOfColumn);
            autosizeColumn(sheet, numberOfColumn - 1);

            // Create file excel
            createOutputFile(workbook, excelFilePath);
            System.out.println("Done!!!");
        } catch (Exception e){
            System.out.println("DXD: "+e);
        }

    }

    public void writeHeaderDayProjectItem(Sheet sheet, int rowIndex) {

        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");

        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);

        // Create row
        Row row = sheet.createRow(rowIndex);

        // Create cells
        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.stt", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.namecategory", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.spentstatus", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.price", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.ea", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.amount", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.approve", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.translation", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(8);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("dayprojectitem.detail.picture", null, loc).toUpperCase(Locale.ROOT));

    }

    // Write data
    private void writeBookDayProjectItem(DayProjectItemDTO dayProjectItemDTO, Row row, Sheet sheet) throws IOException {
        Workbook workbook = row.getSheet().getWorkbook();
        if (cellStyleFormatNumber == null){
            // Format number
            short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");
            // DataFormat df = workbook.createDataFormat();
            // short format = df.getFormat("#,##0");

            //Create CellStyle
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);

            Font font = sheet.getWorkbook().createFont();
            font.setFontName("맑은 고딕");
            cellStyleFormatNumber = workbook.createCellStyle();
            cellStyleFormatNumber.setFont(font);

            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setDataFormat(format);
            cellStyleFormatNumber.setWrapText(true);
            cellStyleFormatNumber.setAlignment(HorizontalAlignment.CENTER);
            cellStyleFormatNumber.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");


        if (dayProjectItemDTO.getType().equals("translate")){
            Cell cell = row.createCell(0);
            cell.setCellValue(messageSource.getMessage("emp.profile.translation", null, loc).toUpperCase(Locale.ROOT));
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String productName = unescapeHtml3(dayProjectItemDTO.getProductName());
            cell = row.createCell(1);
            cell.setCellValue(productName);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(5);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(6);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyleFormatNumber);

            //add image to excel

            cell = row.createCell(8);
            cell.setCellStyle(cellStyleFormatNumber);
        } else {
            Cell cell = row.createCell(0);
            cell.setCellValue(messageSource.getMessage("emp.profile.translation", null, loc).toUpperCase(Locale.ROOT));
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String productName = unescapeHtml3(dayProjectItemDTO.getProductName());
            cell = row.createCell(1);
            cell.setCellValue(productName);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellValue(dayProjectItemDTO.getSpentType());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellValue(dayProjectItemDTO.getPrice());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellValue(dayProjectItemDTO.getEa());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(5);
            cell.setCellValue(Math.abs(dayProjectItemDTO.getAmount()));
            cell.setCellStyle(cellStyleFormatNumber);

            String checkStatus = "";
            if (dayProjectItemDTO.getCheckStatus().equals("Y"))
                checkStatus = messageSource.getMessage("project.type.approved", null, loc).toUpperCase(Locale.ROOT);
            else
                checkStatus = messageSource.getMessage("project.type.not.approve", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(6);
            cell.setCellValue(checkStatus);
            cell.setCellStyle(cellStyleFormatNumber);

            String translationStatus = "";
            if (dayProjectItemDTO.getTranslationStatus().equals("1"))
                translationStatus = messageSource.getMessage("work.detail.finish", null, loc).toUpperCase(Locale.ROOT);
            else
                translationStatus = messageSource.getMessage("work.detail.notfinish", null, loc).toUpperCase(Locale.ROOT);

            cell = row.createCell(7);
            cell.setCellValue(translationStatus);
            cell.setCellStyle(cellStyleFormatNumber);

            //add image to excel

            cell = row.createCell(8);
            cell.setCellStyle(cellStyleFormatNumber);
            //cell.getco.s(60);
            FileInputStream stream;

            if(dayProjectItemDTO.getImgPath() == null){
                dayProjectItemDTO.setImgPath("resources/Upload/images/no-image.jpg");
                stream = new FileInputStream( context.getRealPath(dayProjectItemDTO.getImgPath()));
            } else {
                try {
                    System.out.println("DXD: -> " + dayProjectItemDTO.getImgPath());
                    stream = new FileInputStream(context.getRealPath(dayProjectItemDTO.getImgPath()));
                }
                catch (Exception e){
                    dayProjectItemDTO.setImgPath("resources/Upload/images/no-image.jpg");
                    stream = new FileInputStream( context.getRealPath(dayProjectItemDTO.getImgPath()));
                    e.getStackTrace();
                }
            }
            CreationHelper helper = workbook.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();

            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType( ClientAnchor.AnchorType.MOVE_AND_RESIZE );

            int pictureIndex = workbook.addPicture(IOUtils.toByteArray(stream), Workbook.PICTURE_TYPE_PNG);

            anchor.setCol1( 8 );
            anchor.setRow1( row.getRowNum() );
            anchor.setRow2( row.getRowNum() );
            anchor.setCol2( 8 );
            final Picture pict = drawing.createPicture( anchor, pictureIndex );
            pict.resize(1,1);
        }
    }
}
