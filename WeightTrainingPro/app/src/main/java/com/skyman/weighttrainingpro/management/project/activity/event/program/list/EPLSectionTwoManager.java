package com.skyman.weighttrainingpro.management.project.activity.event.program.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textview.MaterialTextView;
import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.listview.EventProgramItemLvAdapter;
import com.skyman.weighttrainingpro.management.project.activity.SectionManager;
import com.skyman.weighttrainingpro.management.project.activity.SectionInitialization;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

import java.util.ArrayList;

public class EPLSectionTwoManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAE]_EPLSectionTwoManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> selectedEventArrayList;
    private ArrayList<Event> noSelectedEventArrayList;
    private TextView setNumber;
    private TextView restTimeMinute;
    private TextView restTimeSecond;

    // instance variable
    private LinearLayout titleWrapper;
    private ListView programListLv;
    private LinearLayout addWrapper;
    private MaterialTextView add;
    private LinearLayout deleteWrapper;
    private MaterialTextView delete;
    private EventProgramItemLvAdapter adapter;

    // constructor
    public EPLSectionTwoManager(Activity activity, User user, MuscleArea muscleArea, ArrayList<Event> selectedEventArrayList, ArrayList<Event> noSelectedEventArrayList) {
        super(activity, user, muscleArea);
        this.selectedEventArrayList = selectedEventArrayList;
        this.noSelectedEventArrayList = noSelectedEventArrayList;
    }

    // setter
    public void setSetNumber(TextView setNumber) {
        this.setNumber = setNumber;
    }

    public void setRestTimeMinute(TextView restTimeMinute) {
        this.restTimeMinute = restTimeMinute;
    }

    public void setRestTimeSecond(TextView restTimeSecond) {
        this.restTimeSecond = restTimeSecond;
    }


    @Override
    public void mappingWidget() {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) getActivity().findViewById(R.id.event_program_list_section_2_title_wrapper);

        // [iv/C]ListView : programListLv mapping
        this.programListLv = (ListView) getActivity().findViewById(R.id.event_program_list_section_2_lv_event_item);

        // [iv/C]LinearLayout : addWrapper mapping
        this.addWrapper = (LinearLayout) getActivity().findViewById(R.id.event_program_list_section_2_add_wrapper);

        // [iv/C]materialTextView : add mapping
        this.add = (MaterialTextView) getActivity().findViewById(R.id.event_program_list_section_2_bt_add);

        // [iv/C]LinearLayout : deleteWrapper mapping
        this.deleteWrapper = (LinearLayout) getActivity().findViewById(R.id.event_program_list_section_2_delete_wrapper);

        // [iv/C]materialTextView : delete mapping
        this.delete = (MaterialTextView) getActivity().findViewById(R.id.event_program_list_section_2_bt_delete);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionTwo] ";

        // [iv/C]LinearLayout : muscleArea 에 따른 titleWrapper 배경색 변경하기
        this.titleWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [method] : selectedEventArrayList 를 programListLv 에 연결하기
        setProgramListLv(setNumber, restTimeMinute, restTimeSecond);

        // [iv/C]MaterialCardView : addWrapper 색 변경
        this.addWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]MaterialTextView : add click listener
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener
                setClickListenerOfAdd();

            }
        });

        // [iv/C]MaterialCardView : deleteWrapper 색 변경
        this.deleteWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [iv/C]MaterialTextView : delete click listener
        this.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]SparseBooleanArray : programListLv 에서 체크 유무를 가져오기
                SparseBooleanArray checkedItems = programListLv.getCheckedItemPositions();

                // [method] : click listener
                setClickListenerOfDelete(checkedItems);

            }
        });
    }


    /**
     * [method] programListLv 연결하기
     */
    private void setProgramListLv(TextView setNumber, TextView restTimeMinute, TextView restTimeSecond) {

        // [lv/C]EventSelectionItemLvAdapter :
        this.adapter = new EventProgramItemLvAdapter(getActivity(), getUser(), getMuscleArea(), setNumber, restTimeMinute, restTimeSecond);
        this.adapter.setEventArrayList(this.selectedEventArrayList);

        // [lv/C]ListView : 위의 adapter 와 연결하기
        this.programListLv.setAdapter(this.adapter);

    } // End of method [setProgramListLv]


    /**
     * [method] add click listener
     */
    private void setClickListenerOfAdd() {

        final String METHOD_NAME = "[setClickListenerOfAdd] ";

        // [lv/C]ArrayList<String> : noSelectedEventArrayList 의 각 객체의 eventName 만 multiChoice 의 항목으로 만들기 위해
        final ArrayList<String> eventNameList = new ArrayList<>();

        // [lv/b]checkedList : 각 항목의 체크 유무를 판별하기 위한 배열
        final boolean[] checkedList = new boolean[this.noSelectedEventArrayList.size()];

        // [cycle 2] : noSelectedEventArrayList 의 eventName 만을 추가한다.
        for (int index = 0; index < this.noSelectedEventArrayList.size(); index++) {

            // [lv/C]ArrayList<Event> : eventName 을 추가
            eventNameList.add(this.noSelectedEventArrayList.get(index).getEventName());

            // [lv/b]checkedList : 초기는 모두 체크되지 않았기 때문에 false 로 초기설정한다.
            checkedList[index] = false;

        } // [cycle 2]

        // [lv/C]CharSequence : 위의 eventNameList 를 AlertDialog 에 항목으로 넘기기 위해 CharSequence 객체로 만든다.
        final CharSequence[] eventName = eventNameList.toArray(new String[eventNameList.size()]);

        // [lv/C]AlertDialog : builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.event_program_list_section_2_alert_add_title)
                .setMultiChoiceItems(eventName, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++>> 위치 = " + which);

                        // [check 1] : 체크 버튼을 누를 때마다 체크 유무를 판별한다.
                        if (isChecked) {

                            // [lv/b]checkedList : 체크 버튼을 클릭하여 체크된 상태로 바꾸었기 때문에
                            checkedList[which] = true;

                        } else {

                            // [lv/b]checkedList : 체크 버튼을 클릭하여 체크되지 않은 상태로 바꾸었기 때문에
                            checkedList[which] = false;

                        } // [check 1]

                    }
                })
                .setPositiveButton(R.string.event_program_list_section_2_alert_add_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [cycle 3] : 체크된 결과를 담은 checkedList 를 확인하여 true 인 항목을 selectedEventArrayList 에 추가한다.
                        for (int index = 0; index < checkedList.length; index++) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, index + " 번째 : " + checkedList[index]);
                            // [check 2] : check 된 항목의 index 번재를 selectedEventArrayList 에 추가한다.
                            if (checkedList[index]) {

                                // [lv/C]ArrayList<Event> : noSelectedEventArrayList 의 index 번째 항목을 selectedEventArrayList 의 항목에 추가한다. (마지막에)
                                selectedEventArrayList.add(noSelectedEventArrayList.get(index));

                            } // [check 2]

                        } // [cycle 3]

                        // [cycle 4] : 뒤에서 부터 체크된 항목을 noSelectedEventArrayList 에서 제거한다.
                        for (int index = noSelectedEventArrayList.size() - 1; 0 <= index; index--) {

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, index + " 번째 : " + checkedList[index]);

                            // [check 3] : check 된 항목의 index 번째를 제거한다.
                            if (checkedList[index]) {

                                // [lv/C]ArrayList<Event> : noSelectedEventArrayList 의 index 번째 항목을 제거한다. -> 앞에서 부터 제거하면 index 로 접근하다가는 error 가 발생할 수 있다.
                                noSelectedEventArrayList.remove(index);

                            } // [check 3]

                        } // [cycle 4]

                        // [iv/C]EventProgramItemLvAdapter : selectedEventArrayList 를 반영하기
                        adapter.notifyDataSetChanged();

