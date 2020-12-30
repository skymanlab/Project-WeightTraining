package com.skymanlab.weighttraining.management.project.fragment.Intervene;

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
import com.skymanlab.weighttraining.management.user.data.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InterveneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterveneFragment extends Fragment {


    // constant
    private static final String CLASS_NAME = "[FH]_InterveneFragment";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private FragmentTopBarManager topBarManager;

    public InterveneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user FirebaseUser 정보 중 displayName, email, photoUrl 만 담겨있는 객체
     * @return A new instance of fragment InterveneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterveneFragment newInstance(User user) {

        final String METHOD_NAME = "[newInstance] ";

        InterveneFragment fragment = new InterveneFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intervene, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String METHOD_NAME = "[onViewCreated] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "++++++++++++ intervene fragment ++++++++++++");

        // [iv/C]FragmentTopBarManager : fragment top bar section manager
        this.topBarManager = new FragmentTopBarManager(getActivity(), view, getString(R.string.f_intervene_title));
        this.topBarManager.mappingWidget();
        this.topBarManager.initWidget();

    }
}