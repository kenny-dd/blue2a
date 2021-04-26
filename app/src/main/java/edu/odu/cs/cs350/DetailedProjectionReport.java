package edu.odu.cs.cs350;

import java.io.*;
import java.io.FileOutputStream;
import java.io.Console;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
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

	public void LoadHistoricValues(XSSFSheet sheet, CourseProjection cp) {
		int index = 1;
		for (Double ind : cp.getHistoricValuesList().keySet()) {
			Row row = sheet.createRow(index);
			row.createCell(0);
			row.createCell(1);
			sheet.getRow(index).getCell(0).setCellValue(ind);
			sheet.getRow(index).getCell(1).setCellValue(cp.getHistoricValue(ind));
			index++;
		}
	}
	
	public void LoadCurrentValues(XSSFSheet sheet, CourseProjection cp) {

		int index = 1;
		for(Double ind : cp.getCurrentValues().keySet()) {
			Row row = sheet.getRow(index);
			row.createCell(2);
			row.createCell(3);
			sheet.getRow(index).getCell(2).setCellValue(ind);
			sheet.getRow(index).getCell(3).setCellValue(cp.getCurrentValue(ind));
			index++;
		
		}
	}

	public void LoadProjectedValues(XSSFSheet sheet, CourseProjection cp) {
		int index = 1;
		for(Double ind : cp.getProjections().keySet()) {
			Row row = sheet.getRow(index);
			row.createCell(4);
			row.createCell(5);
			sheet.getRow(index).getCell(4).setCellValue(ind);
			sheet.getRow(index).getCell(5).setCellValue(cp.getProjectionCount(ind));
			index++;
		
		}
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
		
		OutputStream fileout = new FileOutputStream(filePath + "/report.xlsx");

		XSSFWorkbook wb =new XSSFWorkbook(templateStream);
		for (CourseProjection cp : projectionResults) {
			XSSFSheet sheet = wb.cloneSheet(0, cp.getName());
			LoadHistoricValues(sheet, cp);
			LoadCurrentValues(sheet, cp);
			LoadProjectedValues(sheet, cp);
		}
		wb.removeSheetAt(0);
		wb.write(fileout);
		fileout.close();
	}

	
	
	private String filePath;
	private String newFilePath;
	private File output;
	private List<CourseProjection> projectionResults;

}