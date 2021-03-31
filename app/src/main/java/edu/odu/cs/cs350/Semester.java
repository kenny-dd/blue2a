package edu.odu.cs.cs350;

import java.io.*;
import java.lang.String;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.bean.CsvToBeanBuilder;

public class Semester {
    // Default Constructor
    public Semester() {
        this.isURL = false;
        this.name = "";
        this.preRegDate = "";
        this.addDeadline = "";
        this.EnrollmentSnapshots = new ArrayList<ArrayList<EnrollmentSnapshot>>();
    }
    public Semester(String semesterPath, String preRegDate, String addDeadline) throws Throwable {
        this.isURL = false;
        setName(semesterPath);
        this.preRegDate = preRegDate;
        this.addDeadline = addDeadline;
        this.EnrollmentSnapshots = new ArrayList<ArrayList<EnrollmentSnapshot>>();
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
    public List<File> getCsvFiles() {
        return this.csvFiles;
    }

    /**
     * The semester directory location for each semester
     * is supplied through the CLI. The name of the Semester is also 
     * the last item in the supplied path, which is the last 6 characters.
     */
    public void setName(String semesterPath) {
        String [] tokens = semesterPath.split("[\\\\|/]");
        this.name = tokens[tokens.length - 1];
    }
    public void setPreRegDate(String date) {
        this.preRegDate = date;
    }
    public void setAddDeadline(String deadline) {
        this.addDeadline = deadline;
    }
    /**
     * Sets path for semester class. 
     * Can take a URL or system path.
     */
    public boolean setPath(String semesterDirPath) throws Throwable {
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
                this.isURL = true;
            }
        }
        else {
            this.pathToSemesterDir = Paths.get(semesterDirPath);
            this.isURL = false;
        }
        return this.isURL;
    }
    /**
     * Instantiate the pre registration and add deadline dates,
     * using the "dates.txt" file given in the directory
     */
    public void setDates(File dates) {
        int i = 0;
        String[] currString = new String[2];
        try {
            Scanner dateScanner = new Scanner(dates);
            while(dateScanner.hasNextLine()) {
                currString[i] = dateScanner.nextLine();
                i++;
            }
            dateScanner.close();
            setPreRegDate(currString[0]);
            setAddDeadline(currString[1]);
        } 
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Fetch all relevant csv files and the "dates.txt" file
     * from the semester's directory.
     * The directory can be a URL or system path.
     * Each csv file is put into the csvFiles list.
     * If the file doesnt exist, or any other issue is encountered,
     * an IOException is thrown.
     */
    public List<File> fetchFiles() throws IOException { 
        this.csvFiles = new ArrayList<File>();
        if(this.isURL) {

            Document site = Jsoup.connect(this.url.toString()).get();
            Elements files = site.select("a[href]");

            System.out.println("Number of files in URL: " + files.size());
            System.out.println("Fetching files from " + this.url + "...");
            for(Element file: files) {

                if (file.text().endsWith(".csv")) {
                    File csvFile;
                    FileUtils.copyURLToFile(new URL(file.attr("abs:href")), csvFile =  new File(file.text()));
                    this.csvFiles.add(csvFile);
                }
                if (file.text().endsWith("dates.txt")) {
                    File datesFile;
                    FileUtils.copyURLToFile(new URL(file.attr("abs:href")), datesFile =  new File(file.text()));
                    this.dates = datesFile;
                }
            }
            System.out.println("Done.");
        }
        else {

            System.out.println("Fetching files from " + this.pathToSemesterDir + "...");
            try(Stream<Path> pathToFiles = Files.walk(this.pathToSemesterDir)) {

                pathToFiles.forEach(filePath -> {
                    String[] tokens = filePath.toString().split("[\\\\|/]");
                    String filename;
                    if(filePath.toString().endsWith(".csv")) {

                        filename = tokens[tokens.length - 1];
                        File tmpCsvFile = new File(filename);
                        this.csvFiles.add(tmpCsvFile);
                    }
                    else if(filePath.toString().endsWith("dates.txt")) {

                        filename = tokens[tokens.length - 1];
                        this.dates = new File(filename);
                    }
                });
                System.out.println("Done.");
            }
        }
        return this.csvFiles;
    }

    /**
     * Fill EnrollmentSnapshots collection
     * with all enrollment snapshot files from
     * the semester directory. The files must
     * be within the pre registration date and add deadline.
     */
    public void retrieveEnrollmentSnapshots() {
        
    }
    
    // Data members
    private boolean isURL;
    private String name;
    private String semesterPath;
    private String preRegDate;
    private String addDeadline;
    private URL url;
    private Path pathToSemesterDir;
    private File dates;
    private List<File> csvFiles;
    protected List<ArrayList<EnrollmentSnapshot>> EnrollmentSnapshots;
}
