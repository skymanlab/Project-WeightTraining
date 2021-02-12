package com.skymanlab.weighttraining.management.project.activity.event.list;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.listview.EventLvManager;
import com.skymanlab.weighttraining.management.project.activity.SectionInitialization;
import com.skymanlab.weighttraining.management.project.activity.SectionManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;


public class ELSectionManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEL] ELSectionManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentManager fragmentManager;

    // instance variable
    private LinearLayout titleWrapper;
    private ListView eventListLv;
    private ProgressBar progressBar;

    // constructor
    public ELSectionManager(Activity activity, FirebaseUser firebaseUser, MuscleArea muscleArea, FragmentManager fragmentManager) {
        super(activity, firebaseUser, muscleArea);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void connectWidget() {

        // [iv/C]LinearLayout : titleWrapper connect
        this.titleWrapper = (LinearLayout) getActivity().findViewById(R.id.event_list_section_1_title_wrapper);

        // [iv/C]ListView : eventListLv connect
        this.eventListLv = (ListView) getActivity().findViewById(R.id.event_list_section_1_lv_event_item);

        // [iv/C]ProgressBar : progressBar connect
        this.progressBar = (ProgressBar) getActivity().findViewById(R.id.event_list_section_1_progressbar);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        // [method] : muscleArea 값에 따라 sectionOneTitleWrapper 색 변경하기
        setBackgroundWithMuscleArea(getMuscleArea());

        // [method] : eventItemLv 에 eventArrayList 연결하여 화면에 보여주기
        setListViewOfEventArrayList();

    }


    /**
     * [method] [set] muscleArea 의 값에 따라서 sectionOneTitleWrapper 의 색을 변경한다.
     */
    private void setBackgroundWithMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[setBackgroundWithMuscleArea] ";

        // [check 1] : muscleArea 가 뭐냐?
        switch (muscleArea) {
            case CHEST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/chest 가 입려되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFirst 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFirst);
                break;

            case SHOULDER:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/shoulder 가 입려되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundSecond 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundSecond);
                break;

            case LAT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/lat 가 입려되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundThird 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundThird);
                break;

            case LOWER_BODY:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/leg 가 입려되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFourth 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFourth);
                break;

            case ARM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/arm 가 입려되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFifth 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFifth);
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 범위 밖의 값이 입력되었습니다. <=");
                break;
        } // [check 1]

    } // End of method [setBackgroundWithMuscleArea]


    /**
     * [method] eventArrayList 를 sectionTwoEventItemLv 에 화면으로 보여주기
     */
    private void setListViewOfEventArrayList() {

        // [lv/C]EventItemLvManager : ListView 에 eventArrayList 를 연결하는 메니저 생성
        EventLvManager lvManager = new EventLvManager(getActivity(), fragmentManager, eventListLv, progressBar, getFirebaseUser().getUid(), getMuscleArea());
        lvManager.setListView();

    } // End of method [setListViewOfEventArrayList]

}
