package com.skymanlab.weighttraining.management.project.activity.event.program;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.activity.SectionInitialization;
import com.skymanlab.weighttraining.management.project.activity.SectionManager;

public class EPSectionOneManager  extends EPSectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEP] EPSectionOneManager";       // EventProgramActivity Section One Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable : section
    private RadioGroup programMakerType;
    private ImageView info;
    private LinearLayout randomWrapper;                     // section wrapper
    private LinearLayout allRandomWrapper;                  // section wrapper
    private LinearLayout directWrapper;                     // section wrapper

    // constructor
    public EPSectionOneManager(Activity activity) {
        super(activity);
    }

    // getter
    public LinearLayout getRandomWrapper() {
        return randomWrapper;
    }

    @Override
    public void connectWidget() {

        // [iv/C]RadioGroup : programMakerType connect
        this.programMakerType = (RadioGroup) super.getActivity().findViewById(R.id.event_program_section_1_program_type);

        // [iv/C]ImageView : info connect
        this.info = (ImageView) super.getActivity().findViewById(R.id.event_program_section_1_info);

        // [iv/C]LinearLayout : randomWrapper connect
        this.randomWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_random_wrapper);                              // section wrapper

        // [iv/C]LinearLayout : allRandomWrapper connect
        this.allRandomWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_all_random_wrapper);                       // section wrapper

        // [iv/C]LinearLayout : directWrapper connect
        this.directWrapper = (LinearLayout) super.getActivity().findViewById(R.id.event_program_section_1_direct_wrapper);                              // section wrapper

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> sectionType = " + getSectionType());

        // [check 1] : sectionType 이 뭐냐?
        if (super.getSectionType() == EPSectionManager.NOT_ZERO_TYPE_SECTION) {

            // [method] : eventArrayList 의 size 가 0 일때의 widget 초기화
            initWidgetWhenSizeIsNotZero();

        } else if (super.getSectionType() == EPSectionManager.ZERO_TYPE_SECTION) {

            // [method] : eventArrayList 의 size 가 0 일때의 widget 초기화
            initWidgetWhenSizeIsZero();

        } // [check 1]


    } // End of method [initWidget]


    @Override
    void initWidgetWhenSizeIsNotZero() {

        final String METHOD_NAME = "[initWidgetWhenSizeIsNotZero] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1 / SECTION_NOT_ZERO_TYPE <<=======================");

        // [iv/C]RadioGroup : programMakerType check change listener
        this.programMakerType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // [check 1] : radio button 의 id 가 뭐냐?
                switch (checkedId) {
                    case R.id.event_program_section_1_program_type_each_random:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/EACH_RANDOM : 각각의 그룹에서 랜덤으로 무작위로 선택하고, 각각의 무작위로 선택한 event 데이터를 합칩니다.  <=");
                        // [iv/C]LinearLayout : randomWrapper 보여주기
                        randomWrapper.setVisibility(LinearLayout.VISIBLE);

                        // [iv/C]LinearLayout : allRandomWrapper 없애기
                        allRandomWrapper.setVisibility(LinearLayout.GONE);

                        // [iv/C]LinearLayout : directWrapper 없애기
                        directWrapper.setVisibility(LinearLayout.GONE);

                        break;

                    case R.id.event_program_section_1_program_type_all_random:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/All_RANDOM : 전체 그룹에서 무작위로 선택합니다. <=");
                        // [iv/C]LinearLayout : randomWrapper 없애기
                        randomWrapper.setVisibility(LinearLayout.GONE);

                        // [iv/C]LinearLayout : allRandomWrapper 보여주기
                        allRandomWrapper.setVisibility(LinearLayout.VISIBLE);

                        // [iv/C]LinearLayout : directWrapper 없애기
                        directWrapper.setVisibility(LinearLayout.GONE);

                        break;

                    case R.id.event_program_section_1_program_type_direct:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/Direct : 직접 <=");
                        // [iv/C]LinearLayout : randomWrapper 없애기
                        randomWrapper.setVisibility(LinearLayout.GONE);

                        // [iv/C]LinearLayout : allRandomWrapper 없애기
                        allRandomWrapper.setVisibility(LinearLayout.GONE);

                        // [iv/C]LinearLayout : directWrapper 보여주기
                        directWrapper.setVisibility(LinearLayout.VISIBLE);
                        break;

                    default:
                        break;
                }

            }
        });

        // [iv/C]ImageView : info click listener
        this.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]AlertDialog : Builder 객체 생성 및 초기화
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.event_program_section_1_alert_info_message).show();

            }
        });
    }


    @Override
    void initWidgetWhenSizeIsZero() {

        final String METHOD_NAME = "[initWidgetWhenSizeIsNotZero] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1 / SECTION_ZERO_TYPE <<=======================");

        initWidgetWhenSizeIsNotZero();
    }


}
