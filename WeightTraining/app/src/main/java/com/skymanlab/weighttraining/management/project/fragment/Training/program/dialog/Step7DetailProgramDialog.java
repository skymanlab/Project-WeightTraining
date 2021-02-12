package com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.program.data.DetailProgram;

public class Step7DetailProgramDialog extends DialogFragment {

    // constant
    private static final String CLASS_NAME = "[Ac] Step6PartialSettingDialog";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final String REQUEST_KEY = "detailProgram";
    public static final String DETAIL_PROGRAM_EVENT_KEY = "eventKey";
    public static final String DETAIL_PROGRAM_SET_NUMBER = "detailProgramSetNumber";
    public static final String DETAIL_PROGRAM_REST_TIME_MINUTE = "detailProgramRestTimeMinute";
    public static final String DETAIL_PROGRAM_REST_TIME_SECOND = "detailProgramRestTimeSecond";

    // constant
    private static final String EVENT = "event";
    private static final String DETAIL_PROGRAM = "detailProgram";

    // instance variable
    private Event event;
    private DetailProgram detailProgram;

    // instance variable
    private View customView;
    private TextView eventName;
    private NumberPicker setNumber;
    private NumberPicker restTimeMinute;
    private NumberPicker restTimeSecond;
    private Button positive;
    private Button negative;

    // constructor
    public Step7DetailProgramDialog() {

    }

    /**
     * newInstance
     *
     * @param event
     * @return
     */
    public static Step7DetailProgramDialog newInstance(Event event, DetailProgram detailProgram) {

        Step7DetailProgramDialog dialog = new Step7DetailProgramDialog();

        Bundle args = new Bundle();
        args.putSerializable(EVENT, event);
        args.putSerializable(DETAIL_PROGRAM, detailProgram);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreate] ";
        if (getArguments() != null) {
            this.event = (Event) getArguments().getSerializable(EVENT);
            this.detailProgram = (DetailProgram) getArguments().getSerializable(DETAIL_PROGRAM);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String METHOD_NAME = "[onCreateDialog] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "savedInstanceState = " + savedInstanceState);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout init =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]View : layoutInflater 을 통해 custom layout 가져오기
        this.customView = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_detail_program_setting, null);

        // connect
        connectWidget();

        // init
        initWidget();

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= AlertDialog init =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]AlertDialog : dialog init
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(this.customView);

        return builder.create();
    }


    /**
     * layout 의 widget connect
     */
    private void connectWidget() {

        // [iv/C]TextView : eventName connect
        this.eventName = (TextView) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_event_name);

        // [iv/C]NumberPicker : setNumber connect
        this.setNumber = (NumberPicker) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_set_number);

        // [iv/C]NumberPicker : restTimeMinute connect
        this.restTimeMinute = (NumberPicker) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_rest_time_minute);

        // [iv/C]NumberPicker : restTimeSecond connect
        this.restTimeSecond = (NumberPicker) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_rest_time_second);

        // [Button] [positive] widget connect
        this.positive = (Button) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_bt_positive);

        // [Button] [negative] widget connect
        this.negative = (Button) this.customView.findViewById(R.id.custom_dialog_detail_program_setting_bt_negative);


    } // End of method [connectWidget]

    /**
     * layout 의 widget init
     */
    private void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // text
        this.eventName.setText(event.getEventName());

        // [iv/C]NumberPicker : setNumber init
        this.setNumber.setMaxValue(9);
        this.setNumber.setMinValue(0);
        this.setNumber.setValue(detailProgram.getSetNumber());

        // [iv/C]NumberPicker : restTimeMinute init
        this.restTimeMinute.setMaxValue(59);
        this.restTimeMinute.setMinValue(0);
        this.restTimeMinute.setValue(detailProgram.getRestTimeMinute());

        // [iv/C]NumberPicker : init
        this.restTimeSecond.setMaxValue(59);
        this.restTimeSecond.setMinValue(0);
        this.restTimeSecond.setValue(detailProgram.getRestTimeSecond());

        // positive click listener
        this.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkData(setNumber.getValue(), restTimeMinute.getValue(), restTimeSecond.getValue())) {

                    // [lv/C]Bundle : 전달할 데이터 설정
                    Bundle args = new Bundle();
                    args.putString(DETAIL_PROGRAM_EVENT_KEY, event.getKey());
                    args.putInt(DETAIL_PROGRAM_SET_NUMBER, setNumber.getValue());
                    args.putInt(DETAIL_PROGRAM_REST_TIME_MINUTE, restTimeMinute.getValue());
                    args.putInt(DETAIL_PROGRAM_REST_TIME_SECOND, restTimeSecond.getValue());
                    args.putString("hello", "안녕");

                    // FragmentManager 의 FragmentResultListener 를 통해서 args 를 전달
                    getParentFragmentManager().setFragmentResult(REQUEST_KEY, args);

                    dismiss();

                } else {
                    Snackbar.make(customView, R.string.custom_dialog_detail_program_setting_snack_setting_data_error, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        // negative click listener
        this.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    } // End of method [initWidget]


    private boolean checkData(int setNumber, int restTimeMinute, int restTimeSecond) {
        final String METHOD_NAME = "[checkData] ";

        // [check 1] : setNumber 는 0 이상일때만
        if (0 < setNumber) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 통과");

            // '분' 이 0 이더라도 '초' 가 0 이상이면 조건 만족한다.
            boolean restTimeChecker1 = (0 == restTimeMinute) && (0 < restTimeSecond) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 1 = " + restTimeChecker1);

            // '분' 이 0 이상이면 '초' 는 0 이거나 그 이상이면 조건에 만족한다.
            boolean restTimeChecker2 = (0 < restTimeMinute) && (0 <= restTimeSecond) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 2 = " + restTimeChecker2);

            // [check 2] : restTimeMinute, restTimeSecond 는 restTime 으로 조건을 검사한다.
            if (restTimeChecker1 || restTimeChecker2) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 통과");
                return true;

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 불충분");
                return false;
            }

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 불충분");
            return false;

        } // [check 1]

    } // End of method [checkData]

}
