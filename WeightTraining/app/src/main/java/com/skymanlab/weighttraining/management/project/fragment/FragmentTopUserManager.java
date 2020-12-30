package com.skymanlab.weighttraining.management.project.fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.user.data.User;
import com.squareup.picasso.Picasso;

public class FragmentTopUserManager extends FragmentSectionManager implements FragmentSectionInitializable{

    // constant
    private static final String CLASS_NAME = "[F]_FragmentTopUserManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable
    private ImageView userPhoto;
    private TextView userName;
    private TextView userEmail;

    // instance variable
    private boolean shouldDisplayEmail;

    // constructor
    public FragmentTopUserManager(View view, boolean shouldDisplayEmail) {
        super(view);
        this.shouldDisplayEmail = shouldDisplayEmail;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]ImageView : userPhoto mapping
        this.userPhoto = (ImageView) getView().findViewById(R.id.fragment_top_user_photo);

        // [iv/C]TextView : userName mapping
        this.userName = (TextView) getView().findViewById(R.id.fragment_top_user_name);

        // [iv/C]TextView : userEmail mapping
        this.userEmail = (TextView) getView().findViewById(R.id.fragment_top_user_email);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [method] : user info 를 표시하는 과정 진행
        displayUserInfo();
    }


    /**
     * [method] User 정보를 표시하기
     *
     */
    private void displayUserInfo() {

        // [lv/C]FirebaseUser : FirebaseAuth 를 통해 user 정보 가져오기
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // [check 1] : user 정보가 있을 때만
        if (firebaseUser != null) {

            // [iv/C]ImageView : userPhoto 를 둥근 이미지로 만들기 위한 설정 / Picasso 를 이용하여 userPhotoUrl 을 로드하기
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(this.userPhoto);
            this.userPhoto.setBackground(new ShapeDrawable(new OvalShape()));
            this.userPhoto.setClipToOutline(true);

            // [iv/C]TextView : displayName 을 표시하기
            this.userName.setText(firebaseUser.getDisplayName());

            // [check 2] : email 정보를 표시할 건지
            if (this.shouldDisplayEmail) {

                // [iv/C]TextView : email 을 표시하기
                this.userEmail.setText(firebaseUser.getEmail());

            } else {

                // [iv/C]TextView : userEmail 을 GONE 으로
                this.userEmail.setVisibility(TextView.GONE);

            } // [check 2]

        } else {

        } // [check 1]

    } // End of method [displayUserInfo]
}
