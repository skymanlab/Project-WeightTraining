package com.skymanlab.weighttraining.management.project.fragment.More.SectionManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.LoginActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.squareup.picasso.Picasso;

public class MoreUserInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] MoreSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ImageView photo;
    private MaterialButton photoChange;
    private TextView authProvider;
    private TextView email;
    private TextView name;
    private TextView logout;
    private TextView withdraw;

    // constructor
    public MoreUserInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ ImageView | photo ] widget connect
        this.photo = (ImageView) getView().findViewById(R.id.f_more_user_info_photo);

        // [ MaterialButton | photoChange ] widget connect
        this.photoChange = (MaterialButton) getView().findViewById(R.id.f_more_user_info_photo_change);

        // [ TextView | authProvider ] widget connect
        this.authProvider = (TextView) getView().findViewById(R.id.f_more_user_info_auth_provider);

        // [ TextView | email ] widget connect
        this.email = (TextView) getView().findViewById(R.id.f_more_user_info_email);

        // [ TextView | name ] widget connect
        this.name = (TextView) getView().findViewById(R.id.f_more_user_info_name);

        // [ TextView | logout ] widget connect
        this.logout = (TextView) getView().findViewById(R.id.f_more_user_info_logout);

        // [ TextView | withdraw ] widget connect
        this.withdraw = (TextView) getView().findViewById(R.id.f_more_user_info_withdraw);
    }

    @Override
    public void initWidget() {

        // [lv/C]FirebaseUser : FirebaseAuth 를 통해 user 정보 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [check 1] : user 정보가 있을 때만
        if (firebaseUser != null) {

            // [iv/C]ImageView : userPhoto 를 둥근 이미지로 만들기 위한 설정 / Picasso 를 이용하여 userPhotoUrl 을 로드하기
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(this.photo);
            this.photo.setBackground(new ShapeDrawable(new OvalShape()));
            this.photo.setClipToOutline(true);

            // [photoChange] click listener
            this.photoChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // [authProvider] text
            this.authProvider.setText(firebaseUser.getProviderId());

            // [email] text
            this.email.setText(firebaseUser.getEmail());

            // [name] text
            this.name.setText(firebaseUser.getDisplayName());

            // [logout] click listener
            this.logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // [method] firebase 에서 logout 하기
                    logoutUser();
                }
            });

            // [withdraw] click listener
            this.withdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // [method] firebase 에서 탈퇴하기
                    withdrawUser();

                }
            });
        }

    }


    /**
     * [method] App logout
     */
    private void logoutUser() {

        // [lv/C]AlertDialog : Builder 객체 생성 / 초기 설정
        AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getActivity());
        builder.setTitle(R.string.f_more_user_info_alert_logout_user_title)
                .setMessage(R.string.f_more_user_info_alert_logout_user_message)
                .setPositiveButton(R.string.f_more_user_info_alert_logout_user_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [lv/C]FirebaseAuth : signOut 을 호출하여 사용자를 로그아웃한다.
                        FirebaseAuth.getInstance().signOut();

                        // [lv/C]Snackbar : 로그아웃 완료 메시지
                        Snackbar.make(getView(), "로그아웃이 완료되었습니다.", Snackbar.LENGTH_SHORT).show();

                        // [lv/C]Intent : LoginActivity 로 이동하는 객체 생성
                        Intent intent = new Intent(getFragment().getActivity(), LoginActivity.class);
                        getFragment().getActivity().finish();
                        getFragment().getActivity().startActivity(intent);

                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.f_more_user_info_alert_logout_user_bt_negative, new DialogInterface.OnClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getActivity());
        builder.setTitle(R.string.f_more_user_info_alert_withdraw_user_title)
                .setMessage(R.string.f_more_user_info_alert_withdraw_user_message)
                .setPositiveButton(R.string.f_more_user_info_alert_withdraw_user_bt_positive, new DialogInterface.OnClickListener() {
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
                                    Intent intent = new Intent(getFragment().getActivity(), LoginActivity.class);
                                    getFragment().getActivity().finish();
                                    getFragment().getActivity().startActivity(intent);

                                    dialog.dismiss();
                                } // [check 1]
                            }
                        });

                    }
                })
                .setNegativeButton(R.string.f_more_user_info_alert_withdraw_user_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    } // End of method [withdrawUser]
}
