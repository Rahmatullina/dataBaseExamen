package com.DataBaseTask.DataBaseTask.Controllers;

import com.DataBaseTask.DataBaseTask.POJOClasses.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EventController {
    List<Event_> getByPersonId(int id);
    List<Event_> getAllEvents();
    List<Date_> gatDate(int id);
    List<Location> getLocation(int id);
    List<Person_with_comment> getComments(int id);
    @Transactional
    void addComment(String text,String email,String event_name);
    @Transactional
    void addEvent(String id,String title,String description,String price,String start,
                  String end,String city,String address,String loc_title,String date_id,String loc_id);

}
