package com.skyman.weighttrainingpro.management.project.activity.event.add;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.skyman.weighttrainingpro.R;
import com.skyman.weighttrainingpro.management.developer.Display;
import com.skyman.weighttrainingpro.management.developer.LogManager;
import com.skyman.weighttrainingpro.management.event.data.Event;
import com.skyman.weighttrainingpro.management.event.database.EventDbManager;
import com.skyman.weighttrainingpro.management.event.database.mysql.EventRegister;
import com.skyman.weighttrainingpro.management.project.activity.SectionManager;
import com.skyman.weighttrainingpro.management.project.activity.SectionInitialization;
import com.skyman.weighttrainingpro.management.project.data.DataManager;
import com.skyman.weighttrainingpro.management.project.data.type.EquipmentType;
import com.skyman.weighttrainingpro.management.project.data.type.GroupType;
import com.skyman.weighttrainingpro.management.project.data.type.MuscleArea;
import com.skyman.weighttrainingpro.management.user.data.User;

public class EASectionOneManager extends SectionManager implements SectionInitialization {

    // constant
    private static final String CLASS_NAME = "[PAEA]_EASectionOneManager";       // EventProgramActivity Section One Each Random Manager
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private LinearLayout titleWrapper;
    private TextView moreData;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;
    private LinearLayout addWrapper;
    private TextView add;

    // instance variable
    private EventDbManager eventDbManager;

    // constructor
    public EASectionOneManager(Activity activity, User user, MuscleArea muscleArea) {
        super(activity, user, muscleArea);
    }

    // setter
    public void setEventDbManager(EventDbManager eventDbManager) {
        this.eventDbManager = eventDbManager;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) getActivity().findViewById(R.id.event_add_section_1_title_wrapper);

        // [iv/C]TextView : moreData mapping
        this.moreData = (TextView) getActivity().findViewById(R.id.event_add_section_1_bt_more_data);

        // [iv/C]Spinner : equipmentType mapping / 운동기기 종류
        this.equipmentType = (Spinner) getActivity().findViewById(R.id.event_add_section_1_equipment_type);

        // [iv/C]Spinner : groupType mapping / 그룹 유형
        this.groupType = (Spinner) getActivity().findViewById(R.id.event_add_section_1_group_type);

        // [iv/C]EditText : eventName mapping / 종목 이름
        this.eventName = (EditText) getActivity().findViewById(R.id.event_add_section_1_event_name);

        // [iv/C]EditText : properWeight mapping / 적정 중량
        this.properWeight = (EditText) getActivity().findViewById(R.id.event_add_section_1_proper_weight);

        // [iv/C]EditText : maxWeight mapping / 최대 중량
        this.maxWeight = (EditText) getActivity().findViewById(R.id.event_add_section_1_max_weight);

        // [iv/C]LinearLayout : addWrapper mapping
        this.addWrapper = (LinearLayout) getActivity().findViewById(R.id.event_add_section_1_add_wrapper);

