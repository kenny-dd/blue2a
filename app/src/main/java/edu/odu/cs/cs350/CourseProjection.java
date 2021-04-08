package edu.odu.cs.cs350;

import java.util.HashMap;

public class CourseProjection {
    //Default Constructor
    public CourseProjection() {
        this("", 0, 0, 0);
    };

    public CourseProjection(String name, int enrollmentCount, int projectedEnrollment, int enrollmentCap) {
        this.name = name;
        this.projectedCount = projectedEnrollment;
        this.courseCap = enrollmentCap;
        this.historicValues = new HashMap<>();
        this.currentValues = new HashMap<>();
        currentValues.put(0.0, enrollmentCount);
    }

    @Override
    public String toString() {
        char overflowMarker = this.projectedCount > this.courseCap ? '*' : ' ';
        return String.format("%c%-6s %-10d %-9d %-3d", overflowMarker, this.name, this.getEnrollmentCount(), this.projectedCount, this.courseCap);
    }

    // Accessor methods
    public String getName() {
        return name;
    }

    public int getEnrollmentCount() {
        if (currentValues.size() == 0) {
            return 0;
        }

        double max = -1.0;
        for (Double key : currentValues.keySet()) {
            max = (key > max) ? key : max;
        }
        return currentValues.get(max);
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

    public void addCurrentValue(double index, int value){
        currentValues.put(index, value);
    }

    public int getCurrentValue(double index) {
        if (currentValues.containsKey(index)){
            return currentValues.get(index);
        }
        return -1;
    }

    public HashMap<Double, Integer> getCurrentValues() {
        return currentValues;
    }

    //Data members
    private String name;
    private int enrollmentCount;
    private int projectedCount;
    private int courseCap;

    private HashMap<Double, Integer> historicValues;
    private HashMap<Double, Integer> currentValues;
}
