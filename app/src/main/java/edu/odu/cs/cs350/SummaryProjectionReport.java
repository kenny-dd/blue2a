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

   // Data Member
	private List<CourseProjection> ProjectionResults;

}


