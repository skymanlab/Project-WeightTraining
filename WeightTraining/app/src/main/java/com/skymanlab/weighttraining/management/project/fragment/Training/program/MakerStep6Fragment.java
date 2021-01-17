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
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
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
    private static final String CLASS_NAME = "[PFTP] MakerStep6Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String FINAL_ORDER_LIST = "finalOrderList";

    // instance variable
    private ArrayList<Event> finalOrderList;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStep6SectionManager sectionManager;

    // constructor
    public MakerStep6Fragment() {
        // Required empty public constructor
    }

    // getter
    public MakerStep6SectionManager getSectionManager() {
        return sectionManager;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MakerStep5Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep6Fragment newInstance(ArrayList<Event> finalOrderList) {

        MakerStep6Fragment fragment = new MakerStep6Fragment();

        Bundle args = new Bundle();
        args.putSerializable(FINAL_ORDER_LIST, finalOrderList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.finalOrderList = (ArrayList<Event>) getArguments().getSerializable(FINAL_ORDER_LIST);
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

        final String METHOD_NAME = "[onViewCreated] ";

        // [iv/C]FragmentTopBarManager : step 6 fragment top bar
        this.topBarManager = new FragmentTopBarManager(getActivity(), getView(), getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step4D1SectionManager : step 6 fragment section
        this.sectionManager = new MakerStep6SectionManager(getActivity(), getView(), getActivity().getSupportFragmentManager(), this);
        this.sectionManager.setFinalOrderList(this.finalOrderList);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "..................--- fragment = " + this);

    }

}