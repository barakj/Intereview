package com.example.barakjacob.intereview;

import android.app.usage.UsageEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A Singleton Manager class that will store and manage all different classes of the app
 * Will manage Lists of Question class, Topic class and UpcomingEvent class
 * Consists of the actual question, the possible answers to that question, and the answer to that question
 */
public class Manager {

    //Singleton
    private static Manager instance;
    //List of topics
    private List<Topic> topicList;
    //List of events
    private List<UpcomingEvent> eventList;
    //List of questions
    private List<Question> questionList;

    /**
     * Gets the only instance of Manager, or calling the constructor parameters if null
     * @return the only instance of the User singleton
     */
    public static Manager getInstance(){
        if(instance==null){
            instance = new Manager();
        }
        return instance;
    }

    /**
     * Constructs a Manager by initializing all the lists
     */
    private Manager(){
        this.topicList = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.questionList = new ArrayList<>();
    }

    /**
     * Adds a Topic to the list retained in Manager
     */
    public void add(Topic t){
        this.topicList.add(t);
    }

    /**
     * Overloaded method
     * Adds an UpcomingEvent object to the list retained in Manager
     */
    public void add(UpcomingEvent e){
        this.eventList.add(e);
    }

    /**
     * Overloaded method
     * Adds a Question object to the list retained in Manager
     */
    public void add(Question q){
        this.questionList.add(q);
    }

    /**
     * Remove a certain UpcomingEvent object from the list retained in Manager by its index
     * @param index the index of the UpcomingEvent to be removed
     */
    public void removeEvent(int index){
        this.eventList.remove(index);
    }

    /**
     * Gets the List of Topic objects
     * @return the unmodifiable list of all Topics
     */
    public List<Topic> getTopics() {
        //make sure nobody can change this list but me
        return Collections.unmodifiableList(topicList);
    }

    /**
     * Gets the List of Question objects
     * @return the unmodifiable list of all Questions
     */
    public List<Question> getQuestions() {
        //make sure nobody can change this list but me
        return Collections.unmodifiableList(questionList);
    }

    /**
     * Gets the List of UpcomingEvent objects
     * @return the unmodifiable list of all UpcomingEvents
     */
    public List<UpcomingEvent> getUpcomingEvents(){
        //make sure nobody can change this list but me
        return Collections.unmodifiableList(eventList);
    }
}
