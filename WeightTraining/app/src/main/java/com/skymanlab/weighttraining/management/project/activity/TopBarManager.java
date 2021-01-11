package com.skymanlab.weighttraining.management.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skymanlab.weighttraining.LoginActivity;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.SettingsActivity;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.data.BaseEventDataManager;
import com.squareup.picasso.Picasso;

public class TopBarManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PA] TopBarManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private boolean shouldSetTitle;
    private boolean shouldSetSubTitle;

    // instance variable
    private boolean canInit;

    // instance variable
    private String titleContent;
    private String subTitleContent;

    // instance variable
    private TextView title;
    private TextView subTitle;
    private ImageView back;
    private ImageView userPhoto;

    // constructor
    public TopBarManager(Activity activity, FirebaseUser firebaseUser, boolean shouldSetTitle, boolean shouldSetSubTitle) {
        super(activity, firebaseUser);
        this.shouldSetTitle = shouldSetTitle;
        this.shouldSetSubTitle = shouldSetSubTitle;
        this.canInit = false;
    }

    // setter
    public void setTitleContent(String titleContent) {
        this.titleContent = titleContent;
    }

    public void setSubTitleContent(String subTitleContent) {
        this.subTitleContent = subTitleContent;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : title connect
        this.title = (TextView) super.getActivity().findViewById(R.id.top_bar_title);

        // [iv/C]TextView : subTitle connect
        this.subTitle = (TextView) super.getActivity().findViewById(R.id.top_bar_sub_title);

        // [iv/C]ImageView : back connect
        this.back = (ImageView) super.getActivity().findViewById(R.id.top_bar_back);

        // [iv/C]ImageView : userPhoto connect
        this.userPhoto = (ImageView) super.getActivity().findViewById(R.id.top_bar_user_photo);

        this.canInit = true;

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [check 1] : 초기화 작업을 실행할 수 있다.
        if (canInit) {

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= title 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [method] : title 설정
            setTitle();

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= sub title 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [method] : subTitle 설정
            setSubTitle();

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= back 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [iv/C]ImageView : back click listener 설정
            this.back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // [method] : back 버튼 클릭
                    getActivity().onBackPressed();

                }
            });

            // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= user photo 설정 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
            // [lv/C]Picasso : Picasso 를 이용하여 firebaseUser 의 photoUrl 이미지를 userPhoto 에 출력하기
            Picasso.get().load(getFirebaseUser().getPhotoUrl()).into(userPhoto);

            // [iv/C]ImageView : ImageView 를 동그랗게 만들기 위한 설정하기
            this.userPhoto.setBackground(new ShapeDrawable(new OvalShape()));
            this.userPhoto.setClipToOutline(true);


            // [iv/C]ImageView : userPhoto click listener 설정
            this.userPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // [lv/C]SharedPreference : key 가 "basic_event_data" 인 값 가져오기
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String isRegisteredBaseEventData = sharedPreferences.getString("base_event_data", "false");
                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> base_event_data 의 값 = " + isRegisteredBaseEventData);
                    // [check 1] : basic_event_data 의 값이 뭐냐?
                    switch (isRegisteredBaseEventData) {

                        case "false":
                            // [method] : type two 의 top bar menu
                            makeTopBarMenuTypeOne(v);
                            break;

                        case "true":
                            makeTopBarMenuTypeTwo(v);
                            break;

                        default:
                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : top bar menu 의 셋팅 목록에 없는 값입니다. <=");
                            break;

                    } // [check 1]

                }
            });

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : widget 들이 connect 되지 않았어요! <=");
        } // [check 1]
    }


    /**
     * [method] title 설정
     */
    public void setTitle() {

        final String METHOD_NAME = "[setTitle] ";

        // [check 1] : title 을 설정해야 한다.
        if (this.shouldSetTitle) {

            // [iv/C]TextView : title 을 설정한다.
            this.title.setText(this.titleContent);

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : title 은 새롭게 설정할 필요가 없어요! <=");
        } // [check 1]

    } // End of method [setTitle]


    /**
     * [method] sub title 설정
     */
    public void setSubTitle() {

        final String METHOD_NAME = "[setSubTitle] ";
        // [check 1] : subTitle 을 설정해야 한다.
        if (this.shouldSetSubTitle) {

            // [iv/C]TextView : sub title 을 설정한다.
            this.subTitle.setText(subTitleContent);

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : sub title 은 새롭게 설정할 필요가 없어요! <=");
        } // [check 1]

    } // End of method [setSubTitle]


    /**
     * [method] 기본 종목 데이터 입력 버튼이 있는 메뉴 만들기
     */
    private void makeTopBarMenuTypeOne(View view) {

        final String METHOD_NAME = "[makeTopBarMenuTypeOne] ";

        // [lv/C]PopupMenu : 팝업메뉴를 노출하기위한 객체 생성 // view : 팝업 메뉴를 부른 view 가 뭐임?
        PopupMenu popupMenu = new PopupMenu(super.getActivity(), view);

        // [lv/C]PopupMenu : 클릭 이벤트 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // [check 1] : 선택한 항목의 아이디가 뭐냐?
                switch (item.getItemId()) {
                    case R.id.top_bar_menu_popup_base_event_register:

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/base event register <=");
                        // [method] : 기본 종목을 외부, 내부 데이터베이스에 저장한다.
                        saveBaseEvent();
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>> 입력 성공 ");
                        break;

                    case R.id.top_bar_menu_popup_user:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/user <=");
                        break;

                    case R.id.top_bar_menu_popup_setting:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/setting <=");
                        // [method] : SettingsActivity 로 이동
                        moveSetting();
                        break;

                    case R.id.top_bar_menu_popup_logout:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/logout <=");
                        // [method] : FirebaseAuth 로그아웃 수행
                        logoutFirebaseAuth();
                        break;

                    default:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 메뉴에 없는 거 클릭! <=");
                        return false;
                } // [check 1]

                return true;
            }
        });

        // [lv/C]PopupMenu : R.menu.bar_menu 와 연결하기
        popupMenu.inflate(R.menu.top_bar_menu_type_one);

        // [lv/C]PopupMenu : 설정된 팝업메뉴 보여주기
        popupMenu.show();

    } // End of method [makeTopBarMenuTypeOne]


    /**
     * [method] 기본 종목 데이터 입력 버튼이 없는 메뉴 만들기
     */
    private void makeTopBarMenuTypeTwo(View view) {

        final String METHOD_NAME = "[makeTopBarMenuTypeTwo] ";

        // [lv/C]PopupMenu : 팝업메뉴를 노출하기위한 객체 생성 // view : 팝업 메뉴를 부른 view 가 뭐임?
        PopupMenu popupMenu = new PopupMenu(super.getActivity(), view);

        // [lv/C]PopupMenu : 클릭 이벤트 설정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // [check 1] : 선택한 항목의 아이디가 뭐냐?
                switch (item.getItemId()) {

                    case R.id.top_bar_menu_popup_user:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/user <=");
                        break;

                    case R.id.top_bar_menu_popup_setting:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/setting <=");
                        // [method] : SettingsActivity 로 이동
                        moveSetting();
                        break;

                    case R.id.top_bar_menu_popup_logout:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/logout <=");
                        // [method] : FirebaseAuth 로그아웃 수행
                        logoutFirebaseAuth();
                        break;

                    default:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 메뉴에 없는 거 클릭! <=");
                        return false;

                } // [check 1]

                return true;
            }
        });

        // [lv/C]PopupMenu : R.menu.bar_menu 와 연결하기
        popupMenu.inflate(R.menu.top_bar_menu_type_two);

        // [lv/C]PopupMenu : 설정된 팝업메뉴 보여주기
        popupMenu.show();

    } // End of method [makeTopBarMenuTypeTwo]


    /**
     * [method] 기본 event 데이터를 생성하고, 외부 데이터베이스에 저장 후 그 결과값을 내부 데이터베이스에 저장한다.
     */
    private void saveBaseEvent() {

        final String METHOD_NAME = "[saveBaseEvent] ";

        // [lv/C]BaseEventDataManager : 기본 event 데이터 관리자 생성 및 기본 데이터 저장
        BaseEventDataManager dataManager = new BaseEventDataManager(getFirebaseUser().getUid());
        dataManager.makeAllBaseEvent();
        dataManager.saveAllBaseEvent();

        // [lv/C]SharedPreference : 설정을 위한 객체 생성 / base_event_data 를  false -> true 로 변경
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("base_event_data", "true");
        editor.commit();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">+++++++>>++++++>>>> false -> true 로 변경 완료 = " + preferences.getString("base_event_data", ""));

    } // End of method [saveBaseEvent]


    /**
     * [method] 설정화면을 나타내는 SettingActivity 로 이동한다.
     */
    private void moveSetting() {

        // [lv/C]Intent : SettingsActivity 로 이동하기 위한 Intent 생성
        Intent intent = new Intent(super.getActivity(), SettingsActivity.class);
        super.getActivity().startActivity(intent);

    } // End of method [moveSetting]


    /**
     * [method] FirebaseAuth 로그아웃
     */
    private void logoutFirebaseAuth() {

        // [lv/C]FirebaseAuth : FirebaseAuth 로그아웃
        FirebaseAuth.getInstance().signOut();

        // [lv/C]Intent : LoginActivity 로 이동하기 위한
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        getActivity().startActivity(intent);

    } // End of method [logoutFirebaseAuth]

}
