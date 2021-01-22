package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.ProgramFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.Step7DetailProgramResultItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MakerStep7SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep7SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> finalOrderList;
    private Program program;
    private HashMap<String, DetailProgram> detailProgramList;

    // instance variable
    private TextInputEditText nickName;
    private TextView programResultSetNumber;
    private TextView programResultRestTime;
    private ScrollView resultContentWrapper;
    private LinearLayout detailProgramResultListWrapper;

    // constructor
    public MakerStep7SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setDetailProgramList(HashMap<String, DetailProgram> detailProgramList) {
        this.detailProgramList = detailProgramList;
    }

    @Override
    public void connectWidget() {

        // [ TextInputEditText | nickName ] widget connect
        this.nickName = (TextInputEditText) getView().findViewById(R.id.f_maker_step7_nick_name);

        // [ TextView | programResultSetNumber ] widget connect
        this.programResultSetNumber = (TextView) getView().findViewById(R.id.f_maker_step7_program_setting_set_number);

        // [ TextView | programResultRestTime ] widget connect
        this.programResultRestTime = (TextView) getView().findViewById(R.id.f_maker_step7_program_setting_rest_time);

        // [ ScrollView | resultContentWrapper ] widget connect
        this.resultContentWrapper = (ScrollView) getView().findViewById(R.id.f_maker_step7_result_content_wrapper);

        // [ LinearLayout | detailProgramResultListWrapper ] widget connect
        this.detailProgramResultListWrapper = (LinearLayout) getView().findViewById(R.id.f_maker_step7_result_detail_program_setting_list_wrapper);

    }

    @Override
    public void initWidget() {

        // [TextView] [programResultSetNumber] text
        this.programResultSetNumber.setText(DataFormatter.setSetNumberFormat(this.program.getSetNumber()));

        // [TextView] [programResultRestTime] text
        this.programResultRestTime.setText(DataFormatter.setTimeFormat(this.program.getRestTimeMinute(), this.program.getRestTimeSecond()));

        // [method]
        initWidgetOfDetailProgramResultListWrapper();

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                // back stack 에 있던 모든 fragment 를 pop!
//                getFragment().getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                // 'program' fragment 로 이동
                getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, ProgramFragment.newInstance())
                        .commit();

                return null;
            }
        };
    }


    private void initWidgetOfDetailProgramResultListWrapper() {

        // [LayoutInflater] [inflater] activity 에서 fragmentManager 를 가져오기
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [cycle 1] : finalOrderList 의 각 event 로 초기 내용 설정하기
        for (int index = 0; index < this.finalOrderList.size(); index++) {

            addViewOfDetailProgramResultListWrapper(createViewOfDetailProgramResultItem(inflater, finalOrderList.get(index), detailProgramList.get(finalOrderList.get(index).getKey())));

        } // [cycle 1]


        // resultContentWrapper 인 scrollView 를 최상단으로 이동한다.
        getFragment().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // [ScrollView] [resultContentWrapper] 최상단으로 이동
                resultContentWrapper.fullScroll(View.SCROLL_INDICATOR_TOP);

            }
        });

    }


    /**
     * finalOrderList 의 각 event 와 그와 대응되는 detailProgram 으로 detailProgramSettingItem 객체를 생성하여 반환한다.
     *
     * @param inflater
     * @param event
     * @param detailProgram
     * @return
     */
    private Step7DetailProgramResultItem createViewOfDetailProgramResultItem(LayoutInflater inflater, Event event, DetailProgram detailProgram) {
        return new Step7DetailProgramResultItem.Builder()
                .setInflater(inflater)
                .setEvent(event)
                .setDetailProgram(detailProgram)
                .newInstance()
                .createItem();
    }


    /**
     * 해당 detailProgramResultItem 의 view 를 detailProgramResultListWrapper 에 추가하여 화면에 표시한다.
     *
     * @param detailProgramResultItem
     */
    private void addViewOfDetailProgramResultListWrapper(Step7DetailProgramResultItem detailProgramResultItem) {
        this.detailProgramResultListWrapper.addView(detailProgramResultItem.getItem());
    }
}
