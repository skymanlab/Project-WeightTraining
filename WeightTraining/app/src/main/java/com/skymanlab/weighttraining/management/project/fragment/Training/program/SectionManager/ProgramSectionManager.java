package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep1Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MyProgramFragment;

public class ProgramSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] ProgramSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instructor
    private MaterialCardView programMaker;
    private MaterialCardView myProgram;
    private MaterialCardView recommendProgram;

    // constructor
    public ProgramSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ MaterialCardView ] widget connect
        this.programMaker = (MaterialCardView) getView().findViewById(R.id.f_program_menu_program_maker);
        this.myProgram = (MaterialCardView) getView().findViewById(R.id.f_program_menu_my_program);
//        this.recommendProgram = (MaterialCardView) getView().findViewById(R.id.f_program_menu_recommend_program);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [MaterialCardView] [programMaker] click listener
        this.programMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // [lv/C]MakerStep1Fragment : maker step 1 fragment 생성
                MakerStep1Fragment step1Fragment = MakerStep1Fragment.newInstance();

                // [FragmentTransaction] [transaction] 'maker step 1' fragment 으로 이동
                FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, step1Fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        // [MaterialCardView] [myProgram] click listener
        this.myProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // [lv/C]MyProgramFragment : my program fragment 생성
                MyProgramFragment myProgramFragment = MyProgramFragment.newInstance();

                // [FragmentTransaction] [transaction] 'my program' fragment 으로 이동
                FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, myProgramFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        // [MaterialCardView] [recommendProgram] click listener
//        this.recommendProgram.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }
}