//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> noSelected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, noSelectedEventArrayList);
//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> selected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedEventArrayList);
                    }
                })
                .setNegativeButton(R.string.event_program_list_section_2_alert_add_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .show();

    } // End of method [setClickListenerOfAdd]


    /**
     * [method] delete click listener
     */
    private void setClickListenerOfDelete(SparseBooleanArray checkedItems) {

        final String METHOD_NAME = "[setClickListenerOfDelete] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. checkedItems = " + checkedItems.size());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. programListLv size = " + programListLv.getCount());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. selected size = " + selectedEventArrayList.size());
        // [cycle 1] : programListLv(ListView) 에서 체크 유무가 확인된 리스트를
        for (int index = 0; index < programListLv.getCount(); index++) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "> 는  " + checkedItems.get(index));

            // [check 1] : 체크된 항목은 noSelectedEventArrayList 에
            if (checkedItems.get(index)) {

                // [iv/C]ArrayList<Event> : selectedEventArrayList 의 index 번째 항목을 noSelectedEventArrayList 의 항목에 추가한다.(맨끝에)
                noSelectedEventArrayList.add(selectedEventArrayList.get(index));

            } // [check 1]

        } // [cycle 1]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. checkedItems = " + checkedItems.size());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. programListLv size = " + programListLv.getCount());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. selected size = " + selectedEventArrayList.size());

        // [cycle 2 : programListLv(ListView) 에서 체크 유무가 확인된 리스트를
        for (int index = this.programListLv.getCount() - 1; 0 <= index; index--) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + "> 는  " + checkedItems.get(index));

            // [check 1] : 체크된 항목의 selectedEventArrayList 에서 index 번째를 제거한다.
            if (checkedItems.get(index)) {

                // [iv/C]ArrayList<Event> : 위에서 추가한 selectedEventArrayList 의 항목을 제거한다.
                this.selectedEventArrayList.remove(index);

            } // [check 1]

        } // [cycle 2]

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        // [iv/C]ListVie : 선택 상태 초기화
        this.programListLv.clearChoices();

        // [iv/C]EventProgramItemLvAdapter : selectedEventArrayList 를 반영하기
        this.adapter.notifyDataSetChanged();

//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> noSelected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, noSelectedEventArrayList);
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> selected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedEventArrayList);

    } // End of method [setClickListenerOfDelete]

}