        // [iv/C]TextView : add mapping
        this.add = (TextView) getActivity().findViewById(R.id.event_add_section_1_add);

    }

    @Override
    public void initWidget() {

        final String METHOD_NAME = "[initWidgetOfSectionOne] ";

        // [method] : muscleArea 값에 따라 sectionOneTitleWrapper, sectionOneAddWrapper, sectionTwoTitleWrapper 의 색 바꾸기
        setBackgroundWithMuscleArea(getMuscleArea());

        // [method] : equipmentType 의 adapter 설정
        setAdapterOfEquipmentType();

        // [method] : groupType 의 adapter 설정
        setAdapterOfGroupType();

        // [iv/C]TextView : moreData click listener
        this.moreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // [iv/C]Button : add click listener
        this.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 데이터저장 전에 alertDialog 로 진행 여부 물어보기
                showAlertDialogOfSave();

            }
        });
    }


    /**
     * [method] [set] muscleArea 의 값에 따라서 titleWrapper, addWrapper 의 색을 변경한다.
     */
    private void setBackgroundWithMuscleArea(MuscleArea muscleArea) {

        final String METHOD_NAME = "[setBackgroundWithMuscleArea] ";

        // [check 1] : muscleArea 가 뭐냐?
        switch (muscleArea) {
            case CHEST:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/chest 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFirst 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFirst);

                // [iv/C]LinearLayout : addWrapper 의 색을 R.color.colorBackgroundFirst 으로 설정
                this.addWrapper.setBackgroundResource(R.color.colorBackgroundFirst);
                break;

            case SHOULDER:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/shoulder 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundSecond 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundSecond);

                // [iv/C]LinearLayout : addWrapper 의 색을 R.color.colorBackgroundSecond 으로 설정
                this.addWrapper.setBackgroundResource(R.color.colorBackgroundSecond);
                break;

            case LAT:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/lat 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundThird 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundThird);

                // [iv/C]LinearLayout : addWrapper 의 색을 R.color.colorBackgroundThird 으로 설정
                this.addWrapper.setBackgroundResource(R.color.colorBackgroundThird);
                break;

            case LEG:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/leg 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFourth 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFourth);

                // [iv/C]LinearLayout : addWrapper 의 색을 R.color.colorBackgroundFourth 으로 설정
                this.addWrapper.setBackgroundResource(R.color.colorBackgroundFourth);
                break;

            case ARM:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/arm 가 입력되었습니다. <=");
                // [iv/C]LinearLayout : titleWrapper 의 색을 R.color.colorBackgroundFifth 으로 설정
                this.titleWrapper.setBackgroundResource(R.color.colorBackgroundFifth);

                // [iv/C]LinearLayout : addWrapper 의 색을 R.color.colorBackgroundFifth 으로 설정
                this.addWrapper.setBackgroundResource(R.color.colorBackgroundFifth);
                break;

            default:
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/default : 범위 밖의 값이 입력되었습니다. <=");
                break;
        } // [check 1]

    } // End of method [setBackgroundWithMuscleArea]


    /**
     * [method] [set] equipmentType 과 R.array.equipmentType 과 연결하기
     */
    private void setAdapterOfEquipmentType() {

        // [lv/C]ArrayAdapter : R.array.sectionOneType 을 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.equipmentType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 을 equipmentType 와 연결
        this.equipmentType.setAdapter(adapter);

    } // End of method [setAdapterOfSectionOneEquipmentType]


    /**
     * [method] [set] groupType 과 R.array.groupType 과 연결하기
     */
    private void setAdapterOfGroupType() {

        // [lv/C]ArrayAdapter : R.array.groupType 을 연결하기 위한 adapter 생성
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.groupType, android.R.layout.simple_spinner_dropdown_item);

        // [iv/C]Spinner : 위 adapter 을 targetMuscleType 와 연결
        this.groupType.setAdapter(adapter);

    } // End of method [setAdapterOfGroupType]


    /**
     * [method] modify button 을 눌렀을 때, 해당 내용을 저장할 건지 물어보는 alert dialog 를 띄운다.
     */
    private void showAlertDialogOfSave() {

        final String METHOD_NAME = "[showAlertDialogOfSave] ";

        // [lv/C]AlertDialog : builder 객체 생성 / 초기 설정 및 노출
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.cu_event_item_alert_save_title)
                .setMessage(R.string.cu_event_item_alert_save_message)
                .setPositiveButton(R.string.cu_event_item_alert_save_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // [method] : sectionOneAdd button 의 click listener
                        setClickListenerOfSectionOneAdd();

                    }
                })
                .setNegativeButton(R.string.cu_event_item_alert_save_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                })
                .show();

    } // End of method [showAlertDialogOfSave]


    /**
     * [method] [set] sectionOneAdd button 의 click listener 을 설정한다.
     */
    private void setClickListenerOfSectionOneAdd() {

        final String METHOD_NAME = "[setClickListenerOfSectionOneAdd] ";

        // [check 1] : Spinner, EditText widget 의 모든 값을 입력 받았다.
        if (checkWhetherInputOfWidget()) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "check_1/true : 입력여부 판별을 통과되었습니다.");

            // [method] : event table 에 데이터 저장
            saveEvent();

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "end : 데이터 저장이 완료되었습니다.");

        } // [check 1]

    } // End of method [setClickListenerOfSectionOneAdd]


    /**
     * [method] sectionOneEquipmentType, sectionOneEvent, sectionOneProperWeight, sectionOneMaxWeight widget 의 값들을 모두 입력 받은지 판별하여 true 또는 false 를 반환한다.
     */
    private boolean checkWhetherInputOfWidget() {

        final String METHOD_NAME = "[checkWhetherInputOfWidget] ";

        // [lv/b]isAllInputted : 모두 입력되었냐?
        boolean isAllInputted = false;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==>> 어떤 값을 입력 받았나요? <<==");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. event name(종목 of 종목 이름) = " + this.eventName.getText().toString());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. equipment type(운동기구 종류) = " + this.equipmentType.getSelectedItem().toString());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "5. group type(그룹 유형) = " + this.groupType.getSelectedItem().toString());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "6. proper weight(적정중량) = " + this.properWeight.getText().toString());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "7. max weight(최대중량) = " + this.maxWeight.getText().toString());

        // [check 1] : EditText 와 Spinner widget 에 데이터가 입력되었다.
        if (!this.eventName.getText().toString().equals("")
                && !this.equipmentType.getSelectedItem().toString().equals("")
                && !this.groupType.getSelectedItem().toString().equals("")
                && !this.properWeight.getText().toString().equals("")
                && !this.maxWeight.getText().toString().equals("")) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 모두 입력되었습니다. <=");
            // [lv/b]isAllInputted : 모두 입력되었다.
            isAllInputted = true;

        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 모두 입력되지 않았어! <=");
            Toast.makeText(getActivity(), R.string.cu_event_item_toast_error_input_event, Toast.LENGTH_SHORT).show();
        } // [check 1]

        return isAllInputted;

    } // End of method [checkWhetherInputOfWidget]


    /**
     * [method] event 데이터를 event table 에 저장한다.
     * step 1. SQLite 내부 데이터베이스 저장
     * step 2. skyman13.cafe24.com/ 서버의 mysql 데이터베이스에 저장
     */
    private void saveEvent() {

        final String METHOD_NAME = "[saveEvent] ";

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 1. SQLite Database =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // [lv/C]String : eventName, muscleArea, equipmentType, groupType 를 String 으로 가져오기
        String eventNameContent = this.eventName.getText().toString();
        long userCountContent = getUser().getCount();
        MuscleArea muscleAreaContent = getMuscleArea();
        EquipmentType equipmentTypeContent = DataManager.convertEquipmentType(this.equipmentType.getSelectedItemPosition());
        GroupType groupTypeContent = DataManager.convertGroupType(this.groupType.getSelectedItemPosition());

        // [lv/f]properWeightContent, maxWeightContent : properWeight, maxWeight 값을 int 로 가져오기
        float properWeightContent = Float.parseFloat(this.properWeight.getText().toString());
        float maxWeightContent = Float.parseFloat(this.maxWeight.getText().toString());

        // [lv/C]Event : 위의 데이터에 설정
        Event event = new Event();
        event.setEventName(eventNameContent);
        event.setUserCount(userCountContent);
        event.setMuscleArea(muscleAreaContent);
        event.setEquipmentType(equipmentTypeContent);
        event.setGroupType(groupTypeContent);
        event.setProperWeight(properWeightContent);
        event.setMaxWeight(maxWeightContent);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "==>> 내용 확인 <<==");
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "1. userCount = " + userCountContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "2. eventContent = " + eventNameContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "3. muscleAreaContent = " + muscleAreaContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "4. equipmentTypeContent = " + equipmentTypeContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "5. groupTypeContent = " + groupTypeContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "6. properWeightContent = " + properWeightContent);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "7. maxWeightContent = " + maxWeightContent);


        // [lv/C]EventRegister : 외부 -> 내부 데이터로 데이터 저장
        EventRegister eventRegister = new EventRegister(getActivity(), this.eventDbManager, this.equipmentType, this.groupType, this.eventName, this.properWeight, this.maxWeight);
        eventRegister.execute(event);
