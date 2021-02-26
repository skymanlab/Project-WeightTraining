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
import com.skymanlab.weighttraining.management.project.ApiManager.AuthenticationManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.squareup.picasso.Picasso;

public class UserInfoSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFMS] MoreUserInfoSectionManager";
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
    public UserInfoSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ ImageView | photo ] widget connect
        this.photo = (ImageView) getView().findViewById(R.id.f_userInfo_photo);

        // [ MaterialButton | photoChange ] widget connect
        this.photoChange = (MaterialButton) getView().findViewById(R.id.f_userInfo_photoChange);

        // [ TextView | authProvider ] widget connect
        this.authProvider = (TextView) getView().findViewById(R.id.f_userInfo_authProvider);

        // [ TextView | email ] widget connect
        this.email = (TextView) getView().findViewById(R.id.f_userInfo_email);

        // [ TextView | name ] widget connect
        this.name = (TextView) getView().findViewById(R.id.f_userInfo_name);

        // [ TextView | logout ] widget connect
        this.logout = (TextView) getView().findViewById(R.id.f_userInfo_logout);

        // [ TextView | withdraw ] widget connect
        this.withdraw = (TextView) getView().findViewById(R.id.f_userInfo_withdraw);
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

                    AuthenticationManager.showDialogOfSignOut(getFragment().getActivity());
                }
            });

            // [withdraw] click listener
            this.withdraw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AuthenticationManager.showDialogOfWithdraw(getFragment().getActivity(), getFragment().getActivity().getSupportFragmentManager());
                }
            });
        }

    }


}
