package com.DataBaseTask.DataBaseTask.Controllers;

import com.DataBaseTask.DataBaseTask.POJOClasses.*;
import com.DataBaseTask.DataBaseTask.RowMappers.*;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Controller
public class EventControllerImpl implements EventController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<Event_> getByPersonId(int id) {
        String query = "SELECT * FROM event WHERE id in (SELECT event_id FROM event_assignment WHERE person_id = " + id + ")";
        return jdbcTemplate.query(query,new Event_RowMapper());
    }

    @Override
    public List<Event_> getAllEvents() {
        String query = "SELECT * FROM event";
        return jdbcTemplate.query(query,new Event_RowMapper());
    }

    @Override
    public List<Date_> gatDate(int event_id) {
        String query = "SELECT * FROM date where id in (SELECT date_id FROM event where id="+event_id+")";
        return jdbcTemplate.query(query,new Date_RowMapper());
    }

    @Override
    public List<Location> getLocation(int event_id) {
        String query = "SELECT * FROM location where id in (SELECT location_id FROM event where id="+event_id+")";
        return jdbcTemplate.query(query,new Location_RowMapper());
    }

    @Override
    public List<Person_with_comment> getComments(int id) {
        String query = "SELECT comment.text,person.id,person.firstname,person.lastname FROM comment,person WHERE comment.person_id=person.id and comment.event_id = " + id;
        return jdbcTemplate.query(query,new Person_with_commentRowMapper());
    }

    @Override
    @Transactional
    public void addComment(String text, String email, String event_name) {
        String query1 = "SELECT * FROM person WHERE email = \'"+ email + "\'";
        String query2 = "SELECT * FROM event WHERE short_title = \'"+ event_name + "\'";
        List<Person> person = jdbcTemplate.query(query1,new PersonRowMapper());
        List<Event_>event = jdbcTemplate.query(query2,new Event_RowMapper());
        String query = "INSERT INTO comment(text,person_id,event_id) VALUES(\'" + text + "\'"+ "," + person.get(0).getId() + ","
                + event.get(0).getId() + ")";
        jdbcTemplate.execute(query);
    }

    @Override
    @Transactional
    public void addEvent(String id, String title, String description, String price, String start, String end,
                         String city,String address, String loc_title,String date_id,String loc_id) {
        String query1 = "INSERT INTO date(id,start,end)VALUES(?,?,?)";
        String query2 = "INSERT INTO location(id,city,title,address)VALUES(?,?,?,?)";
        String query3 = "INSERT INTO event(id,title,description,price,location_id,date_id) VALUES(?,?,?,?,?,?)";
        jdbcTemplate.execute(query1,(PreparedStatementCallback<Object>)preparedStatement ->{
            preparedStatement.setInt(1,Integer.parseInt(date_id));
            preparedStatement.setString(2,start);
            preparedStatement.setString(3,end);
            return preparedStatement.execute();
        });
        jdbcTemplate.execute(query2,(PreparedStatementCallback<Object>)preparedStatement ->{
            preparedStatement.setInt(1,Integer.parseInt(loc_id));
            preparedStatement.setString(2,city);
            preparedStatement.setString(3,loc_title);
            preparedStatement.setString(4,address);
            return preparedStatement.execute();
        });
        jdbcTemplate.execute(query3,(PreparedStatementCallback<Object>)  preparedStatement ->{
            preparedStatement.setInt(1,Integer.parseInt(id));
            preparedStatement.setString(2,title);
            preparedStatement.setString(3,description);
            preparedStatement.setString(4,price);
            preparedStatement.setInt(5,Integer.parseInt(loc_id));
            preparedStatement.setInt(6,Integer.parseInt(date_id));
            return preparedStatement.execute();
        });
    }
}
