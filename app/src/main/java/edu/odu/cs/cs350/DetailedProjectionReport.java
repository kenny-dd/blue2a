package edu.odu.cs.cs350;

import java.io.*;
import java.io.FileOutputStream;
import java.io.Console;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
	public void outputviaCLI(String filePath) throws IOException
	{
		XSSFWorkbook workbook;
		
		try {
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdir();
			}

			
			workbook = new XSSFWorkbook(filePath + "/report");
			//output = workbook;

			if (output.createNewFile()) {
				//File was created successfully
			} else {
				//File already exists overwrite it
			}
		} catch (IOException e) {
			System.err.println("Error occured when creating file " + filePath + "/report");
		}

		//Come back to this later
	  	//XSSFWorkbook workbook;

		//if (filePath.endsWith("xlsx")) {
		    //workbook = new XSSFWorkbook(filePath);
		//} else if (filePath.endsWith("xls")) {
		   // workbook = new HSSFWorkbook();
	//}// else {
		//throw new IllegalArgumentException("The specified file is not Excel file");
	//}

	   //return workbook;
		
	}
	
	private String filePath;
	private String newFilePath;
	private File output;
	
	
}