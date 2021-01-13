package com.skymanlab.weighttraining.management.event.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.data.EventMapping;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.EquipmentType;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDialog extends DialogFragment {

    // constant
    public static final String CLASS_NAME = "[ED] EventDialog";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String EVENT = "event";
    private static final String SET_ARGUMENTS = "setArguments";

    // instance variable
    private Event event;
    private String[] arguments;

    // instance variable
    private View customView;

    // instance variable
    private LinearLayout titleWrapper;
    private TextView title;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;
    private MaterialTextView positive;
    private MaterialTextView negative;

    // instance variable
    private OnPositiveClickListener clickListener;

    // constructor
    public EventDialog() {

    }

    //setter
    public void setClickListener(OnPositiveClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // newInstance
    public static EventDialog newInstance(Event event, String[] arguments) {

        EventDialog dialog = new EventDialog();

        Bundle args = new Bundle();
        args.putSerializable(EVENT, event);
        args.putStringArray(SET_ARGUMENTS, arguments);
        dialog.setArguments(args);
        return dialog;

    }

    public static String[] setArguments(String title, String positiveTitle, String negativeTitle) {

        String[] arguments = new String[3];

        arguments[0] = title;
        arguments[1] = positiveTitle;
        arguments[2] = negativeTitle;
        return arguments;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreateDialog] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= data getter(from bundle) =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if (getArguments() != null) {
            this.event = (Event) getArguments().getSerializable(EVENT);
            this.arguments = getArguments().getStringArray(SET_ARGUMENTS);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> Dialog event = " + this.event);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> title = " + this.arguments[0]);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> positive title = " + this.arguments[1]);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> negative title = " + this.arguments[2]);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String METHOD_NAME = "[onCreateDialog] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]View : layoutInflater 을 통해 custom layout 가져오기
        customView = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_event, null);

        // [method] : widget mapping
        connectWidget(customView);

        // [method] : widget init
        initWidget(event);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= alert dialog setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]AlertDialog.Builder : builder 객체 생성 및 초기
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(customView);
        return builder.create();
    }

    /**
     * [method] widget connect
     */
    private void connectWidget(View view) {

        // [iv/C]LinearLayout : titleWrapper connect
        this.titleWrapper = (LinearLayout) view.findViewById(R.id.custom_dialog_event_title_wrapper);

        // [iv/C]TextView : title connect
        this.title = (TextView) view.findViewById(R.id.custom_dialog_event_title);

        // [iv/C]Spinner : equipmentType connect
        this.equipmentType = (Spinner) view.findViewById(R.id.custom_dialog_event_equipment_type);

        // [iv/C]Spinner : equipmentType connect
        this.groupType = (Spinner) view.findViewById(R.id.custom_dialog_event_group_type);

        // [ic/C]EditText : eventName connect
        this.eventName = (EditText) view.findViewById(R.id.custom_dialog_event_event_name);

        // [ic/C]EditText : properWeight connect
        this.properWeight = (EditText) view.findViewById(R.id.custom_dialog_event_proper_weight);

        // [ic/C]EditText : maxWeight connect
        this.maxWeight = (EditText) view.findViewById(R.id.custom_dialog_event_max_weight);

        // [iv/C]MaterialTextView : positive connect
        this.positive = (MaterialTextView) view.findViewById(R.id.custom_dialog_event_bt_positive);

        // [iv/C]MaterialTextView : negative connect
        this.negative = (MaterialTextView) view.findViewById(R.id.custom_dialog_event_bt_negative);

    } // End of method [connectWidget]


    /**
     * [method] widget init
     */
    private void initWidget(Event event) {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]TextView : title
        this.title.setText(this.arguments[0]);

        // [iv/C]MaterialTextView : positive text
        this.positive.setText(arguments[1]);

        // [iv/C]MaterialTextView : negative text
        this.negative.setText(arguments[2]);

        // [method] : equipmentType 을 adapter 와 연결하기
        setAdapterOfEquipmentType();

        // [method] : groupType 을 adapter 와 연결하기
        setAdapterOfGroupType();

        // [check 1] : event 객체를 받았을 때만
        if (event != null) {

            // [iv/C]Spinner : type 에 해당하는 위치의 값을 보여준다.
            this.equipmentType.setSelection(DataManager.convertPositionOfEquipmentType(event.getEquipmentType()));

            // [iv/C]Spinner : type 에 해당하는 위치의 값을 보여준다.
            this.groupType.setSelection(DataManager.convertPositionOfGroupType(event.getGroupType()));

            // [iv/C]EditText : eventName 초기 설정
            this.eventName.setText(event.getEventName());

            // [iv/C]EditText : properWeight 초기 설정
            this.properWeight.setText(event.getProperWeight() + "");

            // [iv/C]EditText : maxWeight 초기 설정
            this.maxWeight.setText(event.getMaxWeight() + "");
        } // [check 1]

        // [iv/C]MaterialTextView : positive click listener
        this.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clickListener != null) {

                    clickListener.setClickListener();
                }

            }
        });

        // [iv/C]MaterialTextView : negative click listener
        this.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

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
                Snackbar.make(customView, R.string.custom_dialog_event_snack_0_up_input, Snackbar.LENGTH_SHORT).show();
                return false;
            } // [check 2]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 입력되지 않은 값이 있어! <=");

            // [lv/C]Toast : 모든 값을 입력해주세요!
            Snackbar.make(customView, R.string.custom_dialog_event_snack_all_input, Snackbar.LENGTH_SHORT).show();
            return false;
        } // [check 1]

    } // End of method [checkWhetherInputAllEventData]


    /**
     * [method] Firebase database 에 데이터를 수정하는 과정을 진행
     */
    public void updateContent() {
        final String METHOD_NAME = "[updateContent] ";

        // [check 1] : 입력 데이터 검사
        if (checkWhetherInputAllEventData()) {

            String eventNameContent = eventName.getText().toString();                                                               // 1. event name
            MuscleArea muscleAreaContent = event.getMuscleArea();                                                                   // 2. muscle area
            EquipmentType equipmentTypeContent = DataManager.convertEquipmentType(this.equipmentType.getSelectedItemPosition());    // 3. equipment tyep
            GroupType groupTypeContent = DataManager.convertGroupType(this.groupType.getSelectedItemPosition());                    // 4. group type
            float properWeightContent = Float.parseFloat(this.properWeight.getText().toString());                                   // 5. proper weight
            float maxWeightContent = Float.parseFloat(this.maxWeight.getText().toString());                                         // 6. max weight
            long selectionCounter = event.getSelectionCounter();                                                                    // 7. selection count

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. event name = " + eventNameContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. muscle area = " + muscleAreaContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. equipment type = " + equipmentTypeContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. group type = " + groupTypeContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "5. proper weight = " + properWeightContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "6. max weight = " + maxWeightContent);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "7. selection counter = " + selectionCounter);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----> key = " + event.getKey());

            // [lv/C]HashMap<String, Object> :
            HashMap<String, Object> data = new HashMap<>();
            data.put(Event.Entry.COLUMN_NAME_EVENT_NAME, eventNameContent);
            data.put(Event.Entry.COLUMN_NAME_MUSCLE_AREA, muscleAreaContent);
            data.put(Event.Entry.COLUMN_NAME_EQUIPMENT_TYPE, equipmentTypeContent);
            data.put(Event.Entry.COLUMN_NAME_GROUP_TYPE, groupTypeContent);
            data.put(Event.Entry.COLUMN_NAME_PROPER_WEIGHT, properWeightContent);
            data.put(Event.Entry.COLUMN_NAME_MAX_WEIGHT, maxWeightContent);
            data.put(Event.Entry.COLUMN_NAME_SELECTION_COUNTER, selectionCounter);

            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(event.getMuscleArea().toString())
                    .child(event.getKey())
                    .updateChildren(data, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            // [check 1] : error 발생 안함
                            if (error == null) {
                                // success
                                Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.custom_dialog_event_update_snack_db_success, Snackbar.LENGTH_SHORT).show();

                                // dialog 종료
                                dismiss();

                            } else {
                                // error 발생
                                Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.custom_dialog_event_update_snack_db_error, Snackbar.LENGTH_SHORT).show();

                                // dialog 종료
                                dismiss();
                            } // [check 1]
                        }
                    });

        } // [check 1]

    } // End of method [updateContent]


    /**
     * [method] Firebase database 에 데이터를 저장하는 과정을 진행한다.
     */
    private void saveContent() {

        // [check 1] : 입력데이터 검사
        if (checkWhetherInputAllEventData()) {

            String eventNameContent = eventName.getText().toString();                                                               // 1. event name
            MuscleArea muscleAreaContent = event.getMuscleArea();                                                                   // 2. muscle area
            EquipmentType equipmentTypeContent = DataManager.convertEquipmentType(this.equipmentType.getSelectedItemPosition());    // 3. equipment tyep
            GroupType groupTypeContent = DataManager.convertGroupType(this.groupType.getSelectedItemPosition());                    // 4. group type
            float properWeightContent = Float.parseFloat(this.properWeight.getText().toString());                                   // 5. proper weight
            float maxWeightContent = Float.parseFloat(this.maxWeight.getText().toString());                                         // 6. max weight
            int selectionCount = 0;                                                                                                 // 7. selection count

            // [lv/C]HashMap<String, Object> :
            HashMap<String, Object> data = new HashMap<>();
            data.put(Event.Entry.COLUMN_NAME_EVENT_NAME, eventNameContent);
            data.put(Event.Entry.COLUMN_NAME_MUSCLE_AREA, muscleAreaContent);
            data.put(Event.Entry.COLUMN_NAME_EQUIPMENT_TYPE, equipmentTypeContent);
            data.put(Event.Entry.COLUMN_NAME_GROUP_TYPE, groupTypeContent);
            data.put(Event.Entry.COLUMN_NAME_PROPER_WEIGHT, properWeightContent);
            data.put(Event.Entry.COLUMN_NAME_MAX_WEIGHT, maxWeightContent);
            data.put(Event.Entry.COLUMN_NAME_SELECTION_COUNTER, selectionCount);

            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
            db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child(event.getMuscleArea().toString())
                    .push()
                    .setValue(data, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            // [check 2] : error 발생 안 했음
                            if (error == null) {

                                // "입력에 성공하였습니다." Toast 메시지 표시
                                Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.custom_dialog_event_save_snack_db_success, Snackbar.LENGTH_SHORT);

                            } else {

                                // "입력에 실패하였습니다." Toast 메시지 표시
                                Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.custom_dialog_event_save_snack_input_fail, Snackbar.LENGTH_SHORT);
                            } // [check 2]
                        }
                    });

        }  // [check 1]

    } // End of method [saveContent]

    public interface OnPositiveClickListener {
        void setClickListener();
    }

}
