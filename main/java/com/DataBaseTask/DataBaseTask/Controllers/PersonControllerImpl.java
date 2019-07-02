package com.DataBaseTask.DataBaseTask.Controllers;
import com.DataBaseTask.DataBaseTask.POJOClasses.Person;
import com.DataBaseTask.DataBaseTask.RowMappers.PersonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;


@Controller
public class PersonControllerImpl implements PersonController {

    @Autowired
    private JdbcTemplate template;

    @Override
     public List<Person> getAllPersons()  {
        return template.query("SELECT * FROM person ", new PersonRowMapper());
    }

    @Override
    public List<Person> getByEventId(int id) {
        String query = "SELECT * FROM person WHERE id in(SELECT person_id FROM event_assignment WHERE event_id="+id+")";
        return template.query(query,new PersonRowMapper());
    }

    @Override
    @Transactional
    public void addPersonandEvent(String id, String firstname, String lastname, String email, List<String> events_Id) {
        String query1 = "INSERT INTO person(id,firstname,lastname,email) VALUES(?,?,?,?)";
        String query2 = "INSERT INTO event_assignment(person_id,event_id) VALUES(?,?)";
        template.execute(query1,(PreparedStatementCallback<Object>) preparedStatement -> {
            preparedStatement.setInt(1,Integer.parseInt(id));
            preparedStatement.setString(2,firstname);
            preparedStatement.setString(3,lastname);
            preparedStatement.setString(4,email);
            return  preparedStatement.execute();
        });
        for (String s : events_Id) {
            template.execute(query2,(PreparedStatementCallback<Object>)preparedStatement -> {
                preparedStatement.setInt(1,Integer.parseInt(id));
                preparedStatement.setInt(2,Integer.parseInt(s));
                return preparedStatement.execute();
            });

        }
    }
}
