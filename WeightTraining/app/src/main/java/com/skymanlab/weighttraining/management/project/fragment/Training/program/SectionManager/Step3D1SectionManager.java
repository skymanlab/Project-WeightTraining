package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.DirectSelectionFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.Step3D1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.DirectPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Step3D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTS] Step3D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Fragment fragment;
    private boolean[] isSelectedMuscleAreaList ;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<String> fragmentTitleList;
    private boolean isCompletedFragmentCreation;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ContentLoadingProgressBar progressBar;

    // constructor
    public Step3D1SectionManager(Activity activity, View view, FragmentManager fragmentManager, Fragment fragment,  boolean[] isSelectedMuscleAreaList) {
        super(activity, view, fragmentManager);
        this.fragment = fragment;
        this.isSelectedMuscleAreaList = isSelectedMuscleAreaList;
        this.isCompletedFragmentCreation = false;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]TabLayout : mapping
        this.tabLayout = (TabLayout) getView().findViewById(R.id.f_program_step3_1_tab);

        // [iv/C]ViewPager2 : mapping
        this.viewPager = (ViewPager2) getView().findViewById(R.id.f_program_step3_1_pager);

        // [iv/C]ContentLoadingProgressBar : mapping
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_program_step3_1_progress_bar);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]StepProcessManager : step 3-1
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_THREE);
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.initWidget();

        // [iv/C]ArrayList<DirectSelectionFragment> : DirectSelectionFragment 를 담을 객체 생성
        this.fragmentArrayList = new ArrayList<>();

        // [iv/C]ArrayList<String> : 위의 fragmentArrayList 와 1:1 매핑되는 각 fragment 의 title 을 담을 객체 생성 (tabLayout 에 표시)
        this.fragmentTitleList = new ArrayList<>();

        // [lv/C]DatabaseReference : Firebase Database 의 event 항목을 참조하기위한 객체 생성
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [method] :
        loadContent(db);

    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        // [check 1] : isCompletedFragmentArrayList 가 true 일 때만
        if (this.isCompletedFragmentCreation) {

            // [iv/C]ArrayList<Event> : 각 fragment 들의 checkedEventArrayList 를 모두 담을 ArrayList
            ArrayList<Event> checkedAllEventArrayList = new ArrayList<>();

            // [cycle 1] : fragmentArrayList 의 내용을 확인한다.
            for (int index=0; index < fragmentArrayList.size() ; index++ ) {
                
                if (fragmentArrayList.get(index).getSectionManager() !=null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> sectionManager 가 생성되었습니다.");

                    // [lv/C]ArrayList<Event> : 각 fragment 에서 체크된 event 목록 가져오기
                    ArrayList<Event> checkedEventArrayList = fragmentArrayList.get(index).getSectionManager().getCheckedEventArrayList();

                    // [lv/C]ArrayList<Event> : 위의 데이터를 추가하기
                    checkedAllEventArrayList.addAll(checkedEventArrayList);

                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> eventArrayList 아직 화면이 아직 만들어지지 않았습니다. ");
                }

            } // [cycle 1]

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------------------------------------------------------------------------------------------");
            // [cycle 1] : checkedAllEventArrayList 확인하기
            for (int index=0; index < checkedAllEventArrayList.size() ; index++ ) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>> 선택된 eventName 은 ? " + checkedAllEventArrayList.get(index).getEventName());
            } // [cycle 1]

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 아직 siCompletedFragmentArrayList 가 완료 되지 않았습니다. <=");
        } // [check 1]

    }


    /**
     * [method] Firebase Database 에서 event/$uid/$MuscleArea 에 해당하는 내용을 muscleArea 으로 가져오기
     */
    private void loadContent(DatabaseReference db) {
        final String METHOD_NAME = "[loadContent] ";

        // muscleArea 에 해당하는 isSelectedMuscleArea 가 true 인 경우에만 수행되는 method 로
        // 무조건 수행되어 Fragment 를 만들어야 한다.

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "uid = " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        // [lv/C]Query : uid 로 query 만들기
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "snapshot 내용 = " + snapshot);

                // [method] : [0] CHEST 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[0], MuscleArea.CHEST);

                // [method] : [1] SHOULDER 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[1], MuscleArea.SHOULDER);

                // [method] : [2] LAT 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[2], MuscleArea.LAT);

                // [method] : [3] UPPER_BODY(or LEG) 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[3], MuscleArea.LEG);

                // [method] : [4] ARM 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[4], MuscleArea.ARM);

                // [method] : [5] ETC 의 선택된 항목일 때, Fragment 객체를 생성하여 fragmentArrayList 에 추가하는 과정 진행
                makeFragment(snapshot, isSelectedMuscleAreaList[5], MuscleArea.ETC);

                // [iv/b]isCom : fragment 생성 완료 되었음을 알림!
                isCompletedFragmentCreation = true;

                // [method] : 생성한 fragment 가 있는 fragmentArrayList 를 viewPager 와 연결하기
                initViewPager();

                // [iv/C]ContentLoadingProgressBar : GONE
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // [check 1] : error 가 발생하면 -> muscleArea 에 해당하는 Fragment 를 만들지만, eventArrayList 는 null 인 fragment 객체를 생성한다.
                if (error != null) {

                    // "데이터를 가져오는데 오류가 발생하였습니다." Toast 메시지 표시
                    Toast.makeText(getActivity(), getActivity().getString(R.string.f_program_step3_1_firebase_database_error_message), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * [method]
     */
    private void makeFragment(DataSnapshot snapshot, boolean isSelectedMuscleArea, MuscleArea muscleArea) {
        final String METHOD_NAME = "[makeFragment] ";

        // [check 1] : step 2-1 에서 선택된 항목만 Fragment 생성하고 fragmentArrayList 에 추가한다.
        if (isSelectedMuscleArea) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> muscleArea = " + muscleArea + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            // [lv/C]ArrayList<Event> : 하위 항목을 담을 ArrayList 를 생성
            ArrayList<Event> eventArrayList = new ArrayList<>();

            // [cycle 1] : event/$muscleArea 의 하위 항목을 가져온다.
            for (DataSnapshot search : snapshot.child(muscleArea.toString()).getChildren()) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>> key = " + search.getKey());
                // [lv/C]Event : 하위 항목을 Event 객체로 만들기, key 값도 설정하기
                Event data = search.getValue(Event.class);
                data.setKey(search.getKey());

                // [lv/C]ArrayList<Event> : 하위 항목을 추가한다.
                eventArrayList.add(data);

            } // [cycle 1]

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>> eventArrayList 의 size = " + eventArrayList.size());

            // [lv/C]DirectSelectionFragment : muscleArea 와 eventArrayList 로 Fragment 객체 생성
            DirectSelectionFragment fragment = DirectSelectionFragment.newInstance(muscleArea, eventArrayList);

            // [iv/C]ArrayList<DirectSelectionFragment> : 위에서 생성한 fragment 추가한다.
            fragmentArrayList.add(fragment);

            // [iv/C]ArrayList<String> : fragment 의 title 도 저장
            fragmentTitleList.add(DataManager.convertHanguleOfMuscleArea(muscleArea));

        } // [check 1]

    } // End of method [makeFragment]


    /**
     * [method] fragmentArrayList 의 fragment 로 tabLayout, viewPager 초기화 하기
     *
     */
    public void initViewPager() {

        // [lv/C]DirectPagerAdapter : viewPager 의 adapter 생성
        DirectPagerAdapter adapter = new DirectPagerAdapter(fragment, fragmentArrayList);

        // [iv/C]ViewPager2 : 위의 adapter 를 연결
        this.viewPager.setAdapter(adapter);

        // [lv/C]TabLayoutMediator : viewPager 와 tabLayout 을 1:1 매핑하고, title 붙이기
        TabLayoutMediator mediator = new TabLayoutMediator(this.tabLayout, this.viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                tab.setText(fragmentTitleList.get(position));

            }
        });
        mediator.attach();

    } // End of method [initViewPager]


}
