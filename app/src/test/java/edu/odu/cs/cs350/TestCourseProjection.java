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
        assertEquals("        0          0         0  ", cp.toString(), "toString did not return empty string");
    }

    //Test non default constructor
    @Test
    void testCourseProjectionParameters() {
        CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);

        assertEquals(46, cp.getEnrollmentCount());
        assertEquals(104, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 46         104       120", cp.toString());
    }

    //Test setting the course name
    @Test
    void testSetName() {
        CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);

        cp.setName("CS121G");

        assertEquals(46, cp.getEnrollmentCount());
        assertEquals(104, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS121G", cp.getName());
        assertEquals(" CS121G 46         104       120", cp.toString());
    }

    //Test setting the enrollmentCount
    @Test
    void testSetEnrollmentCount() {
        CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);

        cp.setEnrollmentCount(80);

        assertEquals(80, cp.getEnrollmentCount());
        assertEquals(104, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 80         104       120", cp.toString());
    }

    //Test setting the projected enrollment
    @Test
    void testSetProjectedCount() {
        CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);

        cp.setProjectedCount(125);

        assertEquals(46, cp.getEnrollmentCount());
        assertEquals(125, cp.getProjectionCount());
        assertEquals(120, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals("*CS120G 46         125       120", cp.toString());
    }

    //Test setting the course enrollment cap
    @Test
    void testSetCourseCap() {
        CourseProjection cp = new CourseProjection("CS120G", 46, 104, 120);

        cp.setCourseCap(125);

        assertEquals(46, cp.getEnrollmentCount());
        assertEquals(104, cp.getProjectionCount());
        assertEquals(125, cp.getCourseCap());
        assertEquals("CS120G", cp.getName());
        assertEquals(" CS120G 46         104       125", cp.toString());
    }

}
