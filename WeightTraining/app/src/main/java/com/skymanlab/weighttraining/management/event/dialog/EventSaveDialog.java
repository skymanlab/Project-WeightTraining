package com.skymanlab.weighttraining.management.event.dialog;

import android.app.Dialog;
import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.HashMap;

public class EventSaveDialog extends DialogFragment {

    // constance
    public static final String CLASS_NAME = "[ED] EventItemAddDialog";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Context context;
    private MuscleArea muscleArea;

    // instance variable
    private LinearLayout titleWrapper;
    private TextView title;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;

    // instance variable
    private AlertDialog.Builder builder;

    // constructor
    public EventSaveDialog(Context context, MuscleArea muscleArea) {
        this.context= context;
        this.muscleArea = muscleArea;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String METHOD_NAME ="[onCreateDialog] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]LinearLayoutInflate : layout 부풀리기???
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // [lv/C]View : layoutInflater 을 통해 custom layout 가져오기
        View view = inflater.inflate(R.layout.custom_dialog_event_save, null);

        // [method] : widget mapping
        mappingWidget(view);

        // [method] : widget init
        initWidget(R.string.custom_dialog_event_save_title);

        // [iv/C]builder : 객체 생성
        this.builder = new AlertDialog.Builder(getContext());

        // [iv/C]builder : 위의 view 를 AlertDialog 의 커스텀 레이아웃으로
        this.builder.setView(view)
                .setPositiveButton(R.string.custom_dialog_event_save_alert_save_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] : Firebase Database 에 데이터 저장 과정진행
                        saveContentProcess();
                        
                    }
                })
                .setNegativeButton(R.string.custom_dialog_event_save_alert_save_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return this.builder.create();
    }



    /**
     * [method] widget mapping
     */
    private void mappingWidget(View view) {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) view.findViewById(R.id.custom_dialog_event_save_title_wrapper);

        // [iv/C]TextView : title mapping
        this.title = (TextView) view.findViewById(R.id.custom_dialog_event_save_title);

        // [iv/C]Spinner : equipmentType mapping
        this.equipmentType = (Spinner) view.findViewById(R.id.custom_dialog_event_save_equipment_type);

        // [iv/C]Spinner : equipmentType mapping
        this.groupType = (Spinner) view.findViewById(R.id.custom_dialog_event_save_group_type);

        // [ic/C]EditText : eventName mapping
        this.eventName = (EditText) view.findViewById(R.id.custom_dialog_event_save_event_name);

        // [ic/C]EditText : properWeight mapping
        this.properWeight = (EditText) view.findViewById(R.id.custom_dialog_event_save_proper_weight);

        // [ic/C]EditText : maxWeight mapping
        this.maxWeight = (EditText) view.findViewById(R.id.custom_dialog_event_save_max_weight);


    } // End of method [mappingWidget]


    /**
     * [method] widget init
     *
     */
    private void initWidget(int titleId) {

        // [iv/C]TextView : title setting
        this.title.setText(titleId);

        // [method] : equipmentType 을 adapter 와 연결하기
        setAdapterOfEquipmentType();

        // [method] : groupType 을 adapter 와 연결하기
        setAdapterOfGroupType();


    } // End of method [initWidget]



    /**
     * [method] equipmentType adapter
     */
    private void setAdapterOfEquipmentType() {

        // [lv/C]ArrayAdapter : R.array.equipmentType 과 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipmentType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 를 연결한다.
        this.equipmentType.setAdapter(adapter);

    } // End of method [setAdapterOfEquipmentType]


    /**
     * [method] groupType adapter
     */
    private void setAdapterOfGroupType() {

        // [lv/C]ArrayAdapter : R.array.groupType 과 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.groupType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 를 연결한다.
        this.groupType.setAdapter(adapter);

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
                Toast.makeText(context, R.string.custom_dialog_event_save_snack_0_up_input, Toast.LENGTH_SHORT).show();
                return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 입력되지 않은 값이 있어! <=");
            // [lv/C]Toast : 모든 값을 입력해주세요!
            Toast.makeText(context, R.string.custom_dialog_event_save_snack_all_input, Toast.LENGTH_SHORT).show();
            return false;
        } // [check 1]

    } // End of method [checkWhetherInputAllEventData]
    
    
    /**
     * [method] Firebase database 에 데이터를 저장하는 과정을 진행한다.
     *
     */
    private void saveContentProcess() {

        // [check 1] : 입력데이터 검사
        if (checkWhetherInputAllEventData()) {

            String eventNameContent = eventName.getText().toString();
            MuscleArea muscleAreaContent = this.muscleArea;
            EquipmentType equipmentTypeContent = DataManager.convertEquipmentType(this.equipmentType.getSelectedItemPosition());
            GroupType groupTypeContent = DataManager.convertGroupType(this.groupType.getSelectedItemPosition());
            float properWeightContent = Float.parseFloat(this.properWeight.getText().toString());
            float maxWeightContent = Float.parseFloat(this.maxWeight.getText().toString());

            // [lv/C]HashMap<String, Object> :
            HashMap<String, Object> data = new HashMap<>();
            data.put(Event.Entry.COLUMN_NAME_EVENT_NAME, eventNameContent);
            data.put(Event.Entry.COLUMN_NAME_MUSCLE_AREA, muscleAreaContent);
            data.put(Event.Entry.COLUMN_NAME_EQUIPMENT_TYPE, equipmentTypeContent);
            data.put(Event.Entry.COLUMN_NAME_GROUP_TYPE, groupTypeContent);
            data.put(Event.Entry.COLUMN_NAME_PROPER_WEIGHT, properWeightContent);
            data.put(Event.Entry.COLUMN_NAME_MAX_WEIGHT, maxWeightContent);
            data.put(Event.Entry.COLUMN_NAME_SELECTION_COUNTER, 0);


            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(this.muscleArea.toString()).push().setValue(data, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    // [check 2] : error 발생 안 했음
                    if (error == null) {

                        // "입력에 성공하였습니다." Toast 메시지 표시
                        Toast.makeText(context, R.string.custom_dialog_event_save_snack_input_success, Toast.LENGTH_SHORT).show();

                    } else {

                        // "입력에 실패하였습니다." Toast 메시지 표시
                        Toast.makeText(context, R.string.custom_dialog_event_save_snack_input_fail, Toast.LENGTH_SHORT).show();
                    } // [check 2]
                }
            });

        } else {

            // "모든 데이터를 입력해주세요." Toast 메시지 표시
            Toast.makeText(context, R.string.custom_dialog_event_save_snack_all_input, Toast.LENGTH_SHORT).show();

        } // [check 1]

    } // End of method [saveContentProcess]
}
