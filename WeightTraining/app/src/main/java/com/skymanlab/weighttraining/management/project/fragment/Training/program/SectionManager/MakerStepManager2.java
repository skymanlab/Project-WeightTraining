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

public class MakerStepManager2 extends FragmentSectionManager implements FragmentSectionInitializable {

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
    private static final int SELECTED_NUMBER_1 = R.drawable.step_display_middle_number_1;
    private static final int SELECTED_NUMBER_2 = R.drawable.step_display_middle_number_2;
    private static final int SELECTED_NUMBER_3 = R.drawable.step_display_middle_number_3;
    private static final int SELECTED_NUMBER_4 = R.drawable.step_display_middle_number_4;
    private static final int SELECTED_NUMBER_5 = R.drawable.step_display_middle_number_5;
    private static final int SELECTED_NUMBER_6 = R.drawable.step_display_middle_number_6;
    private static final int SELECTED_NUMBER_7 = R.drawable.step_display_middle_number_7;

    // constant
    private static final int NO_SELECTED_NUMBER_1 = R.drawable.step_display_side_number_1;
    private static final int NO_SELECTED_NUMBER_2 = R.drawable.step_display_side_number_2;
    private static final int NO_SELECTED_NUMBER_3 = R.drawable.step_display_side_number_3;
    private static final int NO_SELECTED_NUMBER_4 = R.drawable.step_display_side_number_4;
    private static final int NO_SELECTED_NUMBER_5 = R.drawable.step_display_side_number_5;
    private static final int NO_SELECTED_NUMBER_6 = R.drawable.step_display_side_number_6;
    private static final int NO_SELECTED_NUMBER_7 = R.drawable.step_display_side_number_7;

    // instance variable : step counter
    private ImageView firstNumber;
    private ImageView secondNumber;
    private ImageView thirdNumber;
    private ImageView firstBar;
    private ImageView secondBar;

    // instance variable
    private int step;

    // constructor
    public MakerStepManager2(View view, FragmentManager fragmentManager, int step) {
        super(view, fragmentManager);
        this.step = step;
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

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

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

        // [firstNumber] VISIBLE -> INVISIBLE
        this.firstNumber.setVisibility(ImageView.INVISIBLE);

        // [firstBar] VISIBLE -> INVISIBLE
        this.firstBar.setVisibility(ImageView.INVISIBLE);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_1
        this.secondNumber.setImageResource(SELECTED_NUMBER_1);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_2
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_2);


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


}