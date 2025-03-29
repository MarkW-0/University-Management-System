package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.*;
import java.util.function.Function;

public abstract class Service<T extends Model> {
	private final List<T> data;

	public Service(List<T> data) {
		this.data = data;
	}

	public Service(ExcelService excelService, XSSFSheet sheet, Function<Row, T> fn, String[] expectedFirstRow) {
		this.data = excelService.getData(sheet, expectedFirstRow, fn);
	}

	public List<T> getAll() { return new ArrayList<>(data); }
	public void add(T thing) { data.add(thing); }
	public void delete(T thing) { data.remove(thing); }

	// Method to update
	public void update(T updated) {
		for (int i = 0; i < data.size(); i++) {
			T thing = data.get(i);
			if (thing.isEqual(updated)) {
				data.set(i, updated);
				break;
			}
		}
	}
}