package com.example.barakjacob.intereview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


/**
 * An activity class that allows the user create an event
 */
public class CreateEventActivity extends ActionBarActivity {

    //Spinner and its adapter
    private Spinner spinner;
    private Manager myManager;
    private ArrayAdapter<CharSequence> myAdapter;
    private EditText company;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        putImageForActionBar();
        myManager = Manager.getInstance();
        spinner = (Spinner)findViewById(R.id.eventType);
        company = (EditText)findViewById(R.id.eventCompany) ;
        dateButton = (Button)findViewById(R.id.eventDate) ;
        settingSpinnerAdapter();
    }

    /**
     * Sets the adapter of the spinner using the String array created in res/values/strings
     */
    private void settingSpinnerAdapter(){
        myAdapter = ArrayAdapter.createFromResource(this,R.array.eventTypes,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
    }

    /**
     * Displays the Date fragment (Fragment that allows user to choose a date)
     * Will be triggered after 'Choose Date' button was pressed (onClick method)
     */
    public void openDateDialog(View view){
        dateButton.setError(null);
        DateDialogFragment fragment = new DateDialogFragment();
        fragment.show(getSupportFragmentManager(),"Date Picker");
        dateButton.setTextColor(Color.BLACK);
    }

    /**
     * Validates the input fields and adds a new event to the UpcomingEvent List in Manager class
     * Will be triggered after 'Create Event' button was pressed (onClick method)
     * @param view the View object from which this method was triggered
     */
    public void onClick(View view){
        String choice = (String)spinner.getSelectedItem();
        String companyString = company.getText().toString();
        validateFields(choice,companyString);

    }

    /**
     * Validates the input fields and adds a new event to the UpcomingEvent List in Manager class
     * Outputs an appropriate error message if input is invalid
     * @param choice the spinner choice (type of event)
     * @param companyString the company entered by the user
     */
    private void validateFields(String choice, String companyString){
        //Spinner object does not need a validation (an option will always be chosen)
        //Validation of the company name
        if (companyString.trim().equals("")){
            company.requestFocus();
            company.setError("Please enter a valid company name");
            return;
        }
        //validation of the date - making sure a date was chosen by checking the chosen static boolean variable
        if(!DateDialogFragment.chosen) {
            dateButton.requestFocus();
            dateButton.setError("Please choose a date for this event");
            return;
        }
        myManager.add(new UpcomingEvent(choice,companyString,DateDialogFragment.getStringDate()));
        myAdapter.notifyDataSetChanged();
        DateDialogFragment.chosen = false;
        Intent i = new Intent(this,ProfileActivity.class);
        Toast.makeText(this,"Event added successfully!", Toast.LENGTH_LONG).show();
        startActivity(i);
    }




        /**
         * Puts an image instead of text for the action bar
         */
    private void putImageForActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        //remove all text titles
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        //use this layout as the action bar
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_actionbar, null);
        actionBar.setCustomView(actionBarLayout);
    }


}
