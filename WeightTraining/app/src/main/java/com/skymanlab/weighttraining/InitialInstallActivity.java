package com.skymanlab.weighttraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionManager;
import com.skymanlab.weighttraining.management.project.ApiManager.PermissionUtil;

public class InitialInstallActivity extends AppCompatActivity {

    // instance variable
    private LinearLayout locationPermissionWrapper;
    private MaterialCardView locationPermission;
    private LinearLayout backgroundLocationPermissionWrapper;
    private MaterialCardView backgroundLocationPermission;
    private MaterialButton skip;

    // instance variable
    private int procedureCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_install);

        connectWidget();

        initWidget();

    }


    public void connectWidget() {

        // [ LinearLayout | locationPermissionWrapper ]
        this.locationPermissionWrapper = (LinearLayout) findViewById(R.id.A_initialInstall_locationPermission_wrapper);

        // [ MaterialCardView | locationPermission ]
        this.locationPermission = (MaterialCardView) findViewById(R.id.A_initialInstall_locationPermission);

        // [ LinearLayout | backgroundLocationPermissionWrapper ]
        this.backgroundLocationPermissionWrapper = (LinearLayout) findViewById(R.id.A_initialInstall_backgroundLocationPermission_wrapper);

        // [ MaterialCardView | backgroundLocationPermission ]
        this.backgroundLocationPermission = (MaterialCardView) findViewById(R.id.A_initialInstall_backgroundLocationPermission);

        // [ MaterialButton | skip ]
        this.skip = (MaterialButton) findViewById(R.id.A_initialInstall_skip);

    }

    public void initWidget() {

        this.locationPermission.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 권한 요청
                        PermissionUtil.requestPermission(InitialInstallActivity.this, 0, PermissionManager.LOCATION_PERMISSION_LIST);

                    }
                }
        );

        this.backgroundLocationPermission.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 권한 요청
                        PermissionUtil.requestPermission(InitialInstallActivity.this, 0, PermissionManager.BACKGROUND_PERMISSION);

                    }
                }
        );

        this.skip.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (procedureCounter == 1) {

                            // location
                            locationPermissionWrapper.setVisibility(View.GONE);

                            // background
                            backgroundLocationPermissionWrapper.setVisibility(View.VISIBLE);

                            // procedureCounter
                            procedureCounter += 1;

                        } else if (procedureCounter == 2 ){

                            Intent intent = new Intent(InitialInstallActivity.this, LoginActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                }
        );

    }

}