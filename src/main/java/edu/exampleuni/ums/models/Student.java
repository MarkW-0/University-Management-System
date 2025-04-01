package edu.exampleuni.ums.models;

import java.util.ArrayList;

public class Student extends User {
    private String Address;
    private String TelephoneNumber;
    private String AcademicLevel;
    private String CurrentSemester;
    private String photoLocation;
    private ArrayList<String> Courses;
    private String ThesisTitle;
    private int progress;

    public Student(String name, String email, byte[] password, String address, String TelephoneNumber, String AcademicLevel, String CurrentSemester, String photoLocation, ArrayList<String> Courses, String ThesisTitle, int progress, String fullName, String id) {
        super(name, email, password, fullName, id);
        this.Address = address;
        this.TelephoneNumber = TelephoneNumber;
        this.AcademicLevel = AcademicLevel;
        this.CurrentSemester = CurrentSemester;
        this.photoLocation = photoLocation;
        this.Courses = Courses;
        this.ThesisTitle = ThesisTitle;
        this.progress = progress;
        this.role = Role.STUDENT;
    }

    public String getAddress() {
        return Address;
    }
    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public String getThesisTitle() {
        return ThesisTitle;
    }

    public ArrayList<String> getCourses() {
        return Courses;
    }

    public int getProgress() {
        return progress;
    }

    public String getCurrentSemester() {
        return CurrentSemester;
    }

    public String getAcademicLevel() {
        return AcademicLevel;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }
}
