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

import com.mongo.demo.document.Product;


public class PaymentReportBuilder extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest arg2,
			HttpServletResponse response) throws Exception {
		List<Product> products = (List<Product>) data.get("products");
		response.setHeader("Content-Disposition", "attachment; filename=\"my-xlsx-file.xlsx\"");
		// create a new Excel sheet
        Sheet sheet = workbook.createSheet("Inventory Sheet");
        sheet.setDefaultColumnWidth(20);
        
        //for cell formatting
        CreationHelper createHelper = workbook.getCreationHelper();
        
     // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
         
        // create header row
        Row header = sheet.createRow(1);
         
        header.createCell(0).setCellValue("Name");
        header.getCell(0).setCellStyle(style);
         
        header.createCell(1).setCellValue("Quantity");
        header.getCell(1).setCellStyle(style);
         
        header.createCell(2).setCellValue("Min Quantity");
        header.getCell(2).setCellStyle(style);
           
        // create data rows
        int rowCount = 2;
        
        for (Product product : products) {
            Row aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(product.getName());
            aRow.createCell(1).setCellValue(product.getQtyAbl());
            aRow.createCell(2).setCellValue(product.getAlert());
        }
    }

}
