package com.anthem.jiraTool;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcelRead {

	public static void main(String[] args) throws Exception{
		FileInputStream fis=new FileInputStream(new File("C:/Users/ahunjan/Desktop/PBA Handoff Log_10252018.xlsx"));
		XSSFWorkbook workBook=new XSSFWorkbook(fis);
		Sheet sheet=workBook.getSheetAt(0);
		Iterator<Row> rowIter=sheet.iterator();
		while(rowIter.hasNext()){
			Row row=rowIter.next();
			System.out.println(row.getRowNum());
		}
		
	}
}
