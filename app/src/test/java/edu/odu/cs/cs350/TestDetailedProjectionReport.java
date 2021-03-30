package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class TestDetailedProjectionReport {

    // TODO  Any data members that you want to share among different tests	
	
 public DetailedProjectionReport DetailedProjectionReport1;
 public DetailedProjectionReport DetailedProjectionReport2;
public String path;
	
	
	
  @BeforeEach
  public void setUp() throws Exception {
	  
  DetailedProjectionReport1= new DetailedProjectionReport();
  path = "C:/users/OutputFiles";
  
  
  }

  
   @Test 
   public void TestDPRConstructor () {
	 
	 assertThat (DetailedProjectionReport1.GetFilepath(), is(""));
	 
   
   }
  
  
  
  
  
  @Test
  public void testSetFilePath() {
	  assertThat(DetailedProjectionReport1.GetFilepath(), is(""));
	  DetailedProjectionReport1.setPath(path);
	  assertThat(DetailedProjectionReport1.GetFilepath(), is("C:/users/OutputFiles"));
  
  
  }


 
}