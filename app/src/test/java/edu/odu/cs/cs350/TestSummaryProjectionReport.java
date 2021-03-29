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

	private SummaryProjectionReport spr;

	@BeforeEach
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	    spr = new SummaryProjectionReport();
	}
	
	@Test
	void testConstructor() {
		assertEquals(0, spr.getProjectionResults().size());
	}

	@Test
	void testAdd() {
		CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);
		
		spr.addCourse(cp);
		assertTrue(spr.getProjectionResults().contains(cp));
		assertEquals(1, spr.getProjectionResults().size());
	}

	//Test that courses are added in order by name
	@Test
	void testAddOrdered() {
		CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);
		CourseProjection cp1 = new CourseProjection("CS121G", 32, 86, 100);
		CourseProjection cp2 = new CourseProjection("CS170G", 55, 88, 75);

		spr.addCourse(cp1);
		spr.addCourse(cp);
		spr.addCourse(cp2);

		assertEquals(3, spr.getProjectionResults().size());

		spr.displayProjectionResults();
		assertEquals("76% of enrollment period has elapsed.\r\n"
						+ " Course Enrollment Projected Cap\r\n"
						+ " CS120G 46         104       120\r\n"
						+ " CS121G 32         86        100\r\n"
						+ "*CS170G 55         88        75 \r\n",
				outputStreamCaptor.toString());
	}
	
	@Test
	void testDisplayResults(){
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
