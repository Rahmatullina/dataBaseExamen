package com.DataBaseTask.DataBaseTask.POJOClasses;

import java.util.List;

public class DataBaseStructureClass {

    private List<Person> person_list;
    private List<Event_> event_list;
    private List<Date_> date_list;
    private List<Location> location_list;
    private List<Event_Assignment> event_assignment_list;
    private List<Comment> comment_list;


    public List<Person> getPerson_list() {
        return person_list;
    }

    public void setPerson_list(List<Person> person_list) {
        this.person_list = person_list;
    }

    public List<Event_> getEvent_list() {
        return event_list;
    }

    public void setEvent_list(List<Event_> event_list) {
        this.event_list = event_list;
    }

    public List<Date_> getDate_list() {
        return date_list;
    }

    public void setDate_list(List<Date_> date_list) {
        this.date_list = date_list;
    }

    public List<Location> getLocation_list() {
        return location_list;
    }

    public void setLocation_list(List<Location> location_list) {
        this.location_list = location_list;
    }


    public List<Event_Assignment> getEvent_assignment_list() {
        return event_assignment_list;
    }

    public void setEvent_assignment_list(List<Event_Assignment> event_assignment_list) {
        this.event_assignment_list = event_assignment_list;
    }

    public List<Comment> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<Comment> comment_list) {
        this.comment_list = comment_list;
    }
}
