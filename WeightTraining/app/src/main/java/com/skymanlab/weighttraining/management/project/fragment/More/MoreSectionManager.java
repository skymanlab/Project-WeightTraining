package com.skymanlab.weighttraining.management.project.fragment.More;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.LoginActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.user.data.User;

public class MoreSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFM] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private MaterialTextView logout;
    private MaterialTextView withdraw;
    private LinearLayout fitnessCenter;
    private LinearLayout target;
    private LinearLayout setting;

    // constructor
    public MoreSectionManager(Activity activity, View view) {
        super(activity, view);
    }

    @Override
    public void connectWidget() {

        // [iv/C]MaterialTextView : logout connect
        this.logout = (MaterialTextView) getView().findViewById(R.id.f_more_user_logout);

        // [iv/C]MaterialTextView : withdraw connect
        this.withdraw = (MaterialTextView) getView().findViewById(R.id.f_more_user_withdraw);

        // [iv/C]LinearLayout : fitnessCenter connect
        this.fitnessCenter = (LinearLayout) getView().findViewById(R.id.f_more_fitness_center);

        // [iv/C]LinearLayout : target connect
        this.target = (LinearLayout) getView().findViewById(R.id.f_more_target);

        // [iv/C]LinearLayout : setting connect
        this.setting = (LinearLayout) getView().findViewById(R.id.f_more_setting);

    }

    @Override
    public void initWidget() {

        // [iv/C]MaterialTextView : logout click listener
        this.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : logout 과정 진행
               logoutUser();
            }
        });

        // [iv/C]MaterialTextView : withdraw click listener
        this.withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : withdraw 과정 진행
                withdrawUser();
            }
        });

        // [iv/C]LinearLayout : fitnessCenter click listener
        this.fitnessCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // [iv/C]LinearLayout : target click listener
        this.target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // [iv/C]LinearLayout : setting click listener
        this.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [iv/C]Intent : SettingsActivity 로 이동하는
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(intent);
            }
        });

    }

    /**
     * [method] App logout
     */
    private void logoutUser() {

        // [lv/C]AlertDialog : Builder 객체 생성 / 초기 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.f_more_alert_logout_user_title)
                .setMessage(R.string.f_more_alert_logout_user_message)
                .setPositiveButton(R.string.f_more_alert_logout_user_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]FirebaseAuth : signOut 을 호출하여 사용자를 로그아웃한다.
                        FirebaseAuth.getInstance().signOut();

                        // [lv/C]Snackbar : 로그아웃 완료 메시지
                        Snackbar.make(getView(), "로그아웃이 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                        // [lv/C]Intent : LoginActivity 로 이동하는 객체 생성
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().finish();
                        getActivity().startActivity(intent);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.f_more_alert_logout_user_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    } // End of method [logoutUser]


    /**
     * [method] App withdraw : Firebase 에 등록된 사용자를 삭제한다.
     */
    private void withdrawUser() {

        // [lv/C]AlertDialog : Builder 객체 생성 / 초기 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.f_more_alert_withdraw_user_title)
                .setMessage(R.string.f_more_alert_withdraw_user_message)
                .setPositiveButton(R.string.f_more_alert_withdraw_user_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]FirebaseUser :
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                // [check 1] : task 의 성공여부를 받는다.
                                if (task.isSuccessful()) {

                                    // [lv/C]Snackbar : 탈퇴 완료 메시지
                                    Snackbar.make(getView(), "탈퇴가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                                    // [lv/C]Intent : LoginActivity 로 이동하는 객체 생성
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    getActivity().finish();
                                    getActivity().startActivity(intent);

                                    dialog.dismiss();
                                } // [check 1]
                            }
                        });

                    }
                })
                .setNegativeButton(R.string.f_more_alert_withdraw_user_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    } // End of method [withdrawUser]


}
