package com.example.barakjacob.intereview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A Fragment that is used as the Review tab.
 * Will give the user choices of different topics in computer science to review.
 */
public class ReviewMaterialFragment extends Fragment {

    //top of the page textView
    private TextView welcomeText;


    public ReviewMaterialFragment() {
        // Required empty public constructor
    }

    /**
     * Creates an instance of this fragment
     * @returns instance of this fragment
     */
    public static ReviewMaterialFragment newInstance() {
        ReviewMaterialFragment fragment = new ReviewMaterialFragment();
        return fragment;
    }

    /**
     * Creates the view for this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_review_material, container, false);
        welcomeText = (TextView) myView.findViewById(R.id.WelcomeText);
        welcomeText.setText("\nWhat do you want to work on today?");


        return myView;
    }




}



