package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.notice.Notice;
import com.skymanlab.weighttraining.management.project.ApiManager.NetworkStateChecker;
import com.skymanlab.weighttraining.management.project.ApiManager.NoticeManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.More.dialog.NoticeRvAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoticeSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] NoticeSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private TextView indicator;
    private RecyclerView recyclerView;

    // constructor
    public NoticeSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ TextView | indicator ]
        this.indicator = (TextView) getView().findViewById(R.id.f_notice_indicator);

        // [ RecyclerView | recyclerView ]
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_notice_recyclerView);

    }

    @Override
    public void initWidget() {

        // 네트워크 연결이 되지 않았을 때
        if (!NetworkStateChecker.checkActiveNetwork(getFragment().getContext())) {

            indicator.setVisibility(View.VISIBLE);
            indicator.setText(R.string.network_state_notConnectNetwork);

            return;
        }

        // NoticeManager 
        // loadContent() 메소드와 OnNoticeListener 를 사용하여 적절하게 화면에 표시
        NoticeManager.loadContent(
                new NoticeManager.OnNoticeListener() {
                    @Override
                    public void showNotice(ArrayList<Notice> noticeArrayList) {

                        // indicator 숨기기
                        indicator.setVisibility(View.GONE);

                        // 데이터베이스에서 가져온 '공지사항 목록 리스트' recyclerView 에 표시하기
                        initWidgetOfRecyclerView(noticeArrayList);

                    }

                    @Override
                    public void noData() {

                        // indicator
                        indicator.setVisibility(View.VISIBLE);
                        indicator.setText(R.string.f_notice_noData);

                    }
                }
        );

    }

    private void initWidgetOfRecyclerView(ArrayList<Notice> noticeArrayList) {

        // Adapter
        NoticeRvAdapter adapter = new NoticeRvAdapter(noticeArrayList);

        // recyclerView : adapter 세팅
        recyclerView.setAdapter(adapter);
    }


    public void loadContent() {
        final String METHOD_NAME = "[loadContent] ";

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
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 데이터가 확인 > 데이터가 업서요.");

                                    // indicator
                                    indicator.setVisibility(View.VISIBLE);
                                    indicator.setText(R.string.f_notice_noData);

                                    return;
                                }

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > 내용 = " + snapshot);

                                // 읽어온 데이터를 담을 ArrayList 를 생성하구요.
                                ArrayList<Notice> noticeArrayList = new ArrayList<>();

                                // 하위 노드의 데이터를 읽어와요.
                                for (DataSnapshot search : snapshot.getChildren()) {

                                    // Notice 객체로 가져와요
                                    Notice notice = search.getValue(Notice.class);

                                    // noticeArrayList 에 위에서 가져온 notice 를 추가해요.
                                    noticeArrayList.add(notice);
                                }

                                // 읽어온 내용을 recyclerView 에 표시하기
                                initWidgetOfRecyclerView(noticeArrayList);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                if (error != null) {
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 에러 > 내용 = " + error.getMessage());

                                }
                            }
                        }
                );
    }


    private ArrayList<Notice> sampleData() {

        ArrayList<Notice> noticeArrayList = new ArrayList<>();

        Notice notice = new Notice();
        notice.setNumber(1);
        notice.setDate("2021년 2월 24일");
        notice.setType("업데이트");
        notice.setTitle("첫번째 업데이트");
        notice.setMessage("업데이트 내용을 확인합니다.");

        noticeArrayList.add(notice);
        noticeArrayList.add(notice);
        noticeArrayList.add(notice);

        return noticeArrayList;
    }
}
