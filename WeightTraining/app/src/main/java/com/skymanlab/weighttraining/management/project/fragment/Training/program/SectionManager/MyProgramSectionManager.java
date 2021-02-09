package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;

import androidx.annotation.NonNull;
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
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter.EachMuscleAreaListRvAdapter;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter.MyProgramRvAdapter;

import java.util.ArrayList;

public class MyProgramSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MyProgramSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private RecyclerView recyclerView;

    // instance variable
    private ArrayList<Program> programArrayList;

    // instance variable
    private LinearLayoutManager layoutManager;
    private MyProgramRvAdapter adapter;

    // constructor
    public MyProgramSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]RecyclerView : list connect
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_my_program_recycler_view);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[loadContent] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<< my program >");

        this.programArrayList = new ArrayList<>();
        loadContent();
    }


    private void loadContent() {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("program");

        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot search : snapshot.getChildren()) {

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

                    // muscle area list
                    ArrayList<MuscleArea> muscleAreaList = new ArrayList<>();
                    for (DataSnapshot searchMuscleArea : search.child("muscleAreaList").getChildren()) {
                        muscleAreaList.add(MuscleArea.valueOf(searchMuscleArea.getValue(String.class)));
                    }
                    program.setMuscleAreaList(muscleAreaList);

                    // program 추가하기
                    programArrayList.add(program);

                    // 받아온 데이터로
                    initWidgetOfRecyclerView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void displayProgramArrayList() {
        final String METHOD_NAME = "[loadContent] ";

        for (int index = 0; index < programArrayList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> nick name = " + programArrayList.get(index).getNickName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> total set number = " + programArrayList.get(index).getTotalSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> total event number = " + programArrayList.get(index).getTotalEventNumber());

            for (int indexN = 0; indexN < programArrayList.get(index).getMuscleAreaList().size(); indexN++) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> 의 muscle area index <<< " + indexN + " >>>" + programArrayList.get(index).getMuscleAreaList().get(indexN));
            }
        }
    }


    /**
     * recyclerView 의 layout manager 와 adapter 를 설정하는 초기작업 실행
     */
    private void initWidgetOfRecyclerView() {

        // [lv/C]LinearLayoutManager : recyclerView 의 LayoutManager 를 생성 / 1차원으로 표현하기 위해서 LinearLayoutManager 생성
        this.layoutManager = new LinearLayoutManager(getFragment().getActivity());

        // [iv/C]RecyclerView : 위의 layoutManager 를 설정하기
        this.recyclerView.setLayoutManager(layoutManager);

        // [iv/C]EachListRvAdapter : recyclerView 의 adapter 생성
        this.adapter = new MyProgramRvAdapter(getFragment(), programArrayList);

        // [iv/C] : recyclerView 의 adapter setting
        this.recyclerView.setAdapter(this.adapter);

    } // End of method [initWidgetOfRecyclerView]

}
