package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	 
   
   }
  
  
  @Test
  public void testSetFilePath() {
	  assertThat(DetailedProjectionReport1.GetFilePath(), is(""));
	  DetailedProjectionReport1.setPath(tempPath);
	  assertThat(DetailedProjectionReport1.GetFilePath(), is("C:/users/OutputFiles"));
  }

  @Test 
  public void TestoutputViaCLI() throws IOException {
	  DetailedProjectionReport1.setPath(path);
      assertThat (DetailedProjectionReport1.GetFilePath().toString(), is(path));
      RealPath=Paths.get(XLSX);
      assertThat(DetailedProjectionReport1.GetFilePath(), is(Files.walk(this.RealPath)));
	   
  }
  
  
  
  
  
  
  
  
  
  
 
}