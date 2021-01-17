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

public class SetNumberPickerDialog extends DialogFragment {

    // constant
    public static final String GET_SET_NUMBER = "setNumber";
    // constant
    private static final int MAX_SET_NUMBER = 10;
    private static final int MIN_SET_NUMBER = 0;
    // constant
    private static final String CLASS_NAME = "[ED] SetNumberPickerDialog";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private NoticeDialogListener listener;
    private int setNumber;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // [method] : Bundle 객체를 통해 restTimeMinuteNumber 와  restTimeSecondNumber 값 가져오기
        getContentOfBundle();

        // [lv/C]AlertDialog.Builder : custom layout 을 적용하는 AlertDialog.Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // [lv/C]LayoutInflater : get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // [lv/C]View : 위의 inflater 을 이용하여 custom layout 의 widget 을 사용하기 위한 view 를 가져오기
        View view = inflater.inflate(R.layout.custom_dialog_set_number_picker, null);

        // [lv/C]NumberPicker : 위의 view 에서 setNumber 에 해당하는 NumberPicker widget mapping / 초기 설정 / bundle 로 넘어온 setNumber 을 초기 값으로 설정
        final NumberPicker setNumberPicker = (NumberPicker) view.findViewById(R.id.custom_dialog_set_number_picker_number);
        setNumberPicker.setMaxValue(MAX_SET_NUMBER);
        setNumberPicker.setMinValue(MIN_SET_NUMBER);
        setNumberPicker.setWrapSelectorWheel(true);
        setNumberPicker.setValue(this.setNumber);

        // [lv/C]AlertDialog : 위에서 설정한 view 를 적용한다.
        builder.setView(view)           // error -> 해당 View 를 위에서 부르고 또 여기서 따로 부르면 객체가 통일이 안 되면, numberPicker 가 해당 view 에서만 설정값이 들어가므로 setView 에서 따로 한 View 에는 설정값이 적용되지 않는다.
                .setPositiveButton(R.string.custom_dialog_set_number_picker_alert_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [iv/C]NoticeDialogListener : 해당 Dialog 를 부른 activity 에서 해당 내용을 처리하도록 한다.
                        listener.onDialogPositiveClick(SetNumberPickerDialog.this, setNumberPicker.getValue());
                    }
                })
                .setNegativeButton(R.string.custom_dialog_set_number_picker_alert_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [iv/C]NoticeDialogListener : 해당 Dialog 를 부른 activity 에서 해당 내용을 처리하도록 한다.
                        listener.onDialogNegativeClick(SetNumberPickerDialog.this);

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

            // [iv/i]setNumber : bundle 에서 'setNumber' 에 해당 하는 값 가져오기
            this.setNumber = bundle.getInt(GET_SET_NUMBER);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> 1. set number = " + this.setNumber);
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

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    // positive, negative button 클릭에 관한 메소드를 정하도록 interface 로 만든다.
    public interface NoticeDialogListener {
        void onDialogPositiveClick(SetNumberPickerDialog dialog, int setNumber);

        void onDialogNegativeClick(SetNumberPickerDialog dialog);
    }
}
