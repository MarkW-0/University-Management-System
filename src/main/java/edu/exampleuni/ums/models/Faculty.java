package edu.exampleuni.ums.models;

import java.util.ArrayList;

public class Faculty extends User {
    private String degree;
    private String researchInterest;
    private  String officeLocation;
    // Don't know what's the max courses a faculty member can offer, probably will change later
    private ArrayList<String> coursesOffered;

    public ArrayList<String> getCoursesOffered() {
        return coursesOffered;
    }

    public String getResearchInterest() {
        return researchInterest;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public String getDegree() {
        return degree;
    }
    //
    public Faculty(String name, String email, byte[] password, String degree, String researchInterest, String officeLocation, ArrayList<String> fullName, String id){
        super(name, email, password, fullName, id);
        this.degree = degree;
        this.researchInterest = researchInterest;
        this.officeLocation = officeLocation;
        this.role = FACULTY;
    }
}
