package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;

public class DetailProgramItem {

    // instance variable
    private LayoutInflater inflater;
    private DetailProgram detailProgram;

    // instance variable
    private View item;

    // instance variable
    private TextView order;
    private TextView muscleArea;
    private TextView eventName;
    private TextView setNumber;
    private TextView restTime;

    // constructor
    private DetailProgramItem(Builder builder) {
        this.inflater = builder.inflater;
        this.detailProgram = builder.detailProgram;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public View getItem() {
        return item;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= create =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public DetailProgramItem createItem() {

        // inflate item
        this.item = inflateItem();

        // connect widget
        connectWidget(this.item);

        // init item and widget
        initItem();
        initWidget();

        return this;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inflate =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private View inflateItem() {

        return inflater.inflate(R.layout.include_detail_program_item, null);

    } // End of method [inflateItem]

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void connectWidget(View view) {

        // 0. order
        connectWidgetOfOrder(view);

        // 1. muscle area
        connectWidgetOfMuscleArea(view);

        // 2. event name
        connectWidgetOfEventName(view);

        // 3. set number
        connectWidgetOfSetNumber(view);

        // 4. rest time
        connectWidgetOfRestTime(view);
    }

    private void connectWidgetOfOrder(View view) {
        this.order = view.findViewById(R.id.include_detail_program_item_order);
    }

    private void connectWidgetOfMuscleArea(View view) {
        this.muscleArea = view.findViewById(R.id.include_detail_program_item_muscle_area);
    }

    private void connectWidgetOfEventName(View view) {
        this.eventName = view.findViewById(R.id.include_detail_program_item_event_name);
    }

    private void connectWidgetOfSetNumber(View view) {
        this.setNumber = view.findViewById(R.id.include_detail_program_item_set_number);
    }

    private void connectWidgetOfRestTime(View view) {
        this.restTime = view.findViewById(R.id.include_detail_program_item_rest_time);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void initItem() {

        this.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initWidget() {

        // 0. order
        initWidgetOfOrder();

        // 1. muscle area
        initWidgetOfMuscleArea();

        // 2. event name
        initWidgetOfEventName();

        // 3. set number
        initWidgetOfSetNumber();

        // 4. rest time
        initWidgetOfRestTime();
    }

    private void initWidgetOfOrder() {
        this.order.setText(detailProgram.getOrder() + "");
    }

    private void initWidgetOfMuscleArea() {
        this.muscleArea.setText(
                DataManager.convertHanguleOfMuscleArea(
                        detailProgram.getMuscleArea()
                )
        );
    }

    private void initWidgetOfEventName() {
        this.eventName.setText(detailProgram.getEventName());
    }

    private void initWidgetOfSetNumber() {
        this.setNumber.setText(
                DataFormatter.setSetNumberFormat(
                        detailProgram.getSetNumber()
                )
        );
    }

    private void initWidgetOfRestTime() {
        this.restTime.setText(
                DataFormatter.setTimeFormat(
                        detailProgram.getRestTimeMinute(),
                        detailProgram.getRestTimeSecond()
                )
        );
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private LayoutInflater inflater;
        private DetailProgram detailProgram;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
            return this;
        }

        public Builder setDetailProgram(DetailProgram detailProgram) {
            this.detailProgram = detailProgram;
            return this;
        }

        // newInstance
        public DetailProgramItem newInstance() {
            return new DetailProgramItem(this);
        }
    }
}
