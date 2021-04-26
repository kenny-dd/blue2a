package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCourseProjection {

    //As a projection creator, I want to see one line per course in the summary projection report
	/*
	Print a single course projection on one line
	 */
    @Test
    void testCourseProjection() {
        CourseProjection cp = new CourseProjection();

        assertEquals(0, cp.getProjectionCount());
        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getCourseCap());
        assertEquals("", cp.getName());
        assertEquals("        0          0         0  ", cp.toString(), "toString did not return empty string");
    }

    //Test non default constructor
    @Test
    void testCourseProjectionParameters() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 0          0         120", cp.toString());
    }

    //Test setting the course name
    @Test
    void testSetName() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.setName("CS121G");

        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS121G", cp.getName());
        assertEquals(" CS121G 0          0         120", cp.toString());
    }

    //Test setting the course enrollment cap
    @Test
    void testSetCourseCap() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.setCourseCap(125);

        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(125, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 0          0         125", cp.toString());
    }

    //Test Setting historical enrollment value
    @Test
    void testAddHistoricValue() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.addHistoricValue(0.5, 40);
        cp.addHistoricValue(0.76, 50);

        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals(40, cp.getHistoricValue(0.5));
        assertEquals(50, cp.getHistoricValue(0.76));
        assertEquals(2, cp.getHistoricValuesList().size());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 0          0         120", cp.toString());
    }

    //Test adding historical data from multiple semesters
    @Test
    void testAddHistoricValueMultiple() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.addHistoricValue(0.5, 40);
        cp.addHistoricValue(0.75, 50);
        cp.addHistoricValue(0.5, 20);

        assertEquals(0, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals(30, cp.getHistoricValue(0.5));
        assertEquals(50, cp.getHistoricValue(0.75));
        assertEquals(2, cp.getHistoricValuesList().size());
        assertNotNull(cp.getCurrentValues());
        assertEquals(0, cp.getCurrentValues().size());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 0          0         120", cp.toString());

    }

    //Test Setting a current enrollment value
    @Test
    void testAddCurrentValue() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.addCurrentValue(0.5, 55);
        cp.addCurrentValue(0.85, 100);

        assertEquals(100, cp.getEnrollmentCount());
        assertEquals(0, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals(55, cp.getCurrentValue(0.5));
        assertEquals(100, cp.getCurrentValue(0.85));
        assertEquals(2, cp.getCurrentValues().size());
        assertEquals(" CS120G 100        0         120", cp.toString());
    }

    //Test making a projection for a course at a specified date
    @Test
    void testMakeProjection() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.addCurrentValue(0.5, 50);
        cp.addHistoricValue(0.5, 40);
        cp.addHistoricValue(0.75, 80);
        cp.addHistoricValue(1.0, 104);

        cp.makeProjection(0.75);
        cp.makeProjection();

        assertEquals(50, cp.getEnrollmentCount());
        assertEquals(130, cp.getProjectionCount());
        assertEquals(100, cp.getProjectionCount(0.75));
        assertEquals(120, cp.getCourseCap());
        assertEquals(50, cp.getCurrentValue(0.5));
        assertEquals(1, cp.getCurrentValues().size());
        assertEquals(40, cp.getHistoricValue(0.5));
        assertEquals(104, cp.getHistoricValue(1.0));
        assertEquals(3, cp.getHistoricValuesList().size());
        assertEquals(2, cp.getProjections().size());
        assertEquals("CS120G", cp.getName());
        assertEquals("*CS120G 50         130       120", cp.toString());
    }

    //Test a projection in which interpolation is used
    @Test
    void testMakeProjectionInterpolation() {
        CourseProjection cp = new CourseProjection("CS120G", 120);

        cp.addCurrentValue(0.5, 50);
        cp.addHistoricValue(0.3, 25);
        cp.addHistoricValue(1.0, 104);

        cp.makeProjection();

        assertEquals(50, cp.getEnrollmentCount());
        assertEquals(109, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals(50, cp.getCurrentValue(0.5));
        assertEquals(1, cp.getCurrentValues().size());
        assertEquals(25, cp.getHistoricValue(0.3));
        assertEquals(104, cp.getHistoricValue(1.0));
        assertEquals(48, cp.getHistoricValue(0.5));
        assertEquals(2, cp.getHistoricValuesList().size());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 50         109       120", cp.toString());
    }
}
