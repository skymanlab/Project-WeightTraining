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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep1SectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStep1SectionManager sectionManager;

    public MakerStep1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step1D0Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakerStep1Fragment newInstance() {
        MakerStep1Fragment fragment = new MakerStep1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maker_step1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [iv/C]FragmentTopBarManager : maker step 1 fragment top bar manager
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]Step1D0SectionManager : maker step 1 fragment section manager
        this.sectionManager = new MakerStep1SectionManager(getActivity(), view, getActivity().getSupportFragmentManager());
        this.sectionManager.mappingWidget();
        this.sectionManager.initWidget();

    }
}