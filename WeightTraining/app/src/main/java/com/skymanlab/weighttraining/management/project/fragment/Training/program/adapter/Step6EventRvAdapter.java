package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog.Step6DetailProgramSettingDialog;

import java.util.ArrayList;

public class Step6EventRvAdapter extends RecyclerView.Adapter<Step6EventRvAdapter.ViewHolder> {

    // instance variable
    private FragmentManager fragmentManager;
    private ArrayList<Event> finalOrderList;

    // constructor
    public Step6EventRvAdapter(Builder builder) {
        this.fragmentManager = builder.fragmentManager;
        this.finalOrderList = builder.finalOrderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_step6_event_item, parent, false);
        return new Step6EventRvAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // click listener
        holder.getContentWrapper().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Step6DetailProgramSettingDialog dialog = Step6DetailProgramSettingDialog.newInstance(finalOrderList.get(position));
                dialog.setCancelable(false);
                dialog.show(fragmentManager, "PartialSetting");

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
        private FragmentManager fragmentManager;
        private ArrayList<Event> finalOrderList;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public Builder setFinalOrderList(ArrayList<Event> finalOrderList) {
            this.finalOrderList = finalOrderList;
            return this;
        }

        // create
        public Step6EventRvAdapter create() {
            return new Step6EventRvAdapter(this);
        }
    }

}
