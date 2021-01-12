package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class SelectedEventRvAdapter extends RecyclerView.Adapter<SelectedEventRvAdapter.ViewHolder> {

    // constant
    private static final String CLASS_NAME = "[PFTLA] SelectedEventItemRvAdapter";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> selectedEventArrayList;

    // instance variable
    private int chestCounter = 0;
    private int shoulderCounter = 0;
    private int latCounter = 0;
    private int upperBodyCounter = 0;
    private int armCounter = 0;
    private int etcCounter = 0;

    // constructor
    public SelectedEventRvAdapter(ArrayList<Event> selectedEventArrayList) {
        this.selectedEventArrayList = selectedEventArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_selected_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String METHOD_NAME = "[onBindViewHolder] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> MuscleArea = " + selectedEventArrayList.get(position).getMuscleArea() + " // EventName = " + selectedEventArrayList.get(position).getEventName());

        // contentWrapper : background
        holder.getContentWrapper().setBackgroundResource(DataManager.convertColorIntOfMuscleArea(selectedEventArrayList.get(position).getMuscleArea()));

        // muscleAreaNumber : text
        holder.getMuscleAreaNumber().setText(makeMuscleAreaNumber(selectedEventArrayList.get(position).getMuscleArea()));

        // eventName : text
        holder.getEventName().setText(selectedEventArrayList.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return selectedEventArrayList.size();
    }

    private String makeMuscleAreaNumber(MuscleArea muscleArea) {

        int number = 0;

        switch (muscleArea) {
            case CHEST:
                chestCounter++;
                number = chestCounter;
                break;
            case SHOULDER:
                shoulderCounter++;
                number = shoulderCounter;
                break;
            case LAT:
                latCounter++;
                number = latCounter;
                break;
            case UPPER_BODY:
                upperBodyCounter++;
                number = upperBodyCounter;
                break;
            case ARM:
                armCounter++;
                number = armCounter;
                break;
            case ETC:
                etcCounter++;
                number = etcCounter;
                break;
        }

        StringBuilder muscleAreaNumber = new StringBuilder();

        muscleAreaNumber.append(DataManager.convertHanguleOfMuscleArea(muscleArea));
        muscleAreaNumber.append(" ");
        muscleAreaNumber.append(number);

        return muscleAreaNumber.toString();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= view holder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private MaterialCardView contentWrapper;
        private MaterialTextView muscleAreaNumber;
        private MaterialTextView eventName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.contentWrapper = (MaterialCardView) itemView.findViewById(R.id.cu_selected_event_item_content_wrapper);
            this.muscleAreaNumber = (MaterialTextView) itemView.findViewById(R.id.cu_selected_event_item_muscle_area_number);
            this.eventName = (MaterialTextView) itemView.findViewById(R.id.cu_selected_event_item_event_name);

        }

        // getter
        public MaterialCardView getContentWrapper() {
            return contentWrapper;
        }

        public MaterialTextView getMuscleAreaNumber() {
            return muscleAreaNumber;
        }

        public MaterialTextView getEventName() {
            return eventName;
        }

    }
}
