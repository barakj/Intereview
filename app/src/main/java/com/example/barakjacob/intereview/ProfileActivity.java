package com.example.barakjacob.intereview;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
/**
 * An activity class that is used as the profile activity
 * This activity is used as a menu (Tabbed Activity) for other activities such as creating an event, and reviewing material
 * IMPORTANT: Template of this activity was created using the default Tabbed Activity in IntelliJ
 * Methods I had to change will have the "(My addition" tag
 */
public class ProfileActivity extends ActionBarActivity implements ActionBar.TabListener {
    //Ending indexes for each of the categories in the Manager
    public static final int NUM_DATA_STRUCTURE = 9;
    public static final int NUM_SORTS = 5 + NUM_DATA_STRUCTURE;
    public static final int NUM_TERMS = 12 + NUM_SORTS;
    public static final int DATA_STRUCTURE_ID = 0;
    public static final int SORTS_ID = 1;
    public static final int TERMS_ID = 2;
    public static final int QUESTIONS_ID = 3;

    private Manager myManager;
    //Adapter for the tab fragments
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private User currentUser;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        myManager = Manager.getInstance();
        currentUser = User.getInstance();
        // Create the adapter that will return a fragment for each of the three primary sections of the activity
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        //Using an image for the action bar
        putImageForActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }
    /**
     * Saves upcoming events to the internal storage when application is paused
     */
    @Override
    public void onPause(){
        super.onPause();
        saveToInternalStorage();
    }

    /**
     * Saves upcoming events to the internal storage of the device
     */
    public void saveToInternalStorage() {
        try {
            FileOutputStream fos = this.openFileOutput("EVENTS", Context.MODE_PRIVATE);
            ObjectOutputStream of = new ObjectOutputStream(fos);
            of.writeObject(myManager.getUpcomingEvents());
            of.flush();
            of.close();
            fos.close();
        } catch (Exception e) {
            Log.e("InternalStorage", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Creates the 'My Profile' tab - the initial tab (My addition)
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //inflating the profile fragment layout
            View myView = inflater.inflate(R.layout.fragment_profile, container, false);
            TextView profileText = (TextView) myView.findViewById(R.id.profileTextView);
            TextView emailText = (TextView) myView.findViewById(R.id.emailTextView);
            profileText.setText("Hello " + User.getInstance().getFirstName() + "!");
            emailText.setText(User.getInstance().getEmail());
            return myView;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Instantiates the fragment for the given page/tab (My addition)
         * @return the instance of the fragment to be shown
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                //Profile
                case 0:
                    return PlaceholderFragment.newInstance(position + 1);
                //Review
                case 1:
                    return ReviewMaterialFragment.newInstance();
                //Event
                case 2:
                    return UpcomingEventsFragment.newInstance();
            }
            return null;
        }
        /**
         * Counts the number of tabs (My addition)
         * @return the number of tabs/pages
         */
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        /**
         * sets the tab header based on the page/tab position(My addition)
         * @return the tab header as a String
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Profile";
                case 1:
                    return "Review";
                case 2:
                    return "My Events";
            }
            return null;
        }
    }

    /**
     * Redirects to Category Activity page (My addition)
     * Determines which topics should be shown in the next Activity (Category Activity)
     * Will use the button that triggered this method to find the topics of which category to show
     * Will send information to Category Activity on which category was pressed
     * @param view the View object from which this method was triggered
     */
    public void onClick(View view) {
        //use to check which button was triggered this method
        int buttonId = view.getId();
        Intent i = new Intent(this, CategoryActivity.class);
        switch (buttonId) {
            //if Data Structure button was pressed
            case R.id.DataStructuresButton:
                i.putExtra("info", DATA_STRUCTURE_ID);
                break;
            //if Sorts button was pressed
            case R.id.SortsButton:
                i.putExtra("info", SORTS_ID);
                break;
            //if TERMS button was pressed
            case R.id.TermsButton:
                i.putExtra("info", TERMS_ID);
                break;
            //if Sample Questions button was pressed
            case R.id.SampleQuestionsButton:
                i.putExtra("info", QUESTIONS_ID);
        }
        startActivity(i);
    }

    /**
     * Redirects the user to the CreateEvent Activity
     * Will be triggered after 'Add Event' button was pressed (onClick method)
     * @param view the View object from which this method was triggered
     */
    public void createEvent(View view){
        Intent i = new Intent(this,CreateEventActivity.class);
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

    /**
     * Disables the back button for profile activity, prevent random page from showing
     */
    @Override
    public void onBackPressed(){
    }



}