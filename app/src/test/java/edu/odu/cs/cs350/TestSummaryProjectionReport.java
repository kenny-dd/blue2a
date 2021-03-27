package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestSummaryProjectionReport {
	private final PrintStream standardOut = System.out;
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
	
	@Test
	void testConstructor() {
		SummaryProjectionReport spr = new SummaryProjectionReport();
		
		assertEquals(0, spr.getProjectionResults().size());
	}

	@Test
	void testAdd() {
		CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);
		SummaryProjectionReport sp = new SummaryProjectionReport();
		
		sp.addCourse(cp);
		assertTrue(sp.getProjectionResults().contains(cp));
		assertEquals(1, sp.getProjectionResults().size());
	}
	
	@Test
	void testDisplayResults(){
		SummaryProjectionReport spr = new SummaryProjectionReport();
		CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);
		CourseProjection cpr = new CourseProjection("CS121G", 50, 130, 110);

		spr.addCourse(cp);
		spr.addCourse(cpr);

		spr.displayProjectionResults();
		assertEquals("76% of enrollment period has elapsed.\r\n"
				+ " Course Enrollment Projected Cap\r\n"
				+ " CS120G 46         104       120\r\n" 
				+ "*CS121G 50         130       110\r\n", 
				outputStreamCaptor.toString());
	}   
	
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}


}
