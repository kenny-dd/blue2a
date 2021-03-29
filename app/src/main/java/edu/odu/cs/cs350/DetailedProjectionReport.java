package edu.odu.cs.cs350;

import java.io.FileOutputStream;
import java.io.Console;
import java.io.IOException;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DetailedProjectionReport {
	
	
	public DetailedProjectionReport () {
		
		this.filePath="";
		
		
	}
	
	public String GetFilepath() {
		return filePath;
	}
	
	public void setPath(String newFilePath) {
		
		this.filePath=newFilePath;
	}
	
	
	
	
	public static void GenerateHistoricalGraph() {
		
		
		
		
	}
	
	public static void GenerateCurrentGraph() {
		
		
		
	}
	
	
	public static void GenerateProjectionGraph() {
		
		
		
	}
	
	
	public static void outputviaCLI(String filePath) throws IOException
	{
		
		Console c = System.console();
		
		if (c == null) {
			
			System.out.println("console not activated");
			System.exit(1);
		}
		
		filePath= c.readLine("Please specify a Location for the output file: ");
		
		//  Workbook workbook = null;
		  
		    if (filePath.endsWith("xlsx")) {
		 //       workbook = new XSSFWorkbook(filePath);
		  //  } else if (filename.endsWith("xls")) {
		  //      workbook = new HSSFWorkbook(filePath);
		    } else {
		        throw new IllegalArgumentException("The specified file is not Excel file");
		    }
		 
		  //  return workbook;
		
	}
	
	private String filePath;
	
	
	
}