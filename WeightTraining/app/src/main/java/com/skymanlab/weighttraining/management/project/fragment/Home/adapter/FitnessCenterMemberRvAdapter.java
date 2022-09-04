package com.skymanlab.weighttraining.management.project.fragment.Home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;

import java.util.ArrayList;

/**
 * 홈 화면에서 피트니스를 등록한 사람
 */
public class FitnessCenterMemberRvAdapter extends RecyclerView.Adapter<FitnessCenterMemberRvAdapter.ViewHolder> {

    // instance variable
    private String uid;
    private UserFitnessCenter myFitnessCenter;
    private ArrayList<Member> memberArrayList;

    // constructor
    public FitnessCenterMemberRvAdapter(String uid,
                                        UserFitnessCenter myFitnessCenter,
                                        ArrayList<Member> memberArrayList) {
        this.uid = uid;
        this.myFitnessCenter = myFitnessCenter;
        this.memberArrayList = memberArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_fitness_center_member, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        // 각각의 회원들이 자신의 FitnessCenter 등록에 대해서 '공개'를 하였을 때
        if (memberArrayList.get(position).getIsDisclosed()) {

            // init state image
            initWidgetOfStateImage(holder.stateImage, memberArrayList.get(position));

            // member name
            holder.memberName.setText(memberArrayList.get(position).getUserName());
            initWidgetOfMemberName(holder.memberName, myFitnessCenter, memberArrayList.get(position));
        }

        // init state image
    }

    @Override
    public int getItemCount() {
        return memberArrayList.size();
    }

    // ====================================================== etc ======================================================
    private boolean checkMyData(long myMemberNumber, long memberNumber) {

        if (myMemberNumber == memberNumber) {
            return true;
        } else {
            return false;
        }
    }

    private void initWidgetOfStateImage(ImageView stateImage, Member member) {

        switch (member.getActiveState()) {
            case Member.ACTIVE_STATE_ENTER:
                // fitness center 에 입장
                stateImage.setImageResource(R.drawable.active_state_enter);
                break;
            case Member.ACTIVE_STATE_EXERCISE:
                // 운동 중
                stateImage.setImageResource(R.drawable.active_state_exercise);
                break;
            case Member.ACTIVE_STATE_EXIT:
                // fitness center 를 퇴장
                stateImage.setImageResource(R.drawable.active_state_exit);
                break;
        }

    }

    private void initWidgetOfMemberName(TextView memberName, UserFitnessCenter myFitnessCenter, Member member) {

        // 공개 상태일 때, 회원 목록 중에 나의 정보가 있으면
        if (checkMyData(myFitnessCenter.getMemberNumber(), member.getMemberNumber())) {
            //memberName 뒤에 '(나)' 라고 표기하기
            memberName.setText(member.getUserName() + "(나)");
            return;
        }

        memberName.setText(member.getUserName());
    }

    // ====================================================== ViewHolder ======================================================
    public class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private ImageView stateImage;
        private TextView memberName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.stateImage = (ImageView) itemView.findViewById(R.id.custom_list_fitness_center_member_stateImage);
            this.memberName = (TextView) itemView.findViewById(R.id.custom_list_fitness_center_member_memberName);
        }


    }
}
