package com.skymanlab.weighttraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;


public class AdMobActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac] AdMobActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;


    // instance variable
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_mob);

        final String METHOD_NAME = "[onCreate] ";

        // [lv/C]FirebaseApp :
        FirebaseApp.initializeApp(this);

        // [lv/C]MobileAds : Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        // [lv/C]InterstitialAd : Create the InterstitialAd and set the adUnitId.
        InterstitialAd iniInterstitialAd = new InterstitialAd(this);
        iniInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        iniInterstitialAd.loadAd(new AdRequest.Builder().build());

        iniInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 종료합니다.");
                finish();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
            }

            @Override
            public void onAdLoaded() {
//                super.onAdLoaded();
                if (iniInterstitialAd.isLoaded()) {
                    iniInterstitialAd.show();
                } else {
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> The interstitial wasn't loaded yet.");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (adView != null) {
            adView.destroy();
        }
    }
}