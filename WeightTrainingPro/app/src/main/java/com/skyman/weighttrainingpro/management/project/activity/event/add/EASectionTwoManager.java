package com.skyman.weighttrainingpro.management.project.activity.event.add;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.skyman.weighttrainingpro.EventListActivity;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.event.listview.EventItemLvManager;
import com.skyman.weighttrainingpro.management.project.activity.SectionManager;
import com.skyman.weighttrainingpro.management.project.activity.SectionInitialization;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

public class EASectionTwoManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEA]_EASectionTwoManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private LinearLayout titleWrapper2;
    private TextView display;
    private ListView eventListLv;

    // instance variable
    private EventDbManager eventDbManager;

    // constructor
    public EASectionTwoManager(Activity activity, User user, MuscleArea muscleArea) {
        super(activity, user, muscleArea);
    }

    // setter
    public void setEventDbManager(EventDbManager eventDbManager) {
        this.eventDbManager = eventDbManager;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]LinearLayout : titleWrapper2 mapping
        this.titleWrapper2 = (LinearLayout) getActivity().findViewById(R.id.event_add_section_2_title_wrapper);

        // [iv/C]TextView : display mapping
        this.display = (TextView) getActivity().findViewById(R.id.event_add_section_2_bt_display);

        // [iv/C]ListView : eventListLv mapping
        this.eventListLv = (ListView) getActivity().findViewById(R.id.event_add_section_2_lv_event_item);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionTwo] ";

        // [method] : muscleArea 값에 따라 sectionOneTitleWrapper, sectionOneAddWrapper, sectionTwoTitleWrapper 의 색 바꾸기
        setBackgroundWithMuscleArea(getMuscleArea());

        // [method] : eventItemLv 에 eventArrayList 연결하여 화면에 보여주기
        setListViewOfEventListLv();

        // [iv/C]TextView :  click listener
        this.display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 팝업메뉴 보여주기
                setClickListenerOfDisplay(v);
            }
        });

    }


    /**
     * [method] [set] muscleArea 의 값에 따라서 titleWrapper, addWrapper 의 색을 변경한다.
     */
    private void setBackgroundWithMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[setBackgroundWithMuscleArea] ";

        // [check 1] : muscleArea 가 뭐냐?
        switch (muscleArea) {
            case CHEST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/chest 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper2 의 색을 R.color.colorBackgroundFirst 으로 설정
                this.titleWrapper2.setBackgroundResource(R.color.colorBackgroundFirst);
                break;

            case SHOULDER:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/shoulder 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper2 의 색을 R.color.colorBackgroundSecond 으로 설정
                this.titleWrapper2.setBackgroundResource(R.color.colorBackgroundSecond);
                break;

            case LAT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/lat 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper2 의 색을 R.color.colorBackgroundThird 으로 설정
                this.titleWrapper2.setBackgroundResource(R.color.colorBackgroundThird);
                break;

            case LEG:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/leg 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper2 의 색을 R.color.colorBackgroundFourth 으로 설정
                this.titleWrapper2.setBackgroundResource(R.color.colorBackgroundFourth);
                break;

            case ARM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/arm 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper2 의 색을 R.color.colorBackgroundFifth 으로 설정
                this.titleWrapper2.setBackgroundResource(R.color.colorBackgroundFifth);
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 범위 밖의 값이 입력되었습니다. <=");
                break;
        } // [check 1]

    } // End of method [setBackgroundWithMuscleArea]


    /**
     * [method] eventArrayList 를 sectionTwoEventItemLv 에 화면으로 보여주기
     */
    private void setListViewOfEventListLv() {

        // [lv/C]EventItemLvManager : ListView 에 eventArrayList 를 연결하는 메니저 생성
        EventItemLvManager eventItemLvManager = new EventItemLvManager(getActivity(), this.eventListLv, getUser(), getMuscleArea(), this.eventDbManager);

        // [lv/C]EventItemLvManager : 해당 ListView 에 eventArrayList 연결
        eventItemLvManager.setListView();

    } // End of method [setListViewOfEventListLv]


    /**
     * [method] display click listener 설정
     */
    private void setClickListenerOfDisplay(View view) {

        final String METHOD_NAME = "[setClickListenerOfDisplay] ";

        // [lv/C]PopupMenu : 팝업메뉴를 노출하기위한 객체 생성 // view : 팝업 메뉴를 부른 view 가 뭐임?
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);

        // [lv/C]PopupMenu : 클릭 이벤트 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // [check 1] : 선택한 항목의 아이디가 뭐냐?
                switch (item.getItemId()) {
                    case R.id.event_add_section_2_popup_update:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/갱신하기 : 해당 내용을 갱신합니다. <=");

                        // [method] : eventItemLv 에 eventArrayList 연결하여 화면에 보여주기
                        setListViewOfEventListLv();
                        break;
                    case R.id.event_add_section_2_popup_see_more:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/자세히 보기 : 더 자세히 보기 위해 EventListActivity 로 이동합니다. <=");

                        // [lv/C]Intent : 해당 activity 로 이동하기 위한 객체 생성
                        Intent intent = new Intent(getActivity(), EventListActivity.class);

                        // [lv/C]SessionManager : intent 에 user 추가하기
                        SessionManager.setUserInIntent(intent, getUser());

                        // [lv/C]SessionManager : intent 에 muscleArea 추가하기
                        SessionManager.setMuscleAreaInIntent(intent, getMuscleArea());

                        // [method] : 설정이 끝난 intent 를 다음 Activity 로 전송하며 이동
                        getActivity().startActivity(intent);

                        break;
                    default:
                        return false;
                } // [check 1]

                return true;
            }
        });

        // [lv/C]PopupMenu : R.menu.section_1_popup_menu 와 연결하기
        popupMenu.inflate(R.menu.event_add_section_2);

        // [lv/C]PopupMenu : 설정된 팝업메뉴 보여주기
        popupMenu.show();

    } // End of method [setClickListenerOfDisplay]


}
