/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.odu.cs.cs350;


import java.net.MalformedURLException;
import java.util.*;


public class RunProjections {
    public String getGreeting() {
        return "Hello World!";
    }
     
    public static void main(String[] args) {
    	
    	List<Semester> semesterList = new ArrayList<>();
    	
    	if(args.length < 1)
    	{
    		System.err.println("Invalid directory path/URL");
    		System.exit(1);
    	}  
    	
    	for (int i = 0; i < args.length; i++)
    	{
    		semesterList.add(new Semester());
	    		try {
	    		    semesterList.get(i).setPath(args[i]);
	    		} catch (MalformedURLException e) {
    		  
    		}
    		
    	}
    	
        RunProjections prog = new RunProjections();
//        URL url = prog.getURL(args[0]);        	
        System.out.println(prog.getGreeting());      
        
    }
}
