package com.skymanlab.weighttraining.management.project.fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.More.MoreUserInfoFragment;
import com.squareup.picasso.Picasso;

public class FragmentTopUserManager extends FragmentSectionManager implements FragmentSectionInitializable{

    // constant
    private static final String CLASS_NAME = "[PF] FragmentTopUserManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;
    // instance variable
    private ImageView userPhoto;
    private TextView userName;
    private TextView userEmail;
    private ImageView moreInfo;

    // instance variable
    private boolean shouldDisplayEmail;

    // constructor
    public FragmentTopUserManager(View view, Fragment fragment, boolean shouldDisplayEmail) {
        super(view, fragment);
        this.shouldDisplayEmail = shouldDisplayEmail;
    }

    @Override
    public void connectWidget() {

        // [iv/C]ImageView : userPhoto connect
        this.userPhoto = (ImageView) getView().findViewById(R.id.include_top_user_photo);

        // [iv/C]TextView : userName connect
        this.userName = (TextView) getView().findViewById(R.id.include_top_user_name);

        // [iv/C]TextView : userEmail connect
        this.userEmail = (TextView) getView().findViewById(R.id.include_top_user_email);

        // [iv/C]ImageView : moreInfo connect
        this.moreInfo = (ImageView)getView().findViewById(R.id.include_top_user_more_info);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidget] ";

        // [method] : user info 의 초기 내용을 설정한다.
        initUserInfo();
        
        // [iv/C]ImageView : moreInfo click listener
        this.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                // [lv/C]Fragment : more user info fragment 생성
                Fragment moreUserInfo = MoreUserInfoFragment.newInstance();

                // fragment 로 이동
                FragmentTransaction transaction = getFragment().getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_home_content_wrapper, moreUserInfo);
                transaction.addToBackStack(null);
                transaction.commit();
                
            }
        });
    }


    /**
     * [method] User 정보를 표시하기
     *
     */
    private void initUserInfo() {

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

    } // End of method [initUserInfo]
}
