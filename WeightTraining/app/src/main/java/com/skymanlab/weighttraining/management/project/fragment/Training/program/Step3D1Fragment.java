package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
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
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.Step3D1SectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Step3D1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Step3D1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] Step3D1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String SELECTED_MUSCLE_AREA_LIST = "selectedMuscleAreaList";

    // instance variable
    private boolean[] isSelectedMuscleAreaList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private Step3D1SectionManager sectionManager;

    // instance variable
    private ArrayList<DirectSelectionFragment> fragmentArrayList;
    private ArrayList<String> fragmentMuscleAreaList;

    // constructor
    public Step3D1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param isSelectedMuscleAreaList STEP 2-1 에서 선택한 muscle area list
     * @return A new instance of fragment Step3D1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Step3D1Fragment newInstance(boolean[] isSelectedMuscleAreaList) {

        Step3D1Fragment fragment = new Step3D1Fragment();

        Bundle args = new Bundle();
        args.putBooleanArray(SELECTED_MUSCLE_AREA_LIST, isSelectedMuscleAreaList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String METHOD_NAME = "[onCreate] ";

        if (getArguments() != null) {
            this.isSelectedMuscleAreaList = getArguments().getBooleanArray(SELECTED_MUSCLE_AREA_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step3_1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager : step 3-1 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_training_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step3D1SectionManager : step 3-1 content section manager
        this.sectionManager = new Step3D1SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager(), this, this.isSelectedMuscleAreaList);
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

    }

}