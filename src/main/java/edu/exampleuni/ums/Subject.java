package edu.exampleuni.ums;

public class Subject {
    final javafx.beans.property.StringProperty code;
    final javafx.beans.property.StringProperty subjectName;

    public Subject(String code, String subjectName) {
        this.code = new javafx.beans.property.SimpleStringProperty(code);
        this.subjectName = new javafx.beans.property.SimpleStringProperty(subjectName);
    }
    public String getCode() { return code.get(); } public void setCode(String value) { code.set(value); }
    public String getSubjectName() { return subjectName.get(); } public void setSubjectName(String value) { subjectName.set(value); }
}