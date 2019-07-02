package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Location_RowMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet resultSet, int i) throws SQLException {
        Location event_location= new Location();
        event_location.setId(resultSet.getInt("id"));
        event_location.setCity(resultSet.getString("city"));
        event_location.setTitle(resultSet.getString("title"));
        event_location.setAddress(resultSet.getString("address"));
        return event_location;
    }
}
