package com.example.barakjacob.intereview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * A Fragment that is used as the DatePicker popup.
 * Will give the option to choose a certain date
 */
public class DateDialogFragment  extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    //Variables should be shared between classes
    private static int yearInput;
    private static int monthInput;
    private static int dayInput;
    //variable to check if the date was chosen
    public static boolean chosen = false;

    /**
     * creates the DatePicker fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        //Set the current day,month and year as initial values in popup window
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, this, year, month, day);
        dialog.getDatePicker().setMinDate(new Date().getTime() - 1000);
        return dialog;
    }

    /**
     * saves the day,month and year chosen by the user, triggered after a date is chosen
     * @param view the view from which this method was triggered (popup window)
     * @param year the year chosen by the user
     * @param monthOfYear the month chosen by the user
     * @param dayOfMonth the day chosen by the user
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.yearInput = year;
        this.monthInput = monthOfYear + 1;
        this.dayInput = dayOfMonth;
        chosen = true;
    }

    /**
     * Creates a string that will be used for each item in the UpcomingEvent list (dd.mm.yyyy)
     * @return a string that describes the date chosen by the user, in the format of dd.mm.yyyy
     */
    public static String getStringDate(){
        return dayInput+"/"+monthInput+"/"+yearInput;
    }

}
