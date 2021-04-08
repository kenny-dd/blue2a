package edu.odu.cs.cs350;

import java.util.HashMap;

public class CourseProjection {
    //Default Constructor
    public CourseProjection() {
        this.name = "";
        this.enrollmentCount = 0;
        this.projectedCount = 0;
        this.courseCap = 0;
        this.historicValues = new HashMap<>();
    };

    public CourseProjection(String name, int enrollmentCount, int projectedEnrollment, int enrollmentCap) {
        this.name = name;
        this.enrollmentCount = enrollmentCount;
        this.projectedCount = projectedEnrollment;
        this.courseCap = enrollmentCap;
        this.historicValues = new HashMap<>();
    }

    @Override
    public String toString() {
        char overflowMarker = this.projectedCount > this.courseCap ? '*' : ' ';
        return String.format("%c%-6s %-10d %-9d %-3d", overflowMarker, this.name, this.enrollmentCount, this.projectedCount, this.courseCap);
    }

    // Accessor methods
    public String getName() {
        return name;
    }

    public int getEnrollmentCount() {
        return enrollmentCount;
    }

    public int getProjectionCount() {
        return projectedCount;
    }

    public int getCourseCap() {
        return courseCap;
    }

    // Mutators
    public void setName(String newName) {
        this.name = newName;
    }

    public void setEnrollmentCount(int newCount) {
        this.enrollmentCount = newCount;
    }

    public void setProjectedCount(int newCount) {
        this.projectedCount = newCount;
    }

    public void setCourseCap(int newCap) {
        this.courseCap = newCap;
    }

    public void addHistoricValue(double index, int count) {
        historicValues.put(index, count);
    }

    public int getHistoricValue(double index) {
        if (historicValues.containsKey(index)) {
            return historicValues.get(index);
        }

        return -1;
    }

    public HashMap<Double, Integer> getHistoricValuesList() {
        return historicValues;
    }

    //Data members
    private String name;
    private int enrollmentCount;
    private int projectedCount;
    private int courseCap;

    private HashMap<Double, Integer> historicValues;
}
