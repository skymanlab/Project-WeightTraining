package com.skymanlab.weighttraining.management.event.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.data.EventMapping;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;

import java.util.ArrayList;

public class EventModificationDialog2 extends DialogFragment {

    // constance
    public static final String CLASS_NAME = "[ED] EventModificationDialog2";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Activity activity;
    private String uid;
    private ArrayList<Event> eventArrayList;
    private int position;

    // instance variable
    private LinearLayout titleWrapper;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;

    // constructor
    public EventModificationDialog2(Activity activity, String uid, ArrayList<Event> eventArrayList, int position) {
        this.activity = activity;
        this.uid = uid;
        this.eventArrayList = eventArrayList;
        this.position = position;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String METHOD_NAME = "[onCreateDialog] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= data getter(from bundle) =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]LinearLayoutInflate : layout 부풀리기???
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // [lv/C]View : layoutInflater 을 통해 custom layout 가져오기
        View view = inflater.inflate(R.layout.custom_dialog_event_item_modification, null);

        // [method] : widget mapping
        mappingWidget(view);

        // [method] : widget init
        initWidget(eventArrayList.get(position));

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= alert dialog setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]AlertDialog.Builder : builder 객체 생성 및 초기
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(R.string.cud_rest_time_bt_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] :
                        updateContentProcess();
                    }
                })
                .setNegativeButton(R.string.cud_rest_time_bt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dismiss();
                    }
                });
        return builder.create();
    }

    /**
     * [method] widget mapping
     */
    private void mappingWidget(View view) {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) view.findViewById(R.id.cud_event_item_modification_title_wrapper);

        // [iv/C]Spinner : equipmentType mapping
        this.equipmentType = (Spinner) view.findViewById(R.id.cud_event_item_modification_equipment_type);

        // [iv/C]Spinner : equipmentType mapping
        this.groupType = (Spinner) view.findViewById(R.id.cud_event_item_modification_group_type);

        // [ic/C]EditText : eventName mapping
        this.eventName = (EditText) view.findViewById(R.id.cud_event_item_modification_event_name);

        // [ic/C]EditText : properWeight mapping
        this.properWeight = (EditText) view.findViewById(R.id.cud_event_item_modification_proper_weight);

        // [ic/C]EditText : maxWeight mapping
        this.maxWeight = (EditText) view.findViewById(R.id.cud_event_item_modification_max_weight);


    } // End of method [mappingWidget]


    /**
     * [method] widget init
     */
    private void initWidget(Event event) {

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

    } // End of method [initWidget]


    /**
     * [method] equipmentType adapter
     */
    private void setAdapterOfEquipmentType(EquipmentType type) {

        // [lv/C]ArrayAdapter : R.array.equipmentType 과 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipmentType, android.R.layout.simple_spinner_dropdown_item);

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
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.groupType, android.R.layout.simple_spinner_dropdown_item);

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
                Toast.makeText(getActivity(), R.string.cud_event_item_modification_snack_0_up_input, Toast.LENGTH_SHORT).show();
                return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 입력되지 않은 값이 있어! <=");
            // [lv/C]Toast : 모든 값을 입력해주세요!
            Toast.makeText(getActivity(), R.string.cud_event_item_modification_snack_all_input, Toast.LENGTH_SHORT).show();
            return false;
        } // [check 1]

    } // End of method [checkWhetherInputAllEventData]


    /**
     * [method] Firebase database 에 데이터를 수정하는 과정을 진행
     *
     */
    private void updateContentProcess() {

        final String METHOD_NAME = "[updateContentProcess] ";

        // [check 1] : 입력 데이터 검사
        if (checkWhetherInputAllEventData()) {

            // [lv/C]Event : 변경된 내용으로 설정
            Event modifiedEvent = new Event();
            modifiedEvent.setKey(eventArrayList.get(position).getKey());                                                // 0. key : 안 변함
            modifiedEvent.setEventName(eventName.getText().toString());                                                 // 1. event name
            modifiedEvent.setMuscleArea(eventArrayList.get(position).getMuscleArea());                                  // 2. muscle area : 안 변함
            modifiedEvent.setEquipmentType(DataManager.convertEquipmentType(equipmentType.getSelectedItemPosition()));  // 3. equipment type
            modifiedEvent.setGroupType(DataManager.convertGroupType(groupType.getSelectedItemPosition()));              // 4. group type
            modifiedEvent.setProperWeight(Float.parseFloat(properWeight.getText().toString()));                         // 5. proper weight
            modifiedEvent.setMaxWeight(Float.parseFloat(maxWeight.getText().toString()));                               // 6. max weight
            modifiedEvent.setSelectionCounter(eventArrayList.get(position).getSelectionCounter());                      // 7. selection counter : 안 변함

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 클릭 후 event 확인 ++++++++++++++++++");
            LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, modifiedEvent);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 1. key = " + eventArrayList.get(position).getKey());
            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
            db.child(uid).child(eventArrayList.get(position).getMuscleArea().toString()).child(eventArrayList.get(position).getKey()).updateChildren(EventMapping.mappingHashMapForAdd(modifiedEvent), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    // [check 1] : error 발생 안함
                    if (error == null) {
                        // 성공
                        Toast.makeText(activity, R.string.cud_event_item_modification_snack_modify_success, Toast.LENGTH_SHORT).show();
                    } else {
                        // error 발생
                    } // [check 1]
                }
            });

        } // [check 1]
        

    } // End of method [updateContentProcess]
}
