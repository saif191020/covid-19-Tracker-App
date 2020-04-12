package com.sba.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateViewHolder> {

    private Context context;
    private OnStateItemClicked onStateItemClicked;

    private ArrayList<StateModelClass> mState;

    public StateAdapter(Context context, OnStateItemClicked onStateItemClicked, ArrayList<StateModelClass> mState) {
        this.context = context;
        this.onStateItemClicked = onStateItemClicked;
        this.mState = mState;
    }


    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.state_list_item, parent, false);
        return new StateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder holder, int position) {
        StateModelClass currentItem = mState.get(position);

        holder.stateConstrain.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_slide));
        holder.stateName.setText(String.valueOf(currentItem.getStateName()));
        holder.stateCnm.setText(String.valueOf(currentItem.getCon()));
        holder.stateRec.setText(String.valueOf(currentItem.getRec()));
        holder.stateDec.setText(String.valueOf(currentItem.getDec()));
        if (currentItem.getdCon() == 0)
            holder.stateDCnm.setVisibility(View.INVISIBLE);
        else {
            holder.stateDCnm.setVisibility(View.VISIBLE);
            holder.stateDCnm.setText("↑" + String.valueOf(currentItem.getdCon()));
        }

        if (currentItem.getdRec() == 0)
            holder.statedRec.setVisibility(View.INVISIBLE);
        else {
            holder.statedRec.setVisibility(View.VISIBLE);
            holder.statedRec.setText("↑" + String.valueOf(currentItem.getdRec()));
        }
        if (currentItem.getdDec() == 0)
            holder.statedDec.setVisibility(View.INVISIBLE);
        else {
            holder.statedDec.setVisibility(View.VISIBLE);
            holder.statedDec.setText("↑" + String.valueOf(currentItem.getdDec()));
        }
    }

    @Override
    public int getItemCount() {
        return mState.size();
    }

    public class StateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView stateName, stateCnm, stateDCnm, stateRec, statedRec, stateDec, statedDec;
        public ConstraintLayout stateConstrain;

        public StateViewHolder(@NonNull View itemView) {

            super(itemView);
            stateName = itemView.findViewById(R.id.state_name);
            stateCnm = itemView.findViewById(R.id.state_c);
            stateDCnm = itemView.findViewById(R.id.state_cd);
            stateRec = itemView.findViewById(R.id.state_r);
            statedRec = itemView.findViewById(R.id.state_rd);
            stateDec = itemView.findViewById(R.id.state_d);
            statedDec = itemView.findViewById(R.id.state_dd);
            stateConstrain = itemView.findViewById(R.id.state_item_constrain_layout);

            stateConstrain.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onStateItemClicked.onItemClicked(getAdapterPosition());
        }
    }

    public interface OnStateItemClicked {
        void onItemClicked(int position);

    }
}
