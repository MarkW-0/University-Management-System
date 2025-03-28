package edu.exampleuni.ums.services;

import java.io.*;
import java.util.*;
import java.util.function.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelService {
	private final XSSFWorkbook workbook;
	final XSSFSheet subjects;
	final XSSFSheet courses;
	final XSSFSheet students;
	final XSSFSheet faculty;
	final XSSFSheet events;
	public ExcelService() {
		String file = "src/main/resources/edu/exampleuni/ums/UMS_Data.xlsx";
		try (InputStream fileIn = new FileInputStream(file)) {
			this.workbook = new XSSFWorkbook(fileIn);
			this.subjects = this.getSheet("Subjects");
			this.courses = this.getSheet("Courses");
			this.students = this.getSheet("Students");
			this.faculty = this.getSheet("Faculty");
			this.events = this.getSheet("Events");

			this.logSheet(this.subjects);
			this.logSheet(this.courses);
			this.logSheet(this.students);
			this.logSheet(this.faculty);
			this.logSheet(this.events);
			//try (OutputStream fileOut = new FileOutputStream(file)) {
			//	this.workbook.write(fileOut);
			//}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private XSSFSheet getSheet(String name) {
		XSSFSheet temp = this.workbook.getSheet(name);
		if (temp == null) temp = this.workbook.createSheet(name);
		return temp;
	}
	private void logSheet(XSSFSheet sheet) {
		System.out.println(this.workbook.getSheetName(this.workbook.getSheetIndex(sheet)));
		for (Row row : sheet) {
			for (Cell cell : row) {
				this.logCell(cell);
			}
			System.out.println();
		}
	}
	private void logCell(Cell cell) {
		switch (cell.getCellType()) {
			case CellType.BLANK:
				System.out.print("BLANK");
				break;
			case CellType.BOOLEAN:
				System.out.print(cell.getBooleanCellValue());
				break;
			case CellType.NUMERIC:
				System.out.print(cell.getNumericCellValue());
				break;
			case CellType.STRING:
				System.out.print(cell.getStringCellValue());
				break;
			case CellType.FORMULA:
				System.out.print(cell.getCellFormula());
				break;
			default:
				throw new RuntimeException("Unknown cell type " + cell.getCellType());
		}
		System.out.print("\t");
	}
	<R> List<R> getData(XSSFSheet sheet, String[] expectedRow, Function<Row,R> fn) {
		List<R> output = new ArrayList<>();
		Row firstRow = sheet.getRow(0);
		if (firstRow == null)
			throw new RuntimeException("Expected column headers");
		for (Row row : sheet) {
			if (row == firstRow) {
				for (int i = 0; i < expectedRow.length; i++) {
					Cell cell = firstRow.getCell(i);
					if(cell.getCellType() != CellType.STRING)
						throw new RuntimeException("Expected column header \"" + expectedRow[i] + "\" got cell type " + cell.getCellType());
					if(!cell.getStringCellValue().equals(expectedRow[i]))
						throw new RuntimeException("Expected column header \"" + expectedRow[i] + "\" got \"" + cell.getStringCellValue() + "\"");
				}
			} else output.add(fn.apply(row));
		}
		return output;
	}
}