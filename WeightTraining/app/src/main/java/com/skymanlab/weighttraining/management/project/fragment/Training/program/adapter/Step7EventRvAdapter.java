package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog.Step7DetailProgramDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class Step7EventRvAdapter extends RecyclerView.Adapter<Step7EventRvAdapter.ViewHolder> {

    // instance variable
    private Fragment fragment;
    private ArrayList<Event> finalOrderList;
    private HashMap<String, DetailProgram> detailProgramList;



    // constructor
    public Step7EventRvAdapter(Builder builder) {
        this.fragment = builder.fragment;
        this.finalOrderList = builder.finalOrderList;
        this.detailProgramList = builder.detailProgramList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_step6_event_item, parent, false);
        return new Step7EventRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // click listener
        holder.getContentWrapper().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Step7DetailProgramDialog dialog = Step7DetailProgramDialog.newInstance(
                        finalOrderList.get(position),
                        detailProgramList.get(finalOrderList.get(position).getKey())
                );
                dialog.setCancelable(false);
                dialog.show(fragment.getActivity().getSupportFragmentManager(), "PartialSetting");

            }
        });

        // text
        holder.getNumber().setText((position + 1) + "");

        // text
        holder.getMuscleArea().setText(DataManager.convertHanguleOfMuscleArea(finalOrderList.get(position).getMuscleArea()));

        // text
        holder.getEventName().setText(finalOrderList.get(position).getEventName());

    }

    @Override
    public int getItemCount() {
        return finalOrderList.size();
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc method =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ViewHolder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private MaterialCardView contentWrapper;
        private MaterialTextView number;
        private MaterialTextView muscleArea;
        private MaterialTextView eventName;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // [iv/C]MaterialCardView : contentWrapper connect
            this.contentWrapper = (MaterialCardView) itemView.findViewById(R.id.custom_list_step6_event_item_content_wrapper);

            // [iv/C]MaterialTextView : muscleAreaNumber connect
            this.number = (MaterialTextView) itemView.findViewById(R.id.custom_list_step6_event_item_number);

            // [iv/C]MaterialTextView : muscleArea connect
            this.muscleArea = (MaterialTextView) itemView.findViewById(R.id.custom_list_step6_event_item_muscle_area);

            // [iv/C]MaterialTextView : eventName connect
            this.eventName = (MaterialTextView) itemView.findViewById(R.id.custom_list_step6_event_item_event_name);
        }

        // getter
        public MaterialCardView getContentWrapper() {
            return contentWrapper;
        }

        public MaterialTextView getNumber() {
            return number;
        }

        public MaterialTextView getMuscleArea() {
            return muscleArea;
        }

        public MaterialTextView getEventName() {
            return eventName;
        }
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private Fragment fragment;
        private ArrayList<Event> finalOrderList;
        private HashMap<String, DetailProgram> detailProgramList;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setFragment(Fragment fragment) {
            this.fragment = fragment;
            return this;
        }

        public Builder setFinalOrderList(ArrayList<Event> finalOrderList) {
            this.finalOrderList = finalOrderList;
            return this;
        }

        public Builder setDetailProgramList(HashMap<String, DetailProgram> detailProgramList) {
            this.detailProgramList = detailProgramList;
            return this;
        }

        // create
        public Step7EventRvAdapter create() {
            return new Step7EventRvAdapter(this);
        }
    }

}
