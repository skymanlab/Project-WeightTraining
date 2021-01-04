package com.skymanlab.weighttraining.management.project.activity.event.program.list;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.HomeActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.listview.EventProgramItemLvAdapter;
import com.skymanlab.weighttraining.management.project.activity.SectionInitialization;
import com.skymanlab.weighttraining.management.project.activity.SectionManager;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EPLSectionTwoManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEPL] EPLSectionTwoManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> selectedEventArrayList;
    private ArrayList<Event> noSelectedEventArrayList;
    private ArrayList<Boolean> isCompleted;
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
    private LinearLayout completeWrapper;
    private MaterialTextView complete;

    // instance variable
    private EventProgramItemLvAdapter adapter;

    // instance variable
    private int completedCounter = 0;

    // constructor
    public EPLSectionTwoManager(Activity activity, FirebaseUser firebaseUser, MuscleArea muscleArea, ArrayList<Event> selectedEventArrayList, ArrayList<Event> noSelectedEventArrayList) {
        super(activity, firebaseUser, muscleArea);
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

    // getter
    public ArrayList<Boolean> getIsCompleted() {
        return isCompleted;
    }

    public EventProgramItemLvAdapter getAdapter() {
        return adapter;
    }

    public LinearLayout getAddWrapper() {
        return addWrapper;
    }

    public MaterialTextView getAdd() {
        return add;
    }

    public LinearLayout getDeleteWrapper() {
        return deleteWrapper;
    }

    public MaterialTextView getDelete() {
        return delete;
    }

    public LinearLayout getCompleteWrapper() {
        return completeWrapper;
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

        // [iv/C]LinearLayout : completeWrapper mapping
        this.completeWrapper = (LinearLayout) getActivity().findViewById(R.id.event_program_list_section_2_complete_wrapper);

        // [iv/C]MaterialTextView : complete mapping
        this.complete = (MaterialTextView) getActivity().findViewById(R.id.event_program_list_section_2_bt_complete);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionTwo] ";

        // [iv/C]LinearLayout : muscleArea 에 따른 titleWrapper 배경색 변경하기
        this.titleWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

        // [lv/C]ArrayList<Boolean> :
        this.isCompleted = new ArrayList<>();

        // [method] : selectedEventArrayList 의 목록이 완료되었는지 확인하는 isCompleted 를 selectedEvenArrayList 의 size 만큼 초기화하기
        initIsCompleted(this.selectedEventArrayList.size());

        // [method] : selectedEventArrayList 를 programListLv 에 연결하기
        setProgramListLv(setNumber, restTimeMinute, restTimeSecond);

        // [iv/C]MaterialCardView : addWrapper 색 변경 / click listener
        this.addWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : click listener
                setClickListenerOfAdd();

            }
        });

        // [iv/C]LinearLayout : deleteWrapper 색 변경 - muscleArea 와 관련된 색으로
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

        // [iv/C]MaterialCardView : complete click listener
        this.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]Intent : HomeActivity 로 이동하기 위한 Intent 객체 생성 / 현재 Activity 종료 / 이동
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                getActivity().finish();
                getActivity().startActivity(intent);
                
            }
        });
    }


    /**
     * [method] programListLv 연결하기
     */
    private void setProgramListLv(TextView setNumber, TextView restTimeMinute, TextView restTimeSecond) {

        final String METHOD_NAME = "[setProgramListLv] ";
        // [lv/C]EventSelectionItemLvAdapter :
        this.adapter = new EventProgramItemLvAdapter(getActivity(), getMuscleArea(), setNumber, restTimeMinute, restTimeSecond);
        this.adapter.setEventArrayList(this.selectedEventArrayList);
        this.adapter.setIsCompleted(this.isCompleted);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. selectedEventArrayList 확인");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, this.selectedEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. isCompleted 확인");
        for (boolean search : this.isCompleted) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> " + search);
        }

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

//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">+++++++++++++++++++++++++++++++++++++++++++++++ 새롭게 추가된");
//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++ size =  " + selectedEventArrayList.size());
//                        // [method] : 새롭게 추가된 size 만큼 isCompleted 초기화
                        initIsCompleted(selectedEventArrayList.size());
//
//                        // [iv/C]EventProgramItemLvAdapter : selectedEventArrayList 를 반영하기
                        adapter.notifyDataSetChanged();

