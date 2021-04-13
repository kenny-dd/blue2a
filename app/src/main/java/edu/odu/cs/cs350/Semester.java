package edu.odu.cs.cs350;

import java.io.*;
import java.lang.String;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Semester {
    // Default Constructor
    public Semester() {
        this.isURL = false;
        this.name = "";
        this.preRegDate = "";
        this.addDeadline = "";
    }
    public Semester(String semesterPath, String preRegDate, String addDeadline) throws Throwable {
        this.isURL = false;
        setName(semesterPath);
        this.preRegDate = preRegDate;
        this.addDeadline = addDeadline;
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
     *
     * @param semesterDirPath
     * @throws Throwable
     * @return this.isURL
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
     *
     * @return this.csvFiles :list of fetched csv files
     * @throws IOException
     */
    public List<File> fetchFiles() throws IOException { 
        this.csvFiles = new ArrayList<File>();
        if(this.isURL) {

            Document site = Jsoup.connect(this.url.toString()).get();
            Elements files = site.select("a[href]");

            System.out.println("Number of files in URL: " + files.size());
            System.out.println("Fetching files from " + this.url + "...");

            // We need to find the dates file first to set the pre registration and add deadline dates,
            // so we can grab the relevant enrollment snapshots only
            for(Element file : files) {
                if (file.text().endsWith("dates.txt")) {
                    File datesFile;
                    FileUtils.copyURLToFile(new URL(file.attr("abs:href")), datesFile =  new File(file.text()));
                    this.dates = datesFile;
                    setDates(this.dates);
                }
            }
            // Grab relevant enrollment snapshots
            boolean end = true;
            for(Element file: files) {
                // Start grabbing snapshots when we see pre registration date
                if (file.text().endsWith(this.preRegDate + ".csv")) {
                    end = false;
                }
                if(!end) {
                    File csvFile;
                    FileUtils.copyURLToFile(new URL(file.attr("abs:href")), csvFile =  new File(file.text()));
                    this.csvFiles.add(csvFile);
                }
                // Stop grabbing snapshots after we see add deadline
                if (file.text().endsWith(this.addDeadline + ".csv")) {
                    break;
                }
            }
            System.out.println("Done.");
        }
        else {
            //@TODO: Still need to implement snapshot filtering for regular directories.
            System.out.println("Fetching files from " + this.pathToSemesterDir + "...");
            try(Stream<Path> pathToFiles = Files.walk(this.pathToSemesterDir)) {
                pathToFiles.forEach(filePath -> {
                    String[] tokens = filePath.toString().split("[\\\\|/]");
                    String filename;
                    if (filePath.toString().endsWith(".csv")) {
                        filename = tokens[tokens.length - 1];
                        File tmpCsvFile = new File(filename);
                        this.csvFiles.add(tmpCsvFile);
                    }
                     if(filePath.toString().endsWith("dates.txt")) {

                        filename = tokens[tokens.length - 1];
                        this.dates = new File(filename);
                        setDates(this.dates);
                    }
                });
                System.out.println("Done.");
            }
        }
        return this.csvFiles;
    }

    /**
     * Read a csv file, storing each field in the line into an
     * element in a string array. The array representing the csv line
     * is then added to a list.
     *
     * @param reader :the reader for the csv file
     * @return list :list of string arrays; each element in the list
     *               represents a line in the csv file.
     * @throws Exception
     */
    public List<String[]> readCsv(Reader reader) throws Exception {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withEscapeChar('\\')
                .withQuoteChar('\"')
                .withIgnoreQuotations(true)
                .build();
        List<String[]> list = new ArrayList<>();
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(0)
                .withCSVParser(parser)
                .build();
        String[] line;
        while ((line = csvReader.readNext()) != null) {
            list.add(line);
        }
        reader.close();
        csvReader.close();
        return list;
    }

    /**
     * Reads each line in the csv file,
     * then creates a new Enrollment snapshot class.
     * Then the Enrollment snapshot's overall cap,
     * overall enrollment, and title are assigned
     * using the data read from the csv file.
     *
     * @param filename :the csv file
     * @return snaps :the list of enrollment snapshots,
     *                with each snapshot having their data
     *                obtained from the csv file supplied by filename
     * @throws Exception
     */
    public List<EnrollmentSnapshot> readCsvByLine(String filename) throws Exception {
        Reader reader = Files.newBufferedReader(Paths.get(filename));
        List<EnrollmentSnapshot> snaps = new ArrayList<>();
        List<String[]> arr = readCsv(reader);
        for(int i = 1; i< arr.size(); i++) {
            EnrollmentSnapshot snap = new EnrollmentSnapshot();

            if(arr.get(i)[21].equals("")) {

                int cap = (arr.get(i)[22].equals("")) ? 0 : Integer.parseInt(arr.get(i)[22]);
                snap.setOVERALL_CAP(cap);
                int enr = Integer.parseInt(arr.get(i)[23]);
                snap.setENR(enr);
                String title = arr.get(i)[3];
                snap.setTITLE(title);
                snaps.add(snap);
            }
            if(!(arr.get(i)[21].equals("")|| arr.get(i)[21].equals("IN"))) {

                int cap = (arr.get(i)[23].equals("")) ? 0 : Integer.parseInt(arr.get(i)[23]);
                snap.setOVERALL_CAP(cap);
                int enr = Integer.parseInt(arr.get(i)[24]);
                snap.setENR(enr);
                String title = arr.get(i)[3];
                snap.setTITLE(title);
                snaps.add(snap);
            }
        }
        return snaps;
    }
    
    // Data members
    private boolean isURL;
    private String name;
    private String preRegDate;
    private String addDeadline;
    private URL url;
    private Path pathToSemesterDir;
    private File dates;
    private List<File> csvFiles;
}
