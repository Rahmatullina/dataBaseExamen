package com.DataBaseTask.DataBaseTask.Controllers;

import com.DataBaseTask.DataBaseTask.POJOClasses.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonController {
    List<Person> getAllPersons();
    List<Person> getByEventId(int id);
    @Transactional
    void addPersonandEvent(String id,String firstname,String lastname,String email,List<String> events_Id);
}
