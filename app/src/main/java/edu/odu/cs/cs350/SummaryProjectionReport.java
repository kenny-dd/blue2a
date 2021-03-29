package edu.odu.cs.cs350;
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
		System.out.println("76% of enrollment period has elapsed.");
		System.out.println(" Course Enrollment Projected Cap");
		
		for(int i=0; i<ProjectionResults.size(); i++) {
			System.out.println(ProjectionResults.get(i));
		}
	}
	
	// Data Member
	private List<CourseProjection> ProjectionResults;

}


