package com.example.barakjacob.intereview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An activity class that is used as the sign up/register activity
 * Will only be shown when the app is launched for the first time(No user yet), and will redirect to Profile activity
 */
public class RegisterActivity extends ActionBarActivity {

    //Register page text fields
    EditText fullNameInput;
    EditText emailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fullNameInput = (EditText)findViewById(R.id.fullNameField);
        emailInput = (EditText)findViewById(R.id.emailField);
    }

    /**
     * Validates the input fields and saves input to shared preference file
     * Will be triggered after 'Sign Up' button was pressed (ActionListener/onClick method)
     * @param view the View object from which this method was triggered
     */
    public void saveInfo(View view){
        //get a reference to the shared preference file
        SharedPreferences pref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String fullName = fullNameInput.getText().toString();
        String email = emailInput.getText().toString();
        validateFields(fullName,email,editor);


    }

    /**
     * uses Validation methods to validate the input fields
     * Outputs an appropriate error message if fields are invalid or saves the info into a shared preference file if valid
     * @param fullName the name to validate/save
     * @param email the email to validate/save
     * @param editor the Shared Preference editor that will write the information into the file
     */
    public void validateFields(String fullName, String email, SharedPreferences.Editor editor){
        //if name is not valid
        if(!isValidName(fullName)) {
            fullNameInput.requestFocus();
            fullNameInput.setError("Please enter a valid full name");
        }
        //if email is not valid
        else if(!isValidEmail(email)) {
            emailInput.requestFocus();
            emailInput.setError("Please enter a valid email address");
        }
        //Both are valid - save the information using the shared preference editor and redirect to Profile Activity
        else{
            editor.putString("fullName", fullName);
            editor.putString("email", email);
            editor.apply();
            Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_LONG).show();
            User.getInstance(fullName, email);
            Intent i = new Intent(this, ProfileActivity.class);
            startActivity(i);
        }

    }

    /**
     * Validates that the email entered is valid
     * @param target the email to validate
     * @returns true if email is valid, false if not
     */
    public boolean isValidEmail(String target) {
        if (target == null)
            return false;
        //use a built in email pattern
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Validates that the full name entered is valid
     * @param target the name to validate
     * @returns true if name is valid, false if not
     */
    public boolean isValidName(String target){
        //regular expression was found using online sources, takes care of every name other than 1 letter first/last names
        String regex = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}";
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(target);
        //making sure the name includes a space (full name)
        if(target.toString().trim().indexOf(" ") == -1)
            return false;
        return match.find();
    }

}
