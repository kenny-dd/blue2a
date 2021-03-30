package edu.odu.cs.cs350;

import java.io.*;
import java.io.FileOutputStream;
import java.io.Console;
import java.io.IOException;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DetailedProjectionReport {
	
	//Default Constructor
	public DetailedProjectionReport () {
		
		this.filePath="";
		
		
	}
	//gets and sets output file path 
	public String GetFilePath() {
		return filePath;
	}
	
	public void setPath(String newFilePath) {
		
		this.filePath=newFilePath;
	
	}
	
	
	
	
	public void GenerateHistoricalGraph() {
		
		
		
		
	}
	
	public void GenerateCurrentGraph() {
		
		
		
	}
	
	
	public void GenerateProjectionGraph() {
		
		
		
	}
	
	//takes in output file path and creates an excel workbook based on version specified by CLI. 
	public static void outputviaCLI(String filePath) throws IOException
	{
		
		Console c = System.console();
		
		if (c == null) {
			
			System.out.println("console not activated");
			System.exit(1);
		}
		
		filePath=c.readLine("Please specify a Location for the output file: ");
		
		//filePath=Paths.get(tempfilePath);
		
		
		//  Workbook workbook = null;
		  
		    if (filePath.endsWith("xlsx")) {
		 //       workbook = new XSSFWorkbook(filePath);
		    } else if (filePath.endsWith("xls")) {
		  //      workbook = new HSSFWorkbook(filePath);
		    } else {
		        throw new IllegalArgumentException("The specified file is not Excel file");
		    }
		 
		  //  return workbook;
		
	}
	
	private String filePath;
	private String newFilePath;
	
	
}