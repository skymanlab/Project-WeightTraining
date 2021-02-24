package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.program.data.DetailProgram;
import com.skymanlab.weighttraining.management.program.data.Program;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.TrainingFragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.Step8DetailProgramSettingItem;

import java.util.ArrayList;
import java.util.HashMap;

public class MakerStep8SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep8SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Program program;

    // instance variable
    private TextInputEditText nickName;
    private TextView programSettingTotalEventNumber;
    private TextView programSettingTotalSetNumber;
    private ScrollView resultContentWrapper;
    private LinearLayout detailProgramSettingListWrapper;

    // constructor
    public MakerStep8SectionManager(Fragment fragment, View view) {
        super(fragment, view);
    }

    // setter
    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public void connectWidget() {

        // [ TextInputEditText | nickName ] widget connect
        this.nickName = (TextInputEditText) getView().findViewById(R.id.f_maker_step8_result_nick_name);

        // [ TextView | programSettingTotalEventNumber ] widget connect
        this.programSettingTotalEventNumber = (TextView) getView().findViewById(R.id.f_maker_step8_result_program_setting_total_event_number);

        // [ TextView | programSettingTotalSetNumber ] widget connect
        this.programSettingTotalSetNumber = (TextView) getView().findViewById(R.id.f_maker_step8_result_program_setting_total_set_number);

        // [ ScrollView | resultContentWrapper ] widget connect
        this.resultContentWrapper = (ScrollView) getView().findViewById(R.id.f_maker_step8_result_content_wrapper);

        // [ LinearLayout | detailProgramSettingListWrapper ] widget connect
        this.detailProgramSettingListWrapper = (LinearLayout) getView().findViewById(R.id.f_maker_step8_result_detail_program_setting_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [ TextView | programSettingTotalEventNumber ] text
        this.programSettingTotalEventNumber.setText(DataFormatter.setEventNumberFormat(this.program.getTotalEventNumber()));

        // [ TextView | programSettingTotalSetNumber ] text
        this.programSettingTotalSetNumber.setText(DataFormatter.setSetNumberFormat(this.program.getTotalSetNumber()));

        // [method]
        initWidgetOfDetailProgramSettingListWrapper();

        ArrayList<MuscleArea> muscleAreaList = program.getMuscleAreaList();

        for (int index = 0; index < muscleAreaList.size(); index++) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< program / muscle Area list > << " + index + " >>  ===> " + muscleAreaList.get(index));
        }

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

                AlertDialog.Builder builder = new AlertDialog.Builder(getFragment().getContext());
                builder.setTitle(R.string.f_maker_step8_alert_save_title)
                        .setMessage(R.string.f_maker_step8_alert_save_message)
                        .setPositiveButton(R.string.f_maker_step8_alert_save_bt_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!nickName.getText().toString().equals("")) {

                                    saveContent();

                                } else {
                                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step8_result_snack_nick_name_input, Snackbar.LENGTH_SHORT).show();
                                }


                            }
                        })
                        .setNegativeButton(R.string.f_maker_step8_alert_save_bt_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                return builder.create();
            }
        };
    }


    private void initWidgetOfDetailProgramSettingListWrapper() {

        // [LayoutInflater] [inflater] activity 에서 fragmentManager 를 가져오기
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [cycle 1] : finalOrderList 의 각 event 로 초기 내용 설정하기
        for (int index = 0; index < program.getDetailProgramList().size(); index++) {

            addViewOfDetailProgramResultListWrapper(
                    createViewOfDetailProgramSettingItem(
                            inflater,
                            program.getDetailProgramList().get(index)
                    )
            );

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
     * @param detailProgram
     * @return
     */
    private Step8DetailProgramSettingItem createViewOfDetailProgramSettingItem(LayoutInflater inflater, DetailProgram detailProgram) {
        return new Step8DetailProgramSettingItem.Builder()
                .setInflater(inflater)
                .setDetailProgram(detailProgram)
                .newInstance()
                .createItem();
    }


    /**
     * 해당 detailProgramResultItem 의 view 를 detailProgramResultListWrapper 에 추가하여 화면에 표시한다.
     *
     * @param detailProgramSettingItem
     */
    private void addViewOfDetailProgramResultListWrapper(Step8DetailProgramSettingItem detailProgramSettingItem) {
        this.detailProgramSettingListWrapper.addView(detailProgramSettingItem.getItem());
    }


    private void saveContent() {

        // [HashMap<String, Object>] [saveData]
        HashMap<String, Object> saveData = new HashMap<>();
        saveData.put(Program.NICK_NAME, nickName.getText().toString());
        saveData.put(Program.TOTAL_EVENT_NUMBER, program.getTotalEventNumber());
        saveData.put(Program.TOTAL_SET_NUMBER, program.getTotalSetNumber());
        saveData.put(Program.COUNT, 0);

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

                    saveContentOfMuscleAreaList(ref);

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
        for (int index = 0; index < program.getDetailProgramList().size(); index++) {

            // 해당 eventKey 에 해당하는 detailProgram 을 가져온다.
            DetailProgram detailProgram = program.getDetailProgramList().get(index);

            HashMap<String, Object> saveData = new HashMap<>();
            saveData.put(DetailProgram.ORDER, (index + 1));
            saveData.put(DetailProgram.MUSCLE_AREA, detailProgram.getMuscleArea());
            saveData.put(DetailProgram.EVENT_KEY, detailProgram.getEventKey());
            saveData.put(DetailProgram.EVENT_NAME, detailProgram.getEventName());
            saveData.put(DetailProgram.SET_NUMBER, detailProgram.getSetNumber());
            saveData.put(DetailProgram.REST_TIME_MINUTE, detailProgram.getRestTimeMinute());
            saveData.put(DetailProgram.REST_TIME_SECOND, detailProgram.getRestTimeSecond());

            // program/$uid$/$programKey$/programList/$eventKey$/ 에 saveData 를 저장한다.
            dbRef.child(Program.DETAIL_PROGRAM_LIST)
                    .child((index + 1) + "")
                    .setValue(saveData);
        } // [cycle ]
    }

    private void saveContentOfMuscleAreaList(DatabaseReference dbRef) {

        // program 에서 muscle area array list 가져오기
        ArrayList<MuscleArea> muscleAreas = program.getMuscleAreaList();

        // db 에 저장하기 위한 HashMap 생성
        HashMap<String, Object> saveData = new HashMap<>();

        for (int index = 0; index < muscleAreas.size(); index++) {

            saveData.put(index + "", muscleAreas.get(index));

        }

        // program/$uid$/$programKey$/muscleAreaList/ 에 saveData 를 저장한다.
        dbRef.child(Program.MUSCLE_AREA_LIST)
                .setValue(saveData);

    }


}
