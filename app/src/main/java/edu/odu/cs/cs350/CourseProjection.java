package edu.odu.cs.cs350;

import java.util.HashMap;

public class CourseProjection {
    /**
     * Default Constructor
     */
    public CourseProjection() {
        this("", 0);
    };

    /**
     * Create a course projection with a name and enrollment cap.
     * @param name
     * @param enrollmentCap
     */
    public CourseProjection(String name, int enrollmentCap) {
        this.name = name;
        this.courseCap = enrollmentCap;
        this.historicValues = new HashMap<>();
        this.currentValues = new HashMap<>();
        this.projections = new HashMap<>();
        this.hCounts = new HashMap<>();
    }

    /**
     * Create a string for this projection in the format of one line of the SPR
     * @return
     */
    @Override
    public String toString() {
        char overflowMarker = this.getProjectionCount() > this.courseCap ? '*' : ' ';
        return String.format("%c%-6s %-10d %-9d %-3d", overflowMarker, this.name, this.getEnrollmentCount(), this.getProjectionCount(), this.courseCap);
    }

    // Accessor methods

    /**
     * Get the name of the course being projected.
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get the latest enrollment count for this course in the active semester
     * @return
     */
    public int getEnrollmentCount() {
        if (currentValues.size() == 0) {
            return 0;
        }

        return currentValues.get(getMaxMapIndex(currentValues));
    }

    /**
     * Get the latest projected enrollment count for this course
     * @return
     */
    public int getProjectionCount() {
        if (projections.size() == 0) {
            return 0;
        }

        return projections.get(getMaxMapIndex(projections));
    }

    /**
     * Get the enrollment cap for the course.
     * @return
     */
    public int getCourseCap() {
        return courseCap;
    }

    // Mutators

    /**
     * Set the name of the course being projected
     * @param newName The string to use as the new name of the course.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Set the course cap to be used in this projection.
     * @param newCap The value to be used as the course enrollment cap.
     */
    public void setCourseCap(int newCap) {
        this.courseCap = newCap;
    }

    /**
     * Add a historic enrollment value for an index. Multiple entries at the same index will be averaged.
     * @param index The mapped date number to add the historical value for.
     * @param count The historical value to be added.
     */
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

    /**
     * Get a historical enrollment average value used for the projection at the index
     * @param index The index to get the average historical value.
     * @return
     */
    public int getHistoricValue(double index) {
        if (historicValues.containsKey(index)) {
            return (int)Math.ceil(historicValues.get(index) / (double)hCounts.get(index));
        }

        return interpolate(historicValues, index);
    }

    /**
     * Fetch the map of historical enrollment averages used in this projection.
     * @return
     */
    public HashMap<Double, Integer> getHistoricValuesList() {
        return historicValues;
    }

    /**
     * Add a current enrollment count to the map.
     * @param index The mapped index for the value to be saved at.
     * @param value The value of the current enrollment to be added at the index.
     */
    public void addCurrentValue(double index, int value){
        currentValues.put(index, value);
    }

    /**
     * Get a current enrollment at a mapped number (0.0-1.0)
     * @param index The mapped number to get the enrollment count from.
     * @return
     */
    public int getCurrentValue(double index) {
        if (currentValues.containsKey(index)) {
            return currentValues.get(index);
        }
        return -1;
    }

    /**
     * Fetch the map of current enrollments from a course projection
     * @return
     */
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
        int recentHistoric = interpolate(historicValues, recentIndex);
        int dateHistoric = interpolate(historicValues, dateNum);

        if (dateNum <= recentIndex) {
            return;
        }

        projections.put(dateNum, (int)Math.ceil((double)currentValues.get(recentIndex) / recentHistoric * dateHistoric));
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

    private int interpolate(HashMap map, double index) {
        if (map.containsKey(index)) {
            return (int)map.get(index);
        }

        double prevIndex = -1.0;
        double nextIndex = -1.0;
        int prevVal;
        int nextVal;
        int calculatedVal;

        for (Object ind : map.keySet()) {
            if ((Double)ind < index){
                prevIndex = (Double)ind;
            }
            else {
                nextIndex = (Double)ind;
            }
        }

        prevVal = (int)map.get(prevIndex);
        nextVal = (int)map.get(nextIndex);

        calculatedVal = (int) Math.ceil(prevVal + ((index - prevIndex)/(nextIndex - prevIndex))*(nextVal - prevVal));

        return calculatedVal;
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
