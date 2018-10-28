package com.anthem.jiraTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HandOffExcelReader {

	private String inputFileName="";
	public HandOffExcelReader(String handOffFileName){
		inputFileName=handOffFileName;
	}
	
	public Set<String> getJiraIssues() throws IOException{
		FileInputStream fin=null;
		HashSet<String> jiraIssuesSet=new HashSet<>();
		try {
			fin=new FileInputStream(new File(inputFileName));
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found : "+inputFileName);
		}
		
		XSSFWorkbook workbook=new XSSFWorkbook(fin);
		Sheet sheet=workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator=sheet.iterator();
		rowIterator.next();
		rowIterator.next();
		Row headerRow=rowIterator.next();
		
		int eCEMIndex=0,jiraTicketNumberIndex=0;
		
		Iterator<Cell> cellIterator=headerRow.cellIterator();
		while(cellIterator.hasNext()){
			Cell cell=cellIterator.next();
			String cellValue=cell.getStringCellValue();
			if(cellValue.equals("JIRA Ticket #"))
				jiraTicketNumberIndex=cell.getColumnIndex();
			if(cellValue.equals("eCEM Needed?"))
				eCEMIndex=cell.getColumnIndex();
		}
		
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			Cell eCEMCell=row.getCell(eCEMIndex);
			if(eCEMCell!=null && eCEMCell.getStringCellValue().equalsIgnoreCase("yeS")){
				Cell jiraTicketNumberCell=row.getCell(jiraTicketNumberIndex);
				jiraIssuesSet.add(jiraTicketNumberCell.getStringCellValue());
			}
		}
		
		return jiraIssuesSet;
	}
}

