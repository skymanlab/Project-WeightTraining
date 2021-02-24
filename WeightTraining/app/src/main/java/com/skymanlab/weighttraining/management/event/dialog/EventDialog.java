package com.skymanlab.weighttraining.management.event.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.HashMap;

public class EventDialog extends DialogFragment {

    // constant
    public static final String CLASS_NAME = "[ED] EventDialog";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    public static final int MESSAGE_TYPE_NONE = 0;
    public static final int MESSAGE_TYPE_SNACK_BAR = 1;
    public static final int MESSAGE_TYPE_LOG = 2;

    // constant
    private static final String MUSCLE_AREA = "muscleArea";
    private static final String EVENT = "event";
    private static final String SET_ARGUMENTS = "setArguments";

    // instance variable
    private MuscleArea muscleArea;
    private Event event;
    private String[] arguments;

    // instance variable
    private View customView;

    // instance variable
    private TextView title;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;
    private MaterialTextView positive;
    private MaterialTextView negative;

    // instance variable
    private DatabaseListener databaseListener;

    // constructor
    private EventDialog() {

    }

    //setter
    public void setDatabaseListener(DatabaseListener databaseListener) {
        this.databaseListener = databaseListener;
    }

    // newInstance
    public static EventDialog newInstance(MuscleArea muscleArea, Event event, String[] arguments) {

        EventDialog dialog = new EventDialog();

        Bundle args = new Bundle();
        args.putSerializable(MUSCLE_AREA, muscleArea);
        args.putSerializable(EVENT, event);
        args.putStringArray(SET_ARGUMENTS, arguments);
        dialog.setArguments(args);
        return dialog;

    }


