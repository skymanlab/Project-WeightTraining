package com.skymanlab.weighttraining.management.event.listview;

import android.app.Activity;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.RightDataChecker;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EventItemLvManager {

    // constructor
    private static final String CLASS_NAME = "[EL] EventItemLvManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private EventItemLvAdapter eventItemLvAdapter;

    // instance variable
    private Activity activity;
    private FragmentManager fragmentManager;
    private ListView targetListView;
    private ProgressBar progressBar;

    // instance variable
    private String uid;
    private MuscleArea muscleArea;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private ArrayList<Boolean> isFolded;

    // constructor
    public EventItemLvManager(Activity activity, FragmentManager fragmentManager, ListView targetListView, ProgressBar progressBar, String uid, MuscleArea muscleArea) {
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.targetListView = targetListView;
        this.progressBar = progressBar;
        this.uid = uid;
        this.muscleArea = muscleArea;
        this.eventArrayList = new ArrayList<>();
        this.isFolded = new ArrayList<>();
    }


    /**
     * [method] eventArrayList 를 EventItemLvAdapter 를 통해 targetListView 에 mapping 하기
     */
    public void setListView() {

        final String METHOD_NAME = "[setListView] ";

        // [check 2] : 옳은 muscleArea 데이터이다.
        if (RightDataChecker.checkWhetherRightMuscleArea(this.muscleArea)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_3/true : targetListView 에 데이터를 뿌려줄거야! <=");
            // [iv/C]EventItemLvAdapter : ListView adapter 생성
            this.eventItemLvAdapter = new EventItemLvAdapter(this.activity, this.fragmentManager, this.uid);
//            this.eventItemLvAdapter.setDataOfEventArrayList(this.eventArrayList);
            this.eventItemLvAdapter.setEventArrayList(this.eventArrayList);
            this.eventItemLvAdapter.setIsFolded(this.isFolded);

            // [iv/C]ListView : targetListView 와 eventItemLvAdapter 를 연결하기
            this.targetListView.setAdapter(this.eventItemLvAdapter);

            // [lv/C]DatabaseReference :
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

            // [lv/C]Query :
//            Query query = db.child(this.uid).child(this.muscleArea.toString());
            Query query = db.child("W9QHPdGS5kMzeIyL4N0u8JCD5Wj1").child(this.muscleArea.toString());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // [iv/C]ArrayList<Event> : 기존의 eventArrayList 의 모든 항목 삭제한다.
                    eventArrayList.clear();

                    // [iv/C]ArrayList<Boolean> : 기존의 isFolded 의 모든 항목을 삭제한다.
                    isFolded.clear();

                    // [cycle 1] : 읽어온 내용 가져오기
                    for (DataSnapshot searchData : snapshot.getChildren()) {

                        // [lv/C]Event :
                        Event data = searchData.getValue(Event.class);
                        data.setKey(searchData.getKey());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>> key = " + searchData.getKey());

                        // [iv/C]ArrayList<Event> : 변경된 내용을 추가하기
                        eventArrayList.add(data);

                        // [iv/C]ArrayList<Boolean> : 위의 eventArrayList 와 대응되는 isFolded 를 true 로 설정한다.
                        isFolded.add(true);

                    } // [cycle 1]

                    // [lv/C]EventItemLvAdapter :
//                    eventItemLvAdapter.setDataOfEventArrayList(eventArrayList);

                    // [iv/C]ArrayList<Event> : adapter 변경된 내용 적용하기
                    eventItemLvAdapter.notifyDataSetChanged();

                    // [iv/C]ListView : targetListView 을 VISIBLE 로
                    targetListView.setVisibility(ListView.VISIBLE);

                    // [iv/C]ProgressBar : progressBar 를 GONE 으로
                    progressBar.setVisibility(ProgressBar.GONE);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : 정확하지 않은 muscle 데이터입니다. <=");
        } // [check 2]

    } // End of method [setListView]

}
