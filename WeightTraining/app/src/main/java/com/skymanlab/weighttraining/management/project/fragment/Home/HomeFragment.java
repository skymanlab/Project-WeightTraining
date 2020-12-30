package com.skymanlab.weighttraining.management.project.fragment.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopUserManager;
import com.skymanlab.weighttraining.management.user.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // constant
    private static final String CLASS_NAME = "[FH]_HomeFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FragmentTopBarManager topBarManager;
    private FragmentTopUserManager topUserManager;

    // constructor
    public HomeFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user FirebaseUser 정보 중 displayName, email, photoUrl 만 담겨있는 객체
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(User user) {

        final String METHOD_NAME = "[newInstance] ";

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String METHOD_NAME = "[onViewCreated] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ home fragment ++++++++++++");

        // [iv/C]FragmentTopBarManager : fragment top bar section manager
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_home_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

        // [iv/C]FragmentTopUserManager : fragment top user section manager
        this.topUserManager = new FragmentTopUserManager(view, false);
        this.topUserManager.mappingWidget();
        this.topUserManager.initWidget();
    }
}