package com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter;

import android.app.Activity;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
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
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EventListRvManager {

    // constant
    private static final String CLASS_NAME = "[FTA]_EventListRvManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String DB_TARGET = "event";

    // instance variable
    private RecyclerView recyclerView;
    private EventListRvAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // instance variable
    private ContentLoadingProgressBar progressBar;
    private ImageButton add;

    // instance variable
    private Activity activity;
    private FragmentManager fragmentManager;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private MuscleArea muscleArea;

    // constructor
    private EventListRvManager(Builder builder) {
        this.activity = builder.activity;
        this.fragmentManager = builder.fragmentManager;
        this.muscleArea = builder.muscleArea;
        this.recyclerView = builder.recyclerView;
        this.progressBar = builder.progressBar;
        this.add = builder.add;
    }

    //

    /**
     * [method] RecyclerView setting init
     */
    public void initRecyclerView() {

        // [iv/C]ArrayList<Event> : eventArrayList 객체 생성
        this.eventArrayList = new ArrayList<>();

        // [iv/C]RecyclerView.LayoutManager : LinearLayoutManager is used here, this will layout the elements in a similar fashion to the way ListView would layout elements. The RecyclerView.LayoutManager defines how elements are laid out.
        this.layoutManager = new LinearLayoutManager(this.activity);

        // [method] : recyclerView 의 layoutManager 연결하는 과정 진행
        setRecyclerViewLayoutManager();

        // [iv/C]EventListRvAdapter : adapter 객체 생성
        this.adapter = new EventListRvAdapter(eventArrayList, this.activity, this.fragmentManager);

        // [iv/C]RecyclerView : recyclerView 와 adapter 를 연결하기
        this.recyclerView.setAdapter(this.adapter);

        // [method] : Firebase 의 database 를 감시하면서 변경된 내용이 있을 때 가져와 recyclerView 에 mapping 하는 과정진행
        loadChangedContent();

    }


    /**
     * [method] Set RecyclerView's Layoutmanager to the one given.
     */
    private void setRecyclerViewLayoutManager() {

        // [lv/i]scrollPosition :
        int scrollPosition = 0;

        // [check 1] : If a layout manager has already been set, get current scroll position.
        if (this.recyclerView.getLayoutManager() != null) {

            // [lv/i]scrollPosition :
            scrollPosition = ((LinearLayoutManager) this.recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        } // [check 1]

        // [iv/C]RecyclerView :
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.scrollToPosition(scrollPosition);

    } // End of method [setRecyclerViewLayoutManager]


    /**
     * [method] Firebase Database 에서 event/uid/CHEST 의 항목 가져오기
     */
    private void loadChangedContent() {

        final String METHOD_NAME = "[loadChangedContent] ";

        // [lv/C]DatabaseReference :
        DatabaseReference db = FirebaseDatabase.getInstance().getReference(DB_TARGET);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++ uid =" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        // [lv/C]Query :
        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(this.muscleArea.toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // [iv/C]ContentLoadingProgressBar : 모든 데이터를 가져오기 전이므로 ProgressBar 로 가져오는 중이라는 걸 알려줌 VISIBLE
                progressBar.setVisibility(ContentLoadingProgressBar.VISIBLE);

                // [iv/C]ArrayList<Event> : eventArrayList 의  모든 데이터 삭제
                eventArrayList.clear();

                // [cycle 1] : snapshot 의 자식 데이터를 가져오기
                for (DataSnapshot search : snapshot.getChildren()) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">+>++>+>++>++>++>+> snapshot data = " + search);

                    // [lv/C]Event : search 에서 각 데이터를 가져와서 Event 객체로 만듬 // search 에서 key 도 가져와서 설정한다.
                    Event data = search.getValue(Event.class);
                    data.setKey(search.getKey());

                    // [iv/C]ArrayList<Event> : 위에서 가져온 Event 객체를 추가한다.
                    eventArrayList.add(data);

                } // [cycle 1]

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, " 가져온 데이터에 대해서 알아보자!!!!!!!!!!!!!!");
                for (int index = 0; index < eventArrayList.size(); index++) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<" + index + ">>>>  의 데이터는 = " + eventArrayList.get(index).getEventName());
                }

                // [iv/C]EventListRvAdapter : adapter 에 데이터가 변경되었다고 알림
                adapter.notifyDataSetChanged();

                // [iv/C]ContentLoadingProgressBar : 모든 데이터를 가져왔으므로 GONE
                progressBar.setVisibility(ContentLoadingProgressBar.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    } // End of method [loadChangedContent]


    public static class Builder {

        // instance variable
        private Activity activity;
        private FragmentManager fragmentManager;
        private MuscleArea muscleArea;

        // instance variable
        private RecyclerView recyclerView;
        private ContentLoadingProgressBar progressBar;
        private ImageButton add;

        // constructor
        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public Builder setMuscleArea(MuscleArea muscleArea) {
            this.muscleArea = muscleArea;
            return this;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public Builder setProgressBar(ContentLoadingProgressBar progressBar) {
            this.progressBar = progressBar;
            return this;
        }

        public Builder setAdd(ImageButton add) {
            this.add = add;
            return this;
        }

        public EventListRvManager build() {
            return new EventListRvManager(this);
        }

    }

}
