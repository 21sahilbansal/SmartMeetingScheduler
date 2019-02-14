package com.example.sahil.smartmeetingscheduler;

import android.app.ProgressDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sahil.smartmeetingscheduler.Adapter.SmartMeetingAdapter;
import com.example.sahil.smartmeetingscheduler.Fragments.DatePickerFragment;
import com.example.sahil.smartmeetingscheduler.Fragments.NewMeeting;
import com.example.sahil.smartmeetingscheduler.Model.MeetingResponse;
import com.example.sahil.smartmeetingscheduler.Service.RetrofitClient;
import com.example.sahil.smartmeetingscheduler.Service.RetrofitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button mPrevious, mNext, mSelectDate, mScheduleMeeting;
    RecyclerView recyclerView;
    ArrayList<MeetingResponse> meetingResponseslist = new ArrayList<>();
    SmartMeetingAdapter mAdapter;
    Date nextDate, previousdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrevious = (Button) findViewById(R.id.button);
        mNext = (Button) findViewById(R.id.button2);
        mSelectDate = (Button) findViewById(R.id.button3);
        mScheduleMeeting = (Button) findViewById(R.id.button4);
        mScheduleMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment1 = new DatePickerFragment();
                newFragment1.show(getSupportFragmentManager(), "datePicker1");
                NewMeeting newMeeting = new NewMeeting();
                Bundle bundle = new Bundle();
                newMeeting.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().add(R.id.fml,newMeeting ).addToBackStack("NewMeeting").commit();

            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousClick();
            }
        });
        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectDateClick();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new SmartMeetingAdapter(this, meetingResponseslist);
        recyclerView.setAdapter(mAdapter);


    }


    public void setDate(Date date, Date nxtDate, Date prvsDate) {
        nextDate = nxtDate;
        previousdate = prvsDate;
        getMeetingList(date);

    }
    /*public void setDate(int year, int month, int dayOfMonth){
         month_string = Integer.toString(month);
         day_string = Integer.toString(dayOfMonth);
         year_string = Integer.toString(year);
       // Assign the concatenated strings to dateMessage.
         dateMessage = (month_string + "/" +
                day_string + "/" + year_string);
           dateSelector = true;
    }*/

    private void onSelectDateClick() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    private void onPreviousClick() {

        getMeetingList(previousdate);
    }

    private void onNextClick() {
        getMeetingList(nextDate);
    }


    private void getMeetingList(Date date) {

        RetrofitService apiService = RetrofitClient.getClient().create(RetrofitService.class);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<List<MeetingResponse>> call = apiService.getMeetingList(date);

        call.enqueue(new Callback<List<MeetingResponse>>() {
            @Override
            public void onResponse(Call<List<MeetingResponse>> call, Response<List<MeetingResponse>> response) {
                List<MeetingResponse> meetingsResponses = response.body();

                meetingResponseslist.clear();
                meetingResponseslist.addAll(meetingsResponses);
                mAdapter.notifyDataSetChanged();

                if (meetingsResponses.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(), "There is no meeting scheduled", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.setVisibility(View.VISIBLE);

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<MeetingResponse>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getBaseContext(), "Please try again", Toast.LENGTH_LONG).show();
                Log.e("serviceError", t.toString());
            }
        });

    }


}
