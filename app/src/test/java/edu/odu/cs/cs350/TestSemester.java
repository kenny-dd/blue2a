package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class TestSemester {
   
        public String semesterName;
        public String pathToDir;
        public String pathToDatesFile;
        public String pathToCsvFile;
        public String urlPathToSemester;
        public String strPathToSemester;
        public String enrollStart;
        public String enrollDeadline;
        public Path path;
        public URL url;
        public File dateFile;
        public File csvFile;
        public List<String[]> csv1;
        public List<String[]> csv2;
        public List<EnrollmentSnapshot> snapshots;
        public List<EnrollmentSnapshot> snapshots2;
        public BufferedReader csvReader;
        public BufferedReader csvReader2;
        public Semester semester1;
        public Semester semester2;
        public Semester semester3;
    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        semester1 = new Semester();

        url = new URL("https://www.cs.odu.edu/~zeil/courseSchedule/History/202020");
        urlPathToSemester = "https://www.cs.odu.edu/~zeil/courseSchedule/History/202020";
        strPathToSemester = "/~zeil/courseSchedule/History/202020";

        semesterName = "";
        enrollStart = "1111-01-01";
        enrollDeadline = "1111-02-02";

        path = Paths.get(strPathToSemester);
        pathToDir = "src/test/resources";
        pathToDatesFile = "src/test/resources/dates.txt";
        pathToCsvFile = "src/test/resources/2020-10-05.csv";

        csv1 = new ArrayList<>();
        csv2 = new ArrayList<>();
        snapshots = new ArrayList<>();
        snapshots2 = new ArrayList<>();

        dateFile = new File(pathToDatesFile);
        csvFile = new File(pathToCsvFile);

        csvReader = new BufferedReader(new FileReader(pathToCsvFile));
        csvReader2 = new BufferedReader(new FileReader(pathToCsvFile));
    }
    /**
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testConstructor() throws Throwable {
        assertNull (semester1.getURL());
        assertThat (semester1.getName(), is(""));
        assertThat (semester1.getPreRegDate(), is(""));
        assertThat (semester1.getAddDeadline(), is(""));

        semester2 = new Semester(urlPathToSemester, enrollStart, enrollDeadline);
       
        assertThat (semester2.getURL(), is(url));
        assertThat (semester2.getName(), is("202020"));
        assertThat (semester2.getPreRegDate(), is(enrollStart));
        assertThat (semester2.getAddDeadline(), is(enrollDeadline));
        assertThat (semester2.getPath(), is(path));

        semester3 = new Semester(strPathToSemester, enrollStart, enrollDeadline);
       
        assertNull (semester3.getURL());
        assertThat (semester3.getName(), is("202020"));
        assertThat (semester3.getPreRegDate(), is(enrollStart));
        assertThat (semester3.getAddDeadline(), is(enrollDeadline));
        assertThat (semester3.getPath(), is(path));
    }

    @Test
    public void testSetName() {
        assertThat (semester1.getName(), not("202020"));
        assertThat (semester1.getName(), is(""));
        semester1.setName(urlPathToSemester);
        assertThat (semester1.getName(), is("202020"));
    }

    @Test
    public void testSetPreRegDate() {
        assertThat (semester1.getPreRegDate(), not(enrollStart));
        semester1.setPreRegDate(enrollStart);
        assertThat (semester1.getPreRegDate(), is(enrollStart));
    }
    
    @Test
    public void testSetAddDeadline() {
        assertThat (semester1.getAddDeadline(), not(enrollDeadline));
        semester1.setAddDeadline(enrollDeadline);
        assertThat (semester1.getAddDeadline(), is(enrollDeadline));
    }
    /**
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testSetPath() throws Throwable {
        semester2 = new Semester(urlPathToSemester, enrollStart, enrollDeadline);

        assertThat (semester1.getPath(), not(path));
        semester1.setPath(urlPathToSemester);
        assertThat (semester1.getPath(), is(semester2.getPath()));
    }
    /**
     * @throws java.io.IOException
     * @todo Still need to add a few tests.
     */
    @Test
    public void testFetchFiles() throws Throwable {
        semester1.setPath(pathToDir);
        semester1.fetchFiles();
        semester1.setPath(urlPathToSemester);
        semester1.fetchFiles();
    }
    @Test
    public void testSetDates() {
        semester1.setDates(dateFile);
        assertThat (semester1.getPreRegDate(), is("2020-10-19"));
        assertThat (semester1.getAddDeadline(), is("2021-01-26"));
    }
    @Test
    public void testReadCsv() throws Exception {
        // For some reason, I cannot reuse the same buffered reader for reading the same filepath
        csv1 = semester1.readCsv(csvReader);
        csv2 = semester1.readCsv(csvReader2);
        assertEquals(csv1.get(0)[1], "CRN");
        assertEquals(csv2.get(0)[2], "SUBJ");

        int i = 0;
        for(String[] csvLine : csv1) {

            int k = 0;
            for(String field: csvLine) {

                assertEquals(field, csv2.get(i)[k]);
                k++;
            }
            i++;
        }
    }
    @Test
    public void testReadCsvByLine() throws Exception {
        snapshots = semester1.readCsvByLine(pathToCsvFile);
        snapshots2 = semester1.readCsvByLine(pathToCsvFile);
        for (int i = 0; i<snapshots.size(); i++) {
            assertEquals(snapshots.get(i).getOVERALL_CAP(), snapshots2.get(i).getOVERALL_CAP());
            assertEquals(snapshots.get(i).getOVERALL_ENR(), snapshots2.get(i).getOVERALL_ENR());
            assertEquals(snapshots.get(i).getCRSE(), snapshots2.get(i).getCRSE());
        }
    }
}
