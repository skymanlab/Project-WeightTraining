package com.skymanlab.weighttraining.management.project.fragment.Home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.FitnessCenter.data.FitnessCenter;
import com.skymanlab.weighttraining.management.FitnessCenter.data.Member;
import com.skymanlab.weighttraining.management.user.data.UserFitnessCenter;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.ApiManager.FitnessCenterMemberManager;
import com.skymanlab.weighttraining.management.project.data.FirebaseConstants;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.Home.adapter.FitnessCenterMemberRvAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeSectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFH] HomeSectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    public static final int REQUEST_CODE = 1000;

    // instance variable
    private MaterialButtonToggleGroup fitnessCenterActiveStateGroup;
    private RecyclerView fitnessCenterMemberRecyclerView;
    private TextView instruction;

    // instance variable
    private AdView adView;

    // constructor
    public HomeSectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    @Override
    public void connectWidget() {

        // [ MaterialButtonToggleGroup | fitnessCenterActiveStateGroup ]
        this.fitnessCenterActiveStateGroup = (MaterialButtonToggleGroup) getView().findViewById(R.id.f_home_fitness_center_active_state_group);

        // [ RecyclerView | fitnessCenterMemberRecyclerView ]
        this.fitnessCenterMemberRecyclerView = (RecyclerView) getView().findViewById(R.id.f_home_fitness_center_member_recycler_view);

        // [ TextView | instruction ] 
        this.instruction = (TextView) getView().findViewById(R.id.f_home_fitness_center_instruction);

        // [ AdView | adView ] widget connect
        this.adView = (AdView) getView().findViewById(R.id.f_home_ad_mob);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // ad mob adView init
        initWidgetOfAdView();

        // 나의 fitness center 의 회원 목록을 가져와서
        // 각 상황에 맞게
        // 관련된 widget 들을 초기 내용을 설정한다.
        loadContentOfFitnessCenterMemberListSection();

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

    private void initWidgetOfFitnessCenterActiveButtonGroup(boolean isActive) {

    }


    private void loadContentOfFitnessCenterMemberListSection() {
        final String METHOD_NAME = "[loadContent] ";


        FitnessCenterMemberManager fitnessCenterMemberManager = new FitnessCenterMemberManager(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        fitnessCenterMemberManager.init(new FitnessCenterMemberManager.OnMemberManipulateListener() {

            @Override
            public void isDisclosedState(String myUid, UserFitnessCenter myFitnessCenter, Member myMemberData, ArrayList<Member> memberArrayList) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------------------------------------------------------------------------");

                // 내가 등록한 피트니스 센터가 있고 '공개' 상태일 때
                // fitness center member list section 의 widget 들의 초기 내용을 설정한다.
                if (myFitnessCenter != null) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > getIsDisclosed = " + myFitnessCenter.getIsDisclosed());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< myFitnessCenter > getMemberNumber = " + myFitnessCenter.getMemberNumber());
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< memberArrayList > size = " + memberArrayList.size());

                    // 주의)
                    // 피트니스 센터에 등록했는데 나의 회원 정보가 없다면 이상한 일이다.!!!!!!!!!!!
                    if (myMemberData != null) {

                        // 나의 피트니스 센터에 등록되어 있는 회원 데이터
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 멤버 > myMemberData 객체는 ? = " + myMemberData);

                        // 내가 등록한 FitnessCenter 에 회원이 있을 경우에
                        if (!memberArrayList.isEmpty()) {

                            // ==================================================== recycler view ====================================================
                            if (instruction.getVisibility() == View.VISIBLE) {
                                instruction.setVisibility(View.GONE);
                            }

                            // ==================================================== recycler view ====================================================
                            // 만약 recycler view 가 GONE 이면 VISIBLE 로 변경
                            if (fitnessCenterMemberRecyclerView.getVisibility() == View.GONE) {
                                fitnessCenterMemberRecyclerView.setVisibility(View.VISIBLE);
                            }
                            // recycler view adapter
                            FitnessCenterMemberRvAdapter adapter = new FitnessCenterMemberRvAdapter(myUid, myFitnessCenter, memberArrayList);

                            // recycler view
                            // 해당 layout xml 파일에 layoutManager 를 LinearLayout 으로 설정해 놓아서 별다른 설정은 안 해 주었다.
                            fitnessCenterMemberRecyclerView.setAdapter(adapter);

                            // ==================================================== button group ====================================================
                            if (fitnessCenterActiveStateGroup.getVisibility() == View.GONE) {
                                fitnessCenterActiveStateGroup.setVisibility(View.VISIBLE);
                            }

                            // 1. 기존의 클릭 리스너 제거 후
                            fitnessCenterActiveStateGroup.clearOnButtonCheckedListeners();

                            // 2. 나의 member 데이터에 저장되어 있는 isActive 값을 통해 버튼 체크하고
                            if (myMemberData.getActiveState() == Member.ACTIVE_STATE_ENTER) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의 ActiveState > activeState 가 '입장' 상태입니다.");
                                fitnessCenterActiveStateGroup.check(R.id.f_home_fitness_center_active_state_enter);

                            } else if (myMemberData.getActiveState() == Member.ACTIVE_STATE_EXERCISE) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의 ActiveState > activeState 가 '운동 중' 상태입니다.");
                                fitnessCenterActiveStateGroup.check(R.id.f_home_fitness_center_active_state_exercise);

                            } else if (myMemberData.getActiveState() == Member.ACTIVE_STATE_EXIT) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 나의 ActiveState > activeState 가 '퇴장' 상태입니다.");
                                fitnessCenterActiveStateGroup.check(R.id.f_home_fitness_center_active_state_exit);

                            }

                            // 3.다시 fitnessCenterActiveButtonGroup 의 '입장', '퇴장' 을 눌렀을 때
                            // 해당 데이터 베이스의 isActive 를 변경하는
                            // 리스너를 재 등록 한다.
                            fitnessCenterActiveStateGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
                                @Override
                                public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 체크 확인 > group = " + group);
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 체크 확인 > checkedId = " + checkedId);
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 체크 확인 > isChecked = " + isChecked);

                                    if (isChecked) {
                                        switch (checkedId) {
                                            case R.id.f_home_fitness_center_active_state_enter:
                                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 토글 버튼 > enter 버튼을 클릭 하였습니다.");
                                                updateContentOfMyIsActive(myFitnessCenter, Member.ACTIVE_STATE_ENTER);
                                                break;
                                            case R.id.f_home_fitness_center_active_state_exercise:
                                                updateContentOfMyIsActive(myFitnessCenter, Member.ACTIVE_STATE_EXERCISE);
                                                break;
                                            case R.id.f_home_fitness_center_active_state_exit:
                                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 토글 버튼 > exist 버튼을 클릭 하였습니다.");
                                                updateContentOfMyIsActive(myFitnessCenter, Member.ACTIVE_STATE_EXIT);
                                                break;
                                        }
                                    }

                                }
                            });


                        }
                    }
                }
            }

            @Override
            public void isNotIsDisclosedState() {

                // 내가 등록한 피트니스 센터가 있지만, '비공개' 상태일 때
                // recycler view : 내가 비공개로 했으므로 다른 사람의 접속여부도 알지 못하도록 GONE
                fitnessCenterMemberRecyclerView.setVisibility(View.GONE);

                // activeStateGroup 도 GONE
                fitnessCenterActiveStateGroup.setVisibility(View.GONE);

                // instruction
                instruction.setText(R.string.f_home_fitness_center_is_not_disclosed);

            }

            @Override
            public void isNotRegisteredTheFitnessCenter() {
                // 내가 등록한 피트니스 센터가 없을 때

                // activeStateGroup 도 GONE
                fitnessCenterActiveStateGroup.setVisibility(View.GONE);

                // instruction
                instruction.setText(R.string.f_home_fitness_center_not_register);
            }
        });
    }


    private void updateContentOfMyIsActive(UserFitnessCenter myFitnessCenter, int activeState) {
        final String METHOD_NAME = "[updateContentOfMyIsActive] ";

        HashMap<String, Object> activeUpdate = new HashMap<>();
        activeUpdate.put(Member.ACTIVE_STATE, activeState);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(FirebaseConstants.DATABASE_FIRST_NODE_FITNESS_CENTER)
                .child(myFitnessCenter.getFirstAddress())
                .child(myFitnessCenter.getSecondAddress())
                .child(myFitnessCenter.getThirdAddress())
                .child(FitnessCenter.MEMBER_LIST)
                .child(myFitnessCenter.getMemberNumber() + "")
                .updateChildren(activeUpdate, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error != null) {
                            return;
                        }

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< 활성화 상태 > 나의 활성화 상태 변경을 완료하였습니다.");

                    }
                });
    }

}
