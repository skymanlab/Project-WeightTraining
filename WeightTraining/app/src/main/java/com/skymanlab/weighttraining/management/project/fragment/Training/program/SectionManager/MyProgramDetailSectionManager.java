package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.skymanlab.weighttraining.management.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.DetailProgramItem;

import java.util.ArrayList;

public class MyProgramDetailSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MyProgramDetailSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private Program program;

    // instance variable
    private TextView nickName;
    private TextView muscleAreaList;
    private TextView totalEventNumber;
    private TextView totalSetNumber;
    private LinearLayout detailProgramListWrapper;

    // constructor
    public MyProgramDetailSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public void connectWidget() {

        // [ TextView | nickName ]
        this.nickName = (TextView) getView().findViewById(R.id.f_myProgramDetail_nick_name);

        // [ TextView | muscleAreaList ]
        this.muscleAreaList = (TextView) getView().findViewById(R.id.f_myProgramDetail_muscle_area_list);

        // [ TextView | totalEventNumber ]
        this.totalEventNumber = (TextView) getView().findViewById(R.id.f_myProgramDetail_totalEventNumber);

        // [ TextView | totalSetNumber ]
        this.totalSetNumber = (TextView) getView().findViewById(R.id.f_myProgramDetail_totalSetNumber);

        // [ LinearLayout | detailProgramListWrapper ]
        this.detailProgramListWrapper = (LinearLayout) getView().findViewById(R.id.f_myProgramDetail_detailProgramList_wrapper);

    }

    @Override
    public void initWidget() {

        // init widget of nick name
        initWidgetOfNickName();

        // init widget of muscle area list
        initWidgetOfMuscleAreaList();

        // init widget of total event number
        initWidgetOfTotalEventNumber();

        // init widget of total set number
        initWidgetOfTotalSetNumber();

        // 데이터 가져오기 및 화면에 표시
        loadContent();
    }

    private void initWidgetOfNickName() {

        // [ TextView | totalEventNumber ] text
        this.nickName.setText(
                program.getNickName()
        );
    }

    private void initWidgetOfMuscleAreaList() {

        // [ TextView | totalSetNumber ] text
        this.muscleAreaList.setText(
                DataFormatter.setMuscleAreaList(
                        program.getMuscleAreaList()
                )
        );
    }

    private void initWidgetOfTotalEventNumber() {

        // [ TextView | totalEventNumber ] text
        this.totalEventNumber.setText(
                DataFormatter.setTotalEventNumberFormat(
                        program.getTotalEventNumber()
                )
        );

    }

    private void initWidgetOfTotalSetNumber() {

        // [ TextView | totalSetNumber ] text
        this.totalSetNumber.setText(
                DataFormatter.setTotalSetNumberFormat(
                        program.getTotalSetNumber()
                )
        );
    }

    private void initWidgetOfDetailProgramListWrapper(ArrayList<DetailProgram> detailProgramList) {

        if (0 < detailProgramList.size()) {

            LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for (int index = 0; index < detailProgramList.size(); index++) {

                addViewOfDetailProgramListWrapper(
                        newInstanceOfDetailProgramItem(
                                inflater,
                                detailProgramList.get(index)
                        )
                );
            }
        }
    }


    private void loadContent() {
        final String METHOD_NAME = "[loadContent] ";

        // database 에서 데이터 가져오기
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("program");

        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())        // uid
                .child(program.getKey())                                                    // program key
                .child(Program.DETAIL_PROGRAM_LIST);                                        // detailProgramList
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // 해당 프로그램의 세부 내용을 가져오기 위해서
                // program 객체의 내용으로
                // 해당 프로그램의 DetailProgram 리스트를 가저온다.
                // 데이터가 변경될 때마다 새로운 내용을 담아야 하므로 
                ArrayList<DetailProgram> detailProgramList = new ArrayList<>();

                for (DataSnapshot search : snapshot.getChildren()) {

                    DetailProgram detailProgram = search.getValue(DetailProgram.class);
                    detailProgramList.add(detailProgram);

                }

                // 위에서 가져온 데이터를 바탕으로 화면 내용 구성
                initWidgetOfDetailProgramListWrapper(detailProgramList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private DetailProgramItem newInstanceOfDetailProgramItem(LayoutInflater inflater, DetailProgram detailProgram) {
        return new DetailProgramItem.Builder()
                .setInflater(inflater)
                .setDetailProgram(detailProgram)
                .newInstance()
                .createItem();
    }

    private void addViewOfDetailProgramListWrapper(DetailProgramItem detailProgramItem) {
        this.detailProgramListWrapper.addView(detailProgramItem.getItem());
    }


    private void displayInfoOfDetailProgramList(ArrayList<DetailProgram> detailProgramList) {
        final String METHOD_NAME = "[displayInfoOfDetailProgramList] ";

        for (int index = 0; index < detailProgramList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getOrder = " + detailProgramList.get(index).getOrder());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getMuscleArea = " + detailProgramList.get(index).getMuscleArea());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getEventKey = " + detailProgramList.get(index).getEventKey());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getEventName = " + detailProgramList.get(index).getEventName());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getSetNumber = " + detailProgramList.get(index).getSetNumber());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getRestTimeMinute = " + detailProgramList.get(index).getRestTimeMinute());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getRestTimeSecond = " + detailProgramList.get(index).getRestTimeSecond());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
}
