package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Date_;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Date_RowMapper implements RowMapper<Date_> {
    @Override
    public Date_ mapRow(ResultSet resultSet, int i) throws SQLException {
        Date_ event_date = new Date_();
        event_date.setId(resultSet.getInt("id"));
        event_date.setDate_start(resultSet.getString("date_start"));
        event_date.setDate_end(resultSet.getString("date_end"));

        return event_date;
    }
}
