package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
    public static final int STEP_EIGHT = 8;

    // constant
    private static final int SELECTED_NUMBER_1 = R.drawable.step_display_middle_number_1;
    private static final int SELECTED_NUMBER_2 = R.drawable.step_display_middle_number_2;
    private static final int SELECTED_NUMBER_3 = R.drawable.step_display_middle_number_3;
    private static final int SELECTED_NUMBER_4 = R.drawable.step_display_middle_number_4;
    private static final int SELECTED_NUMBER_5 = R.drawable.step_display_middle_number_5;
    private static final int SELECTED_NUMBER_6 = R.drawable.step_display_middle_number_6;
    private static final int SELECTED_NUMBER_7 = R.drawable.step_display_middle_number_7;
    private static final int SELECTED_NUMBER_8 = R.drawable.step_display_middle_number_8;

    // constant
    private static final int NO_SELECTED_NUMBER_1 = R.drawable.step_display_side_number_1;
    private static final int NO_SELECTED_NUMBER_2 = R.drawable.step_display_side_number_2;
    private static final int NO_SELECTED_NUMBER_3 = R.drawable.step_display_side_number_3;
    private static final int NO_SELECTED_NUMBER_4 = R.drawable.step_display_side_number_4;
    private static final int NO_SELECTED_NUMBER_5 = R.drawable.step_display_side_number_5;
    private static final int NO_SELECTED_NUMBER_6 = R.drawable.step_display_side_number_6;
    private static final int NO_SELECTED_NUMBER_7 = R.drawable.step_display_side_number_7;
    private static final int NO_SELECTED_NUMBER_8 = R.drawable.step_display_side_number_8;

    // instance variable
    private int step;

    // instance variable : step counter
    private ImageView firstNumber;
    private ImageView secondNumber;
    private ImageView thirdNumber;
    private ImageView firstBar;
    private ImageView secondBar;

    // constructor
    public MakerStepManager(Fragment fragment, View view, int step) {
        super(fragment, view);
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
                // [method] maker step 1 로 단계 설정
                initStepOne();
                break;
            case STEP_TWO:
                // [method] maker step 2 로 단계 설정
                initStepTwo();
                break;
            case STEP_THREE:
                // [method] maker step 3 로 단계 설정
                initStepThree();
                break;
            case STEP_FOUR:
                // [method] maker step 4 로 단계 설정
                initStepFour();
                break;
            case STEP_FIVE:
                // [method] maker step 5 로 단계 설정
                initStepFive();
                break;
            case STEP_SIX:
                // [method] maker step 6 로 단계 설정
                initStepSix();
                break;
            case STEP_SEVEN:
                // [method] maker step 7 로 단계 설정
                initStepSven();
                break;
            case STEP_EIGHT:
                // [method] maker step 7 로 단계 설정
                initStepSven();
                break;
        }

    }

    /**
     * [method] step 1 widget init
     */
    private void initStepOne() {

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [firstNumber] VISIBLE -> INVISIBLE
        this.firstNumber.setVisibility(ImageView.INVISIBLE);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_1
        this.secondNumber.setImageResource(SELECTED_NUMBER_1);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_2
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_2);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= bar =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [firstBar] VISIBLE -> INVISIBLE
        this.firstBar.setVisibility(ImageView.INVISIBLE);

    } // End of method [initStepOne]

    /**
     * [method] step 2 widget init
     */
    private void initStepTwo() {

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_5
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_5);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_6
        this.secondNumber.setImageResource(SELECTED_NUMBER_6);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_7
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_7);

    } // End of method [initStepSix]


    /**
     * [method] step 7 widget init
     */
    private void initStepSven() {

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_6
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_6);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_7
        this.secondNumber.setImageResource(SELECTED_NUMBER_7);

        // [iv/C]ImageView : thirdNumber -> NO_SELECTED_NUMBER_8
        this.thirdNumber.setImageResource(NO_SELECTED_NUMBER_8);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= bar =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]ImageView : secondBar -> INVISIBLE
        this.secondBar.setVisibility(ImageView.INVISIBLE);

    } // End of method [initStepSix]


    /**
     * [method] step 8 widget init
     */
    private void initStepEight() {

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= number =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]ImageView : firstNumber -> NO_SELECTED_NUMBER_7
        this.firstNumber.setImageResource(NO_SELECTED_NUMBER_7);

        // [iv/C]ImageView : secondNumber -> SELECTED_NUMBER_8
        this.secondNumber.setImageResource(SELECTED_NUMBER_8);

        // [iv/C]ImageView : thirdNumber -> INVISIBLE
        this.thirdNumber.setVisibility(ImageView.INVISIBLE);

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= bar =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]ImageView : secondBar -> INVISIBLE
        this.secondBar.setVisibility(ImageView.INVISIBLE);

    } // End of method [initStepSix]

}