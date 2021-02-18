package com.skymanlab.weighttraining.management.project.fragment.Home;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.FitnessCenter.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.FitnessCenterMemberManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Home.adapter.FitnessCenterMemberRvAdapter;

import java.util.ArrayList;

public class HomeSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFH] HomeSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int REQUEST_CODE = 1000;

    // instance variable
    private AdView adView;
    private RecyclerView fitnessCenterMemberRecyclerView;

    // constructor
    public HomeSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ AdView | adView ] widget connect
        this.adView = (AdView) getView().findViewById(R.id.f_home_ad_mob);

        // [ RecyclerView | fitnessCenterMemberRecyclerView ]
        this.fitnessCenterMemberRecyclerView = (RecyclerView) getView().findViewById(R.id.f_home_fitness_center_member_recycler_view);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // ad mob adView init
        initWidgetOfAdView();

        // fitness center member recycler view
        initWidgetOfFitnessCenterMemberRecyclerView();

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


    private void initWidgetOfFitnessCenterMemberRecyclerView() {
        final String METHOD_NAME = "[initWidgetOfFitnessCenterMemberRecyclerView] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        FitnessCenterMemberManager fitnessCenterMemberManager = new FitnessCenterMemberManager(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        fitnessCenterMemberManager.init(new FitnessCenterMemberManager.OnMemberManipulateListener() {

            @Override
            public void isDisclosedState(String myUid, UserFitnessCenter myFitnessCenter, ArrayList<Member> memberArrayList) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------------------------------------------------------------");

                // 내가 FitnessCenter 를 등록했을 때
                if (myFitnessCenter != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > getIsDisclosed = " + myFitnessCenter.getIsDisclosed());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > getMemberNumber = " + myFitnessCenter.getMemberNumber());

                    // 내가 등록한 FitnessCenter 에 회원이 있을 경우에
                    if (!memberArrayList.isEmpty()) {

                        // recycler view adapter
                        FitnessCenterMemberRvAdapter adapter = new FitnessCenterMemberRvAdapter(myUid, myFitnessCenter, memberArrayList);

                        // recycler view
                        fitnessCenterMemberRecyclerView.setAdapter(adapter);

                        if (fitnessCenterMemberRecyclerView.getVisibility() == View.GONE) {
                            fitnessCenterMemberRecyclerView.setVisibility(View.VISIBLE);
                        }

                    }

                }
            }

            @Override
            public void isNotIsDisclosedState() {
                // recycler view : 내가 비공개로 했으므로 다른 사람의 접속여부도 알지 못하도록 GONE
                fitnessCenterMemberRecyclerView.setVisibility(View.GONE);

            }

            @Override
            public void isNotRegisteredTheFitnessCenter() {
                //
            }
        });

    }


    private boolean getMyIsDisclosed(long myMemberNumber, ArrayList<Member> memberArrayList) {
        final String METHOD_NAME = "[getMyIsDisclosed] ";

        for (Member search : memberArrayList) {
            if (search.getMemberNumber() == myMemberNumber) {

                return search.getIsDisclosed();
            }
        }
        return false;
    }

}
