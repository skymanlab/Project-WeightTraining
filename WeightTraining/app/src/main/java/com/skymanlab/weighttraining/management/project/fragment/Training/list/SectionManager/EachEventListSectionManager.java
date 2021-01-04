package com.skymanlab.weighttraining.management.project.fragment.Training.list.SectionManager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.dialog.EventItemAddDialog;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter.EventListRvManager;

public class EachEventListSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTLS] EachEventListSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // constant
    private static final String DB_TARGET = "event";

    // instance variable
    private FragmentManager fragmentManager;
    private MuscleArea muscleArea;

    // instance variable
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private ImageButton add;

    // instance variable
    private EventListRvManager eventListRvManager;

    // constructor
    public EachEventListSectionManager(Activity activity, View view, FragmentManager fragmentManager, MuscleArea muscleArea) {
        super(activity, view);
        this.fragmentManager = fragmentManager;
        this.muscleArea = muscleArea;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]RecyclerView : recyclerView mapping
        this.recyclerView = (RecyclerView) getView().findViewById(R.id.f_each_event_list_recycler_view);

        // [iv/C]ContentLoadingProgressBar : progressBar mapping
        this.progressBar = (ContentLoadingProgressBar) getView().findViewById(R.id.f_each_event_list_progress_bar);

        // [iv/C]ImageButton : add mapping
        this.add = (ImageButton) getView().findViewById(R.id.f_each_event_list_bt_add);
    }

    @Override
    public void initWidget() {

        // [iv/C]EventListRvManager :
        this.eventListRvManager = new EventListRvManager.Builder(getActivity())
                .setFragmentManager(this.fragmentManager)
                .setMuscleArea(this.muscleArea)
                .setRecyclerView(this.recyclerView)
                .setProgressBar(this.progressBar)
                .setAdd(this.add)
                .build();

        this.eventListRvManager.initRecyclerView();

        // [iv/C]ImageButton : add click listener
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]EventItemAddDialog : dialog 객체 생성
                EventItemAddDialog dialog = new EventItemAddDialog(getActivity(), muscleArea);
                dialog.show(fragmentManager, muscleArea.toString() + "Add");

            }
        });
    }
}
