package com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
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
import com.skymanlab.weighttraining.management.event.dialog.EventDialog;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter.EachMuscleAreaListRvAdapter;

import java.util.ArrayList;

public class EachMuscleAreaListSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTLS] EachMuscleAreaListSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private MuscleArea muscleArea;

    // instance variable
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private ImageButton add;

    // instance variable
    private EachMuscleAreaListRvAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Event> eventArrayList;

    // constructor
    public EachMuscleAreaListSectionManager(Fragment fragment, View view, MuscleArea muscleArea) {
        super(fragment, view);
        this.muscleArea = muscleArea;
    }

    @Override
    public void connectWidget() {

        // [iv/C]RecyclerView : recyclerView connect
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_each_muscle_area_list_recycler_view);

        // [iv/C]ContentLoadingProgressBar : progressBar connect
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_each_muscle_area_list_progress_bar);

        // [iv/C]ImageButton : add connect
        this.add = (ImageButton) getView().findViewById(R.id.f_each_muscle_area_list_bt_add);

    }

    @Override
    public void initWidget() {

        // [method] : adapter 생성 후 recyclerView 에 설정하기
        initWidgetOfRecyclerView();

        // [method] : DB 에서 데이터를 가져오는 과정 진행
        loadContentByMuscleArea(this.muscleArea);

        // [iv/C]ImageButton : add click listener
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // [lv/C]String : EventDialog 에서 제공하는 setArguments() 메소드를 사용하여, arguments 항목을 설정한다.
                String[] arguments = EventDialog.setArguments(
                        getFragment().getString(R.string.custom_dialog_event_save_title),
                        getFragment().getString(R.string.custom_dialog_event_save_positive_title),
                        getFragment().getString(R.string.custom_dialog_event_save_negative_title));

                // [lv/C]EventDialog : event dialog 를 생성
                EventDialog dialog = EventDialog.newInstance(muscleArea, null, arguments);
                dialog.setDatabaseListener(new EventDialog.DatabaseListener() {
                    @Override
                    public void setDatabaseListener(Event event) {

                        // EventDialog 에서 제동하는 database 에 event 내용을 저장하는 메소드를 실행한다.
                        dialog.saveContent(event, EventDialog.MESSAGE_TYPE_SNACK_BAR);
                    }

                });
                dialog.setCancelable(false);
                dialog.showNow(getFragment().getActivity().getSupportFragmentManager(), null);
            }
        });

    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView() {

        // [iv/C]ArrayList<Event> : eventArrayList 생성
        this.eventArrayList = new ArrayList<>();

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        this.layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.recyclerView.setLayoutManager(layoutManager);

        // [iv/C]EachListRvAdapter : recyclerView 의 adapter 생성
        this.adapter = new EachMuscleAreaListRvAdapter(getFragment(), eventArrayList);

        // [iv/C] : recyclerView 의 adapter setting
        this.recyclerView.setAdapter(this.adapter);

    } // End of method [initWidgetOfRecyclerView]


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
                if (snapshot.getValue() == null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

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

                // [iv/C]ContentLoadingProgressBar : progressBar 를 GONE -> VISIBLE 로
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    } // End of method [loadContentByMuscleArea]

}
