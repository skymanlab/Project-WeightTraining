package com.skymanlab.weighttraining.management.project.ApiManager;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.management.notice.Notice;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class NoticeManager {

    public static void loadContent(OnNoticeListener noticeListener) {

        // Firebase - realtime database 에서 데이터를 읽어와요.
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_NOTICE)
                .child(FirebaseConstants.DATABASE_NODE_NOTICE_LIST_OF_NOTICE)
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                // 하위 노드에 데이터가 없다면 종료!
                                if (snapshot.getValue() == null) {

                                    // 공지사항 리스너를 통해 데이터가 없는 경우에 해당하는 화면을 설정한다.
                                    noticeListener.noData();
                                    return;
                                }

                                // 읽어온 데이터를 담을 ArrayList 를 생성하구요.
                                ArrayList<Notice> noticeArrayList = new ArrayList<>();

                                // 하위 노드의 데이터를 읽어와요.
                                for (DataSnapshot search : snapshot.getChildren()) {

                                    // Notice 객체로 가져와요
                                    Notice notice = search.getValue(Notice.class);

                                    // noticeArrayList 에 위에서 가져온 notice 를 추가해요.
                                    noticeArrayList.add(notice);
                                }

                                // 공지사랑 리스너를 통해
                                // 데이터가 있는 경우에 해당하는 화면을 설정한다.
                                noticeListener.showNotice(noticeArrayList);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                if (error != null) {

                                }
                            }
                        }
                );
    }



    public static void saveContent(String type,
                                   String date,
                                   String title,
                                   String message) {

        // 경로 : notice/
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_NOTICE)
                .runTransaction(
                        new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                                // 경로 : notice/noticeNumberCounter 의 value 를 읽어오기
                                Integer counter = currentData.child(FirebaseConstants.DATABASE_NODE_NOTICE_NUMBER_COUNTER_OF_NOTICE).getValue(Integer.class);

                                // 만약 읽어온 데이터가 없다면
                                if (counter == null) {

                                    // 데이터가 없으면 1번
                                    counter = 1;

                                } else {

                                    // 데이터가 있으면 기존의 값에 +1
                                    counter += 1;

                                }

                                // 하위 노드에 저장할 데이터
                                HashMap<String, Object> saveContent = new HashMap<>();
                                saveContent.put(Notice.TYPE, type);
                                saveContent.put(Notice.DATE, date);
                                saveContent.put(Notice.TITLE, title);
                                saveContent.put(Notice.MESSAGE, message);
                                saveContent.put(Notice.NUMBER, counter);

                                // 저장
                                // 경로 : notice/noticeNumberCounter
                                currentData.child(FirebaseConstants.DATABASE_NODE_NOTICE_NUMBER_COUNTER_OF_NOTICE).setValue(counter);
                                // 경로 : notice/noticeList 의 하위 노드에 'counter' 를 키로 하여 저장
                                currentData.child(FirebaseConstants.DATABASE_NODE_NOTICE_LIST_OF_NOTICE).child(counter+"").setValue(saveContent);

                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

                            }
                        }
                );
    }


    // ====================================================== interface ======================================================
    public interface OnNoticeListener{
        void showNotice(ArrayList<Notice> noticeArrayList);
        void noData();
    }
}
