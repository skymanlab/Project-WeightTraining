package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.compose.animation.core.Spring;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.event.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.TrainingFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.ProgramFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.Step7DetailProgramResultItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MakerStep7SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep7SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // constant
    private static final String NICK_NAME = "nickName";
    private static final String TOTAL_EVENT_NUMBER = "totalEventNumber";
    private static final String TOTAL_SET_NUMBER = "totalSetNumber";
    private static final String SET_NUMBER = "setNumber";
    private static final String DETAIL_PROGRAM_ORDER = "order";
    private static final String REST_TIME_MINUTE = "restTimeMinute";
    private static final String REST_TIME_SECOND = "restTimeSecond";

    // instance variable
    private ArrayList<Event> finalOrderList;
    private Program program;
    private HashMap<String, DetailProgram> detailProgramList;

    // instance variable
    private TextInputEditText nickName;
    private TextView programSettingSetNumber;
    private TextView programSettingRestTime;
    private TextView programSettingTotalEventNumber;
    private TextView programSettingTotalSetNumber;
    private ScrollView resultContentWrapper;
    private LinearLayout detailProgramResultListWrapper;

    // constructor
    public MakerStep7SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setFinalOrderList(ArrayList<Event> finalOrderList) {
        this.finalOrderList = finalOrderList;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setDetailProgramList(HashMap<String, DetailProgram> detailProgramList) {
        this.detailProgramList = detailProgramList;
    }

    @Override
    public void connectWidget() {

        // [ TextInputEditText | nickName ] widget connect
        this.nickName = (TextInputEditText) getView().findViewById(R.id.f_maker_step7_result_nick_name);

        // [ TextView | programSettingSetNumber ] widget connect
        this.programSettingSetNumber = (TextView) getView().findViewById(R.id.f_maker_step7_result_program_setting_set_number);

        // [ TextView | programSettingRestTime ] widget connect
        this.programSettingRestTime = (TextView) getView().findViewById(R.id.f_maker_step7_result_program_setting_rest_time);

        // [ TextView | programSettingTotalEventNumber ] widget connect
        this.programSettingTotalEventNumber = (TextView) getView().findViewById(R.id.f_maker_step7_result_program_setting_total_event_number);

        // [ TextView | programSettingTotalSetNumber ] widget connect
        this.programSettingTotalSetNumber = (TextView) getView().findViewById(R.id.f_maker_step7_result_program_setting_total_set_number);

        // [ ScrollView | resultContentWrapper ] widget connect
        this.resultContentWrapper = (ScrollView) getView().findViewById(R.id.f_maker_step7_result_content_wrapper);

        // [ LinearLayout | detailProgramResultListWrapper ] widget connect
        this.detailProgramResultListWrapper = (LinearLayout) getView().findViewById(R.id.f_maker_step7_result_detail_program_setting_list_wrapper);

    }

    @Override
    public void initWidget() {

        // [TextView] [programSettingSetNumber] text
        this.programSettingSetNumber.setText(DataFormatter.setSetNumberFormat(this.program.getSetNumber()));

        // [TextView] [programSettingRestTime] text
        this.programSettingRestTime.setText(DataFormatter.setTimeFormat(this.program.getRestTimeMinute(), this.program.getRestTimeSecond()));

        // [ TextView | programSettingTotalEventNumber ]
        this.programSettingTotalEventNumber.setText(DataFormatter.setEventNumberFormat(this.program.getTotalEventNumber()));

        // [ TextView | programSettingTotalSetNumber ] text
        this.programSettingTotalSetNumber.setText(DataFormatter.setSetNumberFormat(this.program.getTotalSetNumber()));

        // [method]
        initWidgetOfDetailProgramResultListWrapper();

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                if (!nickName.getText().toString().equals("")) {

                    saveContent();

                } else {
                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step7_result_snack_nick_name_input, Snackbar.LENGTH_SHORT).show();
                }


                return null;
            }
        };
    }


    private void initWidgetOfDetailProgramResultListWrapper() {

        // [LayoutInflater] [inflater] activity 에서 fragmentManager 를 가져오기
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [cycle 1] : finalOrderList 의 각 event 로 초기 내용 설정하기
        for (int index = 0; index < this.finalOrderList.size(); index++) {

            addViewOfDetailProgramResultListWrapper(createViewOfDetailProgramResultItem(inflater, finalOrderList.get(index), detailProgramList.get(finalOrderList.get(index).getKey())));

        } // [cycle 1]


        // resultContentWrapper 인 scrollView 를 최상단으로 이동한다.
        getFragment().getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // [ScrollView] [resultContentWrapper] 최상단으로 이동
                resultContentWrapper.fullScroll(View.SCROLL_INDICATOR_TOP);

            }
        });

    }


    /**
     * finalOrderList 의 각 event 와 그와 대응되는 detailProgram 으로 detailProgramSettingItem 객체를 생성하여 반환한다.
     *
     * @param inflater
     * @param event
     * @param detailProgram
     * @return
     */
    private Step7DetailProgramResultItem createViewOfDetailProgramResultItem(LayoutInflater inflater, Event event, DetailProgram detailProgram) {
        return new Step7DetailProgramResultItem.Builder()
                .setInflater(inflater)
                .setEvent(event)
                .setDetailProgram(detailProgram)
                .newInstance()
                .createItem();
    }


    /**
     * 해당 detailProgramResultItem 의 view 를 detailProgramResultListWrapper 에 추가하여 화면에 표시한다.
     *
     * @param detailProgramResultItem
     */
    private void addViewOfDetailProgramResultListWrapper(Step7DetailProgramResultItem detailProgramResultItem) {
        this.detailProgramResultListWrapper.addView(detailProgramResultItem.getItem());
    }


    private void saveContent() {

        // [HashMap<String, Object>] [saveData]
        HashMap<String, Object> saveData = new HashMap<>();
        saveData.put(NICK_NAME, nickName.getText().toString());
        saveData.put(TOTAL_EVENT_NUMBER, program.getTotalEventNumber());
        saveData.put(TOTAL_SET_NUMBER, program.getTotalSetNumber());

        // [DatabaseReference] [db] program/$uid$/$key$/
        DatabaseReference db = FirebaseDatabase
                .getInstance()
                .getReference("program")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        String programKey = db.push().getKey();

        db.child(programKey).setValue(saveData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                if (error == null) {

                    saveContentOfProgramList(ref);

                    // back stack 에 있던 모든 fragment  를 pop!
                    getFragment().getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    // 'program' fragment 로 이동
                    getFragment().getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_home_content_wrapper, TrainingFragment.newInstance())
                            .commit();

                }

            }
        });
    }

    /**
     * program/&uid/&programKey&/list 의 하위 항목으로 저장한다.
     *
     * @param dbRef
     */
    private void saveContentOfProgramList(DatabaseReference dbRef) {

        // [cycle ] :
        for (int index = 0; index < finalOrderList.size(); index++) {

            // 해당 eventKey 에 해당하는 detailProgram 을 가져온다.
            DetailProgram detailProgram = detailProgramList.get(finalOrderList.get(index).getKey());

            HashMap<String, Object> saveData = new HashMap<>();

            // detailProgram 객체의 유무에 따라 saveData 설정 방법이 다름
            if (detailProgram != null) {

                // detailProgram 내용을 토대로 저장
                saveData.put(DETAIL_PROGRAM_ORDER, index);
                saveData.put(SET_NUMBER, detailProgram.getSetNumber());
                saveData.put(REST_TIME_MINUTE, detailProgram.getRestTimeMinute());
                saveData.put(REST_TIME_SECOND, detailProgram.getRestTimeSecond());

            } else {

                // program 내용을 토대로 저장
                saveData.put(DETAIL_PROGRAM_ORDER, index);
                saveData.put(SET_NUMBER, program.getSetNumber());
                saveData.put(REST_TIME_MINUTE, program.getRestTimeMinute());
                saveData.put(REST_TIME_SECOND, program.getRestTimeSecond());

            }


            // program/$uid$/$programKey$/list/$eventKey$/ 에 saveData 를 저장한다.
            dbRef.child("list")
                    .child(finalOrderList.get(index).getKey())
                    .setValue(saveData);
        } // [cycle ]
    }

}
