package com.skymanlab.weighttraining.management.event.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;


public class RestTimePickerDialog extends DialogFragment {

    // constant
    public static final String GET_REST_TIME_MINUTE_NUMBER = "restTimeMinuteNumber";
    public static final String GET_REST_TIME_SECOND_NUMBER = "restTimeSecondNumber";
    // constant
    private static final int MAX_NUMBER = 59;
    private static final int MIN_NUMBER = 0;
    // constant
    private static final String CLASS_NAME = "[ED]_RestTimePickerDialog";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;
    // instance variable
    private NoticeDialogListener listener;

    // instance variable
    private int restTimeMinuteNumber;
    private int restTimeSecondNumber;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String METHOD_NAME = "[onCreateDialog] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>><<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>++++++++++++++++++++++++++++");

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= data getter(from bundle) =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [method] : Bundle 객체를 통해 restTimeMinuteNumber 와  restTimeSecondNumber 값 가져오기
        getContentOfBundle();


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]LayoutInflater : custom layout 을 가져오기위한
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // [lv/C]View : 위의 inflater 을 이용하여 custom layout 의 widget 을 사용하기 위한 view 를 가져오기
        View view = inflater.inflate(R.layout.custom_rest_time, null);

        // [lv/C]NumberPicker : 위의 view 에서 restTime 의 minute 에 해당하는 NumberPicker widget mapping / 초기 설정 / bundle 로 넘어온 restTimeMinuteNumber 을 초기 값으로 설정
        final NumberPicker restTimeMinutePicker = (NumberPicker) view.findViewById(R.id.cud_rest_time_minute);
        restTimeMinutePicker.setMaxValue(MAX_NUMBER);
        restTimeMinutePicker.setMinValue(MIN_NUMBER);
        restTimeMinutePicker.setWrapSelectorWheel(true);
        restTimeMinutePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        restTimeMinutePicker.setValue(this.restTimeMinuteNumber);

        // [lv/C]NumberPicker : 위의 view 에서 restTime 의 second 에 해당하는 NumberPicker widget mapping / 초기 설정 / bundle 로 넘어온 restTimeSecondNumber 을 초기 값으로 설정
        final NumberPicker restTimeSecondPicker = (NumberPicker) view.findViewById(R.id.cud_rest_time_second);
        restTimeSecondPicker.setMaxValue(MAX_NUMBER);
        restTimeSecondPicker.setMinValue(MIN_NUMBER);
        restTimeSecondPicker.setWrapSelectorWheel(true);
        restTimeSecondPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        restTimeSecondPicker.setValue(restTimeSecondNumber);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= alert dialog setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]AlertDialog.Builder : custom layout 을 적용하는 AlertDialog.Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // [lv/C]AlertDialog.Builder : builder 초기 설정
        builder.setView(view)
                .setPositiveButton(R.string.cud_rest_time_bt_change, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. rest time minute number = " + restTimeMinutePicker.getValue());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. rest time second number = " + restTimeSecondPicker.getValue());

                        // [iv/C]NoticeDialogListener : positive 버튼을 클릭했을 때 onDialogPositiveClick 을 구현하여 해당 Dialog 와 custom layout 의 widget 을 사용할 수 있도록 한다.
                        listener.onDialogPositiveClick(RestTimePickerDialog.this, restTimeMinutePicker.getValue(), restTimeSecondPicker.getValue());

                    }
                })
                .setNegativeButton(R.string.cud_rest_time_bt_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [iv/C]NoticeDialogListener : negative 버튼을 클릭했을 때, NoticeDialogListener 의 interface onDialogNegativeClick 을 구현한 곳에서 Dialog 와 custom layout 의 widget 의 내용을 넘겨 받는다. 그 Activity 에서는 이 내용을 이용하여 해당 Activity 에 적용할 수 있다.
                        listener.onDialogNegativeClick(RestTimePickerDialog.this);

                    }
                });

        return builder.create();

    } // End of method [onCreateDialog]

    /**
     * [method] RestTimePickerDialog 를 객체를 생성하며 Bundle 에 포함하여 보낸 값드을 가져오기
     */
    private void getContentOfBundle() {

        final String METHOD_NAME = "[getContentOfBundle] ";

        // [lv/C]Bundle : 보내온 Bundle 객체가져오기
        Bundle bundle = getArguments();

        // [check 1] : bundle 객체가 있다.
        if (bundle != null) {

            // [iv/i]restTimeMinuteNumber : bundle 에서 'restTimeMinuteNumber' 에 해당 하는 값 가져오기
            this.restTimeMinuteNumber = bundle.getInt(GET_REST_TIME_MINUTE_NUMBER);

            // [iv/i]restTimeSecondNumber : bundle 에서 'restTimeSecondNumber' 에 해당 하는 값 가져오기
            this.restTimeSecondNumber = bundle.getInt(GET_REST_TIME_SECOND_NUMBER);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. restTimeMinuteNumber = " + restTimeMinuteNumber);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. restTimeSecondNumber = " + restTimeSecondNumber);

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : Bundle 객체가 없어서 가져올 수 없어! <=");
        } // [check 1]

    } // End of method [getContentOfBundle]

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {

            // [iv/C]NoticeDialogListener : 해당 interface 의 객체 생성
            listener = (NoticeDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener!");
        }

    } // End of method [onAttach]

    // interface : AlertDialog 의 positive 와 negative 버튼에 대한 click listener 를 설정하도록
    public interface NoticeDialogListener {
        void onDialogPositiveClick(RestTimePickerDialog dialog, int restTimeMinuteNumber, int restTimeSecondNumber);

        void onDialogNegativeClick(RestTimePickerDialog dialog);
    }


}
