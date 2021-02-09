package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;

public class Step8DetailProgramResultItem {

    // instance variable
    private LayoutInflater inflater;
    private Event event;
    private DetailProgram detailProgram;


    // instance variable : view
    private View item;

    // instance variable : widget
    private TextView muscleArea;
    private TextView eventName;
    private LinearLayout contentWrapper;
    private TextView setNumber;
    private TextView restTime;

    // constructor
    private Step8DetailProgramResultItem(Builder builder) {
        this.inflater = builder.inflater;
        this.event = builder.event;
        this.detailProgram = builder.detailProgram;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public View getItem() {
        return item;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= create =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 을 만들고 설정을 초기화한다.
     */
    public Step8DetailProgramResultItem createItem() {

        // [iv/C]View : item 생성하기
        this.item = inflateItem();

        // item 안의 내용물(widget)을 연결한다.
        connectWidget(this.item);

        // [iv/C]View : item 의 초기 내용을 설정한다.
        initItem();

        return this;

    } // End of method [makeItem]

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inflate =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * inflater 를 이용하여 include_step_final_order_item.xml layout 을 inflate 한 view 를 반환다.
     *
     * @return final order 의 한 항목인 item 이다.
     */
    private View inflateItem() {

        return inflater.inflate(R.layout.include_maker_step8_detail_program_result_item, null);

    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 의 모든 widget 을 사용하기 위해 연결(연관)시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidget(View view) {

        this.muscleArea = (TextView) view.findViewById(R.id.include_maker_step8_detail_program_result_item_muscle_area);
        this.eventName = (TextView) view.findViewById(R.id.include_maker_step8_detail_program_result_item_event_name);
        this.contentWrapper = (LinearLayout) view.findViewById(R.id.include_maker_step8_detail_program_result_item_content_wrapper);
        this.setNumber = (TextView) view.findViewById(R.id.include_maker_step8_detail_program_result_item_set_number);
        this.restTime = (TextView) view.findViewById(R.id.include_maker_step8_detail_program_result_item_rest_time);

    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 의 초기 내용을 설정한다.
     */
    private void initItem() {

        // item 안의 widget 들의 초기 내용을 설정한다.
        initWidget();

    } // End of method [initWidgetOfItem]


    /**
     * item 의 각 widget 들의 초기 상태를 설정(준비)한다.
     */
    private void initWidget() {

        // muscleArea
        initWidgetOfMuscleAre();

        // eventName
        initWidgetOfEventName();

        // contentWrapper
        initWidgetOfContentWrapper();

        // setNumber
        initWidgetOfSetNumber();

        // restTime
        initWidgetOfRestTime();

    } // End of method [initWidget]


    private void initWidgetOfMuscleAre() {

        // text
        this.muscleArea.setText(DataManager.convertHanguleOfMuscleArea(event.getMuscleArea()));

    } // End of method [initWidget]


    private void initWidgetOfEventName() {

        // text
        this.eventName.setText(event.getEventName());

    } // End of method [initWidget]


    private void initWidgetOfContentWrapper() {

        // visible
        if (detailProgram == null) {
            this.contentWrapper.setVisibility(View.GONE);
        }

    } // End of method [initWidget]


    private void initWidgetOfSetNumber() {

        // text
        if (detailProgram != null)
            this.setNumber.setText(DataFormatter.setSetNumberFormat(detailProgram.getSetNumber()));

    } // End of method [initWidget]


    private void initWidgetOfRestTime() {

        // text
        if (detailProgram != null)
            this.restTime.setText(DataFormatter.setTimeFormat(detailProgram.getRestTimeMinute(), detailProgram.getRestTimeSecond()));

    } // End of method [initWidget]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private LayoutInflater inflater;
        private Event event;
        private DetailProgram detailProgram;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
            return this;
        }

        public Builder setEvent(Event event) {
            this.event = event;
            return this;
        }

        public Builder setDetailProgram(DetailProgram detailProgram) {
            this.detailProgram = detailProgram;
            return this;
        }

        // create
        public Step8DetailProgramResultItem newInstance() {
            return new Step8DetailProgramResultItem(this);
        }
    }
}
