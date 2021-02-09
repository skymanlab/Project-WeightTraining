package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionInitializable;
import com.skymanlab.weighttraining.management.project.fragment.FragmentSectionManager;
import com.skymanlab.weighttraining.management.project.fragment.FragmentTopBarManager;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.MakerStep6Fragment;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.Step5EventItem;
import com.skymanlab.weighttraining.management.project.fragment.Training.program.item.Step5FinalOrderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MakerStep5SectionManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PFTPS] MakerStep5SectionManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> selectedChestEventArrayList;
    private ArrayList<Event> selectedShoulderEventArrayList;
    private ArrayList<Event> selectedLatEventArrayList;
    private ArrayList<Event> selectedUpperBodyEventArrayList;
    private ArrayList<Event> selectedArmEventArrayList;
    private ArrayList<Event> selectedEtcEventArrayList;

    // instance variable :
    private ScrollView finalOrderListScroll;                // [1] final order 의 목록(list)을 보여주기 위한 scroll view
    private LinearLayout finalOrderListWrapper;             // [1] final order 의 목록(list)을 보여주는 scroll view 에서 각 항목(item)을 담을 linear layout
    private GridLayout eventListWrapper;                    // [2] event 의 목록(list)을 보여주는 scroll view 안에 각 항목(item)을 담을 grid layout

    // instance variable : [1] final order
    private HashMap<String, Step5FinalOrderItem> finalOrderItemList;

    // instance variable : [2] event
    private HashMap<String, Step5EventItem> eventItemList;

    // instance variable : next step 으로 넘어갈 데이터
    private ArrayList<Event> finalOrderList;
    private ArrayList<MuscleArea> muscleAreaArrayList;

    // constructor
    public MakerStep5SectionManager(Fragment fragment, View view) {
        super(fragment, view);
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
    public void connectWidget() {

        // [iv/C]ScrollView : finalOrderListScroll connect
        this.finalOrderListScroll = (ScrollView) getView().findViewById(R.id.f_maker_step5_final_order_list_scroll);

        // [iv/C]LinearLayout : finalOrderListWrapper connect
        this.finalOrderListWrapper = (LinearLayout) getView().findViewById(R.id.f_maker_step5_final_order_list_wrapper);

        // [iv/C]GridLayout : eventListWrapper connect
        this.eventListWrapper = (GridLayout) getView().findViewById(R.id.f_maker_step5_event_list_wrapper);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

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

        // [iv/C]HashMap<String, Step4EventItem> :
        this.eventItemList = new HashMap<String, Step5EventItem>();

        // [iv/C]HashMap<String, Step4FinalOrderItem> :
        this.finalOrderItemList = new HashMap<String, Step5FinalOrderItem>();

        // [iv/C]HashMap<String, Event> :
        this.finalOrderList = new ArrayList<>();

        // [iv/C]ArrayList<MuscleARea>
        this.muscleAreaArrayList = new ArrayList<>();

        // [lv/C]LayoutInflater : layout inflater 가져오기
        LayoutInflater inflater = (LayoutInflater) getFragment().getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // [0] chest
        initItemByMuscleArea(inflater, this.selectedChestEventArrayList);

        // [1] shoulder
        initItemByMuscleArea(inflater, this.selectedShoulderEventArrayList);

        // [2] lat
        initItemByMuscleArea(inflater, this.selectedLatEventArrayList);

        // [3] upper body
        initItemByMuscleArea(inflater, this.selectedUpperBodyEventArrayList);

        // [4] arm
        initItemByMuscleArea(inflater, this.selectedArmEventArrayList);

        // [5] etc
        initItemByMuscleArea(inflater, this.selectedEtcEventArrayList);

    }


    /**
     * 다음 단계를 진행하기 위한 과정을 EndButtonLister 객체를 생성하여 반환한다.
     *
     * @return
     */
    public FragmentTopBarManager.EndButtonListener newEndButtonListenerInstance() {
        final String METHOD_NAME = "[newEndButtonListenerInstance] ";

        return new FragmentTopBarManager.EndButtonListener() {
            @Override
            public AlertDialog setEndButtonClickListener() {

                int allMuscleAreaEventArrayListSize = selectedChestEventArrayList.size() + selectedShoulderEventArrayList.size() + selectedLatEventArrayList.size() + selectedUpperBodyEventArrayList.size() + selectedArmEventArrayList.size() + selectedEtcEventArrayList.size();

                // [check 1] : 총 개수와 finalOrderListWrapper 에 포함되어 있는 view 의 개수와 같을 때만
                if (allMuscleAreaEventArrayListSize == finalOrderListWrapper.getChildCount()) {

                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>> 다음 단계를 진행합니다.");

                    // [cycle ] :  그냥 확인 하기 위해서
                    for (int index = 0; index < finalOrderList.size(); index++) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<< " + index + " >> event name = " + finalOrderList.get(index).getEventName());
                    } // [cycle ]

                    // [lv/C]MakerStep6Fragment : step 6 fragment
                    MakerStep6Fragment step6Fragment = MakerStep6Fragment.newInstance(finalOrderList, muscleAreaArrayList);

                    FragmentTransaction transaction = getFragment().getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_home_content_wrapper, step6Fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {
                    Snackbar.make(getFragment().getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.f_maker_step5_snack_final_order_not_complete, Snackbar.LENGTH_SHORT).show();
                } // [check 1]

                return null;
            }
        };
    }


    /**
     * 표시해야하는 muscleArea 의 Step5EventItem 객체를 생성하고 eventItemList 와 eventListWrapper 에 추가하여 화면에 표시한다.
     * 그리고 Step5FinalOrderItem 객체를 생성하여 finalOrderItemList 에 추가하여 해당 view 를 관리한다.
     *
     * @param inflater
     * @param eventArrayList
     */
    private void initItemByMuscleArea(LayoutInflater inflater, ArrayList<Event> eventArrayList) {
        final String METHOD_NAME = "[initItemByMuscleArea]";

        // [check 1] : eventArrayList 의 데이터가 있을 때만 item 을 생성하는 초기 설정을 진행한다.
        if (!eventArrayList.isEmpty()) {
            
            // muscle area 추가
            muscleAreaArrayList.add(eventArrayList.get(0).getMuscleArea());
            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "< Muscle Area > " + eventArrayList.get(0).getMuscleArea());

            // [cycle 1] : eventArrayList 의 각 항목들을 이용하여 Step4EventItem 과 Step4FinalOrderItem 의 객체를 생성하는 초기 설정을 한다.
            for (int index = 0; index < eventArrayList.size(); index++) {

                // [lv/C]Step4EventItem : event 의 항목(item) 생성
                Step5EventItem eventItem = createEventItem(inflater, eventArrayList.get(index), (index + 1));

                // event 의 item 을 관리하기 위한 리스트에 추가한다.
                addItemToEventItemList(eventArrayList.get(index).getKey(), eventItem);

                // eventListWrapper(LinearLayout) 에 eventItem 의 item view 를 추가하여 화면에 표시하는 과정 진행
                addViewToEventListWrapper(eventItem);

                // [lv/C]Step4FinalOrderItem : final order 의 항목(item) 을 생성
                Step5FinalOrderItem finalOrderItem = createFinalOrderItem(inflater, eventArrayList.get(index), (index + 1));

                // final order 의 item 을 관리하기 위한 리스트에 추가한다.
                addItemToFinalOrderItemList(eventArrayList.get(index).getKey(), finalOrderItem);

            } // [cycle 1]

        } // [check 1]

    } // End of method [initItemByMuscleArea]


    /**
     * eventItemList 에 Step4EventItem 객체를 key 를 eventKey 로 하여 추가한다.
     *
     * @param eventKey  HashMap 의 key
     * @param eventItem HashMap 에 추가할 Step4EventItem 객체
     */
    private void addItemToEventItemList(String eventKey, Step5EventItem eventItem) {

        // [iv/C]HashMap<String, Step4EventItem> : event 의 key 값으로 eventItem 을 추가하기
        this.eventItemList.put(eventKey, eventItem);

    } // End of method [addItemToEventItemList]


    /**
     * finalOrderItemList 에 Step4FinalOrderItem 객체릴 key 를 eventKey 로 하여 추가한다.
     *
     * @param eventKey       HashMap 의 key
     * @param finalOrderItem HashMap 에 추가할 Step4EventItem 객체
     */
    private void addItemToFinalOrderItemList(String eventKey, Step5FinalOrderItem finalOrderItem) {

        // [iv/C]HashMap<String, Step4FinalOrderItem> : eventKey 을 key 로 하여 finalOrderItem 을 추가한다.
        this.finalOrderItemList.put(eventKey, finalOrderItem);

    } // End of method [addItemToFinalOrderItemList]


    /**
     * Step4EventItem 을 이용하여 event 의 각 항목을 나타내는 객체를 생성하여 반환한다.
     *
     * @param inflater    layout 을 inflate 하기 위한 LayoutInflater
     * @param event       각 항목에 나타낼 데이터가 담긴 Event 객체
     * @param eventNumber MuscleArea 별로 event 를 구분하기 위한 수
     * @return event 의 각 항목을 나타내는 객체
     */
    private Step5EventItem createEventItem(LayoutInflater inflater, Event event, int eventNumber) {
        final String METHOD_NAME = "[createEventItem] ";

        // [lv/C]Step4EventItem : event item 의 초기 내용을 설정한다.
        Step5EventItem eventItem = new Step5EventItem.Builder()
                .setInflater(inflater)
                .setContentWrapperColor(ContextCompat.getColor(getFragment().getActivity(), DataManager.convertColorIntOfMuscleArea(event.getMuscleArea())))
                .setEvent(event)
                .setEventNumber(eventNumber)
                .setItemClickListener(new Step5EventItem.OnItemClickListener() {
                    @Override
                    public void includeFinalOrderItem(View item, MaterialCardView contentWrapper, Event event) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "-->> 클릭한 event item 의 key 는? = " + event.getKey());

                        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= final order =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                        // [lv/C]LinearLayout : finalOrderItemList 에서 eventKey 로 finalOrderItem 을 찾는다. 그리고 item view 를 가져와서 finalOrderListWrapper 에 추가한다. // view 가 동익함
                        finalOrderListWrapper.addView(finalOrderItemList.get(event.getKey()).getItem());

                        // finalOrderListWrapper 의 모든 항목(finalOrderItem)들의 number 를 재설정한다.
                        resetFinalOrderItemNumber(event.getKey());

                        // 최종 리스트에 추가
                        finalOrderList.add(event);

                        // [iv/C]HorizontalScrollView : Ui thread 를 이용하여 scrollView 를 마지막으로 이동한다.(horizontal 이므로 right 이다.)
                        finalOrderListScroll.post(new Runnable() {
                            @Override
                            public void run() {

                                // [iv/C]HorizontalScrollView : 마지막으로 이동
                                finalOrderListScroll.fullScroll(ScrollView.FOCUS_DOWN);

                            }
                        });

                        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= event =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                        // [lv/C]MaterialCardView : contentWrapper 의 색을 변경
                        contentWrapper.setCardBackgroundColor(getFragment().getActivity().getColor(R.color.colorBackgroundGray));

                        // [lv/C]View : view 의 click 금지
                        item.setEnabled(false);
                    }


                })
                .create();

        // [lv/C]Step4EventItem : event 의 item 을 생성한다.
        eventItem.createItem();

        return eventItem;
    } // End of method [createEventItem]


    /**
     * 해당 event 에 해당하는 Step5FinalOrderItem 객체를 생성하여 반환한다.
     *
     * @param inflater
     * @param event
     * @param eventNumber
     * @return
     */
    private Step5FinalOrderItem createFinalOrderItem(LayoutInflater inflater, Event event, int eventNumber) {
        final String METHOD_NAME = "[Step4FinalOrderItem] ";

        // [lv/C]Step4FinalOrderItem : final order item 의 초기 내용을 설정한다.
        Step5FinalOrderItem finalOrderItem = new Step5FinalOrderItem.Builder()
                .setInflater(inflater)
                .setContentWrapperColor(ContextCompat.getColor(getFragment().getActivity(), DataManager.convertColorIntOfMuscleArea(event.getMuscleArea())))
                .setEvent(event)
                .setEventNumber(eventNumber)
                .setItemClickListener(new Step5FinalOrderItem.OnItemClickListener() {
                    @Override
                    public void excludeFinalOrderItem(View item, Event event, int contentWrapperColor) {
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "--->> fianl order item 을 클릭 하였습니다. key 는 ? " + event.getKey());

                        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= final order =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                        // [lv/C]LinearLayout : finalOrderListWrapper 에 내 자신의 view 를 삭제한다.
                        finalOrderListWrapper.removeView(item);

                        // finalOrderListWrapper 의 모든 항목(finalOrderItem)들의 number 를 재설정한다.
                        resetFinalOrderItemNumber(event.getKey());

                        // 최종 리스트에서 삭제
                        finalOrderList.remove(event);

                        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= selected item =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                        // [iv/C]HashMap<String, Step4EventItem> : eventItemList 에서 key 가 eventKey 인 객체에서 contentWrapper 를 가져온다. 그리고 색을 다시 기존의 색으로 변경한다.
                        eventItemList.get(event.getKey()).getContentWrapper().setCardBackgroundColor(contentWrapperColor);

                        // [iv/C]HashMap<String, Step4EventItem>  : eventItemList 에서 key 가 eventKey 인 객체에서 item 을 가져온다. 그리고 다시 사용할 수 있도록 변경한다.
                        eventItemList.get(event.getKey()).getItem().setEnabled(true);

                    }

                })
                .create();

        // [lv/C]Step4FinalOrderItem : item 생성
        finalOrderItem.createItem();

        return finalOrderItem;

    }


    /**
     * finalOrderListWrapper 에 추가된 view 들을 순서대로 각 item 의 number 를 재설정한다.
     *
     * @param eventKey
     */
    private void resetFinalOrderItemNumber(String eventKey) {

        // [cycle 1] : finalOrderListWrapper 에 순서대로 나열되어 있는 finalOrderItem 의 number 를 순서대로 번호를 다시 매긴다.
        for (int index = 0; index < this.finalOrderListWrapper.getChildCount(); index++) {

            // [iv/C]HashMap<String, Step4FinalOrderList> : eventKey 로 finalOrderItem 을 찾은 뒤 number 의 text 를 재설정한다.
            finalOrderItemList.get(eventKey).getNumber().setText((index + 1) + "");

        } // [cycle 1]

    } // End of method [resetFinalOrderItemNumber]


    /**
     * Step5EventItem 객체를 eventListWrapper 에 해당 객체의 view 를 추가하여 화면에 표시한다.
     *
     * @param eventItem
     */
    private void addViewToEventListWrapper(Step5EventItem eventItem) {
        final String METHOD_NAME = "[addViewToEventListWrapper] ";

        // [iv/C]GridLayout : ViewTreeObserver 를 이용하여 eventListWrapper 의 width 를 구하고 eventItem 의 item view 의 width 를 설정한다.
        this.eventListWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 width = " + eventListWrapper.getWidth());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 padding left = " + eventListWrapper.getPaddingLeft());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 padding right = " + eventListWrapper.getPaddingRight());

                // [lv/C]GridLayout.LayoutParams : view 의 width 의 초기 내용을 설정한다.
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (eventListWrapper.getWidth() - (eventListWrapper.getPaddingLeft() + eventListWrapper.getPaddingRight())) / eventListWrapper.getColumnCount();

                // Ui Thread 를 이용하여 params 를 item 에 적용한다.
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // [lv/C]Step4EventItem : item view 에 prams 적용하기
                        eventItem.applyLayoutParams(params);

                        // [iv/C]GridLayout : eventListWrapper 에 eventItem 의 item view 를 추가하기
                        eventListWrapper.addView(eventItem.getItem());

                    }
                });

                // [iv/C]GridLayout : ViewThreeObserver 의 onGlobalLayoutListener(this) 를 삭제하여, 무한 반복을 하지 않도록 한다.
                eventListWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

    } // End of method [addViewToEventListWrapper]


    /**
     * eventItemList 의 각 item 을 eventListWrapper 에 모두 추가한다.
     */
    private void addAllViewToEventListWrapper() {
        final String METHOD_NAME = "[addAllViewToEventListWrapper] ";

        // [iv/C]GridLayout : ViewTreeObserver 를 이용하여 eventListWrapper 의 width 를 구하고 eventItem 의 item view 의 width 를 설정한다.
        this.eventListWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 width = " + eventListWrapper.getWidth());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 padding left = " + eventListWrapper.getPaddingLeft());
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>> eventListWrapper 의 padding right = " + eventListWrapper.getPaddingRight());

                // [lv/C]GridLayout.LayoutParams : view 의 width 의 초기 내용을 설정한다.
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = (eventListWrapper.getWidth() - (eventListWrapper.getPaddingLeft() + eventListWrapper.getPaddingRight())) / eventListWrapper.getColumnCount();

                // Ui Thread 를 이용하여 params 를 item 에 적용한다.
                getFragment().getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // [lv/C]Iterator : eventItemList 에서 key set 을 가져온다. 이 key set 의 모든 항목을 읽어올 준비를 한다.
                        Iterator iterator = eventItemList.keySet().iterator();

                        // [cycle 1] : iterator 를 통해 다음 항목이 있을 때만 수행한다.
                        while (iterator.hasNext()) {

                            // [lv/C]String : iterator 을 통해 key 를 가져온다.
                            String key = (String) iterator.next();

                            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "----------- key = " + key);
                            // [lv/C]HashMap<String, Step4EventItem> : eventItemList 에서 해당 key 의 객체를 가져온다. 그리고 이 객체의 item(view) 에 params 를 적용시킨다.
                            eventItemList.get(key).getItem().setLayoutParams(params);

                            // [iv/C]GridLayout : eventItemList 에서 해당 key 의 객체를 가져온다. 그리고 이 객체의 item(view) 을 eventListWrapper 에 추가하여 화면에 표시한다.
                            eventListWrapper.addView(eventItemList.get(key).getItem());

                        } // [cycle 1]

                    }
                });

                // [iv/C]GridLayout : ViewThreeObserver 의 onGlobalLayoutListener(this) 를 삭제하여, 무한 반복을 하지 않도록 한다.
                eventListWrapper.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });

    } // End of method [addAllViewToEventListWrapper]

}
