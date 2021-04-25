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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class DetailedProjectionReport {
	
	
	private InputStream file;
	private FileOutputStream fileout;
	
	
	//Default Constructor
	public DetailedProjectionReport () {
		this.filePath="";
		projectionResults = new ArrayList<CourseProjection>();
		//file = getClass().getResourceAsStream("/src/template.xlsx");
		
	
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

	public void addProjection(CourseProjection proj) {
		projectionResults.add(proj);
	}

	public List<CourseProjection> getProjections() {
		return projectionResults;
	}
	
	//takes in output file path and creates an excel workbook based on version specified by CLI. 
	public void outputviaCLI(String filePath) throws IOException, InvalidFormatException
	{
		InputStream templateStream = DetailedProjectionReport.class.getResourceAsStream("/template.xlsx");
		
		try {
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdir();
			}

			output = new File(filePath + "/report.xlsx");
			if (output.createNewFile()) {
				//File was created successfully
			} else {
				//File already exists overwrite it
			}
			}catch (IOException e) {
			System.err.println("Error occured when creating file " + filePath + "/report.xlsx");
		}
		OutputStream out = new BufferedOutputStream(new FileOutputStream(output));
		byte[] buffer = new byte[1024];
		int lengthRead;
		while ((lengthRead = templateStream.read(buffer)) > 0){
			out.write(buffer, 0, lengthRead);
			out.flush();
		}
		XSSFWorkbook wb =new XSSFWorkbook(OPCPackage.open(filePath + "/report.xlsx"));
		XSSFSheet sheet = wb.getSheetAt(0);
		sheet.getRow(2).getCell(0).setCellValue("changed value");
		wb.write(out);
		out.close();
	}

	
	
	private String filePath;
	private String newFilePath;
	private File output;
	private List<CourseProjection> projectionResults;
	
	
}