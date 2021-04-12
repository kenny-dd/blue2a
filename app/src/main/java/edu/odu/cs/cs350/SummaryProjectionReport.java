package edu.odu.cs.cs350;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SummaryProjectionReport{	
		
	// Default Constructor
	public SummaryProjectionReport(){       
		this.ProjectionResults = new ArrayList<CourseProjection>();
	}

	/**
	 *
	 * @param course The course to be added
	 * @see			 CourseProjection
	 */
	public void addCourse(CourseProjection course) {
		int index = 0;
		for (index = 0; index < ProjectionResults.size(); index++){
			if (ProjectionResults.get(index).getName().compareTo(course.getName()) > 0) {
				break;
			}
		}
		ProjectionResults.add(index, course);
	}

	public List<CourseProjection> getProjectionResults(){
		return ProjectionResults;
	}

	public void displayProjectionResults() {
		System.out.println(enrollmentPeriod("2021-01-01", "2021-01-31", "2021-01-22")
								+ "% of enrollment period has elapsed.");
		
		System.out.println(" Course Enrollment Projected Cap");
		
		for(int i=0; i<ProjectionResults.size(); i++) {
			System.out.println(ProjectionResults.get(i));
		}
	}
	
	// Data Member
	private List<CourseProjection> ProjectionResults;
	
public int enrollmentPeriod(String startDate, String endDate, String currentDate) {
		
	    // Parsing the date into proper format
		LocalDate firstDate = LocalDate.parse(startDate);
		LocalDate secondDate = LocalDate.parse(endDate);
		LocalDate current = LocalDate.parse(currentDate);
	    
		// Calculate days between dates
		long period = ChronoUnit.DAYS.between(firstDate, secondDate);
		long elapsed = ChronoUnit.DAYS.between(firstDate, current);

	    // Find percentage of elapsed time
		int percentPassed = (int) Math.round(100 * ((double)elapsed / (double)period));
		
		// If number is 0 or negative, return 0. If number is greater than 100, return 100.
		if(percentPassed <= 0) {
			return 0;
		}
		else if(percentPassed > 100) {
			return 100;
		}
		else
		return percentPassed;
	}
}


