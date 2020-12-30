package com.skyman.weighttrainingpro.management.project.activity.event.program;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.program.data.GroupingEventData;
import com.skyman.weighttrainingpro.management.event.program.util.DirectEventSelectionUtil;
import com.skyman.weighttrainingpro.management.project.activity.SectionInitialization;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.type.GroupType;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EPSectionOneDirectManager extends EPSectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEP]_EPSectionOneDirectManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private GroupingEventData groupingEventData;

    // instance variable
    private TextView directTitle;                           // title
    private MaterialCardView directAGroupWrapper;
    private MaterialCardView directBGroupWrapper;
    private MaterialCardView directCGroupWrapper;
    private MaterialCardView directDGroupWrapper;
    private MaterialCardView directEGroupWrapper;
    private LinearLayout directAGroupEventItemAdder;
    private LinearLayout directBGroupEventItemAdder;
    private LinearLayout directCGroupEventItemAdder;
    private LinearLayout directDGroupEventItemAdder;
    private LinearLayout directEGroupEventItemAdder;
    private View directDivider;
    private LinearLayout directProgramWrapper;              // program button wrapper
    private Button directProgram;                           // program button
    private int[] directAGroupEventItemIdList;
    private int[] directBGroupEventItemIdList;
    private int[] directCGroupEventItemIdList;
    private int[] directDGroupEventItemIdList;
    private int[] directEGroupEventItemIdList;

    // constructor
    public EPSectionOneDirectManager(Activity activity, User user, MuscleArea muscleArea, GroupingEventData groupingEventData) {
        super(activity, user, muscleArea);
        this.groupingEventData = groupingEventData;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TextView : directTitle mapping
        this.directTitle = (TextView) getActivity().findViewById(R.id.event_program_section_1_direct_title);                                      // title


        // [iv/C]MaterialCardView : directAGroupWrapper mapping
        this.directAGroupWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_section_1_direct_a_group_wrapper);

        // [iv/C]MaterialCardView : directAGroupWrapper mapping
        this.directBGroupWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_section_1_direct_b_group_wrapper);

        // [iv/C]MaterialCardView : directAGroupWrapper mapping
        this.directCGroupWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_section_1_direct_c_group_wrapper);

        // [iv/C]MaterialCardView : directAGroupWrapper mapping
        this.directDGroupWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_section_1_direct_d_group_wrapper);

        // [iv/C]MaterialCardView : directAGroupWrapper mapping
        this.directEGroupWrapper = (MaterialCardView) getActivity().findViewById(R.id.event_program_section_1_direct_e_group_wrapper);


        // [iv/C]LinearLayout : directAGroupItemAdder mapping
        this.directAGroupEventItemAdder = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_a_group_event_item_adder);

        // [iv/C]LinearLayout : directAGroupItemAdder mapping
        this.directBGroupEventItemAdder = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_b_group_event_item_adder);

        // [iv/C]LinearLayout : directAGroupItemAdder mapping
        this.directCGroupEventItemAdder = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_c_group_event_item_adder);

        // [iv/C]LinearLayout : directAGroupItemAdder mapping
        this.directDGroupEventItemAdder = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_d_group_event_item_adder);

        // [iv/C]LinearLayout : directAGroupItemAdder mapping
        this.directEGroupEventItemAdder = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_e_group_event_item_adder);


        // [iv/C]View : directDivider mapping
        this.directDivider = (View) getActivity().findViewById(R.id.event_program_section_1_direct_divider);

        // [iv/C]LinearLayout : directProgramWrapper mapping
        this.directProgramWrapper = (LinearLayout) getActivity().findViewById(R.id.event_program_section_1_direct_bt_program_wrapper);            // program button wrapper

        // [iv/C]Button : directProgram mapping
        this.directProgram = (Button) getActivity().findViewById(R.id.event_program_section_1_direct_bt_program);                                 // program button

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

    }

    @Override
    void initWidgetWhenSizeIsNotZero() {

        final String METHOD_NAME = "[initWidgetOfSectionOneDirect] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_direct 초기화 <<=======================");

        // [iv/i]directAGroupEventItemIdList : aGroupEventArrayList 로 a group 의 widget 들의 초기 설정을 한다. 그리고 groupEventItemAdder 에 추가된 CheckBox 의 id 리스트를 가져온다.
        this.directAGroupEventItemIdList = setWidgetOfGroupEventItemAdder(GroupType.A_GROUP, groupingEventData.getAGroupEventArrayList(), this.directAGroupWrapper, this.directAGroupEventItemAdder);

        // [iv/i] : bGroupEventArrayList 로 b group 의 widget 들의 초기 설정을 한다. 그리고 groupEventItemAdder 에 추가된 CheckBox 의 id 리스트를 가져온다.
        this.directBGroupEventItemIdList = setWidgetOfGroupEventItemAdder(GroupType.B_GROUP, groupingEventData.getBGroupEventArrayList(), this.directBGroupWrapper, this.directBGroupEventItemAdder);

        // [iv/i] : cGroupEventArrayList 로 c group 의 widget 들의 초기 설정을 한다. 그리고 groupEventItemAdder 에 추가된 CheckBox 의 id 리스트를 가져온다.
        this.directCGroupEventItemIdList = setWidgetOfGroupEventItemAdder(GroupType.C_GROUP, groupingEventData.getCGroupEventArrayList(), this.directCGroupWrapper, this.directCGroupEventItemAdder);

        // [ic/i] : dGroupEventArrayList 로 d group 의 widget 들의 초기 설정을 한다. 그리고 groupEventItemAdder 에 추가된 CheckBox 의 id 리스트를 가져온다.
        this.directDGroupEventItemIdList = setWidgetOfGroupEventItemAdder(GroupType.D_GROUP, groupingEventData.getDGroupEventArrayList(), this.directDGroupWrapper, this.directDGroupEventItemAdder);

        // [iv/i] : eGroupEventArrayList 로 e group 의 widget 들의 초기 설정을 한다. 그리고 groupEventItemAdder 에 추가된 CheckBox 의 id 리스트를 가져온다.
        this.directEGroupEventItemIdList = setWidgetOfGroupEventItemAdder(GroupType.E_GROUP, groupingEventData.getEGroupEventArrayList(), this.directEGroupWrapper, this.directEGroupEventItemAdder);

        // [iv/C]LinearLayout : muscleArea 에 따른 directProgramWrapper 배경색을 변경하기
        this.directProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]Button : directProgram click listener
        this.directProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 직접 선택하고 EventSelectionProgramActivity 으로 이동
                setClickListenerOfDirectProgram();

            }
        });
    }

    @Override
    void initWidgetWhenSizeIsZero() {

        final String METHOD_NAME = "[initWidgetOfSectionOneDirectWhenEventArrayListIsEmpty] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=======================>> section_1_direct / empty 초기화 <<=======================");

        // [iv/C]TextView : directTitle 문자열 다시 설정
        this.directTitle.setText(R.string.event_program_section_1_direct_title_empty);

        // [iv/C]LinearLayout : directAGroupWrapper 없애기
        this.directAGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]LinearLayout : directBGroupWrapper 없애기
        this.directBGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]LinearLayout : directCGroupWrapper 없애기
        this.directCGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]LinearLayout : directDGroupWrapper 없애기
        this.directDGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]LinearLayout : directEGroupWrapper 없애기
        this.directEGroupWrapper.setVisibility(LinearLayout.GONE);

        // [iv/C]View : directDivider 없애기
        this.directDivider.setVisibility(View.GONE);

        // [iv/C]LinearLayout : muscleArea 에 따른 directProgramWrapper 색 변경하기
        this.directProgramWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]Button : directProgram 의 문자열 다시 설정 / click listener 설정
        this.directProgram.setText(R.string.event_program_section_1_direct_bt_program_empty);
        this.directProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> directProgram program click listener / start <<++++++++++");
                // [method] : EventAddActivity 로 이동
                moveEventAddActivity();
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> directProgram program click listener / end <<++++++++++");
            }
        });

    }

    /**
     * [method] 가각의 그룹 유형으로 나뉜 데이터를 가지고 각 그룹의 데이터를 추가하거나 해당 widget 을 없앤다.
     *
     * @param groupType           그룹 유형
     * @param groupEventArrayList 해당 그룹의 eventArrayList
     * @param groupWrapper        해당 그룹의 wrapper
     * @param groupEventItemAdder 해당 그룹의 CheckBox 를 추가하기 위한 container(LinearLayout)
     */
    private int[] setWidgetOfGroupEventItemAdder(GroupType groupType, ArrayList<Event> groupEventArrayList, MaterialCardView groupWrapper, LinearLayout groupEventItemAdder) {

        final String METHOD_NAME = "[setWidgetOfGroupEventItemAdder] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> start <<********");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">> group type = " + groupType);


        // [iv/i]groupEventItemIdList : 해당 그룹의 CheckBox 의 아이디를 저장하기 위한 배열
        int[] groupEventItemIdList = new int[groupEventArrayList.size()];

        // [check 1] : eventArrayList 가 생성되었다.
        if (groupEventArrayList != null) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 해당 group 의 객체가 생성되었습니다. <=");

            // [check 2] : eventArrayList 에 데이터가 있다.
            if (groupEventArrayList.size() != 0) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/true : 해당 group 의 데이터가 있습니다. <=");

                // [cycle 1] : 해당 group 의 eventArrayList 의 size 만큼
                for (int index = 0; index < groupEventArrayList.size(); index++) {

                    // [iv/i]groupEventItemIdList : 해당 그룹의 기본 시작 ID 에 index 를 더하여 id 값을 생성한다.
                    groupEventItemIdList[index] = DataManager.getDefaultIdOfGroupType(groupType) + index;

                    // [lv/C]CheckBox : 해당 종목을 추가하기 위한 CheckBox 생성 / id 설정 / 문자열 설정
                    CheckBox eventItemChecker = new CheckBox(getActivity());
                    eventItemChecker.setId(groupEventItemIdList[index]);
                    eventItemChecker.setText((index + 1) + ". " + groupEventArrayList.get(index).getEventName());

                    // [iv/C]LinearLayout : 위의 종목 CheckBox 를 추가한다.
                    groupEventItemAdder.addView(eventItemChecker);

                } // [cycle 1]

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 해당 group 의 size 가 0 이야! <=");
                // [lv/C]MaterialCardView : 해당 그룹을 감싸고 있는 CardView 를 없앤다.
                groupWrapper.setVisibility(MaterialCardView.GONE);

            } // [check 2]

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 해당 group 의 객체가 생성되지 않았어요. <=");
            // [lv/C]MaterialCardView : 해당 그룹을 감싸고 있는 CardView 를 없앤다.
            groupWrapper.setVisibility(MaterialCardView.GONE);

        } // [check 1]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "********>> end <<********");

        return groupEventItemIdList;

    } // End of method [setWidgetOfGroupEventItemAdder]


    /**
     * [method] directProgram click listener
     */
    private void setClickListenerOfDirectProgram() {

        final String METHOD_NAME = "[setClickListenerOfDirectProgram] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> directProgram program click listener / start <<++++++++++");

        // [lv/C]DirectEventSelectionUtil : 그룹별로 직접 선택한 종목들을 체크되어 있는지 검사하여 하나의 ArrayList 에 추가한다.
        DirectEventSelectionUtil eventSelectionUtil = new DirectEventSelectionUtil();

        // [lv/C]DirectEventSelectionUtil : 구분된(그룹화된) eventArrayList 를 추가한다.
        eventSelectionUtil.setGroupingEventData(this.groupingEventData);

        // [lv/C]DirectEventSelectionUtil : 각 그룹의 CheckBox 의 id 를 이용하여 체크된 항목들을 설정한다.
        eventSelectionUtil.setIsCheckedAGroup(isCheckedOfGroupEventItemChecker(directAGroupEventItemIdList));
        eventSelectionUtil.setIsCheckedBGroup(isCheckedOfGroupEventItemChecker(directBGroupEventItemIdList));
        eventSelectionUtil.setIsCheckedCGroup(isCheckedOfGroupEventItemChecker(directCGroupEventItemIdList));
        eventSelectionUtil.setIsCheckedDGroup(isCheckedOfGroupEventItemChecker(directDGroupEventItemIdList));
        eventSelectionUtil.setIsCheckedEGroup(isCheckedOfGroupEventItemChecker(directEGroupEventItemIdList));

        // [lv/C]DirectEventSelectionUtil : 각 그룹의 체크된 항목들을 구별하여 directSelectedEventArrayList 와 noSelectedEventArrayList 를 구분하기
        eventSelectionUtil.selectDirectEvent();

        // [method] : directSelectedEventArrayList 를 포함하여 EventSelectionProgramActivity 로 이동한다.
        moveEventSelectionProgramActivity(eventSelectionUtil.getDirectSelectedEventArrayList(), eventSelectionUtil.getNoSelectedEventArrayList());

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++>> directProgram program click listener / end <<++++++++++");

    } // End of method [setClickListenerOfDirectProgram]


    /**
     * [method] 그룹 별로 CheckBox 의 체크유무를 확인한다. 그리고 각각의 CheckBox 체크 유무를 저장하여 반환한다.
     *
     * @param groupEventItemIdList 해당 그룹의 CheckBox 의 id 리스트
     * @return 해당 그룹의 모든 CheckBox 의 체크 유무
     */
    private boolean[] isCheckedOfGroupEventItemChecker(int[] groupEventItemIdList) {

        final String METHOD_NAME = "[isCheckedOfGroupEventItemChecker] ";

        // [lv/b]isCheckedOfGroupEventChecker : 해당 그룹의 체크 유무를 선별하여 반환한다.
        boolean[] isCheckedOfGroupEventChecker = new boolean[groupEventItemIdList.length];

        // [cycle 1] : 해당 그룹에 생성된 CheckBox 의 수 만큼
        for (int index = 0; index < groupEventItemIdList.length; index++) {

            // [lv/C]CheckBox : id 값으로 CheckBox 객체 생성 / 체크유무를 확인하기 위해서
            CheckBox checkBox = (CheckBox) getActivity().findViewById(groupEventItemIdList[index]);

            // [lv/b] : 체크된 유무 저장
            isCheckedOfGroupEventChecker[index] = checkBox.isChecked();

//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "> 는 " + groupEventItemIdList[index] + " 이다.");
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "> 는 체크가 되었냥? = " + checkBox.isChecked());

        } // [cycle 1]

        return isCheckedOfGroupEventChecker;

    } // End of method [isCheckedOfGroupEventItemChecker]
}
