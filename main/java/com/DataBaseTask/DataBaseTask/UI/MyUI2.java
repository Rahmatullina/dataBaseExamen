package com.DataBaseTask.DataBaseTask.UI;

import com.DataBaseTask.DataBaseTask.Controllers.DataBaseController;
import com.DataBaseTask.DataBaseTask.Controllers.EventController;
import com.DataBaseTask.DataBaseTask.POJOClasses.*;
import com.google.gson.Gson;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringUI(path="/events")
public class MyUI2 extends UI {

    @Autowired
    private EventController eventController;

    @Autowired
    DataBaseController dataBaseController;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout verticalLayout = new VerticalLayout();
        final HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        final HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        final HorizontalLayout horizontalLayout3 = new HorizontalLayout();

        Button buttonShowEvents = new Button("Show Events");
        Button buttonDownloadDataJSON = new Button("Download Data in JSON format");
        Button buttonLoadDataJSON = new Button("Upload Data in JSON format");
        Button buttonAddComment = new Button("Add Comment");

        TextField event_name = new TextField();
        event_name.setPlaceholder("Event name");

        TextField text= new TextField();
        text.setPlaceholder("Your comment");

        TextField email = new TextField();
        email.setPlaceholder("Your Email");


        Grid<Person_with_comment> commentGrid = new Grid<>();
        commentGrid.setSizeFull();
        commentGrid.addColumn(Person_with_comment::getText).setCaption("Comment");
        commentGrid.addColumn(Person_with_comment::getPerson_firstname).setCaption("firstname");
        commentGrid.addColumn(Person_with_comment::getPerson_lastname).setCaption("lastname");

        Grid<Event_> eventGrid = new Grid<>();
        eventGrid.setSizeFull();
        eventGrid.addColumn(Event_::getId).setCaption("ID ").setWidth(70);
        eventGrid.addColumn(Event_::getShort_title).setCaption("Short_title");
        eventGrid.addColumn(Event_::getDescription).setCaption("Description");
        eventGrid.addColumn(Event_::getPrice).setCaption("Price");

        Grid<Date_> dateGrid = new Grid<>();
        dateGrid.addColumn(Date_::getDate_start).setCaption("Date_start").setWidth(150);
        dateGrid.addColumn(Date_::getDate_end).setCaption("Date_end").setWidth(150);
        dateGrid.setWidth("300");

        Grid<Location> locationGrid = new Grid<>();
        locationGrid.addColumn(Location::getTitle).setCaption("Title");
        locationGrid.addColumn(Location::getCity).setCaption("City").setWidth(80);
        locationGrid.addColumn(Location::getAddress).setCaption("Address");

        eventGrid.addSelectionListener(event -> {
            if(event.getFirstSelectedItem().isPresent())
                commentGrid.setItems(eventController.getComments(event.getFirstSelectedItem().get().getId()));
            } );


        buttonShowEvents.addClickListener(clickEvent -> {
            List<Event_> listEvent = eventController.getAllEvents();
            List<Date_> listDate = new ArrayList<>();
            List<Location> listLocation = new ArrayList<>();
            listEvent.forEach(event -> {
                listDate.add(eventController.gatDate(event.getId()).get(0));
                listLocation.add(eventController.getLocation(event.getId()).get(0));
            });
            eventGrid.setItems(listEvent);
            dateGrid.setItems(listDate);
            locationGrid.setItems(listLocation);
            commentGrid.setItems(new ArrayList<>());

        });
        buttonDownloadDataJSON.addClickListener(clickEvent -> {
            Gson gson = new Gson();
            String dataJSON = gson.toJson(dataBaseController.getAllDataInJSON());

            try (FileWriter out = new FileWriter("C:\\Users\\stron\\IdeaProjects\\DataBaseTask\\src\\" +
                    "main\\resources\\DownloadedData\\data.json" )) {
                out.write(dataJSON);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        buttonLoadDataJSON.addClickListener(clickEvent -> {
            Gson gson = new Gson();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\stron\\IdeaProjects\\"+
                    "DataBaseTask\\src\\main\\resources\\DataToUpload\\fillFB.json"))) {
                String json = new String();
                String line ;
                while((line =  bufferedReader.readLine())!=null)
                    json+=line;
                dataBaseController.fillDataBasInJSON(gson.fromJson(json,DataBaseStructureClass.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        buttonAddComment.addClickListener(clickEvent ->{
            if(event_name.getValue()!="" && email.getValue()!="" && text.getValue()!="" )
                eventController.addComment(text.getValue(),email.getValue(),event_name.getValue());
   });

        horizontalLayout1.addComponents(buttonShowEvents,buttonDownloadDataJSON,buttonLoadDataJSON);
        horizontalLayout2.addComponents(eventGrid,dateGrid,locationGrid);
        horizontalLayout3.addComponents(text,email,event_name,buttonAddComment);
        verticalLayout.addComponents(horizontalLayout1,horizontalLayout3,horizontalLayout2,commentGrid);
        setContent(verticalLayout);

    }
}
