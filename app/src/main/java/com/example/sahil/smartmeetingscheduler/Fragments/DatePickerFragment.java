package com.example.sahil.smartmeetingscheduler.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sahil.smartmeetingscheduler.MainActivity;
import com.example.sahil.smartmeetingscheduler.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    final Calendar c = Calendar.getInstance();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month, dayOfMonth, 0, 0, 0);
        Date chosenDate = cal.getTime();
        Date nxtDate,previousDate;
         cal.add(Calendar.DATE, 1);
         nxtDate = cal.getTime();
         cal.add(Calendar.DATE, -1);
         previousDate= cal.getTime();
        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.setDate(chosenDate,nxtDate,previousDate);



    }
}
