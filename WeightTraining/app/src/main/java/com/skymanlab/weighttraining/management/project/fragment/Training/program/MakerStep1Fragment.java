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
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStep1SectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager.MakerStepManager2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakerStep1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakerStep1Fragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFTP] MakerStep1Fragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MakerStep1SectionManager sectionManager;
    private MakerStepManager2 makerStepManager;

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

        final String METHOD_NAME = "[onViewCreated] ";

        // [FragmentTopBarManager] [topBarManager] maker step 1 fragment top bar manager
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_program_menu_program_maker));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MakerStepManager2] [makerStepManager] maker step 1 단계 설정
        this.makerStepManager = new MakerStepManager2(getView(), getParentFragmentManager(), MakerStepManager.STEP_ONE);
        this.makerStepManager.connectWidget();
        this.makerStepManager.initWidget();

        // [MakerStep1SectionManager] [sectionManager] maker step 1 fragment section manager
        this.sectionManager = new MakerStep1SectionManager(getActivity(), view, getActivity().getSupportFragmentManager());
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 를 생성하여 start button click listener 설정하기
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public void setStartButtonClickListener() {

                // [method] fragment manager 를 통해 back stack 에서 pop!
                getParentFragmentManager().popBackStack();

            }
        });
        this.topBarManager.initWidgetOfStartButton(null);

    }
}