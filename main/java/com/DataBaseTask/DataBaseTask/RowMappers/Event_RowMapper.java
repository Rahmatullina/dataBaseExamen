package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Event_;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Event_RowMapper implements RowMapper<Event_> {
    @Override
    public Event_ mapRow(ResultSet resultSet, int i) throws SQLException {
        Event_ event = new Event_();
        event.setId(resultSet.getInt("id"));
        event.setShort_title(resultSet.getString("short_title"));
        event.setDescription(resultSet.getString("description"));
        event.setPrice(resultSet.getString("price"));
        event.setLocation_id(resultSet.getInt("location_id"));
        event.setDate_id(
                resultSet.getInt("date_id"));
        return event;
    }
}
