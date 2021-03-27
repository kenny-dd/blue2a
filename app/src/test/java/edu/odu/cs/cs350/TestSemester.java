package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestSemester {
   
        public String semesterName;
        public String urlPathToSemester;
        public String strPathToSemester;
        public String enrollStart;
        public String enrollDeadline;
        public Path path;
        public URL url;
        
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
    }
    /**
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testConstructor() throws MalformedURLException {
        assertNull (semester1.getURL());
        assertThat (semester1.getName(), is(""));
        assertThat (semester1.getPreRegDate(), is(""));
        assertThat (semester1.getAddDeadline(), is(""));
        assertTrue (semester1.EnrollmentSnapshots.isEmpty());
        
        semester2 = new Semester(urlPathToSemester, enrollStart, enrollDeadline);
       
        assertThat (semester2.getURL(), is(url));
        assertThat (semester2.getName(), is("202020"));
        assertThat (semester2.getPreRegDate(), is(enrollStart));
        assertThat (semester2.getAddDeadline(), is(enrollDeadline));
        assertThat (semester2.getPath().toString(), is(url.getPath().toString()));
        assertThat (semester2.getPath(), is(path));
        assertTrue (semester2.EnrollmentSnapshots.isEmpty());
        
        semester3 = new Semester(strPathToSemester, enrollStart, enrollDeadline);
       
        assertNull (semester3.getURL());
        assertThat (semester3.getName(), is("202020"));
        assertThat (semester3.getPreRegDate(), is(enrollStart));
        assertThat (semester3.getAddDeadline(), is(enrollDeadline));
        assertThat (semester3.getPath(), is(path));
        assertTrue (semester3.EnrollmentSnapshots.isEmpty());
        
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
    public void testSetPath() throws MalformedURLException {
        semester2 = new Semester(urlPathToSemester, enrollStart, enrollDeadline);

        assertThat (semester1.getPath(), not(path));
        semester1.setPath(urlPathToSemester);
        assertThat (semester1.getPath(), is(semester2.getPath()));
    }
}
