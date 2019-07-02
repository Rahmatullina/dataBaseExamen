package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Event_Assignment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Event_Assignment_RowMapper implements RowMapper<Event_Assignment> {
    @Override
    public Event_Assignment mapRow(ResultSet resultSet, int i) throws SQLException {
        Event_Assignment list_person_events = new Event_Assignment();
        list_person_events.setId(resultSet.getInt("id"));
        list_person_events.setEvent_id(resultSet.getInt("event_id"));
        list_person_events.setPerson_id(resultSet.getInt("person_id"));
        return list_person_events;
    }
}
