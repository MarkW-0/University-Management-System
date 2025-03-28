package edu.exampleuni.ums;

import java.util.*;
import java.io.*;

import edu.exampleuni.ums.services.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelDemo {
	public static void main(String[] args) {
		//String exel_demo = "exel_demo.xlsx";
		//String ums_data = "src/main/resources/edu/exampleuni/ums/UMS_Data.xlsx";
		new ExcelService();
		/*
		try {
			//writeExcelDemo(exel_demo);
		} catch (IOException e) {
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		*/
	}
	public static void writeExcelDemo(String exel) throws IOException {
		//Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		//Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee Data");

		//Prepare data to be written as an Object[]
		Map<String, Object[]> data = new TreeMap<>();
		data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
		data.put("2", new Object[] {1, "Amit", "Shukla"});
		data.put("3", new Object[] {2, "Lokesh", "Gupta"});
		data.put("4", new Object[] {3, "John", "Adwards"});
		data.put("5", new Object[] {4, "Brian", "Schultz"});

		//Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof String) cell.setCellValue((String)obj);
				else if(obj instanceof Integer) cell.setCellValue((Integer)obj);
			}
		}

		//Write the workbook in file system

			FileOutputStream out = new FileOutputStream(exel);
			workbook.write(out);
			out.close();
			System.out.println(exel + " written successfully on disk.");
	}
}
