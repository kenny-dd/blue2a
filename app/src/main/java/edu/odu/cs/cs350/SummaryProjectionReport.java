package edu.odu.cs.cs350;
import java.util.*;

public class SummaryProjectionReport{	
		
	// Default Constructor
	public SummaryProjectionReport(){       
		this.ProjectionResults = new ArrayList<CourseProjection>();
	}
	 
	public void addCourse(CourseProjection course) {
		ProjectionResults.add(course);
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


