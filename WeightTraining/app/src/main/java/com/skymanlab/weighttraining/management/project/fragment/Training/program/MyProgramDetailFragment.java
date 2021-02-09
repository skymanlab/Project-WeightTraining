package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MyProgramDetailSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MyProgramSectionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProgramDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProgramDetailFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MyProgramDetailFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;
    // constant
    private static final String PROGRAM = "program";

    // instance variable
    private Program program;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MyProgramDetailSectionManager sectionManager;

    // instance variable
    private ArrayList<DetailProgram> detailProgramArrayList;
    private HashMap<String, Event> eventList;

    // constructor
    public MyProgramDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyProgramDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProgramDetailFragment newInstance(Program program) {
        MyProgramDetailFragment fragment = new MyProgramDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PROGRAM, program);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.program = (Program) getArguments().getSerializable(PROGRAM);
        }

        detailProgramArrayList = new ArrayList<>();
        eventList = new HashMap<>();
        loadContent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_program_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String METHOD_NAME = "[onViewCreated] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program Key > " + program.getKey());

        // [FragmentTopBarManager] [topBarManager] this is 'maker step 1' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_my_program_detail_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [iv/C]MyProgramDetailSectionManager :
        this.sectionManager = new MyProgramDetailSectionManager(this, view);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 와 EndButtonListener 설정
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public AlertDialog setStartButtonClickListener() {
                getParentFragmentManager().popBackStack();
                return null;
            }
        });
        this.topBarManager.initWidgetOfStartButton(null);
    }


    private void loadContent() {
        final String METHOD_NAME = "[loadContent] ";

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("program");

        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(program.getKey())
                .child("detailProgramList");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Snapshot > 내용 = " + snapshot);

                for (DataSnapshot search : snapshot.getChildren()) {
                    DetailProgram detailProgram = search.getValue(DetailProgram.class);
                    detailProgram.setEventKey(search.getKey());

                    detailProgramArrayList.add(detailProgram);

                }

                for (int index = 0; index < detailProgramArrayList.size(); index++) {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getKey = " + detailProgramArrayList.get(index).getEventKey());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getOrder = " + detailProgramArrayList.get(index).getOrder());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getRestTimeMinute = " + detailProgramArrayList.get(index).getRestTimeMinute());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getRestTimeSecond = " + detailProgramArrayList.get(index).getRestTimeSecond());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< " + index + " > getSetNumber = " + detailProgramArrayList.get(index).getSetNumber());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadContentOfEvent(String key) {
        final String METHOD_NAME = "[loadContent] ";

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");

        Query query = db.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< snapshot > 내용 : " + snapshot);

                Event event = snapshot.getValue(Event.class);
                event.setKey(key);

                eventList.put(key, event);

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getEventName = " + event.getEventName());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getMuscleArea = " + event.getMuscleArea());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getEquipmentType = " + event.getEquipmentType());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getGroupType = " + event.getGroupType());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getMaxWeight = " + event.getMaxWeight());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getProperWeight = " + event.getProperWeight());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "====> getSelectionCounter = " + event.getSelectionCounter());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}