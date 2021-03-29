package edu.odu.cs.cs350;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvBindByName;

/**
 * A java bean class to be used with CsvToBeanBuilder for loading 
 * beans with data from each enrollment snapshot.
 */
public class EnrollmentSnapshot implements java.io.Serializable {
    
    @CsvBindByName(column = "CRN", required = true)
    private int CRN;

    @CsvBindByName(column = "SUBJ", required = true)
    private String SUBJ;

    @CsvBindByName(column = "CRSE", required = true)
    private int CRSE;
    
    @CsvBindByName(column = "XLST CAP", required = true)
    private int XLST_CAP;

    @CsvBindByName(column = "ENR")
    private int ENR;

    @CsvBindByName(column = "LINK")
    private String LINK;

    @CsvBindByName(column = "XLST GROUP")
    private String XLST_GROUP;

    @CsvBindByName(column = "OVERALL CAP")
    private int OVERALL_CAP;

    @CsvBindByName(column = "OVERALL_ENR")
    private int OVERALL_ENR;

    public EnrollmentSnapshot() {}

    public String getSUBJ() {
        return this.SUBJ;
    }
    public void setSUBJ(String subject) {
        this.SUBJ = subject;
    }
    public int getCRN() {
        return this.CRN;
    }
    public void setCRN(int crseNum) {
        this.CRN = crseNum;
    }
    public void setCRSE(int course) {
        this.CRSE = course;
    }
    public int getCRSE() {
        return this.CRSE;
    }
    public void setXLST_CAP(int xcap) {
        this.XLST_CAP = xcap;
    }
    public int getXLST_CAP() {
        return this.XLST_CAP;
    }
    public void setENR(int enrolled) {
        this.ENR = enrolled;
    }
    public int getENR() {
        return this.ENR;
    }
    public void setLINK(String link) {
        this.LINK = link;
    }
    public String getLINK() {
        return this.LINK;
    }
    public void setXLST_GROUP(String xlgroup) {
        this.XLST_GROUP = xlgroup;
    }
    public String getXLST_GROUP() {
        return this.XLST_GROUP;
    }
    public void setOVERALL_CAP(int ocap) {
        this.OVERALL_CAP = ocap;
    }
    public int getOVERALL_CAP() {
        return this.OVERALL_CAP;
    }
    public void setOVERALL_ENR(int oenr) {
        this.OVERALL_ENR = oenr;
    }
    public int getOVERALL_ENR() {
        return this.OVERALL_ENR;
    }
}
