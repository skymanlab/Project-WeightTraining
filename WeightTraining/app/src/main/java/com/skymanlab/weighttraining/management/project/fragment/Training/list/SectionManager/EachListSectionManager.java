package com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter.EachListRvAdapter;

import java.util.ArrayList;

public class EachListSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTLS] EachListSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Fragment fragment;
    private MuscleArea muscleArea;

    // instance variable
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private ImageButton add;

    // instance variable
    private EachListRvAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Event> eventArrayList;


    // constructor
    public EachListSectionManager(Activity activity, View view, FragmentManager fragmentManager, Fragment fragment, MuscleArea muscleArea) {
        super(activity, view, fragmentManager);
        this.fragment = fragment;
        this.muscleArea = muscleArea;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]RecyclerView : recyclerView mapping
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_each_list_recycler_view);

        // [iv/C]ContentLoadingProgressBar : progressBar mapping
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_each_list_progress_bar);

        // [iv/C]ImageButton : add mapping
        this.add = (ImageButton) getView().findViewById(R.id.f_each_list_bt_add);

    }

    @Override
    public void initWidget() {

        // [method] : adapter 생성 후 recyclerView 에 설정하기
        initRecyclerView();

        // [method] : DB 에서 데이터를 가져오는 과정 진행
        loadContentByMuscleArea(this.muscleArea);

        // [iv/C]ImageButton : add click listener
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initRecyclerView() {

        // [iv/C]ArrayList<Event> : eventArrayList 생성
        this.eventArrayList = new ArrayList<>();

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        this.layoutManager = new LinearLayoutManager(this.getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.recyclerView.setLayoutManager(layoutManager);

        // [iv/C]EachListRvAdapter : recyclerView 의 adapter 생성
        this.adapter = new EachListRvAdapter(eventArrayList, getActivity(), getFragmentManager());

        // [iv/C] : recyclerView 의 adapter setting
        this.recyclerView.setAdapter(this.adapter);

    } // End of method [initRecyclerView]


    /**
     * Firebase database 에서 event/&uid%/&muscleArea% 의 모든 항목을 가져와서 초기설정이 완료된 recyclerView 에 나타내기
     * 처음 실행된 순간 데이터를 가져오고, 만약 데이터가 변경이 되면 다시 모든 항목을 가져와서 변경된 데이터를 recyclerView 에 나타낸다.
     *
     * @param muscleArea 근육 부위
     */
    private void loadContentByMuscleArea(MuscleArea muscleArea) {
        final String METHOD_NAME = "[loadContentByMuscleArea] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------> uid =" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "------> muscle area = " + muscleArea.toString());

        // [lv/C]DatabaseReference : event 하위 목록을 검색
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        // [lv/C]Query : event 하위 목록 중 uid 와 muscleArea 로 검색하기 위한 query 작성 / 검색한 항목이 변경될 때마다 감시하는 Thread 작성
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(muscleArea.toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-------> snapshot content = " + snapshot);

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 데이터를 가져오기 전 UI 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]Activity : progressBar 를 이용하여 데이터를 가져오는 중이라는 걸 알림 / GONE -> VISIBLE / workThread 가 아닌 UI Thread 에서 수행
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // [iv/C]ContentLoadingProgressBar : progressBar 를 GONE -> VISIBLE 로
                        progressBar.setVisibility(View.VISIBLE);

                    }
                });


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= DB 에서 데이터 가져오기 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]ArrayList<Event> : 모든 종목을 추가하기 전에 기존의 데이터 clear
                eventArrayList.clear();

                // [cycle 1] : 해당 muscleArea 의 모든 종목을 검색하여 eventArrayList 에 추가하기
                for (DataSnapshot search : snapshot.getChildren()) {

                    // [lv/C]Event : 모든 종목이 있는 search 에서 각각의 종목을 Event 객체로 가져오기 / search 에서 각각의 종목을 구분하는 key 를 Event 객체의 key 로 설정하기
                    Event data = search.getValue(Event.class);
                    data.setKey(search.getKey());

                    // [iv/C]ArrayList<Event> : 위에서 가져온 Event 객체를 eventArrayList 에 추가하기
                    eventArrayList.add(data);

                } // [cycle 1]

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, " 가져온 데이터에 대해서 알아보자!!!!!!!!!!!!!!");
                for (int index = 0; index < eventArrayList.size(); index++) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>>>  의 데이터는 = " + eventArrayList.get(index).getEventName());
                }


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= 가져온 데이터로 후 처리하기 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]EachListRvAdapter : adapter 의 데이터들이 변경되었을음 알리기
                adapter.notifyDataSetChanged();

                // [iv/C]Activity : progressBar 를 이용하여 데이터 가져오기가 완료되었다는 걸 알림 / VISIBLE -> GONE / workThread 가 아닌 UI Thread 에서 수행
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // [iv/C]ContentLoadingProgressBar : progressBar 를 GONE -> VISIBLE 로
                        progressBar.setVisibility(View.GONE);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    } // End of method [loadContentByMuscleArea]

}
