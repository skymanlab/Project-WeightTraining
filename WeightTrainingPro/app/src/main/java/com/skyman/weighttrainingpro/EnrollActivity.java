package com.skyman.weighttrainingpro;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.project.data.DataFormatter;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.user.data.EncryptionUtils;
import com.skyman.weighttrainingpro.management.user.data.User;
import com.skyman.weighttrainingpro.management.user.database.mysql.UserEnroll;

public class EnrollActivity extends AppCompatActivity {

    // constant
    private static final String CLASS_NAME = "[Ac]_EnrollActivity";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private CheckBox tempUserChecker;
    private EditText name;
    private EditText emailName;
    private Spinner emailHost;
    private EditText password;
    private Button enroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll);

        final String METHOD_NAME = "[onCreate] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+>+> EnrollActivity <+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+<+");

        // [method] : widget mapping
        mappingOfWidget();

        // [method] : widget init
        initWidgetOfSectionOne();

    } // End of method [onCreate]


    /**
     * [method] [set] widget mapping
     */
    private void mappingOfWidget() {

        // [iv/C]CheckBox : tempUserChecker mapping
        this.tempUserChecker = (CheckBox) findViewById(R.id.enroll_section_1_ch_temp_user);

        // [iv/C]EditText : name mapping
        this.name = (EditText) findViewById(R.id.enroll_section_1_name);

        // [iv/C]EditText : emailName mapping
        this.emailName = (EditText) findViewById(R.id.enroll_section_1_email_name);

        // [iv/C]Spinner : emailHost mapping
        this.emailHost = (Spinner) findViewById(R.id.enroll_section_1_sp_email_host);

        // [iv/C]EditText : password mapping
        this.password = (EditText) findViewById(R.id.enroll_section_1_password);

        // [iv/C]EditText : enroll mapping
        this.enroll = (Button) findViewById(R.id.enroll_section_1_bt_enroll);

    } // End of method [mappingOfWidget]


    /**
     * [method] [set] section_1 widget 의 초기화
     */
    private void initWidgetOfSectionOne() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        // [method] : emailHost 를 R.array.emailHost 와 연결하기
        setAdapterOfEmailHost();

        // [iv/C]Button : enroll click listener
        this.enroll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // [method] : enroll 을 실행할지 물어보는 AlertDialog 를 보여준다.
                showAlertDialogOfEnroll();

            }
        });

        // [iv/C]CheckBox : tempUserChecker click listener / 체크 되어있는지 확인하여 widget 의 상태 변경
        this.tempUserChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // [check 1] : 체크 됨
                if (isChecked) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 체크되었습니다. 입력을 금지합니다. <=");
                    // [method] : 새로운 입력 금지
                    setEnableWidgetOFSectionOne(false, R.color.colorBackgroundGray);

                } else {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_2/false : no. 새로운 회원이 되어줘! <=");
                    // [method] : 새로운 입력 할 수 있도록
                    setEnableWidgetOFSectionOne(true, R.color.colorBackgroundWhite);

                } // [check 1]
            }
        });

    } // End of method [initWidgetOfSectionOne]


    /**
     * [method] [set] section_1 widget 내용 초기화
     */
    private void initContentOfSectionOne() {

        // [iv/C]CheckBox : tempUserChecker 체크 해제
        this.tempUserChecker.setChecked(false);

        // [iv/C]EditText : name 내용 지우기
        this.name.setText("");

        // [iv/C]EditText : emailName 내용 지우기
        this.emailName.setText("");

        // [iv/C]Spinner : emailHost 의 첫 host 를 선택
        this.emailHost.setSelection(0);

        // [iv/C]EditText : password 내용 지우기
        this.password.setText("");

    }


    /**
     * [method] [set] emailHost 와 R.array.emailHost 를 연결한다.
     */
    private void setAdapterOfEmailHost() {

        // [lv/C]ArrayAdapter : R.array.emailHost 와 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.emailHost, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 를 연결하기
        this.emailHost.setAdapter(adapter);

    } // End of method [setAdapterOfEmailHost]


    /**
     * [method] [set] enroll click listener 설정하기
     */
    private void setClickListenerOfEnroll() {

        final String METHOD_NAME = "[setClickListenerOfEnroll] ";

        // [check 1] : 임시 사용자로 등록하기 체크박스에 체크가 되어있다.
        if (this.tempUserChecker.isChecked()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 체크됨 -> 임시회원으로 등록하기 <=");
            // [lv/C]String : 임시 유저 난수 salt
            String salt = EncryptionUtils.generateSalt();

            // [lv/C]String : 임시 유저의 암호화된 password 생성
            String encryptedPassword = EncryptionUtils.encryptPassword("temppassword", salt);

            // [lv/C]User : 임시 유저 생성
            User tempUser = new User();
            tempUser.setName("tempUser");                       // 1. name = tempUser
            tempUser.setEmail("tempUser@temp.user");            // 2. email = tempUser@temp.user
            tempUser.setSalt(salt);                             // 3. salt
            tempUser.setPassword(encryptedPassword);            // 4. password

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "*** temp user 정보 확인 ***");
            LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, tempUser);

            // [lv/C]UserEnroll : 위의 임시유저를 mysql 서버에 저장 -> count 값 할당 -> user table 에 저장
            UserEnroll userEnroll = new UserEnroll(getApplicationContext(), EnrollActivity.this);
            userEnroll.execute(tempUser);

        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 체크안됨 -> 입력한 데이터로 회원등록하기 <=");
            // [check 2] : widget 의 모든 값 입력 확인
            if (checkWhetherInputOfWidget()) {

                // [lv/C]String : name, email, password widget 의 입력내용 가져오기
                String nameContent = DataManager.removeWhitespace(this.name.getText().toString());
                String emailContent = DataFormatter.setEmailFormat(DataManager.removeWhitespace(this.emailName.getText().toString()), this.emailHost.getSelectedItem().toString());
                String passwordContent = DataManager.removeWhitespace(password.getText().toString());

                // [lv/C]String : salt 난수 생성하고, password 암호화하기
                String salt = EncryptionUtils.generateSalt();
                String encryptedPassword = EncryptionUtils.encryptPassword(passwordContent, salt);

                // [lv/C]User : User 객체 생성 및 데이터 입력
                User user = new User();
                user.setName(nameContent);              // 1. name
                user.setEmail(emailContent);            // 2. email
                user.setSalt(salt);                     // 3. salt
                user.setPassword(encryptedPassword);    // 4. password

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "**** user 정보 확인 ***");
                LogManager.displayLogOfUser(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, user);

                // [lv/C]UserEnroll : 위의 user 를 mysql 서버에 저장 -> count 값 할당 -> user table 에 저장
                UserEnroll userEnroll = new UserEnroll(getApplicationContext(), EnrollActivity.this);
                userEnroll.execute(user);

                // [method] : section_top 의 내용 초기화
                initContentOfSectionOne();

            } else {

                // [lv/C]Toast : 모든 값을 입력해줘!
                Toast.makeText(getApplicationContext(), "모든 값을 입력해줘!", Toast.LENGTH_SHORT).show();

            } // [check 2]

        } // [check 1]

    } // End of method [setClickListenerOfEnroll]


    /**
     * [method] [set] tempUserChecker 의 체크 유무에 따라 section_top 의 widget 의 enable 변경, 색 변경
     *
     * @param isEnable 보여줄까? 말까?
     * @param color    배경색은 뭐냐?
     */
    private void setEnableWidgetOFSectionOne(boolean isEnable, int color) {

        // [iv/C]EditText : name disable / 색 변경
        this.name.setEnabled(isEnable);
        this.name.setBackgroundResource(color);

        // [iv/C]EditText : emailName disable / 색 변경
        this.emailName.setEnabled(isEnable);
        this.emailName.setBackgroundResource(color);

        // [iv/C]Spinner : emailHost disable / 색 변경
        this.emailHost.setEnabled(isEnable);
        this.emailHost.setBackgroundResource(color);

        // [iv/C]EditText : password disable / 색 변경
        this.password.setEnabled(isEnable);
        this.password.setBackgroundResource(color);

    } // End of method [setEnableWidgetOFSectionOne]


    /**
     * [method] [checker] name, emailName, emailHost, password widget 의 모든 내용을 입력했는지 판별하여 모든 데이터가 입력되면 true 를 반환한다.
     */
    private boolean checkWhetherInputOfWidget() {

        final String METHOD_NAME = "[checkWhetherInputOfWidget] ";

        // [check 1] : name, emailName, emailHost, password 에 입력된 값이 있다.
        if (!this.name.getText().toString().equals("")
                && !this.emailName.getText().toString().equals("")
                && !this.emailHost.getSelectedItem().toString().equals("")
                && !this.password.getText().toString().equals("")) {

            // 모든 입력 확인
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 모든 값이 입력되었습니다.  <=");
            return true;

        } else {

            // 입력이 안된 곳 발견
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 모든 값이 입려되지 않았어!  <=");
            return false;

        } // [check 1]

    } // End of method [checkWhetherInputOfWidget]


    /**
     * [method] user 를 등록할건지 물어보는 AlertDialog 를 보여준다.
     */
    private void showAlertDialogOfEnroll() {

        // [lv/C]AlertDialog : builder 객체 생성 / 초기 설정 및 노출
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.enroll_alert_enroll_title)
                .setMessage(R.string.enroll_alert_enroll_message)
                .setPositiveButton(R.string.enroll_alert_enroll_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] : enroll click listener
                        setClickListenerOfEnroll();

                    }
                })
                .setNegativeButton(R.string.enroll_alert_enroll_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .show();

    } // End of method [showAlertDialogOfEnroll]
}