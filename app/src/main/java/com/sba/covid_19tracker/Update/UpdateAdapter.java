package com.sba.covid_19tracker.Update;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sba.covid_19tracker.R;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder> {
    Context context;
    public static final String TAG = "Update_LOG";
    SimpleDateFormat sdf;

    ArrayList<UpdateModelClass> updateList;

    public UpdateAdapter(Context context, ArrayList<UpdateModelClass> updateList) {
        this.context = context;
        this.updateList = updateList;
        sdf = new SimpleDateFormat("dd MMM HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        //TODO: Fix The time Issue
        Log.d(TAG, "o/p " + TimeZone.getTimeZone("GMT+5:30"));
    }

    @NonNull
    @Override
    public UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_list_item, parent, false);
        return new UpdateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateViewHolder holder, int position) {
        UpdateModelClass up = updateList.get(position);


        long timestamp = up.getTimestamp();
        Date date = new Date(timestamp * 1000L);
        String fd = sdf.format(date);

        holder.update.setText(up.getUpdate());
        holder.time.setText(fd.concat(" IST"));


    }

    @Override
    public int getItemCount() {
        return updateList.size();
    }

    public class UpdateViewHolder extends RecyclerView.ViewHolder {
        public TextView update, time;

        public UpdateViewHolder(@NonNull View itemView) {
            super(itemView);
            update = itemView.findViewById(R.id.update_content);
            time = itemView.findViewById(R.id.update_time);
        }
    }
}
