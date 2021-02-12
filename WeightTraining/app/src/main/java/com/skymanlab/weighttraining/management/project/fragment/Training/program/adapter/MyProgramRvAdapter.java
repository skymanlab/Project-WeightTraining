package com.skymanlab.weighttraining.management.project.fragment.Training.program.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MyProgramDetailFragment;

import java.util.ArrayList;

public class MyProgramRvAdapter extends RecyclerView.Adapter<MyProgramRvAdapter.ViewHolder> {

    // instance variable
    private Fragment fragment;
    private ArrayList<Program> programArrayList;


    // constructor
    public MyProgramRvAdapter(Fragment fragment, ArrayList<Program> programArrayList) {
        this.fragment = fragment;
        this.programArrayList = programArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_my_program_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // content wrapper
        holder.contentWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_home_content_wrapper, MyProgramDetailFragment.newInstance(programArrayList.get(position)))
                        .addToBackStack(null)
                        .commit();

            }
        });

        // nick name
        holder.programNickName.setText(programArrayList.get(position).getNickName());

        // all muscle Area List
        holder.allMuscleAreaList.setText(getAllMuscleAreaListTitle(programArrayList.get(position).getMuscleAreaList()));

        // total set number
        holder.totalSetNumber.setText(DataFormatter.setTotalSetNumberFormat(programArrayList.get(position).getTotalSetNumber()));

    }

    @Override
    public int getItemCount() {
        return programArrayList.size();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Etc Method =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private String getAllMuscleAreaListTitle(ArrayList<MuscleArea> muscleAreaList) {

        StringBuilder allMuscleAreaList = new StringBuilder();
        for (int index = 0; index < muscleAreaList.size(); index++) {
            allMuscleAreaList.append(DataManager.convertHanguleOfMuscleArea(muscleAreaList.get(index)));

            if (index != (muscleAreaList.size() - 1))
                allMuscleAreaList.append(" / ");
        }

        return allMuscleAreaList.toString();
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= View Holder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private LinearLayout contentWrapper;
        private TextView programNickName;
        private TextView allMuscleAreaList;
        private TextView totalSetNumber;

        // constructor
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            this.contentWrapper = (LinearLayout) itemView.findViewById(R.id.custom_list_my_program_item_content_wrapper);
            this.programNickName = (TextView) itemView.findViewById(R.id.custom_list_my_program_item_program_nick_name);
            this.allMuscleAreaList = (TextView) itemView.findViewById(R.id.custom_list_my_program_item_all_muscle_area_list);
            this.totalSetNumber = (TextView) itemView.findViewById(R.id.custom_list_my_program_item_total_set_number);
        }
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= Builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
}
