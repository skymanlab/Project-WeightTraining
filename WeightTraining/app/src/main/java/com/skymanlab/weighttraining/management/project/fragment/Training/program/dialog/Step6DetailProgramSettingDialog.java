package com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep6Fragment;

public class Step6DetailProgramSettingDialog extends DialogFragment {

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

    // instance variable
    private Event event;

    // instance variable
    private View customView;
    private TextView eventName;
    private NumberPicker setNumber;
    private NumberPicker restTimeMinute;
    private NumberPicker restTimeSecond;

    // constructor
    public Step6DetailProgramSettingDialog() {

    }

    /**
     * newInstance
     *
     * @param event
     * @return
     */
    public static Step6DetailProgramSettingDialog newInstance(Event event) {

        Step6DetailProgramSettingDialog dialog = new Step6DetailProgramSettingDialog();

        Bundle args = new Bundle();
        args.putSerializable(EVENT, event);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreate] ";
        if (getArguments() != null) {
            this.event = (Event) getArguments().getSerializable(EVENT);
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
        builder.setView(this.customView)
                .setPositiveButton(R.string.custom_dialog_detail_program_setting_alert_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // [lv/C]Bundle : 전달할 데이터 설정
                        Bundle args = new Bundle();
                        args.putString(DETAIL_PROGRAM_EVENT_KEY, event.getKey());
                        args.putInt(DETAIL_PROGRAM_SET_NUMBER, setNumber.getValue());
                        args.putInt(DETAIL_PROGRAM_REST_TIME_MINUTE, restTimeMinute.getValue());
                        args.putInt(DETAIL_PROGRAM_REST_TIME_SECOND, restTimeSecond.getValue());
                        args.putString("hello", "안녕");

                        // FragmentManager 의 FragmentResultListener 를 통해서 args 를 전달
                        getParentFragmentManager().setFragmentResult(REQUEST_KEY, args);
                    }
                })
                .setNegativeButton(R.string.custom_dialog_detail_program_setting_alert_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                });

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

    } // End of method [connectWidget]

    /**
     * layout 의 widget init
     */
    private void initWidget() {
        final String METHOD_NAME ="[initWidget] ";

        // nav_home_content_wrapper 에 표시되어있는 Fragment 인 MakerStep6Fragment 를 가져오기
        MakerStep6Fragment fragment = (MakerStep6Fragment)getParentFragmentManager().findFragmentById(R.id.nav_home_content_wrapper);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "parent fragment = " + fragment);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> program set number = " + fragment.getSectionManager().getDataOfProgramSettingSetNumber());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> program rest time minute = " + fragment.getSectionManager().getDataOfProgramSettingRestTimeMinute());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> program rest time second = " + fragment.getSectionManager().getDataOfProgramSettingRestTimeSecond());

        // text
        this.eventName.setText(event.getEventName());

        // [iv/C]NumberPicker : setNumber init
        this.setNumber.setMaxValue(9);
        this.setNumber.setMinValue(0);
        this.setNumber.setValue(fragment.getSectionManager().getDataOfProgramSettingSetNumber());

        // [iv/C]NumberPicker : restTimeMinute init
        this.restTimeMinute.setMaxValue(59);
        this.restTimeMinute.setMinValue(0);
        this.restTimeMinute.setValue(fragment.getSectionManager().getDataOfProgramSettingRestTimeMinute());

        // [iv/C]NumberPicker : init
        this.restTimeSecond.setMaxValue(59);
        this.restTimeSecond.setMinValue(0);
        this.restTimeSecond.setValue(fragment.getSectionManager().getDataOfProgramSettingRestTimeSecond());

    } // End of method [initWidget]

}
