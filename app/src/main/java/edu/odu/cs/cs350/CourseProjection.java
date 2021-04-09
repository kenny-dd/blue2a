package edu.odu.cs.cs350;

import java.util.HashMap;

public class CourseProjection {
    //Default Constructor
    public CourseProjection() {
        this("", 0);
    };

    public CourseProjection(String name, int enrollmentCap) {
        this.name = name;
        this.courseCap = enrollmentCap;
        this.historicValues = new HashMap<>();
        this.currentValues = new HashMap<>();
        this.projections = new HashMap<>();
        this.hCounts = new HashMap<>();
    }

    @Override
    public String toString() {
        char overflowMarker = this.getProjectionCount() > this.courseCap ? '*' : ' ';
        return String.format("%c%-6s %-10d %-9d %-3d", overflowMarker, this.name, this.getEnrollmentCount(), this.getProjectionCount(), this.courseCap);
    }

    // Accessor methods
    public String getName() {
        return name;
    }

    public int getEnrollmentCount() {
        if (currentValues.size() == 0) {
            return 0;
        }

        return currentValues.get(getMaxMapIndex(currentValues));
    }

    public int getProjectionCount() {
        if (projections.size() == 0) {
            return 0;
        }

        return projections.get(getMaxMapIndex(projections));
    }

    public int getCourseCap() {
        return courseCap;
    }

    // Mutators
    public void setName(String newName) {
        this.name = newName;
    }

    public void setCourseCap(int newCap) {
        this.courseCap = newCap;
    }

    public void addHistoricValue(double index, int count) {
        if (historicValues.containsKey(index)) {
            int newVal = count + historicValues.get(index);
            historicValues.put(index, newVal);
            hCounts.put(index, hCounts.get(index) + 1);
        }
        else {
            historicValues.put(index, count);
            hCounts.put(index, 1);
        }

    }

    public int getHistoricValue(double index) {
        if (historicValues.containsKey(index)) {
            return (int)Math.ceil(historicValues.get(index) / (double)hCounts.get(index));
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
        if (currentValues.containsKey(index)) {
            return currentValues.get(index);
        }
        return -1;
    }

    public HashMap<Double, Integer> getCurrentValues() {
        return currentValues;
    }

    /**
     * Make a projection for the end of the enrollment period
     */
    public void makeProjection() {
        makeProjection(1.0);
    }

    /**
     * Calculate the projected enrollment for a course at dateNum
     * @param dateNum
     */
    public void makeProjection(double dateNum) {
        double recentIndex = getMaxMapIndex(currentValues);
        if (dateNum <= recentIndex) {
            return;
        }

        //Add interpolation for historical values here.

        projections.put(dateNum, (int)Math.ceil((double)currentValues.get(recentIndex) / historicValues.get(recentIndex) * historicValues.get(dateNum)));
    }

    private Double getMaxMapIndex(HashMap map) {
        Double max = -1.0;
        for (Object key : map.keySet()) {
            if ((Double)key > max) {
                max = (Double)key;
            }
        }

        return max;
    }

    //Data members
    private String name;
    private int courseCap;

    private HashMap<Double, Integer> historicValues;
    private HashMap<Double, Integer> currentValues;
    private HashMap<Double, Integer> projections;
    //Used for averaging historic enrollment values
    private HashMap<Double, Integer> hCounts;
}
