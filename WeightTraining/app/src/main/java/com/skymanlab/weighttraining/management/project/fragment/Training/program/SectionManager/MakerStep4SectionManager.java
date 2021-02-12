package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep5Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.Step4EventRvAdapter;

import java.util.ArrayList;

public class MakerStep4SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep4SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private EventResultSet chestEventResultSet;
    private EventResultSet shoulderEventResultSet;
    private EventResultSet latEventResultSet;
    private EventResultSet upperBodyEventResultSet;
    private EventResultSet armEventResultSet;
    private EventResultSet etcEventResultSet;

    // instance variable
    private RecyclerView selectedEventListWrapper;

    // constructor
    public MakerStep4SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setChestEventResultSet(EventResultSet chestEventResultSet) {
        this.chestEventResultSet = chestEventResultSet;
    }

    public void setShoulderEventResultSet(EventResultSet shoulderEventResultSet) {
        this.shoulderEventResultSet = shoulderEventResultSet;
    }

    public void setLatEventResultSet(EventResultSet latEventResultSet) {
        this.latEventResultSet = latEventResultSet;
    }

    public void setUpperBodyEventResultSet(EventResultSet upperBodyEventResultSet) {
        this.upperBodyEventResultSet = upperBodyEventResultSet;
    }

    public void setArmEventResultSet(EventResultSet armEventResultSet) {
        this.armEventResultSet = armEventResultSet;
    }

    public void setEtcEventResultSet(EventResultSet etcEventResultSet) {
        this.etcEventResultSet = etcEventResultSet;
    }

    @Override
    public void connectWidget() {

        // [iv/C]RecyclerView : selectedEventListWrapper
        this.selectedEventListWrapper = (RecyclerView) getView().findViewById(R.id.f_maker_step4_selected_event_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [lv/C]ArrayList<Event> : 각 muscleArea 의 selected event 를 합치기 위해서
        ArrayList<Event> selectedEventArrayList = new ArrayList<>();

        // 각 muscleARea 를 selectedEventArrayList 로 합친다.
        selectedEventArrayList.addAll(chestEventResultSet.getSelectedEventArrayList());
        selectedEventArrayList.addAll(shoulderEventResultSet.getSelectedEventArrayList());
        selectedEventArrayList.addAll(latEventResultSet.getSelectedEventArrayList());
        selectedEventArrayList.addAll(upperBodyEventResultSet.getSelectedEventArrayList());
        selectedEventArrayList.addAll(armEventResultSet.getSelectedEventArrayList());
        selectedEventArrayList.addAll(etcEventResultSet.getSelectedEventArrayList());

        // selectedEventArrayList 를 토대로 RecyclerView 에 adapter 설정한다.
        initWidgetOfRecyclerView(selectedEventArrayList);

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

                // [lv/C]MakerStep5Fragment : step 5 fragment 생성
                MakerStep5Fragment step5Fragment = MakerStep5Fragment.newInstance(
                        chestEventResultSet,
                        shoulderEventResultSet,
                        latEventResultSet,
                        upperBodyEventResultSet,
                        armEventResultSet,
                        etcEventResultSet
                );

                // [lv/C]FragmentTransaction : step 5 fragment 로 이동
                FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, step5Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return null;
            }
        };
    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView(ArrayList<Event> selectedEventArrayList) {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.selectedEventListWrapper.setLayoutManager(layoutManager);

        // [iv/C]SelectedEventItemRvAdapter : recyclerView 의 adapter 생성
        Step4EventRvAdapter adapter = new Step4EventRvAdapter(selectedEventArrayList);

        // [iv/C] : recyclerView 의 adapter setting
        this.selectedEventListWrapper.setAdapter(adapter);

    } // End of method [initWidgetOfRecyclerView]

}
