package com.skymanlab.weighttraining.management.project.fragment.More;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.More.SectionManager.MoreUserInfoSectionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreUserInfoFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreUserInfoFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private MoreUserInfoSectionManager sectionManager;

    // constructor
    public MoreUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MoreUserInforagment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreUserInfoFragment newInstance() {
        MoreUserInfoFragment fragment = new MoreUserInfoFragment();
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
        return inflater.inflate(R.layout.fragment_more_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [FragmentTopBarManager] [topBarManager] this is 'more user info' fragment's top bar section manager.
        this.topBarManager = new FragmentTopBarManager(this, view, getString(R.string.f_more_user_info_title));
        this.topBarManager.connectWidget();
        this.topBarManager.initWidget();

        // [MoreSectionManager] [sectionManager] this is 'more user info' fragment's section manager.
        this.sectionManager = new MoreUserInfoSectionManager(this, view);
        this.sectionManager.connectWidget();
        this.sectionManager.initWidget();

        // [FragmentTopBarManager] [topBarManager] StartButtonListener 를 생성하여 start button click listener 설정하기
        this.topBarManager.setStartButtonListener(new FragmentTopBarManager.StartButtonListener() {
            @Override
            public AlertDialog setStartButtonClickListener() {

                // [method] fragment manager 를 통해 back stack 에서 pop!
                getActivity().getSupportFragmentManager().popBackStack();
                return null;
            }
        });
        this.topBarManager.initWidgetOfStartButton(null);

    }
}
