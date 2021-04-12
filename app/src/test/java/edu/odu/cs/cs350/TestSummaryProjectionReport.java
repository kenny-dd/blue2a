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
		CourseProjection cp = new CourseProjection("CS120G", 120);
		
		spr.addCourse(cp);
		assertTrue(spr.getProjectionResults().contains(cp));
		assertEquals(1, spr.getProjectionResults().size());
	}

	//Test that courses are added in order by name
	@Test
	void testAddOrdered() {
		CourseProjection cp = new CourseProjection("CS120G", 120);
		CourseProjection cp1 = new CourseProjection("CS121G", 100);
		CourseProjection cp2 = new CourseProjection("CS170G", 75);

		spr.addCourse(cp1);
		spr.addCourse(cp);
		spr.addCourse(cp2);

		assertEquals(3, spr.getProjectionResults().size());

		spr.displayProjectionResults("2021-01-01", "2021-01-31", "2021-01-22");
		
		assertEquals(70 + "% of enrollment period has elapsed." + System.lineSeparator()
						+ " Course Enrollment Projected Cap" + System.lineSeparator()
						+ " CS120G 0          0         120" + System.lineSeparator()
						+ " CS121G 0          0         100" + System.lineSeparator()
						+ " CS170G 0          0         75 " + System.lineSeparator(),
				outputStreamCaptor.toString());
	}
	
	@Test
	void testDisplayResults(){
		CourseProjection cp = new CourseProjection("CS120G", 120);
		CourseProjection cpr = new CourseProjection("CS121G", 110);

		cpr.addCurrentValue(0.5, 50);
		cpr.addHistoricValue(0.5, 40);
		cpr.addHistoricValue(1.0, 104);
		cpr.makeProjection();

		spr.addCourse(cp);
		spr.addCourse(cpr);

		spr.displayProjectionResults("2021-01-01", "2021-01-31", "2021-01-22");
		
		assertEquals(70 + "% of enrollment period has elapsed." + System.lineSeparator()
				+ " Course Enrollment Projected Cap" + System.lineSeparator()
				+ " CS120G 0          0         120" + System.lineSeparator()
				+ "*CS121G 50         130       110" + System.lineSeparator(),
				outputStreamCaptor.toString());
	}   
	
	// Test that percentage calculated is correct
		@Test
		void testPercentageCalculation() {
			// Call function given example parameters
			double test1 = spr.enrollmentPeriod("2021-01-01", "2021-01-31", "2021-01-22");
			double test2 = spr.enrollmentPeriod("2021-04-01", "2021-05-31", "2021-04-30");
			double greaterThan100 = spr.enrollmentPeriod("2021-01-01", "2021-01-31", "2021-03-01");
			double lessThan100 = spr.enrollmentPeriod("2021-03-01", "2021-03-31", "2021-01-01");

			// Test that the results equal 
			assertEquals(70, test1);	
			assertEquals(48, test2);		
			assertEquals(100, greaterThan100);		
			assertEquals(0, lessThan100);		
	}
		
	@AfterEach
	public void tearDown() {
	    System.setOut(standardOut);
	}
}
