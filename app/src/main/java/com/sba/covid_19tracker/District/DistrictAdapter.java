package com.sba.covid_19tracker.District;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
        boolean isExpanded = district.isExpanded();

        holder.d_name.setText(district.getDistrictname());
        holder.cnfm.setText(String.valueOf(district.getConfirm_case()));
        holder.con.setText(String.valueOf(district.getConfirm_case()));
        holder.rec.setText(String.valueOf(district.getRecovered_case()));
        holder.ded.setText(String.valueOf(district.getDead_case()));


        if (district.getDelta_c() == 0) {
            holder.dcnfm.setVisibility(View.INVISIBLE);
            holder.dcon.setVisibility(View.INVISIBLE);
        } else {
            holder.dcnfm.setVisibility(View.VISIBLE);
            holder.dcon.setVisibility(View.VISIBLE);
            holder.dcnfm.setText("↑" + district.getDelta_c());
            holder.dcon.setText("↑" + district.getDelta_c());
        }
        if (district.getDelta_r() == 0)
            holder.drec.setVisibility(View.INVISIBLE);
        else {
            holder.drec.setVisibility(View.VISIBLE);
            holder.drec.setText("↑" + district.getDelta_r());
        }
        if (district.getDelta_d() == 0)
            holder.dded.setVisibility(View.INVISIBLE);
        else {
            holder.dded.setVisibility(View.VISIBLE);
            holder.dded.setText("↑" + district.getDelta_d());
        }


        if (isExpanded) {
            holder.expandLayout.setVisibility(View.VISIBLE);
            holder.dcnfm.setVisibility(View.INVISIBLE);
            holder.cnfm.setVisibility(View.INVISIBLE);
        } else {
            if (district.getDelta_c() == 0)
                holder.dcnfm.setVisibility(View.INVISIBLE);
            else
                holder.dcnfm.setVisibility(View.VISIBLE);

            holder.expandLayout.setVisibility(View.GONE);
            holder.cnfm.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class DistrictViewHolder extends RecyclerView.ViewHolder {
        TextView d_name, cnfm, dcnfm, rec, drec, ded, dded, con, dcon;
        ConstraintLayout expandLayout;

        public DistrictViewHolder(@NonNull View itemView) {
            super(itemView);
            d_name = itemView.findViewById(R.id.district_name);
            cnfm = itemView.findViewById(R.id.district_c);
            dcnfm = itemView.findViewById(R.id.district_dc);
            con = itemView.findViewById(R.id.district_expn_c);
            dcon = itemView.findViewById(R.id.district_expan_dc);
            rec = itemView.findViewById(R.id.district_expan_r);
            drec = itemView.findViewById(R.id.district_expan_dr);
            ded = itemView.findViewById(R.id.district_expan_d);
            dded = itemView.findViewById(R.id.district_expan_dd);

            expandLayout = itemView.findViewById(R.id.districtExpandableLayout);
            itemView.findViewById(R.id.district_item_constrain_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DistrictModelClass d = mlist.get(getAdapterPosition());
                    d.setExpanded(!d.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
