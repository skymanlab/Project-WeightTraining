package com.skymanlab.weighttraining.management.event.listview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.EventProgramProcessActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EventProgramLvAdapter extends BaseAdapter {

    // constance
    public static final String CLASS_NAME = "[EL] EventProgramItemLvAdapter";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private ArrayList<Boolean> isCompleted;
    private Activity activity;
    private MuscleArea muscleArea;
    private TextView setNumber;
    private TextView restTimeMinute;
    private TextView restTimeSecond;

    // constructor
    public EventProgramLvAdapter(Activity activity, MuscleArea muscleArea, TextView setNumber, TextView restTimeMinute, TextView restTimeSecond) {
        this.activity = activity;
        this.muscleArea = muscleArea;
        this.setNumber = setNumber;
        this.restTimeMinute = restTimeMinute;
        this.restTimeSecond = restTimeSecond;
    }

    @Override
    public int getCount() {
        return eventArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String METHOD_NAME = "[getView] ";

        // [lv/C]Context : context 가져오기
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_event_program_item, parent, false);
        }

        // mapping
        TextView eventName = (TextView) convertView.findViewById(R.id.custom_list_event_program_item_event_name);
        TextView properWeight = (TextView) convertView.findViewById(R.id.custom_list_event_program_item_proper_weight);
        final TextView maxWeight = (TextView) convertView.findViewById(R.id.custom_list_event_program_item_max_weight);
        MaterialTextView start = (MaterialTextView) convertView.findViewById(R.id.custom_list_event_program_item_bt_start);
        MaterialCardView startWrapper = (MaterialCardView) convertView.findViewById(R.id.custom_list_event_program_item_start_wrapper);

        // [lv/C]ArrayList<Event> : event program 에서 선택된 리스트 중 해당 position 의 event 객체 가져오기
        final Event event = (Event) getItem(position);


        // setting
        eventName.setText(event.getEventName());
        properWeight.setText(DataFormatter.setWeightFormat(event.getProperWeight()));
        maxWeight.setText(DataFormatter.setWeightFormat(event.getMaxWeight()));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "size = " + eventArrayList.size());


//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<" + position + ">>>> isCompleted = " + isCompleted.get(position));
        // [check 1] : isCompleted 로 start 가 완료되었음을 알라기
        if (isCompleted.get(position)) {
            // isCompleted = true : 완료되었으므로 다음 화면으로 넘어가지 않음

            // [lv/C]MaterialTextView : '완료' 로 수정 / 사용하지 못 하도록 / 배경색
            start.setText("완료");
            start.setEnabled(false);
            start.setBackgroundResource(R.color.colorBackgroundGray);

        } else {
            // isCompleted = false : 아직 완료 되지 않은 종목이므로 다음 화면으로 넘어갈 수 있음.

            // [lv/C]MaterialCardView : 배경색을 muscleArea 에 맞게 수정
            startWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(this.muscleArea));

            // [lv/C]MaterialTextView : start click listener
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // [lv/i]restTimeMillisecond :
                    int restTimeMillisecond = DataManager.convertMillisecondOfRestTime(Integer.parseInt(restTimeMinute.getText().toString()), Integer.parseInt(restTimeSecond.getText().toString()));

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                    for (int index = 0; index < isCompleted.size(); index++) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>>>> " + isCompleted.get(index));
                    }

                    // [check 1] : setNumber 가 0 보다 큰 값이어야 한다.
                    if (0 < Integer.parseInt(setNumber.getText().toString())) {

                        // [check 2] :
                        if (0 < restTimeMillisecond) {


                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<<<<<" + position + ">>> +++++++++++++++++++++++++++++++++++++++++++   클미ㅏㅇㄴ러ㅣㅏㅁ어나ㅣ언리ㅏ");

                            // [lv/C]Intent : EventProgramSetProcessingActivity 로 이동
                            Intent intent = new Intent(activity, EventProgramProcessActivity.class);

                            // [lv/C]SessionManager : muscle, setNumber, restTime, event, eventPosition 추가하기
                            SessionManager.setMuscleAreaInIntent(intent, muscleArea);
                            SessionManager.setSetNumberInIntent(intent, Integer.parseInt(setNumber.getText().toString()));
                            SessionManager.setRestTimeInIntent(intent, restTimeMillisecond);
                            SessionManager.setEventInIntent(intent, event);
                            SessionManager.setEventPositionInIntent(intent, position);

                            // [iv/C]Activity : EventProgramSetProcessingActivity 로 이동
                            activity.startActivityForResult(intent, 50000);

                        } else {

                            Toast.makeText(activity, R.string.event_program_item_lv_adapter_snack_rest_time_no_setting, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(activity, R.string.event_program_item_lv_adapter_snack_set_number_no_setting, Toast.LENGTH_SHORT).show();
                    }


                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<<<<<<<<<<<<++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>");
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. setNumber = " + setNumber.getText());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. restTimeMinute = " + restTimeMinute.getText());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. restTimeSecond = " + restTimeSecond.getText());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. restTime millisecond = " + restTimeMillisecond);

                }
            });
        }

        return convertView;
    }


    /**
     * [method] eventArrayList 에 Event 객체 추가
     */
    public void setEventArrayList(ArrayList<Event> eventArrayList) {

        // [iv/C]ArrayList<Event> : 위에서 가져온 값으로
        this.eventArrayList = eventArrayList;

    } // End of method [setEventArrayList]


    /**
     * [method]
     */
    public void setIsCompleted(ArrayList<Boolean> isCompleted) {

        this.isCompleted = isCompleted;

    } // End of method [setIsCompleted]


}
