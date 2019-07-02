package com.DataBaseTask.DataBaseTask.UI;

import com.DataBaseTask.DataBaseTask.Controllers.DataBaseController;
import com.DataBaseTask.DataBaseTask.Controllers.EventController;
import com.DataBaseTask.DataBaseTask.Controllers.PersonController;
import com.DataBaseTask.DataBaseTask.POJOClasses.*;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.*;

@SpringUI(path="/persons")
public class MyUI extends UI {
    @Autowired
    private EventController eventController;

    @Autowired
    private PersonController personController;

    @Autowired
    private DataBaseController dataBaseController;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        final HorizontalLayout horizontalLayout1= new HorizontalLayout();
        final HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        final  HorizontalLayout horizontalLayout3 = new HorizontalLayout();

        Button buttonShowPersons = new Button("All Persons");
        Button buttonDownloadDataJSON = new Button("Download Data in JSON format");
        Button buttonLoadDataJSON = new Button("Upload Data in JSON format");
        Button buttonAddPerson = new Button("Add Person");

        TextField id = new TextField();
        id.setPlaceholder("Id");

        TextField firstname = new TextField();
        firstname.setPlaceholder("Firstname");

        TextField lastname = new TextField();
        lastname.setPlaceholder("Lastname");

        TextField email = new TextField();
        email.setPlaceholder("Email");

        TextField eventsId = new TextField();
        eventsId.setPlaceholder("Enter events ids  divided by comma");

        Grid<Person> personGrid = new Grid<>();
        personGrid.setSizeFull();
        personGrid.addColumn(Person::getId).setCaption("ID").setWidth(100);
        personGrid.addColumn(Person::getFirstname).setCaption("Firstname");
        personGrid.addColumn(Person::getLastname).setCaption("Lastname");
        personGrid.addColumn(Person::getEmail).setCaption("Email");

        Grid<Event_> eventGrid = new Grid<>();
        eventGrid.setSizeFull();
        eventGrid.addColumn(Event_::getId).setCaption("Event_ ID ").setWidth(100);
        eventGrid.addColumn(Event_::getShort_title).setCaption("Short_title");
        eventGrid.addColumn(Event_::getDescription).setCaption("Description");
        eventGrid.addColumn(Event_::getPrice).setCaption("Price");

        Grid<Date_> dateGrid = new Grid<>();
        dateGrid.addColumn(Date_::getDate_start).setCaption("Date_start").setWidth(150);
        dateGrid.addColumn(Date_::getDate_end).setCaption("Date_end").setWidth(150);
        dateGrid.setWidth("300");

        Grid<Location> locationgrid = new Grid<>();
        locationgrid.addColumn(Location::getTitle).setCaption("Title");
        locationgrid.addColumn(Location::getCity).setCaption("City").setWidth(80);
        locationgrid.addColumn(Location::getAddress).setCaption("Address");


        personGrid.addSelectionListener(event -> {
            if(event.getFirstSelectedItem().isPresent()) {
                int selectedId = event.getFirstSelectedItem().get().getId();
                List<Event_> events = eventController.getByPersonId(selectedId);
                eventGrid.setItems(events);
                List<Date_> dates = new ArrayList<>();
                events.forEach(event1 -> dates.add(eventController.gatDate(event1.getDate_id()).get(0)));
                List<Location> locations = new ArrayList<>();
                events.forEach(event1 -> locations.add(eventController.getLocation(event1.getDate_id()).get(0)));
                dateGrid.setItems(dates);
                locationgrid.setItems(locations);
            }
        });


        buttonShowPersons.addClickListener(clickEvent -> {
            personGrid.setItems(personController.getAllPersons());
            eventGrid.setItems(new ArrayList<>());
            dateGrid.setItems(new ArrayList<>());
            locationgrid.setItems(new ArrayList<>());
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
        buttonAddPerson.addClickListener(clickEvent -> {
                    String person_id = id.getValue();
                    String person_firstname = firstname.getValue();
                    String person_lastname = lastname.getValue();
                    String person_email = email.getValue();
                    List<String> events_Id = Arrays.asList(eventsId.getValue().split(","));
                    if (person_email != "" && person_firstname != "" && person_lastname != "" && person_id != "" && !events_Id.contains("")){
                        personController.addPersonandEvent(person_id, person_firstname, person_lastname, person_email,events_Id);
                    }
        });
        horizontalLayout1.addComponents(buttonShowPersons,buttonDownloadDataJSON,buttonLoadDataJSON);
        horizontalLayout2.addComponents(eventGrid,dateGrid,locationgrid);
        horizontalLayout3.addComponents(buttonAddPerson,id,firstname,lastname,email,eventsId);
        verticalLayout.addComponents(horizontalLayout1,horizontalLayout3,personGrid, horizontalLayout2);
        setContent(verticalLayout);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public class MyUIServlet extends VaadinServlet {
    }
}
