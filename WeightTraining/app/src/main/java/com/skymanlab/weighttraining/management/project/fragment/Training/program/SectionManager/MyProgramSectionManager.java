package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;
import android.widget.TextView;

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
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.ApiManager.NetworkStateChecker;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.MyProgramRvAdapter;

import java.util.ArrayList;

public class MyProgramSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MyProgramSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private TextView indicator;
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;

    // instance variable
    private LinearLayoutManager layoutManager;
    private MyProgramRvAdapter adapter;

    // constructor
    public MyProgramSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ TextView | indicator ]
        this.indicator = (TextView) getView().findViewById(R.id.f_myProgram_indicator);

        // [ RecyclerView | recyclerView ]
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_myProgram_recyclerView);

        // [ ContentLoadingProgressBar | progressBar ]
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_myProgram_progressBar);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[loadContent] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<< my program >");

        if (!NetworkStateChecker.checkActiveNetwork(getFragment().getContext())) {

            // indicator
            indicator.setText(R.string.network_state_notConnectNetwork);

            // progressBar : INVISIBLE
            progressBar.setVisibility(View.INVISIBLE);

            return;
        }

        // 데이터베이스에서 program 리스트 가져오기
        loadContent();

    }

    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView(ArrayList<Program> programArrayList) {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        this.layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.recyclerView.setLayoutManager(layoutManager);

        // [iv/C]EachListRvAdapter : recyclerView 의 adapter 생성
        this.adapter = new MyProgramRvAdapter(getFragment(), programArrayList);

        // [iv/C] : recyclerView 의 adapter setting
        this.recyclerView.setAdapter(this.adapter);

    } // End of method [initWidgetOfRecyclerView]


    private void loadContent() {
        final String METHOD_NAME = "[loadContent] ";

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference.child(FirebaseConstants.DATABASE_FIRST_NODE_PROGRAM)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< DataSnapshot > 내용 = " + snapshot);

                // snapshot 의 getValue() 가 null 이면
                // 내가 저장한 프로그램 데이터가 없다는 뜻이다.
                // 그러므로 저장된 데이터가 없다는 것을 사용자에게 알려준다.
                // indicator 로 사용자에게 알려준다.
                // progressBar 는 데이터를 읽어오는 중이라는 것을 알리는 역할이므로 필요하지 않은다.(GONE)
                if (snapshot.getValue() == null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 데이터 로드 > 데이터 없음");
                    // indicator
                    indicator.setText(R.string.f_myProgram_indication_noData);

                    // progressBar : GONE
                    progressBar.setVisibility(View.GONE);
                    return;
                }


                // 프로그램이 새롭게 갱신될 수 있으므로
                // 일단 program 리스트를 담을 ArrayList 를 새롭게 생성하여 읽어온 program 을 추가한다.
                // programArrayList 를 토대로 화면을 구성한다.
                ArrayList<Program> programArrayList = new ArrayList<>();
                for (DataSnapshot search : snapshot.getChildren()) {

                    // ============= 하나의 Program 정보를 담은 객체 생성 =============
                    // db 에 저장되어 있는 내용을 Program 객체에 저장
                    Program program = new Program();

                    // key
                    program.setKey(search.getKey());

                    // nick name
                    program.setNickName(search.child("nickName").getValue(String.class));

                    // total set number
                    program.setTotalSetNumber(search.child("totalSetNumber").getValue(Integer.class));

                    // total event number
                    program.setTotalEventNumber(search.child("totalEventNumber").getValue(Integer.class));

                    // muscle area list : 한 program 에는 여러개의 '근육 부위'가 포함될 수 있으므로 ArrayList 에 담는다.
                    ArrayList<MuscleArea> muscleAreaList = new ArrayList<>();
                    for (DataSnapshot searchMuscleArea : search.child("muscleAreaList").getChildren()) {
                        muscleAreaList.add(MuscleArea.valueOf(searchMuscleArea.getValue(String.class)));
                    }

                    // 위의 모든 근육 부위가 담긴 muscleAreaList 를 program 에 추가한다.
                    program.setMuscleAreaList(muscleAreaList);

                    // 위에서 만든 program 객체를 ArrayList 에 추가한다.
                    programArrayList.add(program);

                }

                // init widget : 모든 프로그램 리스트가 담긴 programArrayList 로 RecyclerView 화면을 구성한다.
                initWidgetOfRecyclerView(programArrayList);

                // progressBar : GONE ( 데이터를 읽어오고 있다는 것을 표현하는 것이다. 데이터를 읽어와서 화면에 표시했으므로 사라지면 된다.)
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void display(ArrayList<Program> programArrayList) {
        final String METHOD_NAME = "[display] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "========================================= 모든 프로그램 항목을 확인 =========================================");
        for (int index = 0; index < programArrayList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> nick name = " + programArrayList.get(index).getNickName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> total set number = " + programArrayList.get(index).getTotalSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> total event number = " + programArrayList.get(index).getTotalEventNumber());

            for (int indexN = 0; indexN < programArrayList.get(index).getMuscleAreaList().size(); indexN++) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> 의 muscle area index <<< " + indexN + " >>>" + programArrayList.get(index).getMuscleAreaList().get(indexN));
            }
        }
    }


}
