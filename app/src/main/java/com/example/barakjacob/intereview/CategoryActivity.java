package com.example.barakjacob.intereview;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * An activity class that displays Topics/Questions to review
 * The topics/questions presented will be based on the button pressed by the user in ReviewMaterial fragment in Profile Activity
 */
public class CategoryActivity extends ActionBarActivity {

    BaseExpandableListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        putImageForActionBar();
        DetermineTopicsToDisplay();
        setListAdapter();
    }

    /**
     * Determines the categor to display based on the extra information passed in with the Intent
     * Will set the adapter to either be the Questions adapter(different layout) or the Topics adapter
     * Will set the text at the top of the page depending on which categories will be shown
     */
    public void DetermineTopicsToDisplay() {
        //get info the was passed with intent
        Bundle categoryData = getIntent().getExtras();
        if(categoryData==null)
            return;
        int categoryNumber = categoryData.getInt("info");
        String newCategory = "";
        boolean isQuestion = false;
        int startingIndex = 0;
        int endingIndex = 0;
        switch(categoryNumber) {
            case ProfileActivity.DATA_STRUCTURE_ID:
                newCategory = "Data Structures";
                startingIndex = 0;
                endingIndex = ProfileActivity.NUM_DATA_STRUCTURE;
                break;
            case ProfileActivity.SORTS_ID:
                newCategory = "Sorts";
                startingIndex = ProfileActivity.NUM_DATA_STRUCTURE;
                endingIndex = ProfileActivity.NUM_SORTS;
                break;
            case ProfileActivity.TERMS_ID:
                newCategory = "Terms";
                startingIndex = ProfileActivity.NUM_SORTS;
                endingIndex = ProfileActivity.NUM_TERMS;
                break;
            case ProfileActivity.QUESTIONS_ID:
                isQuestion = true;
        }
        //if category to be presented is the Sample Questions - need to use different layout(different adapter)
        if(isQuestion)
            myAdapter = new ExpandableQuestionListAdapter(this);
        else
            myAdapter = new ExpandableListAdapter(this,startingIndex,endingIndex);

        setPageTitle(newCategory);
    }


    /**
     * Sets the title of the category page with a certain string
     * @param title the top string to be shown as the title
     */
    public void setPageTitle(String title) {
        TextView pageTitle = (TextView)findViewById(R.id.categoryTitle);
        pageTitle.setText(title);
    }

    /**
     * Sets the adapter of the ExpandableListView
     */
    public void setListAdapter() {
        ExpandableListView list = (ExpandableListView)findViewById(R.id.topicsList);
        list.setAdapter(myAdapter);
    }

    /**
     * Puts an image instead of text for the action bar
     */
    public void putImageForActionBar() {
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


