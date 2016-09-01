package com.example.barakjacob.intereview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An Adapter class for the Upcoming Event ListView
 * This adapter will connect between view/group positions(id) and the ListView
 */
public class EventListAdapter extends BaseAdapter {
    private Manager myManager;
    private Context context;

    /**
     * Constructor of adapter
     * @param c the context of the app(the activity in which the ListView was created)
     */
    public EventListAdapter(Context c){
        myManager = Manager.getInstance();
        this.context = c;

    }

    /**
     * Gets the number of items in the ListView
     * @return the size of the ListView
     */
    @Override
    public int getCount() {
        return myManager.getUpcomingEvents().size();
    }

    /**
     * Gets the position of an item in the ListView
     * @return the position
     */
    @Override
    public Object getItem(int position) {
        return position;
    }

    /**
     * Gets the ID of an item in the ListView
     * @return the ID
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Creates the View of each item in the ListView(only parent since non expandable)
     * @return the View of each item in the ListView
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        //Get the layout for each cell
        View myView = inflater.inflate(R.layout.event_list_view,parent,false);
        TextView type = (TextView)myView.findViewById(R.id.type);
        TextView company = (TextView)myView.findViewById(R.id.company);
        TextView date = (TextView)myView.findViewById(R.id.date);
        ImageView delete = (ImageView) myView.findViewById(R.id.delete);

        //ActionListener for the delete button
        delete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                myManager.removeEvent(position);
                notifyDataSetChanged();
            }
        });
        //set text of each item in the last by getting the information of every event
        type.setText(myManager.getUpcomingEvents().get(position).getType());
        company.setText(myManager.getUpcomingEvents().get(position).getCompany());
        date.setText(myManager.getUpcomingEvents().get(position).getDate());

        return myView;
    }
}
