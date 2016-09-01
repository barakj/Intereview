package com.example.barakjacob.intereview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * An Adapter class for the Topics ExpandableListView
 * This adapter will connect between views/group positions(id) and the ListView
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Manager myManager;
    private List<Topic> myList;
    private Context context;

    /**
     * Constructor of adapter
     * @param c the context of the app(the activity in which the ListView was created)
     * @param startingIndex the index of the first topic to be shown
     * @param endingIndex the index of the first topic to NOT be shown (index of last topic + 1)
     */
    public ExpandableListAdapter(Context c, int startingIndex, int endingIndex){
        context = c;
        myManager = Manager.getInstance();
        myList = myManager.getTopics().subList(startingIndex,endingIndex);
    }

    /**
     * Gets the number of groups in this ExpandableListView
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return myList.size();
    }

    /**
     * Gets the number of children in this ExpandableListView
     * @return the number of children
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * Gets the group of each item in the ExpandableListView
     * @return the group/topic of each item
     */
    @Override
    public Object getGroup(int groupPosition) {
        return myList.get(groupPosition);
    }

    /**
     * Gets the child of each item in the ExpandableListView
     * @return the description to be displayed for each chilld
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myList.get(groupPosition).getDescription();
    }

    /**
     * Gets the id of a group in the list
     * @return the id of each group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the id of a child in the list
     * @return the id of each child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Creates the View of group(parent) in the ExpandableListView
     * @return the View of each parent in the List
     */
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View parentView = inflater.inflate(R.layout.custom_list_parent,parent,false);
        Topic myTopic = myList.get(groupPosition);
        //setting the text of each parent
        TextView singleTitle = (TextView)parentView.findViewById(R.id.title);
        singleTitle.setText(myTopic.getTitle());
        //handling the indicator
        ImageView indicator = (ImageView)parentView.findViewById(R.id.indicator);
        //if the list is expanded use arrow up, if not then use arrow down
        int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
        //initial arrow
        indicator.setImageResource(imageResourceId);
        indicator.setOnClickListener(new View.OnClickListener() {
            /**
             * Will expand and collapse groups depending on previous state
             * @param v the View from which the ClickListener was triggered
             */
            @Override
            public void onClick(View v) {

                if (isExpanded)
                    ((ExpandableListView) parent).collapseGroup(groupPosition);
                else
                    ((ExpandableListView) parent).expandGroup(groupPosition, true);

            }
        });


        return parentView;

    }

    /**
     * Creates the View of child in the ExpandableListView
     * @return the View of each child in the List
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View childView = inflater.inflate(R.layout.custom_list_child,parent,false);
        Topic myTopic = myList.get(groupPosition);
        TextView details = (TextView)childView.findViewById(R.id.description);
        details.setText(myTopic.getDescription());
        return childView;


    }

    /**
     * Checks if the child can be selected/pressed as well
     * @return whether the child is selectable or not
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}

