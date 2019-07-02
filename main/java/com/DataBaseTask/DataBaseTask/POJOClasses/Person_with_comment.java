package com.DataBaseTask.DataBaseTask.POJOClasses;

public class Person_with_comment {
    private String text;
    private String person_firstname;
    private String person_lastname;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPerson_firstname() {
        return person_firstname;
    }

    public void setPerson_firstname(String person_firstname) {
        this.person_firstname = person_firstname;
    }

    public String getPerson_lastname() {
        return person_lastname;
    }

    public void setPerson_lastname(String person_lastname) {
        this.person_lastname = person_lastname;
    }
}
