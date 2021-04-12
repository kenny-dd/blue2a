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
	
	
	private InputStream file;
	private FileOutputStream fileout;
	
	
	//Default Constructor
	public DetailedProjectionReport () {
		
		this.filePath="";
		file = getClass().getResourceAsStream("template.xlsx");
		
	
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
		
		output = new File(filePath+"/report.xlsx");
		
		
		try {
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdir();
			}


			if (output.createNewFile()) {
				//File was created successfully
			} else {
				//File already exists overwrite it
			}
			}catch (IOException e) {
			System.err.println("Error occured when creating file " + filePath + "/report");
		}
	
		fileout = new FileOutputStream(filePath+"/report.xlsx");
		int c;
		
		while((c=file.read()) != -1){
			fileout.write(c);
		}
	
	
	
	}

	
	
	private String filePath;
	private String newFilePath;
	private File output;
	
	
}