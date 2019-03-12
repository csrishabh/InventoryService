package com.mongo.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class Test {
	
	public static final String SAMPLE_XLSX_FILE_PATH = "C:\\Users\\Q1018567\\Downloads\\FINALLL SHEETT.xlsx";

    public static void main(String[] args) throws IOException, InvalidFormatException, EncryptedDocumentException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        // 3. Or you can use a Java 8 forEach with lambda
        System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
        List<MySheet> sheets = new ArrayList<>();
        int row_count = 0;
        for (Row row: sheet) {
        	System.out.println(row_count++);
        	if(sheets.size()>881) {
        		break;
        	}
        	MySheet mySheet = new MySheet();
        	int i = 0;
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                if(i == 0) {
                	mySheet.setFileName(cellValue);
                	i++;
                }
                else if(i == 1){
                	mySheet.setStart(Integer.parseInt(cellValue));
                	i++;
                }
                else if (i == 2) {
                	mySheet.setEnd(Integer.parseInt(cellValue));
                	i++;
                }
            }
            sheets.add(mySheet);
        }

        String fData = readFile("C:\\Users\\Q1018567\\Downloads\\TO SEND\\E_scaffolds_"+sheets.get(0).getFileName()+".fa");
        // Closing the workbook
        workbook.close();
    }
    
    
    
    private static String readFile(String fileName) throws IOException {
    	
    	File file = new File(fileName);
    	FileInputStream fis = new FileInputStream(file);
    	byte[] data = new byte[(int) file.length()];
    	fis.read(data);
    	fis.close();

    	return new String(data, "UTF-8");
    }
	
}
