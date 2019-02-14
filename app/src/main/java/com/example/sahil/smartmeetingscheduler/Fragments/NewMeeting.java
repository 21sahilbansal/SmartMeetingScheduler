package com.example.sahil.smartmeetingscheduler.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sahil.smartmeetingscheduler.MainActivity;
import com.example.sahil.smartmeetingscheduler.R;

import java.util.Calendar;


public class NewMeeting extends Fragment {
    private TextView txtBack;
    private ImageView imgBack;
    private RelativeLayout layoutDate, layoutEndTime, layoutStartTime;
    private TextView txtDate, txtStartTime, txtEndTime;
    private Button btnSubmit;

    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    final int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    boolean isDateSelected = false;
    boolean isStartTimeSelected = false;
    boolean isEndTImeSelected = false;

    int startHour, startMinutes, endHour, endMinutes;

    Calendar selectedDate = Calendar.getInstance();

    public NewMeeting() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_new_meeting, container, false);
        setRetainInstance(true);

        txtBack = (TextView) rootView.findViewById(R.id.txtBack);
        imgBack = (ImageView) rootView.findViewById(R.id.imgBack);
        layoutDate = (RelativeLayout) rootView.findViewById(R.id.layoutDate);
        layoutStartTime = (RelativeLayout) rootView.findViewById(R.id.layoutStartTime);
        layoutEndTime = (RelativeLayout) rootView.findViewById(R.id.layoutEndTime);
        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        txtStartTime = (TextView) rootView.findViewById(R.id.txtStartTime);
        txtEndTime = (TextView) rootView.findViewById(R.id.txtEndTime);
        btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        layoutStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openStartTimePicker();
            }
        });

        layoutEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEndTimePicker();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate())
                    return;

                isSlotAvailable();
            }
        });

        return rootView;
    }

    private void openDatePicker() {

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                selectedDate.clear();
                selectedDate.set(year, month, dayOfMonth, 23, 59);
                isDateSelected = true;
                txtDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(month) + "-" + String.valueOf(year));
            }
        }, year, month, day);

        dialog.show();
    }

    private void openStartTimePicker() {

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                isStartTimeSelected = true;
                startHour = hourOfDay;
                startMinutes = minute;
                txtStartTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, hour, minute, true);

        dialog.show();

    }

    private void openEndTimePicker() {

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                isEndTImeSelected = true;
                endHour = hourOfDay;
                endMinutes = minute;
                txtEndTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

            }
        }, hour, minute, true);

        dialog.show();

    }

    private boolean validate() {

        if (!isDateSelected) {
            Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_LONG).show();
            return false;
        }

        if (selectedDate.compareTo(c) < 0) {
            Toast.makeText(getActivity(), "Past date selected", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isStartTimeSelected) {
            Toast.makeText(getActivity(), "Select Start time", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!isEndTImeSelected) {
            Toast.makeText(getActivity(), "Select End time", Toast.LENGTH_LONG).show();
            return false;
        }

        if (startHour > endHour) {
            Toast.makeText(getActivity(), "Start time should be less than end time", Toast.LENGTH_LONG).show();
            return false;
        }
        if ((startHour == endHour) && (startMinutes > endHour)) {
            Toast.makeText(getActivity(), "Start time should be less than end time", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void isSlotAvailable() {
        MainActivity mainActivity = (MainActivity) getActivity();

        if (startHour > 13) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Slot available.")
                    .setPositiveButton("ok", null)
                    .show();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Slot not available.")
                    .setPositiveButton("ok", null)
                    .show();
        }

    }

}
