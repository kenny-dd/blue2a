package edu.odu.cs.cs350;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.nio.file.Path;
import java.nio.file.Paths;

class TestSemester {
   
        public String semesterName;
        public String pathToSemester;
        public String enrollStart;
        public String enrollDeadline;
        public Path path;
        
        public Semester semester1;
        public Semester semester2;
    /*
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        semester1 = new Semester();
        semesterName = "";
        enrollStart = "1111-01-01";
        enrollDeadline = "1111-02-02";
        pathToSemester = "/the/semester/202021";
        path = Paths.get(pathToSemester);
    }

    @Test
    public void testConstructor() {
        assertThat (semester1.getName(), is(""));
        assertThat (semester1.getPreRegDate(), is(""));
        assertThat (semester1.getAddDeadline(), is(""));
        assertTrue (semester1.EnrollmentSnapshots.isEmpty());
        
        semester2 = new Semester(pathToSemester, enrollStart, enrollDeadline);
        
        assertThat (semester2.getName(), is("202021"));
        assertThat (semester2.getPreRegDate(), is(enrollStart));
        assertThat (semester2.getAddDeadline(), is(enrollDeadline));
        assertThat (semester2.getPath(), is(path));
        assertTrue (semester2.EnrollmentSnapshots.isEmpty());
    }

    @Test
    public void testSetName() {
        assertThat (semester1.getName(), not("202021"));
        assertThat (semester1.getName(), is(""));
        semester1.setName(pathToSemester);
        assertThat (semester1.getName(), is("202021"));
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

    @Test
    public void testSetPath() {
        assertThat (semester1.getPath(), not(path));
        semester1.setPath(pathToSemester);
        assertThat (semester1.getPath(), is(path));
    }
}
