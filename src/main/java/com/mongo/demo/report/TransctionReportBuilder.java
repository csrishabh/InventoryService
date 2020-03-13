package com.mongo.demo.report;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.mongo.demo.document.Transction;
import com.mongo.utility.Config;

public class TransctionReportBuilder extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> data, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Transction> transctions = (List<Transction>) data.get("transactions");
		response.setHeader("Content-Disposition", "attachment; filename=\"my-xlsx-file.xlsx\"");

		Sheet sheet = workbook.createSheet("Transctions");
		sheet.setDefaultColumnWidth(20);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		CellStyle redRow = workbook.createCellStyle();
		redRow.setFillPattern(CellStyle.SOLID_FOREGROUND);
		redRow.setFillForegroundColor(HSSFColor.RED.index);
		redRow.setBorderRight((short) 1);
		redRow.setRightBorderColor(IndexedColors.BLACK.getIndex());
		redRow.setBorderLeft((short) 1);
		redRow.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		redRow.setBorderTop((short) 1);
		redRow.setTopBorderColor(IndexedColors.BLACK.getIndex());
		redRow.setBorderBottom((short) 1);
		redRow.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		Row header = sheet.createRow(1);

		header.createCell(0).setCellValue("Date");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Product");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Quantity");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Type");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Added By");
		header.getCell(4).setCellStyle(style);

		int rowCount = 2;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		for (Transction trns : transctions) {
			Row aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(formatter.format(trns.getDate()));
			aRow.createCell(1).setCellValue(trns.getProductName().toUpperCase());
			aRow.createCell(2).setCellValue(Config.format(trns.getQuantityBack(), Config.QTY_FORMATTER));
			aRow.createCell(3).setCellValue(trns.getType().toString());
			aRow.createCell(4).setCellValue(trns.getAddBy());
		}

	}

}