//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> noSelected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, noSelectedEventArrayList);
//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> selected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedEventArrayList);
//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>> selectedEventArrayList size = " + selectedEventArrayList.size());
//                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> isCompleted >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                        for (int index = 0; index < isCompleted.size(); index++) {
//                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>>>> " + isCompleted.get(index));
//                        }
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
        // [iv/C]ListVie : 선택 상태 초기화 ( 체그되어 있던 것들을 없애기 )
        this.programListLv.clearChoices();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">+++++++++++++++++++++++++++++++++++++++++++++++ 삭제된 상태의 ");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++++++++++ size =  " + selectedEventArrayList.size());
        // [method] : 새롭게 추가된 size 만큼 isCompleted 초기화
        initIsCompleted(selectedEventArrayList.size());


        // [iv/C]EventProgramItemLvAdapter : selectedEventArrayList 를 반영하기
        this.adapter.notifyDataSetChanged();

//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> noSelected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, noSelectedEventArrayList);
//        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>> selected >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedEventArrayList);

    } // End of method [setClickListenerOfDelete]


    /**
     * [method] selectedEventArrayList 의 목록이 완료 되었는지 검사하는 isCompleted 를 초기화하기
     */
    private void initIsCompleted(int size) {

        final String METHOD_NAME = "[initIsCompleted] ";

        this.isCompleted.clear();

        // [cycle 1] : size 만큼 false 로 초기화
        for (int index = 0; index < size; index++) {

            // [iv/C]ArrayList<Boolean> : false 로 초기화
            this.isCompleted.add(false);
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<" + index + ">  ===> " + this.isCompleted.get(index));

        } // [cycle 1]

    } // End of method [initIsCompleted]


    /**
     * [method] 운동 프로그램이 시작되고 종료될 때 까지의 과정을 설정한다.
     *
     */
    public void processProgram(int completedEventPosition) {

        final String METHOD_NAME = "[processProgram] ";

        // [iv/i]completedCounter : 완료한 종목이 있으므로 completedCounter 를 1 증가하기
        this.completedCounter++;
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> isCompleted size = " + this.isCompleted.size() + " >>>>> completedCounter = " + completedCounter);

        // [iv/C]ArrayList<Event> : eventArrayList 의 eventPosition 위치의 값이 완료되었음을 isCompleted 로 알려주기 (adapter 에게)
        this.isCompleted.set(completedEventPosition, true);

        // [iv/C]EventProgramItemLvAdapter : 위에서 변경된 isCompleted 를 adapter 를 통해 알려주기
        this.adapter.notifyDataSetInvalidated();

        // [check 1] : 하나의 event 가 완료되었을 때, completedCounter 가 1 일때 초기 설정하고/ completedCounter 가 isCompleted 의 size 가 같으면 종료 설정을 한다.
        if (completedCounter == 1) {

            // [iv/C]LinearLayout : addWrapper 를 배경색을 변경하기 / GONE 으로 변경
            this.addWrapper.setBackgroundResource(R.color.colorBackgroundGray);
            this.addWrapper.setVisibility(LinearLayout.GONE);

            // [iv/C]LinearLayout : deleteWrapper 배경색을 변경하기 / GONE 으로 변경
            this.deleteWrapper.setBackgroundResource(R.color.colorBackgroundGray);
            this.deleteWrapper.setVisibility(LinearLayout.GONE);

            // [iv/C]LinearLayout : completeWrapper 의 색 변경 / VISIBLE 로 변경
            this.completeWrapper.setBackgroundResource(R.color.colorBackgroundGray);
            this.completeWrapper.setVisibility(LinearLayout.VISIBLE);

            // [iv/C]MaterialTextView : complete 의 enable 를 false 로 변경 = click listener 가 실행 되지 않게
            this.complete.setEnabled(false);

        } else if (completedCounter == getIsCompleted().size()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 모든 event 를 완료하였습니다.");
            // [iv/C]EPLSectionTwoManager : section_2 의 completeWrapper 배경색 변경 / enable = true 로 변경
            this.completeWrapper.setBackgroundResource(DataManager.convertColorIntOfMuscleArea(getMuscleArea()));

            // [iv/C]MaterialTextView : title 을 '완료' 로 변경 / complete 의 enable 를 false 로 변경 = click listener 가 실행 되도록
            this.complete.setText(getActivity().getString(R.string.event_program_list_section_2_bt_complete));
            this.complete.setEnabled(true);

        } // [check 1]

    }
}
