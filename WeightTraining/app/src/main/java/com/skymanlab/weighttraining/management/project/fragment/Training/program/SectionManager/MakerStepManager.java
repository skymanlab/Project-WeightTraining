package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class MakerStepManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStepManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    public static final int MAKER_TYPE_DIRECT_SELECTION = 0;
    public static final int MAKER_TYPE_EACH_GROUP_RANDOM = 1;
    public static final int MAKER_TYPE_ALL_GROUP_RANDOM = 2;

    // constant
    public static final int STEP_ONE = 1;
    public static final int STEP_TWO = 2;
    public static final int STEP_THREE = 3;
    public static final int STEP_FOUR = 4;
    public static final int STEP_FIVE = 5;
    public static final int STEP_SIX = 6;
    public static final int STEP_SEVEN = 7;

    // constant
    private static final int SELECTED_NUMBER_1 = R.drawable.primary_number_1;
    private static final int SELECTED_NUMBER_2 = R.drawable.primary_number_2;
    private static final int SELECTED_NUMBER_3 = R.drawable.primary_number_3;
    private static final int SELECTED_NUMBER_4 = R.drawable.primary_number_4;
    private static final int SELECTED_NUMBER_5 = R.drawable.primary_number_5;
    private static final int SELECTED_NUMBER_6 = R.drawable.primary_number_6;
    private static final int SELECTED_NUMBER_7 = R.drawable.primary_number_7;

    // constant
    private static final int NO_SELECTED_NUMBER_1 = R.drawable.third_number_1;
    private static final int NO_SELECTED_NUMBER_2 = R.drawable.third_number_2;
    private static final int NO_SELECTED_NUMBER_3 = R.drawable.third_number_3;
    private static final int NO_SELECTED_NUMBER_4 = R.drawable.third_number_4;
    private static final int NO_SELECTED_NUMBER_5 = R.drawable.third_number_5;
    private static final int NO_SELECTED_NUMBER_6 = R.drawable.third_number_6;
    private static final int NO_SELECTED_NUMBER_7 = R.drawable.third_number_7;

    // instance variable : step counter
    private ImageView firstNumber;
    private ImageView secondNumber;
    private ImageView thirdNumber;
    private ImageView firstBar;
    private ImageView secondBar;

    // instance variable : step direction selector
    private Button previous;
    private Button next;

    // instance variable
    private int step;

    // instance variable
    private OnNextClickListener nextClickListener;
    private OnPreviousClickListener previousClickListener;

    // constructor
    public MakerStepManager(View view, FragmentManager fragmentManager, int step) {
        super(view, fragmentManager);
        this.step = step;
    }

    // setter
    public void setPreviousClickListener(OnPreviousClickListener previousClickListener) {
        this.previousClickListener = previousClickListener;
    }

    public void setNextClickListener(OnNextClickListener nextClickListener) {
        this.nextClickListener = nextClickListener;
    }

    @Override
    public void connectWidget() {

        // [iv/C]ImageView : firstNumber connect
        this.firstNumber = (ImageView) getView().findViewById(R.id.include_maker_step_display_first_number);

        // [iv/C]ImageView : secondNumber connect
        this.secondNumber = (ImageView) getView().findViewById(R.id.include_maker_step_display_second_number);

        // [iv/C]ImageView : thirdNumber connect
        this.thirdNumber = (ImageView) getView().findViewById(R.id.include_maker_step_display_third_number);

        // [iv/C]ImageView : firstBar connect
        this.firstBar = (ImageView) getView().findViewById(R.id.include_maker_step_display_first_bar);

        // [iv/C]ImageView : secondBar connect
        this.secondBar = (ImageView) getView().findViewById(R.id.include_maker_step_display_second_bar);

        // [iv/C]Button : previous connect
        this.previous = (Button) getView().findViewById(R.id.include_maker_step_selector_previous);

        // [iv/C]Button : next connect
        this.next = (Button) getView().findViewById(R.id.include_maker_step_selector_next);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]Button : previous 공통적으로 이전 fragment 로 이동하므로
        this.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [check 1] : click listener 가?
                if (previousClickListener != null) {

                    // [lv/C]AlertDialog : listener 를 통해 결과로 AlertDialog 객체를 가져온다.
                    AlertDialog dialog = previousClickListener.setClickListenerOfPrevious();

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> Dialog = " + dialog);
                    // [check 2] : 위의 dialog 의 객체 유무를 통해 결절하기
                    if (dialog != null) {

                        // [lv/C]AlertDialog : 해당 객체로 만들어진 dialog 를 표시하기
                        dialog.show();

                    } else {

                        // [lv/C]FragmentManager : fragmentManager 를 통해서 stack 에서 pop 하여 이전 Fragment 로 이동
                        getFragmentManager().popBackStack();

                    } // [check 2]

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> 리스터 생성 안됨");
                } // [check 1]

            }
        });

        // [iv/C]Button : next click listener
        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 각 단계마다 다른 설정이 필요하면 각 단계를 초기화하는 method 에서 설정을 해준다.
                // [iv/C]OnNextClickListener : 외부에서 만들어진 OnNextClickListener 를 통해 해당 버튼의 과정을 진행하는 코드를 수행한다.
                nextClickListener.setClickListenerOfNext();
            }
        });

        // [check 1] : 몇 번째 단계인가요?
        switch (this.step) {
            case STEP_ONE:
                // [method] : step 1 의 widget 초기화
                initStepOne();
                break;
            case STEP_TWO:
                // [method] : step 2 의 widget 초기화
                initStepTwo();
                break;
            case STEP_THREE:
                // [method] : step 3 의 widget 초기화
                initStepThree();
                break;
            case STEP_FOUR:
                // [method] : step 4 의 widget 초기화
                initStepFour();
                break;
            case STEP_FIVE:
                // [method] : step 5 의 widget 초기화
                initStepFive();
                break;
            case STEP_SIX:
                // [method] : step 6 의 widget 초기화
                initStepSix();
                break;
        }

    }

    /**
     * [method] step 1 widget init
     */
    private void initStepOne() {

        // [iv/C]ImageView : firstNumber -> INVISIBLE
        this.firstNumber.setVisibility(ImageView.INVISIBLE);

        // [iv/C]ImageView : firstBar -> INVISIBLE
        this.firstBar.setVisibility(ImageView.INVISIBLE);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_1
        this.secondNumber.setImageResource(SELECTED_NUMBER_1);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_2
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_2);

        // [iv/C]Button : previous -> INVISIBLE, enabled 를 false 로
        this.previous.setVisibility(Button.INVISIBLE);
        this.previous.setEnabled(false);

        // [iv/C]Button : next -> INVISIBLE, enabled 를 false 로
        this.next.setVisibility(Button.INVISIBLE);
        this.next.setEnabled(false);

    } // End of method [initStepOne]

    /**
     * [method] step 2 widget init
     */
    private void initStepTwo() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_1
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_1);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_2
        this.secondNumber.setImageResource(SELECTED_NUMBER_2);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_3
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_3);

    } // End of method [initStepTwo]

    /**
     * [method] step 3 widget init
     */
    private void initStepThree() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_2
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_2);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_3
        this.secondNumber.setImageResource(SELECTED_NUMBER_3);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_4
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_4);

    } // End of method [initStepThree]

    /**
     * [method] step 4 widget init
     */
    private void initStepFour() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_3
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_3);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_4
        this.secondNumber.setImageResource(SELECTED_NUMBER_4);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_5
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_5);

    } // End of method [initStepFour]

    /**
     * [method] step 5 widget init
     */
    private void initStepFive() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_4
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_4);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_5
        this.secondNumber.setImageResource(SELECTED_NUMBER_5);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_6
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_6);

    } // End of method [initStepFive]

    /**
     * [method] step 6 widget init
     */
    private void initStepSix() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_1
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_5);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_2
        this.secondNumber.setImageResource(SELECTED_NUMBER_6);

        // [iv/C]ImageView : thirdNumber -> INVISIBLE
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_7);

    } // End of method [initStepSix]


    /**
     * [method] step 7 widget init
     */
    private void initStepSven() {

        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_1
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_6);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_2
        this.secondNumber.setImageResource(SELECTED_NUMBER_7);

        // [iv/C]ImageView : secondBar -> INVISIBLE
        this.secondBar.setVisibility(ImageView.INVISIBLE);

        // [iv/C]ImageView : thirdNumber -> INVISIBLE
        this.thirdNumber.setVisibility(ImageView.INVISIBLE);

    } // End of method [initStepSix]

    // interface
    public interface OnNextClickListener {
        void setClickListenerOfNext();
    }

    // interface
    public interface OnPreviousClickListener {
        AlertDialog setClickListenerOfPrevious();
    }

}