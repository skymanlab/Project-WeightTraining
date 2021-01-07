package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Step4D1SectionManager extends FragmentSectionManager implements FragmentSectionInitializable, StepProcessManager.OnNextClickListener {

    // constant
    private static final String CLASS_NAME = "[PFTPS] Step4D1SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private StepProcessManager stepProcessManager;

    // instance variable
    private ArrayList<Event> selectedChestEventArrayList;
    private ArrayList<Event> selectedShoulderEventArrayList;
    private ArrayList<Event> selectedLatEventArrayList;
    private ArrayList<Event> selectedUpperBodyEventArrayList;
    private ArrayList<Event> selectedArmEventArrayList;
    private ArrayList<Event> selectedEtcEventArrayList;

    // instance variable
    private HorizontalScrollView finalOrderListScroll;
    private LinearLayout finalOrderListWrapper;
    private GridLayout selectedItemListWrapper;

    // instance variable
    private ArrayList<String> finalOrderList;

    // instance variable
    private HashMap<String, View> finalOrderViewList;
    private HashMap<String, TextView> finalOrderNumberList;
    private HashMap<String, View> selectedItemViewList;
    private HashMap<String, MaterialCardView> selectedItemContentWrapperList;
    private int finalOrderNumber;

    // constructor
    public Step4D1SectionManager(Activity activity, View view, FragmentManager fragmentManager) {
        super(activity, view, fragmentManager);
    }

    // setter
    public void setSelectedChestEventArrayList(ArrayList<Event> selectedChestEventArrayList) {
        this.selectedChestEventArrayList = selectedChestEventArrayList;
    }

    public void setSelectedShoulderEventArrayList(ArrayList<Event> selectedShoulderEventArrayList) {
        this.selectedShoulderEventArrayList = selectedShoulderEventArrayList;
    }

    public void setSelectedLatEventArrayList(ArrayList<Event> selectedLatEventArrayList) {
        this.selectedLatEventArrayList = selectedLatEventArrayList;
    }

    public void setSelectedUpperBodyEventArrayList(ArrayList<Event> selectedUpperBodyEventArrayList) {
        this.selectedUpperBodyEventArrayList = selectedUpperBodyEventArrayList;
    }

    public void setSelectedArmEventArrayList(ArrayList<Event> selectedArmEventArrayList) {
        this.selectedArmEventArrayList = selectedArmEventArrayList;
    }

    public void setSelectedEtcEventArrayList(ArrayList<Event> selectedEtcEventArrayList) {
        this.selectedEtcEventArrayList = selectedEtcEventArrayList;
    }

    @Override
    public void mappingWidget() {

        // [iv/C]HorizontalScrollView :
        this.finalOrderListScroll = (HorizontalScrollView) getView().findViewById(R.id.f_program_step4_1_final_order_list_scroll);

        // [iv/C]LinearLayout : finalOrderListWrapper mapping
        this.finalOrderListWrapper = (LinearLayout) getView().findViewById(R.id.f_program_step4_1_final_order_list_wrapper);

        // [iv/C]GridLayout : selectedItemListWrapper mapping
        this.selectedItemListWrapper = (GridLayout) getView().findViewById(R.id.f_program_step4_1_selected_item_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [iv/C]StepProcessManager : step 4-1
        this.stepProcessManager = new StepProcessManager(getView(), getFragmentManager(), StepProcessManager.STEP_FOUR);
        this.stepProcessManager.mappingWidget();
        this.stepProcessManager.setNextClickListener(this);
        this.stepProcessManager.initWidget();

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- chest -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedChestEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- shoulder -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedShoulderEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- lat -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedLatEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- upper body -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedUpperBodyEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- arm -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedArmEventArrayList);
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--------------- etc -------------------------------------------------");
        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, selectedEtcEventArrayList);

        // [iv/C]ArrayList<String> :
        this.finalOrderList = new ArrayList<>();

        // [iv/C]HashMap<String, View> :
        this.finalOrderViewList = new HashMap<>();

        // [lv/C]HashMap<String, TextView> :
        this.finalOrderNumberList = new HashMap<>();

        // [iv/C]HashMap<String, View> :
        this.selectedItemViewList = new HashMap<>();

        // [iv/C]HashMap<String, MaterialCardView> :
        this.selectedItemContentWrapperList = new HashMap<>();

        // [iv/i]finalOrderNumber :
        this.finalOrderNumber = 1;

        // [lv/C]LayoutInflater : layout inflater 가져오기
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [check 1] : chest
        if (!this.selectedChestEventArrayList.isEmpty()) {

            // [cycle 1] :
            for (int index = 0; index < this.selectedChestEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedChestEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedChestEventArrayList.get(index)));

            } // [cycle 1]

        } // [check 1]

        // [check 2] : shoulder
        if (!this.selectedShoulderEventArrayList.isEmpty()) {

            // [cycle 2] :
            for (int index = 0; index < this.selectedShoulderEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedShoulderEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedShoulderEventArrayList.get(index)));

            } // [cycle 2]

        } // [check 2]

        // [check 3] : lat
        if (!this.selectedLatEventArrayList.isEmpty()) {

            // [cycle 3] :
            for (int index = 0; index < this.selectedLatEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedLatEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedLatEventArrayList.get(index)));

            } // [cycle 3]

        } // [check 3]

        // [check 4] : upper body
        if (!this.selectedUpperBodyEventArrayList.isEmpty()) {

            // [cycle 4] :
            for (int index = 0; index < this.selectedUpperBodyEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedUpperBodyEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedUpperBodyEventArrayList.get(index)));

            } // [cycle 4]

        } // [check 4]

        // [check 5] : arm
        if (!this.selectedArmEventArrayList.isEmpty()) {

            // [cycle 5] :
            for (int index = 0; index < this.selectedArmEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedArmEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedArmEventArrayList.get(index)));

            } // [cycle 5]

        } // [check 5]

        // [check 6] : etc
        if (!this.selectedEtcEventArrayList.isEmpty()) {

            // [cycle 6] :
            for (int index = 0; index < this.selectedEtcEventArrayList.size(); index++) {

                // [method] : view 생성 후 finalOrderList 에 추가
                createFinalOrderItemView(inflater, index, this.selectedEtcEventArrayList.get(index));

                // [iv/C]GridLayout : selectedItemList 의 view 를 wrapper 에 추가한다.
                this.selectedItemListWrapper.addView(createSelectedItemView(inflater, index, this.selectedEtcEventArrayList.get(index)));

            } // [cycle 6]

        } // [check 6]

    }

    @Override
    public void setClickListenerOfNext() {
        final String METHOD_NAME = "[setClickListenerOfNext] ";

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> >>>>> >>>> finalOrderListWrapper 의 view 개수 = " + finalOrderListWrapper.getChildCount() );
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> >>>>> >>>> selectedItemListWrapper 의 view 개수 = " + selectedItemListWrapper.getChildCount() );


        // [check 1] : finalOrderListWrapper 의 view 수와 selectedItemListWrapper 의 수가 같을때만 다음 단계 수행
        if (selectedItemListWrapper.getChildCount() == finalOrderListWrapper.getChildCount()) {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 같음");
        } else {
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 다름");

        } // [check 1]
    }


    private View createFinalOrderItemView(LayoutInflater inflater, int index, Event event) {
        final String METHOD_NAME = "[createFinalOrderItemView] ";

        // [lv/C]View : R.layout.include_final_order_item inflate
        View view = inflater.inflate(R.layout.include_final_order_item, null);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= mapping =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]MaterialCardView : wrapper mapping
        MaterialCardView content = (MaterialCardView) view.findViewById(R.id.include_final_order_item_wrapper);

        // [lv/C]TextView : number mapping
        TextView number = (TextView) view.findViewById(R.id.include_final_order_item_number);

        // [lv/C]TextView : muscleAreaNumber mapping
        TextView muscleAreaNumber = (TextView) view.findViewById(R.id.include_final_order_item_muscle_area_number);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= list 에 추가하기 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [iv/C]HashMap<String, View> :  view 를 list 에 추가하기
        this.finalOrderViewList.put(event.getKey(), view);

        // [iv/C]HashMap<String, TextView> : number 를 list 에 추가하기
        this.finalOrderNumberList.put(event.getKey(), number);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // [lv/C]MaterialCardView : content 색 변경
        content.setCardBackgroundColor(getActivity().getColor(DataManager.convertColorIntOfMuscleArea(event.getMuscleArea())));

        // [lv/C]TextView : muscleAreaNumber text setting
        muscleAreaNumber.setText(DataManager.convertHanguleOfMuscleArea(event.getMuscleArea()) + " " + (index + 1));

        // [lv/C]View :
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> event name = " + event.getEventName());

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= final order =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]ArrayList<String> : finalOrderListWrapper 에서 삭제된 view 의 key 를 순서대로 저장
                finalOrderList.remove(event.getKey());

                // [iv/C]finalOrderListWrapper : 여기서 해당 view 삭제하기
                finalOrderListWrapper.removeView(view);

                // [cycle 1] : finalOrderListWrapper 에 추가된 view 들의 순서를 다시 정한다.
                for (int index = 0; index < finalOrderListWrapper.getChildCount(); index++) {

                    // [iv/C]HashMap<String, TextView> : finalOrderNumberList 의 내용을 순서대로 정렬한 번호로 만든다.
                    finalOrderNumberList.get(finalOrderList.get(index)).setText((index + 1) + "");

                } // [cycle 1]


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= selected item =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [lv/C]View : selectedItemViewList 에서 event 의 key 로 view 가져오기
                View selectedItemView = selectedItemViewList.get(event.getKey());
                selectedItemView.setEnabled(true);

                // [lv/C]MaterialCardView : selectedItemContentWrapperList 에서 event 의 key 으로 contentWrapper 가져오기
                MaterialCardView selectedItemContent = selectedItemContentWrapperList.get(event.getKey());
                selectedItemContent.setCardBackgroundColor(getActivity().getColor(DataManager.convertColorIntOfMuscleArea(event.getMuscleArea())));


            }
        });
        return view;
    }

    private View createSelectedItemView(LayoutInflater inflater, int index, Event event) {
        final String METHOD_NAME = "[createSelectedItemView] ";

        // [lv/C]View : R.layout.include_selected_item inflate
        View view = inflater.inflate(R.layout.include_selected_item, null);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= mapping =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]MaterialCardView : contentWrapper mapping
        MaterialCardView contentWrapper = (MaterialCardView) view.findViewById(R.id.include_selected_item_wrapper);

        // [lv/C]ImageView : info mapping
        ImageView info = (ImageView) view.findViewById(R.id.include_selected_item_info);

        // [lv/C]TextView : muscleAreaNumber mapping
        TextView muscleAreaNumber = (TextView) view.findViewById(R.id.include_selected_item_muscle_area_number);

        // [lv/C]TextView : eventName mapping
        TextView eventName = (TextView) view.findViewById(R.id.include_selected_item_event_name);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= list 에 추가하기 =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // [iv/C]HashMap<String, View> : view 를 list 에 추가하기
        this.selectedItemViewList.put(event.getKey(), view);

        // [iv/C]HashMap<String, MaterialCardView> : contentWrapper 를 list 에 추가하기
        this.selectedItemContentWrapperList.put(event.getKey(), contentWrapper);


        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]MaterialCardView : contentWrapper 색 변경
        contentWrapper.setCardBackgroundColor(getActivity().getColor(DataManager.convertColorIntOfMuscleArea(event.getMuscleArea())));

        // [lv/C]TextView : muscleAreaNumber text setting
        muscleAreaNumber.setText(DataManager.convertHanguleOfMuscleArea(event.getMuscleArea()) + (index + 1));

        // [lv/C]TextView : eventName text setting
        eventName.setText(event.getEventName());

        // [lv/C]TextView : info touch listener
        info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> >>> >>> touch down event");
                        break;
                }
                return true;
            }
        });

        // [lv/C]View : itemView click listener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= final order =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [iv/C]ArrayList<String> : finalOrderListWrapper 에 추가된 view 의 key 를 순서대로 저장
                finalOrderList.add(event.getKey());

                // [iv/C]LinearLayout : event 의 key 값으로 finalOrderList 에서 view 를 찾아서 finalOrderListWrapper 에 추가하기
                finalOrderListWrapper.addView(finalOrderViewList.get(event.getKey()));

                // [cycle 1] : finalOrderListWrapper 에 추가된 view 들의 순서를 다시 정한다.
                for (int index = 0; index < finalOrderListWrapper.getChildCount(); index++) {

                    // [iv/C]HashMap<String, TextView> : finalOrderNumberList 의 내용을 순서대로 정렬한 번호로 만든다.
                    finalOrderNumberList.get(finalOrderList.get(index)).setText((index + 1) + "");

                } // [cycle 1]

                // [iv/C]HorizontalScrollView : Ui thread 를 이용하여 scrollView 를 마지막으로 이동한다.(horizontal 이므로 right 이다.)
                finalOrderListScroll.post(new Runnable() {
                    @Override
                    public void run() {

                        // [iv/C]HorizontalScrollView : 마지막으로 이동
                        finalOrderListScroll.fullScroll(ScrollView.FOCUS_RIGHT);

                    }
                });


                // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= selected item =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                // [lv/C]View : contentWrapper 의 색 변경 Gray
                contentWrapper.setCardBackgroundColor(getActivity().getColor(R.color.colorBackgroundGray));

                // [lv/C]View : view 의 click 금지
                view.setEnabled(false);

            }
        });

        // [iv/C]GridLayout : ViewThreeObserver 의 onGlobalLayoutListener(전체 뷰가 그려질때) 통해 selectedItemListWrapper 의 width 를 구하고, params 를 view 에 적용하는 과정을 진행
        selectedItemListWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> 번째에서 확인한 width = " + selectedItemListWrapper.getWidth());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> 번째에서 확인한 column count = " + selectedItemListWrapper.getColumnCount());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> 번째에서 확인한 padding left = " + selectedItemListWrapper.getPaddingLeft());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<<< " + index + ">>> 번째에서 확인한 padding right = " + selectedItemListWrapper.getPaddingRight());

                // [lv/C]GridLayout.LayoutParams : view 의 설정을 하는 객체생성 / parent : selectedItemListWrapper 의 width 를 가지고 각 view 의 width 를 정한다.
                GridLayout.LayoutParams params = (GridLayout.LayoutParams) view.getLayoutParams();
                params.width = ((selectedItemListWrapper.getWidth() - (selectedItemListWrapper.getPaddingLeft() + selectedItemListWrapper.getPaddingRight()) ) / selectedItemListWrapper.getColumnCount());

                // Ui Thread 를 이용하여 params 를 view 에 적용한다.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.setLayoutParams(params);
                    }
                });

                // [iv/C]GridLayout : ViewThreeObserver 의 onGlobalLayoutListener(this) 를 삭제하여, 무한 반복을 하지 않도록 한다.
                selectedItemListWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return view;
    }
}
