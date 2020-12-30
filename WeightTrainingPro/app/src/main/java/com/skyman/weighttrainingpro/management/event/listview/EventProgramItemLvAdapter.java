package com.skyman.weighttrainingpro.management.event.listview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;
import com.skyman.weighttrainingpro.EventProgramProcessActivity;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EventProgramItemLvAdapter extends BaseAdapter {

    // constance
    public static final String CLASS_NAME = "[EL]_EventSelectionItemLvAdapter";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> eventArrayList = new ArrayList<>();
    private Activity activity;
    private User user;
    private MuscleArea muscleArea;
    private TextView setNumber;
    private TextView restTimeMinute;
    private TextView restTimeSecond;

    // constructor
    public EventProgramItemLvAdapter(Activity activity, User user, MuscleArea muscleArea, TextView setNumber, TextView restTimeMinute, TextView restTimeSecond) {
        this.activity = activity;
        this.user = user;
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
            convertView = inflater.inflate(R.layout.custom_event_program_item, parent, false);
        }

        // mapping
        TextView eventName = (TextView) convertView.findViewById(R.id.cu_event_program_item_event_name);
        TextView properWeight = (TextView) convertView.findViewById(R.id.cu_event_program_item_proper_weight);
        final TextView maxWeight = (TextView) convertView.findViewById(R.id.cu_event_program_item_max_weight);
        MaterialTextView start = (MaterialTextView) convertView.findViewById(R.id.cu_event_program_item_bt_start);

        // [lv/C]ArrayList<Event> : event program 에서 선택된 리스트 중 해당 position 의 event 객체 가져오기
        final Event event = (Event) getItem(position);

        // setting
        eventName.setText(event.getEventName());
        properWeight.setText(DataFormatter.setWeightFormat(event.getProperWeight()));
        maxWeight.setText(DataFormatter.setWeightFormat(event.getMaxWeight()));
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/i]restTimeMillisecond :
                int restTimeMillisecond = DataManager.convertMillisecondOfRestTime(Integer.parseInt(restTimeMinute.getText().toString()), Integer.parseInt(restTimeSecond.getText().toString()));

                // [check 1] : setNumber 가 0 보다 큰 값이어야 한다.
                if (0 < Integer.parseInt(setNumber.getText().toString())) {

                    // [check 2] :
                    if (0 < restTimeMillisecond) {

                        // [lv/C]Intent : EventProgramSetProcessingActivity 로 이동
                        Intent intent = new Intent(activity, EventProgramProcessActivity.class);

                        // [lv/C]SessionManager : user, muscle, setNumber, restTime, event 추가하기
                        SessionManager.setUserInIntent(intent, user);
                        SessionManager.setMuscleAreaInIntent(intent, muscleArea);
                        SessionManager.setSetNumberInIntent(intent, Integer.parseInt(setNumber.getText().toString()));
                        SessionManager.setRestTimeInIntent(intent, restTimeMillisecond);
                        SessionManager.setEventInIntent(intent, event);

                        // [iv/C]Activity : EventProgramSetProcessingActivity 로 이동
                        activity.startActivityForResult(intent, 50000);

                    } else {
                        Toast.makeText(activity, "휴식시간을 정하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, "세트 수를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }


                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<<<<<<<<<<<<++++++++++++++++++++++++++>>>>>>>>>>>>>>>>>>>>>>>");
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. setNumber = " + setNumber.getText());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. restTimeMinute = " + restTimeMinute.getText());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. restTimeSecond = " + restTimeSecond.getText());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. restTime millisecond = " + restTimeMillisecond);

            }
        });

        return convertView;
    }


    /**
     * [method] eventArrayList 에 Event 객체 추가
     */
    public void setEventArrayList(ArrayList<Event> eventArrayList) {

        this.eventArrayList = eventArrayList;

    } // End of method [setEventArrayList]


}
