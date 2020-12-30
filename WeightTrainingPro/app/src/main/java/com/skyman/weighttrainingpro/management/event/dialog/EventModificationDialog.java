package com.skyman.weighttrainingpro.management.event.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.event.listview.EventItemLvAdapter;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.type.EquipmentType;
import com.skyman.weighttrainingpro.management.project.data.type.GroupType;

import java.util.ArrayList;

public class EventModificationDialog {

    // constance
    public static final String CLASS_NAME = "[ED]_EventItemModificationDialog";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Context context;
    private EventDbManager eventDbManager;
    private EventItemLvAdapter adapter;
    private ArrayList<Event> eventArrayList;
    private int position;

    // instance variable
    private LinearLayout titleWrapper;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;
    private TextView cancel;
    private TextView modify;

    // constructor
    public EventModificationDialog(Context context, EventDbManager eventDbManager, EventItemLvAdapter adapter, ArrayList<Event> eventArrayList, int position) {
        this.context = context;
        this.eventDbManager = eventDbManager;
        this.adapter = adapter;
        this.eventArrayList = eventArrayList;
        this.position = position;
    }


    public void setDialog() {

        final String METHOD_NAME = "[setDialog] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> event 데이터 확인");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, eventArrayList.get(position));

        // [lv/C]Dialog : 객체 생성
        final Dialog dialog = new Dialog(this.context);

        // [lv/C]Dialog : activity 의 타이틀을 숨긴다.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // [lv/C]Dialog : layout 을 custom_dialog_event_item_modification 으로 설정하기
        dialog.setContentView(R.layout.custom_dialog_event_item_modification);

        // [lv/C]Dialog : 영역 밖 화면 터치시 cancel 안 되게 맊기
        dialog.setCancelable(false);

        // [lv/C]WindowManager :
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // [lv/C]Dialog :
        dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

        // [method] :  widget mapping
        mappingOfWidget(dialog);

        // [method] : init widget
        initWidget(dialog, eventArrayList.get(position));

        dialog.show();

    } // End of method [setDialog]


    /**
     * [method] widget mapping
     */
    private void mappingOfWidget(Dialog dialog) {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) dialog.findViewById(R.id.cud_event_item_modification_title_wrapper);

        // [iv/C]Spinner : equipmentType mapping
        this.equipmentType = (Spinner) dialog.findViewById(R.id.cud_event_item_modification_equipment_type);

        // [iv/C]Spinner : equipmentType mapping
        this.groupType = (Spinner) dialog.findViewById(R.id.cud_event_item_modification_group_type);

        // [ic/C]EditText : eventName mapping
        this.eventName = (EditText) dialog.findViewById(R.id.cud_event_item_modification_event_name);

        // [ic/C]EditText : properWeight mapping
        this.properWeight = (EditText) dialog.findViewById(R.id.cud_event_item_modification_proper_weight);

        // [ic/C]EditText : maxWeight mapping
        this.maxWeight = (EditText) dialog.findViewById(R.id.cud_event_item_modification_max_weight);

        // [iv/C]TextView : cancel mapping
        this.cancel = (TextView) dialog.findViewById(R.id.cud_event_item_modification_cancel);

        // [iv/C]TextView : modify mapping
        this.modify = (TextView) dialog.findViewById(R.id.cud_event_item_modification_modify);

    } // End of method [mappingOfWidget]


    /**
     * [method] widget init
     */
    private void initWidget(final Dialog dialog, final Event event) {

        final String METHOD_NAME = "[initWidget] ";

        // [method] : equipmentType 을 adapter 와 연결하기
        setAdapterOfEquipmentType(event.getEquipmentType());

        // [method] : groupType 을 adapter 와 연결하기
        setAdapterOfGroupType(event.getGroupType());

        // [iv/C]EditText : eventName 초기 설정
        this.eventName.setText(event.getEventName());

        // [iv/C]EditText : properWeight 초기 설정
        this.properWeight.setText(event.getProperWeight() + "");

        // [iv/C]EditText : maxWeight 초기 설정
        this.maxWeight.setText(event.getMaxWeight() + "");

        // [iv/C]TextView : cancel click listener
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        // [iv/C]TextView : modify click listener
        this.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [check 1] : 입력 데이터 검사
                if (checkWhetherInputAllEventData()) {

                    // [iv/C]ArrayList<Event> : position 위치의 event 를 수정
                    eventArrayList.get(position).setEventName(eventName.getText().toString());
                    eventArrayList.get(position).setEquipmentType(DataManager.convertEquipmentType(equipmentType.getSelectedItemPosition()));
                    eventArrayList.get(position).setGroupType(DataManager.convertGroupType(groupType.getSelectedItemPosition()));
                    eventArrayList.get(position).setProperWeight(Float.parseFloat(properWeight.getText().toString()));
                    eventArrayList.get(position).setMaxWeight(Float.parseFloat(maxWeight.getText().toString()));

                    // [lv/C]EventItemLvAdapter : 해당 adapter 의 데이터가 갱신되었으면 알린다.
                    adapter.notifyDataSetChanged();

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 클릭 후 event 확인 ++++++++++++++++++");
                    LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, eventArrayList.get(position));

                    // [iv/C]EventDbManager : 위에 변경된 event 를 event table 에 반영한다.
                    int updateNumber = eventDbManager.updateContentByCountAndUserCount(eventArrayList.get(position));

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 업데이트한 수 = " + updateNumber);

                    // [lv/C]Toast : 수정이 완료되었습니다.
                    Toast.makeText(context, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    // [lv/C]Dialog : 현재 dialog 를 종료한다.
                    dialog.dismiss();

                } // [check 1]

            }
        });

    } // End of method [initWidget]


    /**
     * [method] equipmentType adapter
     */
    private void setAdapterOfEquipmentType(EquipmentType type) {

        // [lv/C]ArrayAdapter : R.array.equipmentType 과 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.equipmentType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 를 연결한다.
        this.equipmentType.setAdapter(adapter);

        // [iv/C]Spinner : type 에 해당하는 위치의 값을 보여준다.
        this.equipmentType.setSelection(DataManager.convertPositionOfEquipmentType(type));

    } // End of method [setAdapterOfEquipmentType]


    /**
     * [method] groupType adapter
     */
    private void setAdapterOfGroupType(GroupType type) {

        // [lv/C]ArrayAdapter : R.array.groupType 과 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.groupType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 를 연결한다.
        this.groupType.setAdapter(adapter);

        // [iv/C]Spinner : type 에 해당하는 위치의 값을 보여준다.
        this.groupType.setSelection(DataManager.convertPositionOfGroupType(type));

    } // End of method [setAdapterOfGroupType]


    /**
     * [method] event 데이터를 모두 입력 하였는지 검사하여 모두
     */
    private boolean checkWhetherInputAllEventData() {

        final String METHOD_NAME = "[initWidget] ";

        // [check 1] : 모두 입력 되었나요?
        if (!equipmentType.getSelectedItem().toString().equals("")
                && !groupType.getSelectedItem().toString().equals("")
                && !eventName.getText().toString().equals("")
                && !properWeight.getText().toString().equals("")
                && !maxWeight.getText().toString().equals("")) {

            // [check 2] : properWeight, maxWeight 이 0 보다 크고 같은 수인가?
            if ((0.0f <= Float.parseFloat(properWeight.getText().toString()))
                    && (0.0f <= Float.parseFloat(maxWeight.getText().toString()))) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 모든 조건에 통과하였습니다. <=");
                return true;
            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false :  0 보다 크거나 같은 수가 아니야! <=");

                // [lv/C]Toast : 0 보다 크거나 같은 수를 입력해주세요!
                Toast.makeText(context, "0 보다 크거나 같은 수를 입력해주세요!", Toast.LENGTH_SHORT).show();
                return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 입력되지 않은 값이 있어! <=");
            // [lv/C]Toast : 모든 값을 입력해주세요!
            Toast.makeText(context, "모든 값을 입력해주세요!", Toast.LENGTH_SHORT).show();
            return false;
        } // [check 1]

    } // End of method [checkWhetherInputAllEventData]
}
