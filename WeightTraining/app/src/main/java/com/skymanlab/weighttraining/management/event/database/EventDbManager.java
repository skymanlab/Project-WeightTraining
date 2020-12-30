package com.skymanlab.weighttraining.management.event.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

import java.util.ArrayList;

public class EventDbManager {
//
//    // constant
//    public static final String EVENT_KEY = "event";
//
//    /**
//     * [method] CHEST 에 해당하는 목록 가져오기
//     *
//     */
//    public static ArrayList<Event> loadContent (String uid, MuscleArea muscleArea) {
//
//        // [lv/C]ArrayList<Event> : 해당 muscleArea 의 목록을 가져온다.
//        ArrayList<Event> eventArrayList = new ArrayList<>();
//
//        // [lv/C]DatabaseReference : 'event/' 의 내용을 가져오기 위한
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference(EVENT_KEY);
//
//        // [lv/C]Query : 'event/uid/muscleArea' 의 목록을 가져오기 위한 query 만들기
//        Query query = db.child(uid).child(muscleArea.toString());
//
//        // [lv/C]Query : 해당 query 로 데이터 목록 가져오기
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                // [cycle 1] :
//                for (DataSnapshot search : snapshot.getChildren()) {
//
//                    // [lv/C]String : key 값 가져오기
//                    String key = search.getKey();
//
//                    // [lv/C]Event : 한 목록 데이터 가져오기
//                    Event event = search.getValue(Event.class);
//                    event.setKey(key);
//
//                    // [lv/C]ArrayList<Event> : 위에서 가져온 event 를 추가하기
//                    eventArrayList.add(event);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
}
