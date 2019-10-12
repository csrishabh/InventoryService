package com.mongo.demo.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.mongo.demo.document.CaseSearchResult;
import com.mongo.demo.document.CaseStatus;
import com.mongo.demo.document.CrownDetail;

public class VendorReportBuilder extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List<CaseSearchResult> cases = (List<CaseSearchResult>) data.get("report");
		response.setHeader("Content-Disposition", "attachment; filename=\"my-xlsx-file.xlsx\"");
		Sheet sheet = workbook.createSheet("Vendor Report");
		sheet.setDefaultColumnWidth(25);
		CreationHelper createHelper = workbook.getCreationHelper();
		
		 // create style for case cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        
        // create style for cancel case cells
        CellStyle canceledStyle = workbook.createCellStyle();
        Font cancedFont = workbook.createFont();
        cancedFont.setFontName("Arial");
        canceledStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        canceledStyle.setFillForegroundColor(HSSFColor.BROWN.index);
        cancedFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cancedFont.setColor(HSSFColor.WHITE.index);
        canceledStyle.setFont(cancedFont);
        
        // create style for alrady Paid case cells
        CellStyle paidStyle = workbook.createCellStyle();
        Font paidFont = workbook.createFont();
        paidFont.setFontName("Arial");
        paidStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        paidStyle.setFillForegroundColor(HSSFColor.GOLD.index);
        paidFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        paidFont.setColor(HSSFColor.WHITE.index);
        paidStyle.setFont(paidFont);
        
        // create style for header cells
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setFontName("Arial");
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.WHITE.index);
        headerStyle.setFont(headerFont);
        
        int rowCount = 0;
        
        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("OPD NO.");
        header.createCell(1).setCellValue("Patient Name");
        header.createCell(2).setCellValue("Status");
        header.createCell(3).setCellValue("Sub Status");
        header.createCell(4).setCellValue("Booking Date");
        header.createCell(5).setCellValue("Remark");
        header.getCell(0).setCellStyle(headerStyle);
        header.getCell(1).setCellStyle(headerStyle);
        header.getCell(2).setCellStyle(headerStyle);
        header.getCell(3).setCellStyle(headerStyle);
        header.getCell(4).setCellStyle(headerStyle);
        header.getCell(5).setCellStyle(headerStyle);
        
        rowCount++;
        
        for (CaseSearchResult result : cases) {
        	if(!result.getStatus().equals(CaseStatus.BOOKED) && !result.getStatus().equals(CaseStatus.INPROCESS)){
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(result.getId());
            aRow.createCell(1).setCellValue(result.getPatientName());
            aRow.createCell(2).setCellValue(result.getStatus());
            aRow.createCell(3).setCellValue(result.getSubStatus());
            aRow.createCell(4).setCellValue(result.getBookingDate());
            if(result.getStatus().equals(CaseStatus.CANCELED)) {
            	 aRow.getCell(0).setCellStyle(canceledStyle);
                 aRow.getCell(1).setCellStyle(canceledStyle);
                 aRow.getCell(2).setCellStyle(canceledStyle);
                 aRow.getCell(3).setCellStyle(canceledStyle);
                 aRow.getCell(4).setCellStyle(canceledStyle);
                 aRow.createCell(5).setCellValue("CANCELED");
                 aRow.getCell(5).setCellStyle(canceledStyle);
            }
            else if(result.isAlreadyPaid()) {
            	 aRow.getCell(0).setCellStyle(paidStyle);
                 aRow.getCell(1).setCellStyle(paidStyle);
                 aRow.getCell(2).setCellStyle(paidStyle);
                 aRow.getCell(3).setCellStyle(paidStyle);
                 aRow.getCell(4).setCellStyle(paidStyle);
                 aRow.createCell(5).setCellValue("Already Paid");
                 aRow.getCell(5).setCellStyle(paidStyle);
            }
            else {
            	aRow.getCell(0).setCellStyle(style);
            	aRow.getCell(1).setCellStyle(style);
            	aRow.getCell(2).setCellStyle(style);
            	aRow.getCell(3).setCellStyle(style);
            	aRow.getCell(4).setCellStyle(style);
            }
            for(CrownDetail cd : result.getCrown().getDetails()) {
            	 Row cRow = sheet.createRow(rowCount++);
            	 cRow.createCell(0).setCellValue(cd.getType().getName());
            	 cRow.createCell(1).setCellValue(cd.getCrownNo());
            	 cRow.createCell(2).setCellValue(cd.getShade());
            }
            
            rowCount++;
           
        	}
        }
        
	}

}
