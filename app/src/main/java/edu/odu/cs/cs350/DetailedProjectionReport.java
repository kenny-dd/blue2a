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
import java.util.Collections;
import java.util.List;
import java.util.Set;


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
		Set<Double> keys = cp.getHistoricValuesList().keySet();
		List<Double> toSort = new ArrayList<>();
		for (Double key : keys) {
			toSort.add(key);
		}

		Collections.sort(toSort);

		int index = 1;
		for (Double ind : toSort) {
			Row row = sheet.createRow(index);
			row.createCell(0);
			row.createCell(1);
			sheet.getRow(index).getCell(0).setCellValue(ind);
			sheet.getRow(index).getCell(1).setCellValue(cp.getHistoricValue(ind));
			index++;
		}
	}

	public void LoadCurrentValues(XSSFSheet sheet, CourseProjection cp) {

		Set<Double> keys = cp.getCurrentValues().keySet();
		List<Double> toSort = new ArrayList<>();
		for (Double key : keys) {
			toSort.add(key);
		}

		Collections.sort(toSort);

		int index = 1;
		for(Double ind : toSort) {
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

		Set<Double> keys = cp.getProjections().keySet();
		List<Double> toSort = new ArrayList<>();
		for (Double key : keys) {
			toSort.add(key);
		}

		Collections.sort(toSort);

		for(Double ind : toSort) {
			Row row = sheet.getRow(index);
			row.createCell(4);
			row.createCell(5);
			sheet.getRow(index).getCell(4).setCellValue(ind);
			sheet.getRow(index).getCell(5).setCellValue(cp.getProjectionCount(ind));
			index++;

		}
	}

	public void GenerateProjectionGraph(XSSFSheet projectionData, CourseProjection cp) {
		int currentVals = cp.getCurrentValues().size();
		int projVals = cp.getProjections().size();
		XDDFNumericalDataSource<Double> dHistorical = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, 100,0,0));
		XDDFNumericalDataSource<Double> Semester1 = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, 100,1,1));

		XDDFNumericalDataSource<Double> dCurrent = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, currentVals,2,2));
		XDDFNumericalDataSource<Double>  Semester2 = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, currentVals,3,3));

		XDDFNumericalDataSource<Double> dProjected = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, projVals,4,4));
		XDDFNumericalDataSource<Double> Projected = XDDFDataSourcesFactory.fromNumericCellRange(projectionData, new CellRangeAddress(1, projVals,5,5));

		XSSFDrawing draw = projectionData.createDrawingPatriarch();
		XSSFClientAnchor anchor = draw.createAnchor(0,0,0,0,0,5,10,15);

		XSSFChart projection = draw.createChart(anchor);
		projection.setTitleText("Course Enrollment Projection");

		XDDFChartLegend legend = projection.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);

		XDDFCategoryAxis xAxis = projection.createCategoryAxis(AxisPosition.BOTTOM);
		xAxis.setTitle("Enrollment Period Elapsed");

		XDDFValueAxis yAxis = projection.createValueAxis(AxisPosition.LEFT);
		yAxis.setCrosses(AxisCrosses.AUTO_ZERO);
		yAxis.setTitle("Total Enrollment");



		XDDFChartData graph = projection.createData(ChartTypes.SCATTER, xAxis, yAxis);

		//plot historical data against semester enrollments
		XDDFChartData.Series histSeries = graph.addSeries(dHistorical, Semester1);
		histSeries.setTitle("Historical", new CellReference(projectionData.getSheetName(),0,1,true,true));

		//plot current data against current enrollments
		XDDFChartData.Series currSeries = graph.addSeries(dCurrent, Semester2);
		currSeries.setTitle("Current", new CellReference(projectionData.getSheetName(),0,3,true,true));

		//plot projected enrollments
		XDDFChartData.Series projSeries = graph.addSeries(dProjected, Projected);
		projSeries.setTitle("Projected", new CellReference(projectionData.getSheetName(),0,5,true,true));

		projection.plot(graph);
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
			GenerateProjectionGraph(sheet, cp);
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