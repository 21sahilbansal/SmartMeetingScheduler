package com.example.sahil.smartmeetingscheduler.Service;


import com.example.sahil.smartmeetingscheduler.Model.MeetingResponse;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("schedule")
    Call<List<MeetingResponse>> getMeetingList(@Query("date") Date date);
}

