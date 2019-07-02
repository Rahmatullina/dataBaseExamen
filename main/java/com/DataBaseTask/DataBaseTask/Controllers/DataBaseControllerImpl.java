package com.DataBaseTask.DataBaseTask.Controllers;

import com.DataBaseTask.DataBaseTask.POJOClasses.*;
import com.DataBaseTask.DataBaseTask.RowMappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class DataBaseControllerImpl implements DataBaseController{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public DataBaseStructureClass getAllDataInJSON() {
        String query1 = "SELECT * FROM person";
        String query2 = "SELECT * FROM event";
        String query3 = "SELECT * FROM comment";
        String query4 = "SELECT * FROM date";
        String query5 = "SELECT * FROM location";
        String query7 = "SELECT * FROM event_assignment";


        List<Person> person_list = jdbcTemplate.query(query1,new PersonRowMapper());
        List<Event_> event_list = jdbcTemplate.query(query2,new Event_RowMapper());
        List<Comment> comment_list = jdbcTemplate.query(query3,new Comment_RowMapper());
        List<Date_> date_list = jdbcTemplate.query(query4,new Date_RowMapper());
        List<Location> location_list = jdbcTemplate.query(query5,new Location_RowMapper());
        List<Event_Assignment> event_assignment_list = jdbcTemplate.query(query7,new Event_Assignment_RowMapper());

        DataBaseStructureClass dataBaseStructureClass = new DataBaseStructureClass();
        dataBaseStructureClass.setEvent_list(event_list);
        dataBaseStructureClass.setDate_list(date_list);
        dataBaseStructureClass.setLocation_list(location_list);
        dataBaseStructureClass.setPerson_list(person_list);
        dataBaseStructureClass.setEvent_assignment_list(event_assignment_list);
        dataBaseStructureClass.setComment_list(comment_list);

        return dataBaseStructureClass;
    }

    @Override
    @Transactional
    public void fillDataBasInJSON(DataBaseStructureClass dataBaseStructureClass) {
        List<Event_> event_list =  dataBaseStructureClass.getEvent_list();
        List<Date_> date_list = dataBaseStructureClass.getDate_list();
        List<Location> location_list = dataBaseStructureClass.getLocation_list();
        List<Person> person_list = dataBaseStructureClass.getPerson_list();
        List<Event_Assignment> event_assignment_list = dataBaseStructureClass.getEvent_assignment_list();
        List<Comment> comment_list = dataBaseStructureClass.getComment_list();

        if (location_list!=null && location_list.size()>0){
            location_list.forEach(event_location -> {
                String query = "INSERT into location VALUES(?,?,?,?);";
                jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) preparedStatement -> {
                    preparedStatement.setInt(1, event_location.getId());
                    preparedStatement.setString(2, event_location.getCity());
                    preparedStatement.setString(3, event_location.getTitle());
                    preparedStatement.setString(4, event_location.getAddress());
                    return preparedStatement.execute();
                });
            });
        }
        if (date_list!=null && date_list.size()>0){
            date_list.forEach(event_date -> {
                String query = "INSERT into date VALUES(?,?,?);";
                jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) preparedStatement -> {
                    preparedStatement.setInt(1, event_date.getId());
                    preparedStatement.setString(2, event_date.getDate_start());
                    preparedStatement.setString(3, event_date.getDate_end());
                    return preparedStatement.execute();
                });
            });
        }
        if (event_list!=null && event_list.size()>0){
            event_list.forEach(event -> {
                String query = "INSERT into event VALUES(?,?,?,?,?,?);";
                jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) preparedStatement -> {
                    preparedStatement.setInt(1, event.getId());
                    preparedStatement.setString(2, event.getShort_title());
                    preparedStatement.setString(3, event.getDescription());
                    preparedStatement.setInt(4, event.getLocation_id());
                    preparedStatement.setString(5, event.getPrice());
                    preparedStatement.setInt(6, event.getDate_id());
                    return preparedStatement.execute();
                });
            });
        }
        if (person_list!=null && person_list.size()>0){
            person_list.forEach(person -> {
                String query = "INSERT into person VALUES(?,?,?,?);";
                jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) preparedStatement -> {
                    preparedStatement.setInt(1, person.getId());
                    preparedStatement.setString(2, person.getFirstname());
                    preparedStatement.setString(3, person.getLastname());
                    preparedStatement.setString(4, person.getEmail());
                    return preparedStatement.execute();
                });
            });
        }
        if (event_assignment_list!=null && event_assignment_list.size()>0){
            event_assignment_list.forEach(event_assignment -> {
                String query = "INSERT into event_assignment(person_id,event_id) VALUES(?,?);";
                jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) preparedStatement -> {
                    preparedStatement.setInt(1, event_assignment.getPerson_id());
                    preparedStatement.setInt(2, event_assignment.getEvent_id());
                    return preparedStatement.execute();
                });
            });
        }
        if(comment_list!=null && comment_list.size()>0){
            comment_list.forEach(comment -> {
                String query = "INSERT into comment VALUES(?,?,?);";
                jdbcTemplate.execute(query,(PreparedStatementCallback<Object>)preparedStatement -> {
                    preparedStatement.setInt(1,comment.getPerson_id());
                    preparedStatement.setInt(2,comment.getEvent_id());
                    preparedStatement.setString(3,comment.getText());
                    return preparedStatement.execute();
                });
            });
        }
    }
}
