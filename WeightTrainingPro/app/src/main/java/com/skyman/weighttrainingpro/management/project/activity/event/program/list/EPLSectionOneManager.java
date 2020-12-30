package com.skyman.weighttrainingpro.management.project.activity.event.program.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.dialog.RestTimePickerDialog;
import com.skyman.weighttrainingpro.management.event.dialog.SetNumberPickerDialog;
import com.skyman.weighttrainingpro.management.project.activity.SectionManager;
import com.skyman.weighttrainingpro.management.project.activity.SectionInitialization;

public class EPLSectionOneManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAE]_EPLSectionOneManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private final int MAX_SET_NUMBER = 10;
    private final int MAX_REST_TIME = 59;

    // instance variable : EventProgramListActivity
    private FragmentManager fragmentManager;

    // instance variable
    private int setNumberCounter;
    private int restTimeMinuteCounter;
    private int restTimeSecondCounter;
    private boolean canInit;
    private boolean isClickedSetNumberPickerDialog;
    private boolean isClickedRestTimePickerDialog;

    // instance variable
    private MaterialCardView setNumberWrapper;
    private TextView setNumber;
    private ImageView setNumberUP;
    private ImageView setNumberDOWN;
    private MaterialCardView restTimeWrapper;
    private TextView restTimeMinute;
    private ImageView restTimeMinuteUP;
    private ImageView restTimeMinuteDOWN;
    private TextView restTimeSecond;
    private ImageView restTimeSecondUP;
    private ImageView restTimeSecondDOWN;


    // constructor
    public EPLSectionOneManager(Activity activity, FragmentManager fragmentManager) {
        super(activity);
        this.fragmentManager = fragmentManager;
        this.setNumberCounter = 0;
        this.restTimeMinuteCounter = 0;
        this.restTimeSecondCounter = 0;
        this.canInit = false;
        this.isClickedSetNumberPickerDialog = false;
        this.isClickedRestTimePickerDialog = false;

    }

    public void setClickedSetNumberPickerDialog(boolean clickedSetNumberPickerDialog) {
        isClickedSetNumberPickerDialog = clickedSetNumberPickerDialog;
    }

    public void setClickedRestTimePickerDialog(boolean clickedRestTimePickerDialog) {
        isClickedRestTimePickerDialog = clickedRestTimePickerDialog;
    }

    public void setSetNumberCounter(int setNumberCounter) {
        this.setNumberCounter = setNumberCounter;
    }

    public TextView getSetNumber() {
        return setNumber;
    }

    public void setRestTimeMinuteCounter(int restTimeMinuteCounter) {
        this.restTimeMinuteCounter = restTimeMinuteCounter;
    }

    public TextView getRestTimeMinute() {
        return restTimeMinute;
    }

    public void setRestTimeSecondCounter(int restTimeSecondCounter) {
        this.restTimeSecondCounter = restTimeSecondCounter;
    }

    public TextView getRestTimeSecond() {
        return restTimeSecond;
    }


    @Override
    public void mappingWidget() {

        // [iv/C]MaterialCardView : setNumberWrapper mapping
        this.setNumberWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_list_section_1_set_number_wrapper);

        // [iv/C]TextView : setNumber mapping
        this.setNumber = (TextView) getActivity().findViewById(R.id.event_program_list_section_1_set_number);

        // [iv/C]ImageView : setNumber mapping
        this.setNumberUP = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_set_number_up);

        // [iv/C]ImageView : setNumberDOWN mapping
        this.setNumberDOWN = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_set_number_down);


        // [iv/C]MaterialCardView : restTimeWrapper mapping
        this.restTimeWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_wrapper);

        // [iv/C]TextView : restTimeMinute mapping
        this.restTimeMinute = (TextView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_minute);

        // [iv/C]ImageView : restTimeMinuteUP mapping
        this.restTimeMinuteUP = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_minute_up);

        // [iv/C]ImageView : restTimeMinuteDOWN mapping
        this.restTimeMinuteDOWN = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_minute_down);


        // [iv/C]TextView : restTimeSecond mapping
        this.restTimeSecond = (TextView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_second);

        // [iv/C]ImageView : restTimeSecondUP mapping
        this.restTimeSecondUP = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_second_up);

        // [iv/C]ImageView : restTimeSecondDOWN mapping
        this.restTimeSecondDOWN = (ImageView) getActivity().findViewById(R.id.event_program_list_section_1_rest_time_second_down);

        // [iv/b]canInit : 초기화 할 준비가 되었다.
        this.canInit = true;

    }


    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>> canInit = " + this.canInit);

        // [iv/C]TextView : setNumber 초기값(setNumberCounter) 설정
        this.setNumber.setText(String.format("%02d", setNumberCounter));                 // error -> int 값을 R.string 에 있는 값을 가져오기 위한 id 값이므로 주의해야한다. 만약 그 int 값이 id 값이 아니라면

        // [iv/C]TextView : restTimeMinute 초기값(restTimeMinuteCounter) 설정
        this.restTimeMinute.setText(String.format("%02d", restTimeMinuteCounter));

        // [iv/C]TextView : restTimeSecond 초기값(restTimeSecondCounter) 설정
        this.restTimeSecond.setText(String.format("%02d", restTimeSecondCounter));


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-= setNumberWrapper =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        // [iv/C]MaterialCardView : setNumberWrapper click listener
        this.setNumberWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> setNumberWrapper 을 클릭했습니다.");

                // [check 1] : 클릭해서 Dialog 가 화면에 나타났나?
                if (!isClickedSetNumberPickerDialog) {

                    // [iv/b]isClickedSetNumberPickerDialog : dialog 를 클릭하여 시작되었다.
                    isClickedSetNumberPickerDialog = true;

                    // [lv/C]Bundle : dialog 에 보낼 값들을 추가한다. / setNumber
                    Bundle arguments = new Bundle();
                    // 1. restTimeMinute 의 number
                    arguments.putInt(SetNumberPickerDialog.GET_SET_NUMBER, Integer.parseInt(setNumber.getText().toString()));

                    // [lv/C]DialogFragment : setNumber 를 NumberPicker 로 선택할 수 있는 DialogFragment 를 보여준다./ 위의 Bundle 객체를 argument 에 등록 / 화면 밖 터치해도 dismiss 안 되게 막기 / 보여준다.
                    DialogFragment dialog = new SetNumberPickerDialog();
                    dialog.setArguments(arguments);
                    dialog.setCancelable(false);
                    dialog.show(fragmentManager, "SetNumberPicker");

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 다시 시작하려면 다이어로그가 종료 되어야 합니다. <=");
                } // [check 1]

            }
        });


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-= setNumber =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        // [iv/C]ImageView : setNumberUP click listener / UP
        this.setNumberUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/i]setNumberCounter : 순환하는 수의 +1 하기
                setNumberCounter = plusOneToCirculatingNumberCounter(setNumberCounter, MAX_SET_NUMBER);

                // [iv/C]TextView : 위의 값으로 setNumber 설정
                setNumber.setText(String.format("%02d", setNumberCounter));

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> plus setNumber = " + setNumberCounter);

            }
        });

        // [iv/C]ImageView : setNumberDOWN click listener / DOWN
        this.setNumberDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/i]setNumberCounter : 순환하는 수의 -1 하기
                setNumberCounter = minusOneToCirculatingNumberCounter(setNumberCounter, MAX_SET_NUMBER);

                // [iv/C]TextView : 위의 값으로 setNumber 설정
                setNumber.setText(String.format("%02d", setNumberCounter));

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> minus setNumber = " + setNumberCounter);

            }
        });


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-= restTimeWrapper =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        // [iv/C]TextView : restTimeWrapper click listener
        this.restTimeWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [check 1] : 클릭해서 RestTimePickerDialog 가 나타 났나요?
                if (!isClickedRestTimePickerDialog) {

                    // [iv/b]isClickedRestTimePickerDialog : dialog 를 클릭하여 시작되었다.
                    isClickedRestTimePickerDialog = true;

                    // [lv/C]Bundle : dialog 에 보낼 값들을 추가한다.
                    Bundle arguments = new Bundle();
                    // 1. restTimeMinute 의 number
                    arguments.putInt(RestTimePickerDialog.GET_REST_TIME_MINUTE_NUMBER, Integer.parseInt(restTimeMinute.getText().toString()));

                    // 2. restTimeSecond 의 number
                    arguments.putInt(RestTimePickerDialog.GET_REST_TIME_SECOND_NUMBER, Integer.parseInt(restTimeSecond.getText().toString()));

                    // [lv/C]DialogFragment : rest time picker dialog 를 생성 / 위의 Bundle 객체를 argument 에 등록 / 화면 밖 터치해도 dismiss 안 되게 막기 / 보여준다.
                    DialogFragment dialog = new RestTimePickerDialog();
                    dialog.setArguments(arguments);
                    dialog.setCancelable(false);
                    dialog.show(fragmentManager, "RestTimePicker");

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 다시 시작하려면 다이어로그가 종료 되어야 합니다. <=");
                } // [check 1]

            }
        });


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-= restTimeMinute =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        // [iv/C]ImageView : restTimeMinuteUP click listener / UP
        this.restTimeMinuteUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/i]restTimeMinuteCounter : 순환하는 수의 +1 하기
                restTimeMinuteCounter = plusOneToCirculatingNumberCounter(restTimeMinuteCounter, MAX_REST_TIME);

                // [iv/C]TextView : 위의 값을 restTimeMinute 에 설정
                restTimeMinute.setText(String.format("%02d", restTimeMinuteCounter));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> plus restTimeMinute = " + restTimeMinuteCounter);

            }
        });

        // [iv/C]ImageView : restTimeMinuteDOWN click listener / DOWN
        this.restTimeMinuteDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/C]restTimeMinuteCount : 순환하는 수의 -1 하기
                restTimeMinuteCounter = minusOneToCirculatingNumberCounter(restTimeMinuteCounter, MAX_REST_TIME);

                // [iv/C]TextView : 위의 값을 restTimeMinute 에 설정
                restTimeMinute.setText(String.format("%02d", restTimeMinuteCounter));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> minus restTimeMinute = " + restTimeMinuteCounter);

            }
        });


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-= restTimeSecond =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        // [iv/C]ImageView : restTimeSecondUP click listener / UP
        this.restTimeSecondUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/i]restTimeSecondCounter : 순환하는 수의 +1 하기
                restTimeSecondCounter = plusOneToCirculatingNumberCounter(restTimeSecondCounter, MAX_REST_TIME);

                // [iv/C]TextView : 위의 값을 restTimeSecond 에 설정
                restTimeSecond.setText(String.format("%02d", restTimeSecondCounter));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>> plus restTimeSecond = " + restTimeSecondCounter);

            }
        });

        // [iv/C]ImageView : restTimeSecondDOWN click listener / DOWN카운터
        this.restTimeSecondDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/C]restTimeMinuteCount : 순환하는 수의 -1 하기
                restTimeSecondCounter = minusOneToCirculatingNumberCounter(restTimeSecondCounter, MAX_REST_TIME);

                // [iv/C]TextView : 위의 값을 restTimeSecond 에 설정
                restTimeSecond.setText(String.format("%02d", restTimeSecondCounter));

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>> minus restTimeSecond = " + restTimeSecondCounter);
            }
        });
    }


    /**
     * [method] 0 부터 maxRange 를 순환하는 수를 counting 한다. UP 버튼을 누름면 해당 범위안에서 +1 높은 값이 리턴된다.
     *
     * @param circulatingNumber counter 의 현재 수
     * @param maxRange          해당 counter 의 최대 값
     * @return 해당 범위를 순환하는 수
     */
    private int plusOneToCirculatingNumberCounter(int circulatingNumber, int maxRange) {

        if ((0 <= circulatingNumber) && (circulatingNumber < maxRange)) {

            // [lv/i]circulatingNumber : +1 하기
            circulatingNumber++;

        } else if (circulatingNumber == maxRange) {

            // [lv/i]circulatingNumber : 해당 값이 maxRange 이면 0 으로 돌아간다.
            circulatingNumber = 0;
        }

        return circulatingNumber;

    } // End of method [addCounter]


    /**
     * [method] 0 부터 maxRange 를 순환하는 수를 counting 하는 것에서 DOWN 을 누르면 나오는 값을 리턴한다.
     *
     * @param circulatingNumber counter 의 현재 수
     * @param maxRange          해당 counter 의 최대 값
     * @return 해당 범위를 순환하는 수
     */
    private int minusOneToCirculatingNumberCounter(int circulatingNumber, int maxRange) {

        if ((0 < circulatingNumber) && (circulatingNumber <= maxRange)) {
            // [lv/i]circulatingNumber : -1
            circulatingNumber--;

        } else if (circulatingNumber == 0) {

            // [lv/i]circulatingNumber : 해당 값이 0 이면 maxRange 로 돌아간다.
            circulatingNumber = maxRange;
        }

        return circulatingNumber;

    } // End of method [minusOneToCounter]

}



