package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.soap.Detail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDetailedProjectionReport {

    // TODO  Any data members that you want to share among different tests
    public DetailedProjectionReport DetailedProjectionReport1;
    public DetailedProjectionReport DetailedProjectionReport2;
    public String path;
    public Path RealPath;
    public String tempPath;
    public String XLSX;

    @BeforeEach
    public void setUp() throws Exception {

        DetailedProjectionReport1= new DetailedProjectionReport();
        tempPath= "C:/users/OutputFiles";
        XLSX="C:users/OutPutFiles/test.xlsx";
        //path = Paths.get(tempPath);
    }

  
   @Test 
   public void TestDPRConstructor () {
        assertThat (DetailedProjectionReport1.GetFilePath(), is(""));
        assertThat(DetailedProjectionReport1.getProjections().size(), is(0));
   }
  
  
    @Test
    public void testSetFilePath() {
        assertThat(DetailedProjectionReport1.GetFilePath(), is(""));
        DetailedProjectionReport1.setPath(tempPath);
        assertThat(DetailedProjectionReport1.GetFilePath(), is("C:/users/OutputFiles"));
        assertThat(DetailedProjectionReport1.getProjections().size(), is(0));
    }

    @Test
    public void TestAddProjection() {
        CourseProjection cp = new CourseProjection();
        DetailedProjectionReport1.addProjection(cp);

        assertThat(DetailedProjectionReport1.GetFilePath(), is(""));
        assertThat(DetailedProjectionReport1.getProjections().size(), is(1));
    }

    @Test
    public void TestoutputViaCLI() throws IOException {
        CourseProjection cp = new CourseProjection("CS120G", 120);
        cp.addCurrentValue(0.5, 50);
        cp.addHistoricValue(0.5, 40);
        cp.addHistoricValue(1.0, 100);
        cp.makeProjection();
        DetailedProjectionReport1.addProjection(cp);

        try {
            DetailedProjectionReport1.outputviaCLI("src/test/reports");
        } catch (Exception e) {
            assertEquals(0, 1, "Exception thrown: " + e.getMessage());
        }

        File file = new File("src/test/reports/report.xlsx");


        assertThat(DetailedProjectionReport1.GetFilePath(), is(""));
        assertThat(DetailedProjectionReport1.getProjections().size(),is(1));
        assertTrue(file.exists());

    }
    
    @Test 
    public void TestGenerateHistoricalGraph() throws InvalidFormatException, IOException {

        //add stub data to Detailed projection Report
        CourseProjection cp = new CourseProjection("CS120G", 120);
        cp.addCurrentValue(0.5, 50);
        cp.addHistoricValue(0.5, 40);
        cp.addHistoricValue(1.0, 100);
        cp.makeProjection();
		
        DetailedProjectionReport1.addProjection(cp);
        
        try {   
        	DetailedProjectionReport1.outputviaCLI("src/test/reports/");        	
        }
        catch (Exception e) {
        	assertEquals(0, 1, "Exception thrown: " + e.getMessage());
        }
		
        XSSFWorkbook wb = new XSSFWorkbook(OPCPackage.open("src/test/reports/report.xlsx"));
		XSSFSheet sheet = wb.getSheetAt(0);
		
    	//assert that data in report matches stub data
    	assertThat(sheet.getRow(1).getCell(0).getNumericCellValue(), is(0.5));
    	assertThat(sheet.getRow(1).getCell(1).getNumericCellValue(), is(40.0));
	
    }

}