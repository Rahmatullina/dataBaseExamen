package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Person_with_comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Person_with_commentRowMapper implements RowMapper<Person_with_comment> {
    @Override
    public Person_with_comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Person_with_comment person_with_comment= new Person_with_comment();
        person_with_comment.setText(resultSet.getString("text"));
        person_with_comment.setPerson_firstname(resultSet.getString("firstname"));
        person_with_comment.setPerson_lastname(resultSet.getString("lastname"));
        return person_with_comment;
    }
}
