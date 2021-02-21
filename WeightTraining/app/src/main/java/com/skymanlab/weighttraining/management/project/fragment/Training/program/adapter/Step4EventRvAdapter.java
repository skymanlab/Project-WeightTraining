package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.os.Build;
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

public class Step4EventRvAdapter extends RecyclerView.Adapter<Step4EventRvAdapter.ViewHolder> {

    // constant
    private static final String CLASS_NAME = "[PFTLA] SelectedEventItemRvAdapter";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> selectedEventArrayList;

    // constructor
    public Step4EventRvAdapter(ArrayList<Event> selectedEventArrayList) {
        this.selectedEventArrayList = selectedEventArrayList;
    }

    private Step4EventRvAdapter(Builder builder){
        this.selectedEventArrayList = builder.selectedEventArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_step4_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String METHOD_NAME = "[onBindViewHolder] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> MuscleArea = " + selectedEventArrayList.get(position).getMuscleArea() + " // EventName = " + selectedEventArrayList.get(position).getEventName());

        // number : text
        holder.number.setText((position+1)+"");

        // muscleArea : text
        holder.muscleArea.setText(DataManager.convertHanguleOfMuscleArea(selectedEventArrayList.get(position).getMuscleArea()));

        // eventName : text
        holder.eventName.setText(selectedEventArrayList.get(position).getEventName());
    }

    @Override
    public int getItemCount() {
        return selectedEventArrayList.size();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= ViewHolder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private MaterialCardView contentWrapper;
        private MaterialTextView number;
        private MaterialTextView muscleArea;
        private MaterialTextView eventName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.contentWrapper = (MaterialCardView) itemView.findViewById(R.id.custom_list_step4_event_item_content_wrapper);
            this.number = (MaterialTextView) itemView.findViewById(R.id.custom_list_step4_event_item_number);
            this.muscleArea = (MaterialTextView) itemView.findViewById(R.id.custom_list_step4_event_item_muscle_area);
            this.eventName = (MaterialTextView) itemView.findViewById(R.id.custom_list_step4_event_item_event_name);

        }

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder{

        // instance variable
        private ArrayList<Event> selectedEventArrayList;

        // constructor
        public Builder(){

        }

        // setter
        public Builder setSelectedEventArrayList(ArrayList<Event> selectedEventArrayList) {
            this.selectedEventArrayList = selectedEventArrayList;
            return this;
        }

        // create
        public Step4EventRvAdapter create() {
            return new Step4EventRvAdapter(this);
        }
    }
}
