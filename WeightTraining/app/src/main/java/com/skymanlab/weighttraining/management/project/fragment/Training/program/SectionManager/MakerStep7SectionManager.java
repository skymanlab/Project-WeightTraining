package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep8Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.Step7EventRvAdapter;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.dialog.Step7DetailProgramDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

public class MakerStep7SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep6SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> finalOrderList;
    private ArrayList<MuscleArea> muscleAreaArrayList;
    private int programSettingSetNumber;
    private int programSettingRestTimeMinute;
    private int programSettingRestTimeSecond;

    // instance variable
    private RecyclerView eventListWrapper;

    // instance variable
    private HashMap<String, DetailProgram> detailProgramList;

    // constructor
    public MakerStep7SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public void setMuscleAreaArrayList(ArrayList<MuscleArea> muscleAreaArrayList) {
        this.muscleAreaArrayList = muscleAreaArrayList;
    }

    public void setProgramSettingSetNumber(int programSettingSetNumber) {
        this.programSettingSetNumber = programSettingSetNumber;
    }

    public void setProgramSettingRestTimeMinute(int programSettingRestTimeMinute) {
        this.programSettingRestTimeMinute = programSettingRestTimeMinute;
    }

    public void setProgramSettingRestTimeSecond(int programSettingRestTimeSecond) {
        this.programSettingRestTimeSecond = programSettingRestTimeSecond;
    }

    @Override
    public void connectWidget() {

        // [iv/C]RecyclerView :
        this.eventListWrapper = (RecyclerView) getView().findViewById(R.id.f_maker_step6_detail_program_setting_event_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        //
        initDetailProgramList();

        // recyclerView 의 초기 내용을 설정한다.
        initWidgetOfRecyclerView();

        // [iv/C]Fragment : MakerStep7Fragment 의 FragmentManger 를 통해 Step7DetailProgramSettingDialog 에서 보낸 데이터 가져오기
        getFragment().getParentFragmentManager().setFragmentResultListener(Step7DetailProgramDialog.REQUEST_KEY, getFragment(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                // dialog 에서 key, setNumber, restTimeMinute, restTimeSecond 가져오기
                String dialogKey = result.getString(Step7DetailProgramDialog.DETAIL_PROGRAM_EVENT_KEY);
                int dialogSetNumber = result.getInt(Step7DetailProgramDialog.DETAIL_PROGRAM_SET_NUMBER);
                int dialogRestTimeMinute = result.getInt(Step7DetailProgramDialog.DETAIL_PROGRAM_REST_TIME_MINUTE);
                int dialogRestTimeSecond = result.getInt(Step7DetailProgramDialog.DETAIL_PROGRAM_REST_TIME_SECOND);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-1. dialog 에서 가져온 key = " + dialogKey);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-2. dialog 에서 가져온 setNumber = " + dialogSetNumber);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-3. dialog 에서 가져온 restTimeMinute = " + dialogRestTimeMinute);
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "A-4. dialog 에서 가져온 restTimeSecond = " + dialogRestTimeSecond);

                if (programSettingSetNumber == dialogSetNumber && programSettingRestTimeMinute == dialogRestTimeMinute && programSettingRestTimeSecond == dialogRestTimeSecond) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "dialog 에 설정된 내용과 기존 프로그램 설정값에서 변환것 없음");
                    return;
                }

                DetailProgram detailProgram = detailProgramList.get(dialogKey);
                if (detailProgram.getSetNumber() == dialogSetNumber
                        && detailProgram.getRestTimeMinute() == dialogRestTimeMinute
                        && detailProgram.getRestTimeSecond() == dialogRestTimeSecond) {

                    // 바뀐 것 없음
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< dialog 의 내용들 > 바뀐 것이 없어요.");

                } else {

                    // 바뀐 것 있으니 새로 반영할 것!
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< dialog 의 내용들 > 바뀐 것이 있어요");
                    detailProgramList.get(dialogKey).setSetNumber(dialogSetNumber);
                    detailProgramList.get(dialogKey).setRestTimeMinute(dialogRestTimeMinute);
                    detailProgramList.get(dialogKey).setRestTimeSecond(dialogRestTimeSecond);

                }

            }
        });

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        final String METHOD_NAME = "[newEndButtonListenerInstance] ";

        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                if (allCheckData()) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< all data > 모든 데이터가 조건에 성립합니다.");

                    // [lv/C]Program : 해당 프로그램의 설정값으로 객체를 생성한다.
                    Program program = new Program();
                    program.setMuscleAreaList(muscleAreaArrayList);
                    program.setFinalOrderList(finalOrderList);
                    program.setDetailProgramList(getSortedDetailProgramList());
                    program.setTotalEventNumber(finalOrderList.size());
                    program.setTotalSetNumber(getTotalSetNumber());

                    // [lv/C]MakerStep8Fragment : step 8 fragment 생성
                    MakerStep8Fragment step8Fragment = MakerStep8Fragment.newInstance(program);

                    // [lv/C]FragmentTransaction : step 8 fragment 로 이동
                    FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_home_content_wrapper, step8Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< total set number >> =====> " + getTotalSetNumber());

                } else {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< all data > 어떤 한 데이터가 조건에 맞지 않습니다.");
                }

                return null;
            }
        };
    }


    /**
     * Step 6 에서 보낸 programSetting 값과 finalOrderList 로 DetailProgramList 객체를 생성하고 초기화하기
     */
    private void initDetailProgramList() {
        final String METHOD_NAME = "[initDetailProgramList] ";

        this.detailProgramList = new HashMap<>();

        for (int index = 0; index < this.finalOrderList.size(); index++) {

            // DetailProgram 의 초기 내용을 설정한다.
            DetailProgram detailProgram = new DetailProgram();
            detailProgram.setOrder(index + 1);                                              // order : key
            detailProgram.setMuscleArea(finalOrderList.get(index).getMuscleArea());         // muscle area
            detailProgram.setEventKey(finalOrderList.get(index).getKey());                  // event key
            detailProgram.setEventName(finalOrderList.get(index).getEventName());           // event name
            detailProgram.setSetNumber(programSettingSetNumber);                            // set number
            detailProgram.setRestTimeMinute(programSettingRestTimeMinute);                  // rest time minute
            detailProgram.setRestTimeSecond(programSettingRestTimeSecond);                  // rest time second

            // 위의 detailProgram 을 리스트에 추가한다.
            detailProgramList.put(detailProgram.getEventKey(), detailProgram);

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< event Name >> ====> " + detailProgram.getEventName());
        }
    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView() {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.eventListWrapper.setLayoutManager(layoutManager);

        // [iv/C]SelectedEventItemRvAdapter : recyclerView 의 adapter 생성
        Step7EventRvAdapter adapter = new Step7EventRvAdapter.Builder()
                .setFragment(getFragment())
                .setFinalOrderList(this.finalOrderList)
                .setDetailProgramList(this.detailProgramList)
                .create();

        // [iv/C] : recyclerView 의 adapter setting
        this.eventListWrapper.setAdapter(adapter);

    } // End of method [initWidgetOfRecyclerView]


    /**
     * detailProgramList 의 DetailProgram 을 order 의 순서대로 정렬하여 ArrayList 객체를 반환한다.
     *
     * @return
     */
    private ArrayList<DetailProgram> getSortedDetailProgramList() {

        ArrayList<DetailProgram> valueSet = new ArrayList<>();

        valueSet.addAll(detailProgramList.values());

        Collections.sort(valueSet, new Comparator<DetailProgram>() {
            @Override
            public int compare(DetailProgram o1, DetailProgram o2) {
                return ((Comparable) o1.getOrder()).compareTo(o2.getOrder());
            }
        });

        return valueSet;
    }


    /**
     * 모든 detailProgram 객체의 setNumber 를 합한 수를 반환한다.
     *
     * @return
     */
    private int getTotalSetNumber() {

        int totalSetNumber = 0;

        Iterator iterator = detailProgramList.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            totalSetNumber = totalSetNumber + detailProgramList.get(key).getSetNumber();
        }

        return totalSetNumber;

    }


    /**
     * program setting 부분의 setNumber, restTimeMinute, restTimeSecond 의 적절한 데이터 변경이 있는지 검사하여 그 결과를 리턴한다.
     *
     * @return
     */
    private boolean checkData(int setNumber, int restTimeMinute, int restTimeSecond) {
        final String METHOD_NAME = "[checkData] ";

        // [check 1] : setNumber 는 0 이상일때만
        if (0 < setNumber) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 통과");

            // '분' 이 0 이더라도 '초' 가 0 이상이면 조건 만족한다.
            boolean restTimeChecker1 = (0 == restTimeMinute) && (0 < restTimeSecond) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 1 = " + restTimeChecker1);

            // '분' 이 0 이상이면 '초' 는 0 이거나 그 이상이면 조건에 만족한다.
            boolean restTimeChecker2 = (0 < restTimeMinute) && (0 <= restTimeSecond) ? true : false;
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. rest time checker 2 = " + restTimeChecker2);

            // [check 2] : restTimeMinute, restTimeSecond 는 restTime 으로 조건을 검사한다.
            if (restTimeChecker1 || restTimeChecker2) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 통과");
                return true;

            } else {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-2. rest time 조건 불충분");
                return false;
            }

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check-1. set number 조건 불충분");
            return false;

        } // [check 1]

    } // End of method [checkData]


    private boolean allCheckData() {
        final String METHOD_NAME = "[allCheckData] ";

        Iterator iterator = detailProgramList.keySet().iterator();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (!checkData(detailProgramList.get(key).getSetNumber(), detailProgramList.get(key).getRestTimeMinute(), detailProgramList.get(key).getRestTimeSecond())) {
                return false;
            }
        }
        return true;
    }


    private void displayInfoOfDetailProgramList() {
        final String METHOD_NAME = "[displayInfoOfDetailProgramList] ";

        Iterator iterator = detailProgramList.keySet().iterator();

        int index = 0;
        while (iterator.hasNext()) {
            String key = (String) iterator.next();

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getOrder = " + detailProgramList.get(key).getOrder());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getEventName = " + detailProgramList.get(key).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getMuscleArea = " + detailProgramList.get(key).getMuscleArea());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getSetNumber = " + detailProgramList.get(key).getSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getRestTimeMinute = " + detailProgramList.get(key).getRestTimeMinute());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getRestTimeSecond = " + detailProgramList.get(key).getRestTimeSecond());

            index++;

        }

    }

    private void displayInfoOfDetailProgramArrayList(ArrayList<DetailProgram> detailProgramArrayList) {
        final String METHOD_NAME = "[displayInfoOfDetailProgramArrayList] ";

        for (int index = 0; index < detailProgramArrayList.size(); index++) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getOrder =" + detailProgramArrayList.get(index).getOrder());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getMuscleArea =" + detailProgramArrayList.get(index).getMuscleArea());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getEventKey =" + detailProgramArrayList.get(index).getEventKey());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getEventName =" + detailProgramArrayList.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getSetNumber =" + detailProgramArrayList.get(index).getSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getRestTimeMinute =" + detailProgramArrayList.get(index).getRestTimeMinute());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> getRestTimeSecond =" + detailProgramArrayList.get(index).getRestTimeSecond());

        }


    }
}
