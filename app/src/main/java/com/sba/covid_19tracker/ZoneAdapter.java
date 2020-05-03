package com.sba.covid_19tracker;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ZoneViewHolder> {
    private Context context;
    ArrayList<ZoneModelClass> mList;

    public ZoneAdapter(Context context, ArrayList<ZoneModelClass> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zone_list_item, parent, false);
        return new ZoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneViewHolder holder, int position) {

        final ZoneModelClass zone = mList.get(position);
        holder.Dname.setText(zone.getDistrict_name());
        if (zone.getDistrict_name().equals("Green Zone")) {
            holder.Dname.setTextColor(Color.parseColor("#ff669900"));
            holder.Dname.setTextSize(18);
        }
        else if (zone.getDistrict_name().equals("Orange Zone")) {
            holder.Dname.setTextColor(Color.parseColor("#ffff8800"));
            holder.Dname.setTextSize(18);
        }
        else if (zone.getDistrict_name().equals("Red Zone")) {
            holder.Dname.setTextColor(Color.parseColor("#ffcc0000"));
            holder.Dname.setTextSize(18);
        }else {
            holder.Dname.setTextColor(Color.parseColor("#ffffff"));
            holder.Dname.setTextSize(16);
        }


        holder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!zone.getLast_update().equals("null"))

                if (zone.getZone_colour().startsWith("Green"))
                    Toast.makeText(context, "Last Updated On :" + zone.getLast_update()+  "\nStatus : Green Zone", Toast.LENGTH_SHORT).show();
                else if (zone.getZone_colour().startsWith("Orange"))
                    Toast.makeText(context, "Last Updated On :" + zone.getLast_update() + "\nStatus : Orange Zone", Toast.LENGTH_SHORT).show();
                else if (zone.getZone_colour().startsWith("Red"))
                    Toast.makeText(context, "Last Updated On :" + zone.getLast_update() + "\nStatus : Red Zone", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ZoneViewHolder extends RecyclerView.ViewHolder {
        private TextView Dname;
        private ConstraintLayout constraintLayout;

        public ZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            Dname = itemView.findViewById(R.id.Zone_list_name);
            constraintLayout = itemView.findViewById(R.id.zone_item_constrain_layout);
        }
    }
}
