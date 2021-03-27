package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSummaryProjectionReport {
	
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


}
