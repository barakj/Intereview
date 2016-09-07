package com.example.barakjacob.intereview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * An Adapter class for the Questions ExpandableListView
 * This adapter will connect between views/group positions(id) and the ListView
 */
public class ExpandableQuestionListAdapter extends BaseExpandableListAdapter {
    private Manager myManager;
    private List<Question> myList;
    private Context context;
    private RadioButton[] possibleAnswers;

    /**
     * Constructor of adapter
     *
     * @param c the context of the app(the activity in which the ListView was created)
     */
    public ExpandableQuestionListAdapter(Context c) {
        context = c;
        myManager = Manager.getInstance();
        myList = myManager.getQuestions();
        possibleAnswers = new RadioButton[4];
    }

    /**
     * Gets the number of groups in this ExpandableListView
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return myList.size();
    }

    /**
     * Gets the number of children in this ExpandableListView
     *
     * @return the number of children
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    /**
     * Gets the group of each item in the ExpandableListView
     *
     * @return the group/topic of each item
     */
    @Override
    public Object getGroup(int groupPosition) {
        return myList.get(groupPosition).getQuestion();
    }

    /**
     * Gets the child of each item in the ExpandableListView
     *
     * @return the description to be displayed for each chilld
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myList.get(groupPosition).getPossibleAnswers();
    }

    /**
     * Gets the id of a group in the list
     *
     * @return the id of each group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the id of a child in the list
     *
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
     *
     * @return the View of each parent in the List
     */
    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View parentView = inflater.inflate(R.layout.custom_list_parent, parent, false);
        Question myQuestion = myList.get(groupPosition);
        //setting the text of each parent
        TextView singleTitle = (TextView) parentView.findViewById(R.id.title);
        singleTitle.setText(myQuestion.getQuestion());
        //handling the indicator
        ImageView indicator = (ImageView) parentView.findViewById(R.id.indicator);
        //if the list is expanded use arrow up, if not then use arrow down
        int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
        //initial arrow
        indicator.setImageResource(imageResourceId);
        indicator.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * Will expand and collapse groups depending on previous state
                 * @param v the View from which the ClickListener was triggered
                 */
                if (isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                else ((ExpandableListView) parent).expandGroup(groupPosition, true);

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
        final View childView = inflater.inflate(R.layout.custom_list_child_questions, parent, false);
        handleRadioButtons(childView,groupPosition);
        childView.invalidate();
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


    private void handleRadioButtons(View childView, int groupPosition) {
        final Question myQuestion = myList.get(groupPosition);
        RadioGroup group = (RadioGroup) childView.findViewById(R.id.buttonGroup);
        initializeButtons(childView);
        setButtonText(myQuestion);
        //if one of the buttons is pressed
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * Will expand and collapse groups depending on previous state
             * @param group the button group this ActionListener will operate on
             * @param checkedId the ID of the individual button that was pressed
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //get the index of the correct answer
                resetDrawable(group);
                int indexAnswer = Integer.parseInt(myQuestion.getAnswer()) - 1;
                for (int i = 0; i < possibleAnswers.length; i++) {
                    //if the button at position i was pressed
                    if (checkedId == possibleAnswers[i].getId()) {
                        //if its the correct answer
                        if (i == indexAnswer) {
                            possibleAnswers[i] = (RadioButton)group.findViewById(checkedId);
                            possibleAnswers[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.checkmark, 0, 0, 0);
                        }
                        else {
                            possibleAnswers[i] = (RadioButton)group.findViewById(checkedId);
                            possibleAnswers[i].setCompoundDrawablesWithIntrinsicBounds(R.drawable.wrong, 0, 0, 0);
                        }

                    }
                }
            }
        });


    }
    /**
     * Resets the drawable to the left of the button to be nothing (removes the image)
     * @param group the radio buttons group
     */
    private void resetDrawable(RadioGroup group) {
        for(int i=0;i<possibleAnswers.length;i++){
            RadioButton radioButton = (RadioButton) group.findViewById(possibleAnswers[i].getId());
            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    /**
     * Sets the text of each radio button as a possible answer
     * @param myQuestion the Question corresponding to the possible answers
     */
    private void setButtonText(Question myQuestion) {
        for (int i = 0; i < possibleAnswers.length; i++) {
            possibleAnswers[i].setText(myQuestion.getPossibleAnswers()[i]);
        }
    }

    /**
     * Initializes the RadioButton array of possible answers
     * @param childView the View in which the radio buttons are defined
     */
    private void initializeButtons(View childView) {
        //array of radioButtons, each button will correspond to a possible answer
        possibleAnswers[0] = (RadioButton) childView.findViewById(R.id.answer1);
        possibleAnswers[1] = (RadioButton) childView.findViewById(R.id.answer2);
        possibleAnswers[2] = (RadioButton) childView.findViewById(R.id.answer3);
        possibleAnswers[3] = (RadioButton) childView.findViewById(R.id.answer4);



    }
}


