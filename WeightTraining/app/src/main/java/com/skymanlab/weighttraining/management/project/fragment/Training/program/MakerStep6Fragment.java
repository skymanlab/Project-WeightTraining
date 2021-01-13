package com.skymanlab.weighttraining.management.project.fragment.Training.program;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep6SectionManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep6Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep6Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep5Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String FINAL_ORDER_EVENT_ARRAY_LIST = "finalOrderEventArrayList";

    // instance variable
    private ArrayList<Event> finalOrderEventArrayList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStep6SectionManager sectionManager;

    // constructor
    public MakerStep6Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakerStep5Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep6Fragment newInstance(ArrayList<Event> finalOrderEventArrayList) {

        MakerStep6Fragment fragment = new MakerStep6Fragment();

        Bundle args = new Bundle();
        args.putSerializable(FINAL_ORDER_EVENT_ARRAY_LIST, finalOrderEventArrayList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.finalOrderEventArrayList = (ArrayList<Event>) getArguments().getSerializable(FINAL_ORDER_EVENT_ARRAY_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager : step 6 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step4D1SectionManager : step 6 fragment section
        this.sectionManager = new MakerStep6SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager());
        this.sectionManager.setFinalOrderEventArrayList(this.finalOrderEventArrayList);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();
    }
}