package com.sba.covid_19tracker.District;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sba.covid_19tracker.R;

import java.util.ArrayList;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder> {
    private Context context;
    private ArrayList<DistrictModelClass> mlist;

    public DistrictAdapter(Context context, ArrayList<DistrictModelClass> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.district_list_item, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        DistrictModelClass district = mlist.get(position);
        holder.d_name.setText(district.getDistrictname());
        holder.cnfm.setText(String.valueOf(district.getConfirm_case()));
        if (district.getDelta_c() == 0)
            holder.dcnfm.setVisibility(View.INVISIBLE);
        else {
            holder.dcnfm.setVisibility(View.VISIBLE);
            holder.dcnfm.setText("↑" +String.valueOf(district.getDelta_c()));
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {
        private TextView d_name, cnfm, dcnfm;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            d_name = itemView.findViewById(R.id.district_name);
            cnfm = itemView.findViewById(R.id.district_c);
            dcnfm = itemView.findViewById(R.id.district_dc);
        }
    }
}
