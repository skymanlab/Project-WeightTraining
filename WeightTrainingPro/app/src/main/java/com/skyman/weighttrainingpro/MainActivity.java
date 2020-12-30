package com.skyman.weighttrainingpro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.project.data.RightDataChecker;
import com.skyman.weighttrainingpro.management.project.data.SessionManager;
import com.skyman.weighttrainingpro.management.project.database.DBServiceable;
import com.skyman.weighttrainingpro.management.project.database.ProjectDbConnector;
import com.skyman.weighttrainingpro.management.user.data.User;
import com.skyman.weighttrainingpro.management.user.database.UserDbManager;

public class MainActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_MainActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ImageView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> MainActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [iv/C]ImageView : log mapping / visible, fadein, fadeout
        this.log = (ImageView) findViewById(R.id.main_log);
        this.log.setVisibility(ImageView.VISIBLE);
        this.log.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein));

        // [lv/C]MoveEnrollActivity : 6초 후 EnrollActivity 이동
        MoveActivity move = new MoveActivity(getApplicationContext());
        move.start();

    } // End of method [onCreate]


    /**
     * [innerClass] user table 에서
     */
    class MoveActivity extends Thread implements DBServiceable {

        // instance variable
        private Context context;

        // instance variable :db
        private ProjectDbConnector projectDbConnector = null;
        private UserDbManager userDbManager = null;

        // constructor
        public MoveActivity(Context context) {
            this.context = context;
        }

        @Override
        public void run() {

            final String METHOD_NAME = "[run] ";

            try {

                // 1. 데이터베이스 연결
                // 2. user table 에 등록되어 있는 user 데이터 가져오기
                // 3. user 데이터가 있는 지 검사
                // 4. user 데이터가 없다면 -> EnrollActivity 로 이동하여 가입 진행
                // 5. user 데이터가 있다면 -> 해당 user 데이터를 intent 에 추가하여 HomeActivity 로 이동한 뒤, 다음 진행
                // 6. 이동하기 전에 데이터베이서 연결 종료

                // [method] : database 연결을 위한 초기 설정과 user table 메니저 생성하기
                connectAndInitOfDatabase();

                // [lv/C]User : userDbManager 를 통해 user table 에 저장된 내용이 있는지 검사한다.
                User user = this.userDbManager.loadContent();
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** user 데이터가 있는지 확인합니다. ***");
                LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, user);

                // [method] : 3초 지연
                sleep(3000);

                // [check 1] : user 객체가 있고, count 값을 할당받은 user 이다.
                if (RightDataChecker.checkWhetherRightUser(user)) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_/true : 이미 추가되어 있는 회원입니다. <=");
                    // [lv/C]Intent : HomeActivity 로 이동하는 intent 생성 및 이동
                    Intent intent = new Intent(this.context, HomeActivity.class);

                    // [lv/C]SessionManager : user 를 intent 에 추가하기
                    SessionManager.setUserInIntent(intent, user);
//                    SessionManager.setInitialSettingCheckerInIntent(intent, true);
                    finish();
                    startActivity(intent);

                } else {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 새롭게 회원 가입을 해야 한다. <=");

                    // [lv/C]Intent : EnrollActivity 이동하는 intent 생성 및 이동
                    Intent intent = new Intent(this.context, EnrollActivity.class);
                    finish();
                    startActivity(intent);

                } // [check 1]

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } // End of method [run]


        @Override
        public void connectAndInitOfDatabase() {

            // [iv/C]ProjectDbConnector : database 를 연결하는 객체 생성 및 연결
            this.projectDbConnector = new ProjectDbConnector(context);
            this.projectDbConnector.connectProjectDB();

            // [iv/C]UserDbManager : 위에서 연결한 projectDbConnector 을 이용하여 user table 을 관리하는 메니저 생성
            this.userDbManager = new UserDbManager(this.projectDbConnector);

        } // End of method [connectAdnInitOfDatabase]

    } // End of innerClass
}