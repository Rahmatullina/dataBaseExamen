package com.DataBaseTask.DataBaseTask.RowMappers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("id"));
        person.setFirstname(resultSet.getString("firstname"));
        person.setLastname(resultSet.getString("lastname"));
        person.setEmail(resultSet.getString("email"));
        return person;
    }
}