//
//        // [lv/l]eventCount : 위 데이터를 event table 저장하고 그 결과를 저장 / 성공하였을 시 count 값 반화 ( 저장된 열 번호)
//        long eventCount = this.eventDbManager.saveContent(
//                eventNameContent,                   // 1. event name
//                userCountContent,                   // 2. user count
//                muscleAreaContent,   // 3. muscle area
//                equipmentTypeContent,       // 4. equipment type
//                targetMuscleTypeContent,    // 5. target muscle type
//                properWeightContent,        // 6. proper weight
//                maxWeightContent            // 5. max weight
//        );
//
//        // [lv/C]Event : event table 에 데이터 입력 후, 결과로 받은 count 값 넣기
//        event.setCount(eventCount);
//
//        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 2. SQLite Database =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//
//        // [check 1] : count 가 0 보다 큰 값이여야지 정상
//        if (0 < eventCount) {
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 조건 통과");
//
//            // [lv/C]EventInsert : mysql 데이터베이스 서버에도 똑같이 저장한다.
//            EventInsert insertEvent = new EventInsert();
//            insertEvent.execute(event);
//
//        } else {
//            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 데이터베이스에서 error 발생했어!");
//        } // [check 1]
//
//        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= step 3. section_1 widget 초기화 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//        // [method] : section_1 widget 초기화 하기
//        initContentOfSectionOne();
//
//        // [lv/C]Toast : 성공했다는 것을 사용자에게 알림
//        Toast.makeText(getApplicationContext(), "저장에 성공하였습니다.", Toast.LENGTH_SHORT).show();

    } // End of method [saveEvent]
}
