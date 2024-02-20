package kr.co.hs.daywork.service;

import kr.co.hs.daywork.dto.DayWorkDTO;
import kr.co.hs.emp.dto.EmpDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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
public class WorkExportExcel {
    @Autowired
    ServletContext context;

    @Autowired
    private MessageSource messageSource;

    private static CellStyle cellStyleFormatNumber = null;
    private static CellStyle cellStyleFormatNumberCustom = null;

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

    // start export excel
    public String exportExcelFile(EmpDTO empDTO, List myDayWork) throws IOException {
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
        writeExcel(myDayWork, excelFilePath);
        return fileName;
    }

    public void writeExcel(List<DayWorkDTO> dayWorkDTOS, String excelFilePath) throws IOException{
        try {
            // Create Workbook
            Workbook workbook = getWorkbook(excelFilePath);
            // Create sheet
            Sheet sheet = workbook.createSheet("MyDayWork"); // Create sheet with sheet name

            sheet.setColumnWidth(5, 10275);

            String langCd = LocaleContextHolder.getLocale().toString();
            Locale loc = new Locale(langCd, "MESSAGES");


            int rowIndex = 0;

            // Write header
            writeHeader(sheet, rowIndex);

            // Write data
            rowIndex++;
            for (DayWorkDTO book : dayWorkDTOS) {

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
    // Auto resize column width
    private void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            if (columnIndex!= 5)
                sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    private void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
        try (OutputStream os = new FileOutputStream(excelFilePath)) {
            workbook.write(os);
        }
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
        cell.setCellValue(messageSource.getMessage("work.list.empname", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.dept", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.position", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.title", null, loc).toUpperCase(Locale.ROOT));

        //////

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.detail.contents", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.registerDate", null, loc).toUpperCase(Locale.ROOT));

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(messageSource.getMessage("work.list.translationstatus", null, loc).toUpperCase(Locale.ROOT));

    }

    private void writeBook(DayWorkDTO dayWorkDTO, Row row, Sheet sheet){
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

            cellStyleFormatNumberCustom = workbook.createCellStyle();
            cellStyleFormatNumberCustom.setFont(font);
            cellStyleFormatNumberCustom.setBorderTop(BorderStyle.THIN);
            cellStyleFormatNumberCustom.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumberCustom.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumberCustom.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumberCustom.setDataFormat(format);
            cellStyleFormatNumberCustom.setWrapText(true);
            cellStyleFormatNumberCustom.setAlignment(HorizontalAlignment.LEFT);
            cellStyleFormatNumberCustom.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        String langCd = LocaleContextHolder.getLocale().toString();
        Locale loc = new Locale(langCd, "MESSAGES");

        if (dayWorkDTO.getType().equals("translate")){
            Cell cell = row.createCell(0);
            cell.setCellValue(messageSource.getMessage("emp.profile.translation", null, loc).toUpperCase(Locale.ROOT));
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            cell = row.createCell(1);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            String title = unescapeHtml3(dayWorkDTO.getTitle());
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);

            //

            cell = row.createCell(5);
            String content = unescapeHtml3(dayWorkDTO.getContents());
            if (content.equals("-")){
                cell.setCellValue(content);
                cell.setCellStyle(cellStyleFormatNumber);
            }else {
                cell.setCellValue(content);
                cell.setCellStyle(cellStyleFormatNumberCustom);
                cell.getRow().setHeight((short) -1);
            }

            cell = row.createCell(6);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(7);
            cell.setCellStyle(cellStyleFormatNumber);

        }else {
            Cell cell = row.createCell(0);
            cell.setCellValue(dayWorkDTO.getType());
            cell.setCellStyle(cellStyleFormatNumber);
            cell.getRow().setHeightInPoints(60);

            String title = unescapeHtml3(dayWorkDTO.getEmpName());
            cell = row.createCell(1);
            cell.setCellValue(title);
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(2);
            cell.setCellValue(dayWorkDTO.getDeptName().replace("<br>", "\n"));
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(3);
            cell.setCellValue(dayWorkDTO.getPositionName());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(4);
            cell.setCellValue(dayWorkDTO.getTitle());
            cell.setCellStyle(cellStyleFormatNumber);

            cell = row.createCell(5);
            cell.setCellValue(dayWorkDTO.getContents());
            cell.setCellStyle(cellStyleFormatNumberCustom);
            //cell.getRow().setHeightInPoints(sheet.getDefaultRowHeight());
            cell.getRow().setHeight((short) -1);


            cell = row.createCell(6);
            cell.setCellValue(dayWorkDTO.getRegDt());
            cell.setCellStyle(cellStyleFormatNumber);

            String type = "";
            int checkTrans = Integer.parseInt(dayWorkDTO.getTranslationStatus());
            if(checkTrans > 0){
                type =messageSource.getMessage("work.detail.finish", null, loc);
            } else {
                type =messageSource.getMessage("work.detail.notfinish", null, loc);
            }
            cell = row.createCell(7);
            cell.setCellValue(type);
            cell.setCellStyle(cellStyleFormatNumber);
        }
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
}
