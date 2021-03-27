package edu.odu.cs.cs350;

import java.lang.String;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Semester {
    // Default Constructor
    public Semester() {
				this.name = "";
        this.preRegDate = "";
        this.addDeadline = "";
        this.EnrollmentSnapshots = new ArrayList<EnrollmentSnapshot>();
    }
    public Semester(String semesterPath, String preRegDate, String addDeadline) throws MalformedURLException {
        this.name = semesterPath.substring(semesterPath.lastIndexOf('/')+1, semesterPath.length());
        this.preRegDate = preRegDate;
        this.addDeadline = addDeadline;
        this.EnrollmentSnapshots = new ArrayList<EnrollmentSnapshot>();
        setPath(semesterPath);
    }
		public URL getURL() {
			return this.url;
		}
    public String getName() {
        return this.name;
    }
    public String getPreRegDate() {
        return this.preRegDate;
    }
    public String getAddDeadline() {
        return this.addDeadline;
    }
    public Path getPath() {
        return this.pathToSemesterDir;
    }

    /*
     * The semester directory location for each semester
     * is supplied through the CLI. The name of the Semester is also 
     * the last item in the supplied path, which is the last 6 characters.
     */
    public void setName(String semesterPath) {
        this.name = semesterPath.substring(semesterPath.lastIndexOf('/')+1, semesterPath.length());
    }
    public void setPreRegDate(String date) {
        this.preRegDate = date;
    }
    public void setAddDeadline(String deadline) {
        this.addDeadline = deadline;
    }
    /*
     * Sets path for semester class. 
     * Can take a URL or standard unix path.
     */
    public String setPath(String semesterDirPath) throws MalformedURLException {
        String s = semesterDirPath.trim().toLowerCase();
        boolean isURL = s.startsWith("http://") || s.startsWith("https://");
        if (isURL) {
						try {
								this.url = new URL(semesterDirPath);
						} 
						catch (MalformedURLException ex) {
								throw ex.getCause();
						}
						finally {
								this.pathToSemesterDir = Paths.get(this.url.getPath());
								return "URL";
        		}
				}
        else {
            this.pathToSemesterDir = Paths.get(semesterDirPath);
            return "notURL";
        }
    }
    /*
     * Instantiate the pre registration and add deadline dates,
     * using the "dates.txt" file given in the directory
     */
    public void setDates(File dates) {
//        dates.open();
//        int i = 0;
//        String[2] currString;
//        Scanner dateScanner = new Scanner(dates);
//        while(dateScanner.hasNextLine()) {
//            currString[i] = dateScanner.nextLine();
//            i++;
//        }
//        dates.close();
//        setPreRegDate(currString[0]);
//        setAddDeadline(currString[1]);
    }
    /*
     * Instantiate the file object for "dates.txt".
     * If the file doesnt exist, or any other issue is encountered,
     * an IOException is thrown.
     */
    public void setDateFile() throws IOException { 
//        try(DirectoryStream<Path> stream = Files.newDirectoryStream(this.pathToSemesterDir, "dates.{txt}")) {
//            for(Path file: stream) {
//                if(file.getFileName() == "dates.txt") {
//                    this.dates = file;
//                }
//                else {
//                    continue;
//                }
//            }
//        } catch (DirectoryIteratorException ex) {
            // I/O error encountered, caused by IOException.
            // "dates.txt" isn't found, or any other I/O error.
//            throw ex.getCause();
//        }
    }

    /*
     * Fill EnrollmentSnapshots collection
     * with all enrollment snapshot files from
     * the semester directory. The files must
     * be within the pre registration date and add deadline.
     */
    public void retrieveEnrollmentSnapshots() {
        
    }
    
    // Data members
    private String name;
    private String semesterPath;
    private String preRegDate;
    private String addDeadline;
    private URL url;
    private Path pathToSemesterDir;
    private File dates;

    protected List<EnrollmentSnapshot> EnrollmentSnapshots;
}
