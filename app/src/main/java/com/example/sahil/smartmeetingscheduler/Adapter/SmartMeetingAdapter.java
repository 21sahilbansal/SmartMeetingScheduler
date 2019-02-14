package com.example.sahil.smartmeetingscheduler.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sahil.smartmeetingscheduler.Model.MeetingResponse;
import com.example.sahil.smartmeetingscheduler.R;

import java.util.ArrayList;

public class SmartMeetingAdapter extends RecyclerView.Adapter<SmartMeetingAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<MeetingResponse> meetingList;


    public SmartMeetingAdapter(Context context, ArrayList<MeetingResponse> meetingList) {
            this.context = context;
            this.meetingList = meetingList;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mStarttime,mEndTime,mDescription,mAttendes;
        public MyViewHolder(View itemView) {
            super(itemView);
            mStarttime = (TextView) itemView.findViewById(R.id.txtStartTime);
            mEndTime = (TextView) itemView.findViewById(R.id.txtEndTime);
            mDescription= (TextView) itemView.findViewById(R.id.txtDescription);
            mAttendes =  (TextView) itemView.findViewById(R.id.attendes);
        }
    }

    @NonNull
    @Override
    public SmartMeetingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
     View view = (View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,viewGroup,false);
     return new SmartMeetingAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SmartMeetingAdapter.MyViewHolder myViewHolder, int position) {
        MeetingResponse response1 = meetingList.get(position);
        String attendes = "";
        int no_of_attedes;
        no_of_attedes = response1.getParticipants().size();
        for(int j = 0 ; j <no_of_attedes;j++){
            attendes = attendes +","+response1.getParticipants().get(j);
        }
        myViewHolder.mStarttime.setText(response1.getStartTime());
        myViewHolder.mEndTime.setText(response1.getEndTime());
        myViewHolder.mDescription.setText(response1.getDescription());
        myViewHolder.mAttendes.setText(attendes);
    }

    @Override
    public int getItemCount() {
        return meetingList.size();
    }
}
