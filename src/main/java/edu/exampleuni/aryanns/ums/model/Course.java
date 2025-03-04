package edu.exampleuni.aryanns.ums.model;

/**
 * The Course class represents a university course.
 * Each course has a name, a unique course code, and is associated with a subject.
 */
public class Course {
    private String courseName;
    private String courseCode;
    private String subjectName; // The subject this course belongs to

    public Course(String courseName, String courseCode, String subjectName) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.subjectName = subjectName;
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
}
