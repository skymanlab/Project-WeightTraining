package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step3D1SectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step3D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step3D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String SELECTED_MUSCLE_AREA_LIST = "selectedMuscleAreaList";

    // instance variable
    private boolean[] isSelectedMuscleAreaList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step3D1SectionManager sectionManager;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<String> fragmentMuscleAreaList;

    // constructor
    public Step3D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isSelectedMuscleAreaList STEP 2-1 에서 선택한 muscle area list
     * @return A new instance of fragment Step3D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3D1Fragment newInstance(boolean[] isSelectedMuscleAreaList) {

        Step3D1Fragment fragment = new Step3D1Fragment();

        Bundle args = new Bundle();
        args.putBooleanArray(SELECTED_MUSCLE_AREA_LIST, isSelectedMuscleAreaList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreate] ";

        if (getArguments() != null) {
            this.isSelectedMuscleAreaList = getArguments().getBooleanArray(SELECTED_MUSCLE_AREA_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step3_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager : step 3-1 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_training_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step3D1SectionManager : step 3-1 content section manager
        this.sectionManager = new Step3D1SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

        // [iv/C]ArrayList<DirectSelectionFragment> : 객체 생성
        this.fragmentArrayList = new ArrayList<>();

        // [iv/C]ArrayList<String> : 객체 생성
        this.fragmentMuscleAreaList = new ArrayList<>();

        // [lv/C]DatabaseReference : Firebase Database 의 event 항목을 참조하기위한 객체 생성
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [method] :
        loadContent(db);

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

                // [iv/C]Step3D1SectionManager : step 3-1 content section manager 의 fragmentArrayList, isCompletedFragmentArrayList set
                sectionManager.setFragmentArrayList(fragmentArrayList);
                sectionManager.setCompletedFragmentArrayList(true);

                // [iv/C]Step3D1SectionManager : step 2-1 content section manager 의 viewPager 를 fragmentArrayList 와 fragmentMuscleArea 으로 Fragment 를 표시하기
                sectionManager.initViewPager(Step3D1Fragment.this, fragmentArrayList, fragmentMuscleAreaList);

                // [iv/C]ContentLoadingProgressBar : step 3-1 content section manager 의 progressBar 을 GONE
                sectionManager.getProgressBar().setVisibility(ContentLoadingProgressBar.GONE);
                
                // "데이터를 가지고 왔습니다." Toast 메시지 표시
                Toast.makeText(getContext(), "데이터를 가지고 왔습니다.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                // [check 1] : error 가 발생하면 -> muscleArea 에 해당하는 Fragment 를 만들지만, eventArrayList 는 null 인 fragment 객체를 생성한다.
                if (error != null) {

//                    // [lv/C]DirectSelectionFragment : muscleArea 와 eventArrayList(=null) 인 Fragment 객체 생성
//                    DirectSelectionFragment fragment = DirectSelectionFragment.newInstance(muscleArea, null);
//
//                    // [iv/C]ArrayList<DirectSelectionFragment> : 위에서 생성한 error 형태의 fragment 객체를 추가한다.
//                    fragmentArrayList.add(fragment);
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

            // [iv/C]ArrayList<String> : fragment 의 muscleArea 도 저장
            fragmentMuscleAreaList.add(DataManager.convertHanguleOfMuscleArea(muscleArea));

        } // [check 1]

    } // End of method [makeFragment]

}