    /**
     * newInstance 에 parameter 로 보내는 arguments 를 설정하여 해당 객체를 반환한다.
     *
     * @param title
     * @param positiveTitle
     * @param negativeTitle
     * @return
     */
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
            this.muscleArea = (MuscleArea) getArguments().getSerializable(MUSCLE_AREA);
            this.event = (Event) getArguments().getSerializable(EVENT);
            this.arguments = getArguments().getStringArray(SET_ARGUMENTS);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> Dialog event = " + this.event);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> muscle area = " + this.muscleArea);
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
        customView = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_event_integrated, null);

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

                if (databaseListener != null) {

                    // [lv/C]Event : widget 과 event 로 데이터베이스에 보낼 Event 객체 생성
                    Event data = new Event();

                    // [check 1] :
                    if (checkWhetherInputAllEventData()) {

                        // [check 2] : event 가 있으면 해당 event 를 수정하는 경우, null 이면 새로운 Event 객체를 저장하는 경우이다.
                        if (event != null) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>---------- event  있습니다.");
                            // update
                            data.setKey(event.getKey());
                            data.setEventName(eventName.getText().toString());
                            data.setMuscleArea(event.getMuscleArea());
                            data.setEquipmentType(DataManager.convertEquipmentType(equipmentType.getSelectedItemPosition()));
                            data.setGroupType(DataManager.convertGroupType(groupType.getSelectedItemPosition()));
                            data.setProperWeight(Float.parseFloat(properWeight.getText().toString()));
                            data.setMaxWeight(Float.parseFloat(maxWeight.getText().toString()));
                            data.setSelectionCounter(event.getSelectionCounter());
                        } else {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>---------- event is null");

                            // save
                            data.setKey(null);
                            data.setEventName(eventName.getText().toString());
                            data.setMuscleArea(muscleArea);
                            data.setEquipmentType(DataManager.convertEquipmentType(equipmentType.getSelectedItemPosition()));
                            data.setGroupType(DataManager.convertGroupType(groupType.getSelectedItemPosition()));
                            data.setProperWeight(Float.parseFloat(properWeight.getText().toString()));
                            data.setMaxWeight(Float.parseFloat(maxWeight.getText().toString()));
                            data.setSelectionCounter(0);

                        } // [check 2]

                        databaseListener.setDatabaseListener(data);

                    } // [check 1]

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


    // =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Database =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * [method] Firebase database 에 데이터를 수정하는 과정을 진행
     */
    public void updateContent(Event event, int messageType) {
        final String METHOD_NAME = "[updateContent] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 1. event name = " + event.getEventName());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 2. MUSCLE_AREA = " + event.getMuscleArea());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 3. EQUIPMENT_TYPE = " + event.getEquipmentType());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 4. GROUP_TYPE = " + event.getGroupType());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 5. PROPER_WEIGHT = " + event.getProperWeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 6. MAX_WEIGHT = " + event.getMaxWeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 7. SELECTION_COUNTER = " + event.getSelectionCounter());

        // [lv/C]HashMap<String, Object> :
        HashMap<String, Object> content = new HashMap<>();
        content.put(Event.EVENT_NAME, event.getEventName());               // [1] event name
        content.put(Event.MUSCLE_AREA, event.getMuscleArea());             // [2] muscle area
        content.put(Event.EQUIPMENT_TYPE, event.getEquipmentType());       // [3] equipment type
        content.put(Event.GROUP_TYPE, event.getGroupType());               // [4] group type
        content.put(Event.PROPER_WEIGHT, event.getProperWeight());         // [5] proper weight
        content.put(Event.MAX_WEIGHT, event.getMaxWeight());               // [6] max weight
        content.put(Event.SELECTION_COUNTER, event.getSelectionCounter()); // [7] selection counter

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
        db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(event.getMuscleArea().toString())
                .child(event.getKey())
                .updateChildren(content, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        // messageType 에 따라 결과 메시지 표시하기
                        showMessage(error, messageType, R.string.custom_dialog_event_update_snack_db_success, R.string.custom_dialog_event_update_snack_db_error);

                        // dialog 종료
                        dismiss();
                    }
                });

    } // End of method [updateContent]


    /**
     * [method] Firebase database 에 데이터를 저장하는 과정을 진행한다.
     */
    public void saveContent(Event event, int messageType) {
        final String METHOD_NAME = "[saveContent] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 1. event name = " + event.getEventName());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 2. MUSCLE_AREA = " + event.getMuscleArea());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 3. EQUIPMENT_TYPE = " + event.getEquipmentType());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 4. GROUP_TYPE = " + event.getGroupType());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 5. PROPER_WEIGHT = " + event.getProperWeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 6. MAX_WEIGHT = " + event.getMaxWeight());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> 7. SELECTION_COUNTER = " + event.getSelectionCounter());

        // [lv/C]HashMap<String, Object> :
        HashMap<String, Object> content = new HashMap<>();
        content.put(Event.EVENT_NAME, event.getEventName());               // [1] event name
        content.put(Event.MUSCLE_AREA, event.getMuscleArea());             // [2] muscle area
        content.put(Event.EQUIPMENT_TYPE, event.getEquipmentType());       // [3] equipment type
        content.put(Event.GROUP_TYPE, event.getGroupType());               // [4] group type
        content.put(Event.PROPER_WEIGHT, event.getProperWeight());         // [5] proper weight
        content.put(Event.MAX_WEIGHT, event.getMaxWeight());               // [6] max weight
        content.put(Event.SELECTION_COUNTER, event.getSelectionCounter()); // [7] selection counter

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
        db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(event.getMuscleArea().toString())
                .push()
                .setValue(content, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        // messageType 에 따라 결과 메시지 표시하기
                        showMessage(error, messageType, R.string.custom_dialog_event_save_snack_db_success, R.string.custom_dialog_event_save_snack_db_error);

                        // dialog 종료
                        dismiss();
                    }
                });


    } // End of method [saveContent]


    private void showMessage(DatabaseError error, int messageType, int successMessageId, int errorMessageId) {
        final String METHOD_NAME = "[showMessage] ";

        // [switch 1] : messageType 에 따라
        switch (messageType) {

            case MESSAGE_TYPE_NONE:
                break;

            case MESSAGE_TYPE_SNACK_BAR:

                if (error == null) {


                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<fragment activity> = " + getActivity());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<get view> = " + getView());
                    // success message
                    Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), successMessageId, Snackbar.LENGTH_SHORT);

                } else {
                    // error message
                    Snackbar.make(getActivity().findViewById(R.id.nav_home_bottom_bar), errorMessageId, Snackbar.LENGTH_SHORT);
                }

                break;

            case MESSAGE_TYPE_LOG:

                if (error == null) {
                    // success message
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, getActivity().getString(successMessageId));
                } else {
                    // error message
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, getActivity().getString(errorMessageId));
                }

                break;

        } // [switch 1]

    }


    // =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-==-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface DatabaseListener {
        void setDatabaseListener(Event event);
    }

}
