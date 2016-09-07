package com.example.barakjacob.intereview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

/**
 * An activity class that is used as the launcher activity
 * Will read data from files and storage and determine the next activity to be shown
 */
public class LauncherActivity extends ActionBarActivity {

    private Manager myManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        //read the Topics, Questions and Events from storage/file
        myManager = Manager.getInstance();
        readFromInternalStorage();
        parseQuestionsFromFile();
        parseTopicsFromFile();
        pickNextActivity();



    }

    /**
     * Parses/reads the questions from the JSON file and adds them to the manager
     */
    private void parseQuestionsFromFile() {
        JSONParser parser = new JSONParser();
        String fileName = "questions.json";
        try {
            //creating an input stream to the file
            InputStream inStream = getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(inStream);
            Object myObject = parser.parse(reader);
            //getting the JSON object and Array from the fields in the JSON file
            JSONObject jObject = (JSONObject) myObject;
            JSONArray topics = (JSONArray) jObject.get("questions");
            for (int i = 0; i < topics.size(); i++) {
                JSONObject object = (JSONObject) topics.get(i);
                //Getting the question
                String question = object.get("the question").toString();
                //Getting the 4 possible answers as a string array
                JSONArray possibleAnswers  = (JSONArray)object.get("choices");
                String[]choices = new String[4];
                for(int j=0;j<possibleAnswers.size();j++){
                    choices[j] = (String)possibleAnswers.get(j);
                }
                //Getting the answer(which is the index+1 of the correct answer in the string array)
                String answer = object.get("answer").toString();
                myManager.add(new Question(question, choices, answer));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Parses/reads the topics from the JSON file and adds them to the manager
     */
    private void parseTopicsFromFile() {
        JSONParser parser = new JSONParser();
        String fileName = "topics.json";
        try {
            InputStream inStream = getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(inStream);
            Object myObject = parser.parse(reader);
            JSONObject jObject = (JSONObject) myObject;
            JSONArray topics = (JSONArray) jObject.get("topics");
            for (int i = 0; i < topics.size(); i++) {
                JSONObject object = (JSONObject) topics.get(i);
                //Getting all the necessary fields
                String category = object.get("category").toString();
                String title = object.get("title").toString();
                String description = object.get("description").toString();
                //adding the Topic to the manager
                myManager.add(new Topic(category, title, description));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Reads the events from the internal storage of the device and adds them to the manager
     */
    private void readFromInternalStorage() {
        List<UpcomingEvent> toReturn = null;
        FileInputStream fis;
        try {
            fis = this.openFileInput("EVENTS");
            ObjectInputStream oi = new ObjectInputStream(fis);
            toReturn = (List<UpcomingEvent>) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (IOException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        addToManager(toReturn);
    }

    /**
     * Adds an UpcomingEvent list into the manager
     * @param list the UpcomingEvent list to be added
     */
    private void addToManager(List<UpcomingEvent> list){
        if (list != null) {
            for(int i=0;i<list.size();i++) {
                myManager.add(list.get(i));
            }
        }
    }

    /**
     * Picks the next activity the app will show based on the information read from the shared preferences
     */
    private void pickNextActivity(){
        //Use Shared Preferences to get the name and email of user
        //Name will be displayed in profile page, email can be used later to send emails
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        //DELETING THE SHARED PREFERENCE - TESTING PURPOSES (comment out when using the app)
        /*
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
        */
        String fullName = pref.getString("fullName","");
        String email = pref.getString("email","");
        Intent i;
        //redirecting to Profile page if data(name/email) for that user exist
        //redirecting to Register page if using app for the first time (no data)
        if(fullName.equals("") || email.equals("")) {
            i = new Intent(this, RegisterActivity.class);
        }
        else{
            i = new Intent(this,ProfileActivity.class);
            //Set the name and email for that user
            User.getInstance(fullName,email);
        }
        startActivity(i);
    }




}
