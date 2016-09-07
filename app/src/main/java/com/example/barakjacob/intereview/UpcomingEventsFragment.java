package com.example.barakjacob.intereview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * A Fragment that is used as the My Events tab.
 * Will give the option to create/delete events such as job interview, hackathon or a workshop
 */
public class UpcomingEventsFragment extends Fragment {

    private TextView eventText;
    private ListView eventList;
    private Manager myManager;

    public UpcomingEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Creates an instance of this fragment
     * @returns instance of this fragment
     */
    public static UpcomingEventsFragment newInstance() {
        UpcomingEventsFragment fragment = new UpcomingEventsFragment();
        return fragment;
    }

    /**
     * Creates the view for this fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        eventList = (ListView) myView.findViewById(R.id.eventsListView);
        myManager = Manager.getInstance();
        //setting the adapter of the listView (Custom class i made)
        eventList.setAdapter(new EventListAdapter(getActivity()));
        return myView;
    }



}


