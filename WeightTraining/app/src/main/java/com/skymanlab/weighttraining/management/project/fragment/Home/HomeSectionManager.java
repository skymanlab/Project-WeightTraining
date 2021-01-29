package com.skymanlab.weighttraining.management.project.fragment.Home;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

public class HomeSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFH] HomeSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int REQUEST_CODE = 1000;

    // instance variable
    private AdView adView;

    // constructor
    public HomeSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ AdView | adView ] widget connect
        this.adView = (AdView) getView().findViewById(R.id.f_home_ad_mob);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // ad mob adView init
        initWidgetOfAdView();

    }


    /**
     * Google AdMob 광고를 적응형 베너로 표시합니다.
     */
    private void initWidgetOfAdView() {

        MobileAds.initialize(getFragment().getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }
        });

    }

}
