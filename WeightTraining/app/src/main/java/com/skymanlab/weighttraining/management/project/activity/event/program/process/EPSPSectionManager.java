package com.skymanlab.weighttraining.management.project.activity.event.program.process;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.dialog.RestTimePickerDialog;
import com.skymanlab.weighttraining.management.project.activity.SectionInitialization;
import com.skymanlab.weighttraining.management.project.activity.SectionManager;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.SessionManager;
import com.skymanlab.weighttraining.management.user.data.User;

import java.util.Timer;
import java.util.TimerTask;

public class EPSPSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEPP] EPSPSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    static Timer timer;
    static TimerTask timerTask;
    static int timeCounter;

    // constant
    private final int MAX_SET_NUMBER = 10;

    // instance variable
    private final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    // instance variable
    private Event event;
    private int eventPosition;
    private int setNumber;
    private int restTime;
    private FragmentManager fragmentManager;
    // instance variable
    private TextView eventName;
    private TextView properWeight;
    private TextView maxWeight;
    private TextView restTimeCounter;
    private TextView restTimeChange;
    private LinearLayout setStepWrapper1;
    private LinearLayout setStepWrapper2;
    private MaterialButton[] setStepButtonList;
    private ImageView[] setStepImageList;
    private MaterialButton nextEventGo;
    // instance variable
    private boolean[] isStartingStep;
    private boolean[] isCompletedStep;
    private boolean isStartingTimer;

    // constructor
    public EPSPSectionManager(Activity activity, FirebaseUser firebaseUser, Event event, int eventPosition, int setNumber, int restTime, FragmentManager fragmentManager) {
        super(activity, firebaseUser);
        this.event = event;
        this.eventPosition = eventPosition;
        this.setNumber = setNumber;
        this.restTime = restTime;
        this.fragmentManager = fragmentManager;
        this.isStartingTimer = false;

    }

    // getter, setter
    public static Timer getTimer() {
        return timer;
    }

    public TextView getRestTimeCounter() {
        return restTimeCounter;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : eventName connect
        this.eventName = (TextView) getActivity().findViewById(R.id.event_program_process_event_name);

        // [iv/C]TextView : properWeight connect
        this.properWeight = (TextView) getActivity().findViewById(R.id.event_program_process_proper_weight);

        // [iv/C]TextView : maxWeight connect
        this.maxWeight = (TextView) getActivity().findViewById(R.id.event_program_process_max_weight);

        // [iv/C]TextView : restTimeCounter connect
        this.restTimeCounter = (TextView) getActivity().findViewById(R.id.event_program_process_rest_time_counter);

        // [iv/C]TextView : restTimeChange connect
        this.restTimeChange = (TextView) getActivity().findViewById(R.id.event_program_process_rest_time_change);


        // [iv/C]LinearLayout : setStepWrapper1 connect
        this.setStepWrapper1 = (LinearLayout) getActivity().findViewById(R.id.event_program_process_set_step_wrapper_1);

        // [iv/C]LinearLayout : setStepWrapper2 connect
        this.setStepWrapper2 = (LinearLayout) getActivity().findViewById(R.id.event_program_process_set_step_wrapper_2);

        // [iv/C]MaterialButton : setStepButtonList connect
        this.setStepButtonList = new MaterialButton[MAX_SET_NUMBER];
        this.setStepButtonList[0] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_1);
        this.setStepButtonList[1] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_2);
        this.setStepButtonList[2] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_3);
        this.setStepButtonList[3] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_4);
        this.setStepButtonList[4] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_5);
        this.setStepButtonList[5] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_6);
        this.setStepButtonList[6] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_7);
        this.setStepButtonList[7] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_8);
        this.setStepButtonList[8] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_9);
        this.setStepButtonList[9] = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_set_step_10);

        // [iv/C]ImageView : setStepImageList connect
        this.setStepImageList = new ImageView[MAX_SET_NUMBER - 1];
        this.setStepImageList[0] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_1);
        this.setStepImageList[1] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_2);
        this.setStepImageList[2] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_3);
        this.setStepImageList[3] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_4);
        this.setStepImageList[4] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_5);
        this.setStepImageList[5] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_6);
        this.setStepImageList[6] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_7);
        this.setStepImageList[7] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_8);
        this.setStepImageList[8] = (ImageView) getActivity().findViewById(R.id.event_program_process_im_step_rest_time_processing_9);

        // [iv/C]MaterialButton : nextEventGo connect
        this.nextEventGo = (MaterialButton) getActivity().findViewById(R.id.event_program_process_bt_next_event);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]TextView : event 로 eventName, properWeight, maxWeight 설정
        this.eventName.setText(this.event.getEventName());
        this.properWeight.setText(DataFormatter.setWeightFormat(this.event.getProperWeight()));
        this.maxWeight.setText(DataFormatter.setWeightFormat(this.event.getMaxWeight()));

        // [iv/C]TextView : restTime 으로 restTimeCounter 값 설정
        this.restTimeCounter.setText(DataFormatter.setTimeFormat(this.restTime));

        // [method] : step 단계에 필요한 자료 초기화
        initStep();

        // [iv/C]TextView : restTimeChange click listener
        this.restTimeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Bundle : RestTimePickerDialog 에 보낼 restTime 의 minute, second 값 넣기
                Bundle arguments = new Bundle();
                arguments.putInt(RestTimePickerDialog.GET_REST_TIME_MINUTE_NUMBER, getRestTimeMinute(restTime));
                arguments.putInt(RestTimePickerDialog.GET_REST_TIME_SECOND_NUMBER, getRestTimeSecond(restTime));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>+++ 0. rest time = " + restTime);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>+++++ 1. minute time = " + getRestTimeMinute(restTime));
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>++++ 2. second tiem = " + getRestTimeSecond(restTime));

                // [lv/C]DialogFragment : RestTimePickerDialog 생성 / arguments 설정 / 화면 밖 터치 안 되도록 / dialog 화면 보여주기
                DialogFragment dialog = new RestTimePickerDialog();
                dialog.setArguments(arguments);
                dialog.setCancelable(false);
                dialog.show(fragmentManager, "RestTimePickerDialog");

            }
        });

        // [iv/C]MaterialButton : 1세트 click listener
        this.setStepButtonList[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");

                processStep(0);

            }
        });

        // [iv/C]MaterialButton : 2세트 click listener
        this.setStepButtonList[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(1);
            }
        });

        // [iv/C]MaterialButton : 3세트 click listener
        this.setStepButtonList[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(2);
            }
        });

        // [iv/C]MaterialButton : 4세트 click listener
        this.setStepButtonList[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 4세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(3);
            }
        });

        // [iv/C]MaterialButton : 5세트 click listener
        this.setStepButtonList[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 5세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(4);

            }
        });

        // [iv/C]MaterialButton : 6세트 click listener
        this.setStepButtonList[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 6세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(5);

            }
        });

        // [iv/C]MaterialButton : 7세트 click listener
        this.setStepButtonList[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 7세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(6);

            }
        });

        // [iv/C]MaterialButton : 8세트 click listener
        this.setStepButtonList[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 8세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(7);
            }
        });

        // [iv/C]MaterialButton : 9세트 click listener
        this.setStepButtonList[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 9세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(8);
            }
        });

        // [iv/C]MaterialButton : 10세트 click listener
        this.setStepButtonList[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 10세트 <<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                processStep(9);
            }
        });

        // [iv/C]MaterialButton : nextEventGo click listener
        this.nextEventGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>> nextEventGo 버튼 클릭");
                // [method] : 이 전 화면으로 이동하며 intent 에 결과값 추가
                setClickListenerOfNextEventGo();

            }
        });
    }


    /**
     * [method] step 초기 설정
     */
    private void initStep() {

        final String METHOD_NAME = "[initStep] ";

        // [iv/b]isStartingStep : MAX_SET_NUMBER 크기 만큼 배열 생성
        this.isStartingStep = new boolean[MAX_SET_NUMBER];

        // [iv/b]isCompletedStep : MAX_SET_NUMBER 크기 만큼 배열 생성
        this.isCompletedStep = new boolean[MAX_SET_NUMBER];

        // [cycle 1] : isStartingStep 과 isCompletedStep 을 false 값으로 초기 설정
        for (int index = 0; index < MAX_SET_NUMBER; index++) {

            // [iv/b]isStartingStep : false 초기 설정
            this.isStartingStep[index] = false;

            // [iv/b]isCompletedStep : false 초기 설정
            this.isCompletedStep[index] = false;

        } // [cycle 1]

        // [check 1] : 세트 수가 5개 이상일 때만 6~7 번 버튼이 있는 LinearLayout 을 보여준다.
        if (5 < setNumber) {

            // [iv/C]LinearLayout : setStepWrapper2 를 보여준다.
            this.setStepWrapper2.setVisibility(LinearLayout.VISIBLE);

        } // [check 1]

    } // End of method [initStep]


    /**
     * [method] step start, complete 구분
     */
    private void processStep(final int stepNumber) {

        final String METHOD_NAME = "[] ";

        // [check 1] : 'X 세트 시작' 상태의 버튼을 눌렀는가?
        if (!isStartingStep[stepNumber]) {

            // [iv/C]MaterialButton : 'X 세트 시작' 버튼을 클릭했으므로 'X 세트 완료' 로 변경하기
            setStepButtonList[stepNumber].setText((stepNumber + 1) + " 세트 완료");

            // [iv/b]isStartingStep : 'X 세트 시작' 버튼을 클릭했으므로 'X 세트 완료' 로 변경되었다. 즉, X 세트가 시작했으므로 true 이다.
            isStartingStep[stepNumber] = true;

            // [iv/C]TextView : restTimeCounter 를 초기화하기
            this.restTimeCounter.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextBlack));
            this.restTimeCounter.setText(DataFormatter.setTimeFormat(0));

            // [iv/C]TextView : restTimeChange 가 작동 안 하도록(RestTimePickerDialog 가 안 나타나도록)
            this.restTimeChange.setVisibility(TextView.GONE);

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 1 세트를 시작 했으므로 완료하면 클릭하세요. <=");

            // '1 세트 완료' 로 변경된 버튼을 다시 클릭하였을 때, 버튼 색상을 GRAY 색으로 변경하고  -> Timer 가 작동하고 -> Timer 가 완료되면 -> isCompletedStep = true -> setStepImageList 을 보여주고 -> 2세트 버튼을 보여준다.

            // [check 2] : 'X 세트 완료' 상태의 버튼을 눌렀는가?
            if (!isCompletedStep[stepNumber]) {

                // [iv/b]isCompletedStep : 'X 세트 완료' 버튼을 클릭했으므로 Timer 가 종료 될때까지 기다려야 하므로 true 변경
                isCompletedStep[stepNumber] = true;

                // [iv/C]MaterialButton : 'X 세트 완료' 버튼을 클릭 했으므로 -> 색을 변경
                setStepButtonList[stepNumber].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getActivity(), R.color.colorBackgroundGray)));

                // [check 3] : 마지막 세트이면
                if (stepNumber == (setNumber - 1)) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : 마지막 세트입니다. Timer 는 작동하지 않습니다. <=");
                    // 해당 버튼의 Timer 가 작동 안하도록

                    // [iv/b]isStartingTimer : true 로 변경 -> 타이머가 작동 안되도록 확고히 하는 차원
                    isStartingTimer = true;

                    // [iv/C]TextView : END 라는 글자 띄워주기 / 색상 원래대로 바꾸기
                    restTimeCounter.setText("-END-");

                    // [iv/C]MaterialButton : nextEventGo 버튼 나타내기
                    nextEventGo.setVisibility(MaterialCardView.VISIBLE);

                } else {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/false : Timer 가 작동합니다. <=");

                    // [check 4] : 타이머가 시작하지 않았을 때만
                    if (!isStartingTimer) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>> time start >>>>>>>>>>>>>>>>");
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. isStringTimer = " + isStartingTimer);
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. timeCounter = " + timeCounter);

                        // [lv/b]isStringTimer : timer 가 시작 중이므로 true 로 변경한다.
                        isStartingTimer = true;

                        // [iv/i]timeCounter : 시작 시간을 restTime 으로 맞추기
                        timeCounter = restTime;

                        // [iv/C]TextView : 처음 시작 시간은 검정색 그리고 restTime 으로
                        restTimeCounter.setText(DataFormatter.setTimeFormat(restTime));

                        // [iv/C]Timer : Timer 객체 생성
                        timer = new Timer();

                        // [iv/C]TimerTask : 1초 마다 restTimeCounter 에 시간을 표시하고, timeCounter 가 restTime 과 같아질 때 종료하는 일을 하는 TimerTask 을 구현한다.
                        timerTask = new TimerTask() {

                            @Override
                            public void run() {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "현재 Time = " + DataFormatter.setTimeFormat(timeCounter));


                                // [check 5] : 0 초까지만
                                if (0 <= timeCounter) {

                                    // [iv/C]TextView : timeCounter 을 시간 포맷으로 화면에 출력하기
                                    restTimeCounter.setText(DataFormatter.setTimeFormat(timeCounter));

                                } // [check 5]

                                // [check 6] : 5초 일 때와 0초가 지났을 때
                                if (timeCounter == 5000
                                        || timeCounter == 4000
                                        || timeCounter == 3000
                                        || timeCounter == 2000
                                        || timeCounter == 1000) {

                                    // 5초 일때

                                    // [iv/C]TextView : 해당 글자색을 빨간색으로 변경한다.
                                    restTimeCounter.setTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_red_dark));

                                } else if (timeCounter < 0) {

                                    // 0 초가 지났을 때

                                    // [iv/b]isStartingTimer : timer 를 다시 시작할 수 있도록 false 로 변경
                                    isStartingTimer = false;

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>END>>>>>>>>>>>>>>>>");

                                    // [error] : main thread 가 아닌 thread 에서 UI 를 수정하면 에러 발생 -> Activity 의 runOnUiThread 를 생성하여 UI 수정 작업을 하면 된다.
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> stepNumber = " + stepNumber);

                                            // 1세트(0) ~ 9세트(8)
                                            // [iv/C]ImageView : 1 세트가 완료 되었으므로 화살표 를 보여준다.
                                            setStepImageList[stepNumber].setVisibility(ImageView.VISIBLE);

                                            // [iv/C]MaterialButton : 다음 세트(X+1 세트) 의 버튼을 보여준다.
                                            setStepButtonList[stepNumber + 1].setVisibility(MaterialButton.VISIBLE);

                                            // [iv/C]TextView : END 라는 글자 띄워주기 / 색상 원래대로 바꾸기
                                            restTimeCounter.setText("-" + DataFormatter.setTimeFormat(0) + "-");

                                            // [iv/C]TextView : restTimeChange 를 다시 쓸 수 있도록
                                            restTimeChange.setVisibility(TextView.VISIBLE);

                                            // [method] : 진동
                                            vibratorSetEnd();

                                        }
                                    });

                                    // [iv/C]Timer : 해당 Timer 종료
                                    timer.cancel();

                                } // [check 6]

                                // [iv/i]timeCounter : 1 초 감소한다.
                                timeCounter -= 1000;
                            }
                        };

                        // [iv/C]Timer : timerTask 객체를 1초 뒤에 1초 간격으로 실행한다.
                        timer.schedule(timerTask, 1000, 1000);

                    } else {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_4/false : 이미 Timer 가 시작 중 이야! <=");
                    } // [check 4]

                } // [check 3]

            } else {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : " + (stepNumber + 1) + " 세트의 완료 버튼을 클릭했으므로, Timer 가 시작했습니다. <=");
            } // [check 2]

        } // [check 1]

    } // End of method [divideStep]


    /**
     * [method] 진동
     */
    private void vibratorSetEnd() {

        // [lv/l]pattern : 진동 패턴
        long[] pattern = {100, 300, 100, 700, 300, 1000};

        // [iv/C]Vibrator : 진동 시작
        vibrator.vibrate(pattern, -1);
    }

    /**
     * [method] nextEventGo click listener
     */
    private void setClickListenerOfNextEventGo() {

        // [lv/C]Intent : 전 Activity 에 결과값을 보내기 위해 빈 Intent 생성
        Intent intent = new Intent();

        // [lv/C]Intent : 결과 추가하기
        intent.putExtra("result", event.getEventName() + "/success");
        SessionManager.setEventPositionInIntent(intent, this.eventPosition);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();

    } // End of method [setClickListenerOfNextEventGo]


    /**
     * [method] restTime 의 '분' 가져오기
     */
    private int getRestTimeMinute(int restTime) {

        return (restTime / (1000 * 60)) % 60;
    } // End of method [getRestTimeMinute]


    /**
     * [method] restTime 의 '초' 가져오기
     */
    private int getRestTimeSecond(int restTime) {

        return (restTime / 1000) % 60;
    } // End of method [getRestTimeSecond]

}
