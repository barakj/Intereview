package com.example.barakjacob.intereview;

import java.io.Serializable;

/**
 * A class that represents a certain upcoming event
 * Consists of the type of the event, the company/host, and the date
 * Implementing serializable in order to save and restore from internal storage
 */
public class UpcomingEvent implements Serializable {
    private String type;
    private String company;
    private String date;

    /**
     * Constructs a Question object with a certain question, possible answers array, and answer
     * @param typeOfEvent the type of the event
     * @param compOfEvent the company hosting the event
     * @param dateOfEvent the date of the event as a string(dd.mm.yyyy)
     */
    public UpcomingEvent(String typeOfEvent, String compOfEvent, String dateOfEvent){
        this.type = typeOfEvent;
        this.company = compOfEvent;
        this.date = dateOfEvent;
    }

    /**
     * Gets the type of an UpcomingEvent
     * @return the type
     */
    public String getType(){
        return this.type;
    }

    /**
     * Gets the date of the UpcomingEvent
     * @return the date
     */
    public String getDate(){
        return this.date;
    }

    /**
     * Gets the company of an UpcomingEvent
     * @return the company
     */
    public String getCompany(){
        return this.company;
    }
}
