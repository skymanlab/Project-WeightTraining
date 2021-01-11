package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.program.data.EventResultSet;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.GroupType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DirectSelectionGroupItem {

    // constant
    private static final int STANDARD_ID_A_GROUP = 10;
    private static final int STANDARD_ID_B_GROUP = 20;
    private static final int STANDARD_ID_C_GROUP = 30;
    private static final int STANDARD_ID_D_GROUP = 40;
    private static final int STANDARD_ID_E_GROUP = 50;

    // instance variable
    private Activity activity;
    private LayoutInflater inflater;
    private GroupType groupType;
    private ArrayList<Event> groupEventArrayList;
    private int standardId;

    // instance variable
    private View item;

    // instance variable
    private MaterialCardView contentWrapper;
    private MaterialTextView title;
    private LinearLayout eventListWrapper;

    // instance variable
    private ArrayList<CheckBox> eventItemList2;
    private HashMap<String, CheckBox> eventItemList;
    private HashMap<String, Integer> eventItemIdList;
    private HashMap<String, Event> checkedEventList;
    private HashMap<String, Event> noCheckedEventList;

    // constructor
    private DirectSelectionGroupItem(Builder builder) {
        this.activity = builder.activity;
        this.inflater = builder.inflater;
        this.groupType = builder.groupType;
        this.groupEventArrayList = builder.groupEventArrayList;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public View getItem() {
        return item;
    }

    public MaterialCardView getContentWrapper() {
        return contentWrapper;
    }

    public MaterialTextView getTitle() {
        return title;
    }

    public LinearLayout getEventListWrapper() {
        return eventListWrapper;
    }

    public HashMap<String, CheckBox> getEventItemList() {
        return eventItemList;
    }

    public HashMap<String, Integer> getEventItemIdList() {
        return eventItemIdList;
    }

    public HashMap<String, Event> getCheckedEventList() {
        return checkedEventList;
    }

    public HashMap<String, Event> getNoCheckedEventList() {
        return noCheckedEventList;
    }

    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= create item =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void createItem() {

        // [iv/C]View : item view 를 inflater 를 이용하여 생성하기
        this.item = inflateItem();

        // item 의 widget 들을 연결(연관)한다.
        connectWidget(this.item);

        // item 의 초기 내용을 설정한다.
        initItem();

        // item 의 widget 들의 초기 내용을 설정한다.
        initWidget();

    } // End of method [createItem]


    private View inflateItem() {
        return inflater.inflate(R.layout.include_maker_type_direct_selection_group_item, null);
    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void connectWidget(View view) {

        // [1] contentWrapper
        connectWidgetOfContentWrapper(view);

        // [2] title
        connectWidgetOfTitle(view);

        // [3] eventListWrapper
        connectWidgetOfEventListWrapper(view);
    }


    private void connectWidgetOfContentWrapper(View view) {
        this.contentWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_direct_selection_group_item_content_wrapper);
    }


    private void connectWidgetOfTitle(View view) {
        this.title = (MaterialTextView) view.findViewById(R.id.include_maker_type_direct_selection_group_item_title);
    }


    private void connectWidgetOfEventListWrapper(View view) {
        this.eventListWrapper = (LinearLayout) view.findViewById(R.id.include_maker_type_direct_selection_group_item_event_list_wrapper);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void initItem() {

    }


    private void initWidget() {

        // [1] contentWrapper
        initWidgetOfContentWrapper();

        // [2] title
        initWidgetOfTitle();

        // [3] eventListWrapper
        initWidgetOfEventListWrapper();

    }


    private void initWidgetOfContentWrapper() {

    }


    private void initWidgetOfTitle() {

        // text
        this.title.setText(DataManager.convertHanguleOfGroupType(groupType) + "111");
    }


    private void initWidgetOfEventListWrapper() {

        // [iv/C]ArrayList<CheckBox> :
        this.eventItemList2 = new ArrayList<CheckBox>();

        // [iv/C]HashMap<String, CheckBox> :
        this.eventItemList = new HashMap<String, CheckBox>();

        // [iv/C]HashMap<String, Integer> :
        this.eventItemIdList = new HashMap<String, Integer>();

        // [iv/C]HashMap<String, Event> : 체크된 항목을 관리하는 리스트
        this.checkedEventList = new HashMap<String, Event>();

        // [iv/C]HashMap<String, Event> : 체크되지 않은 항목을 관리하는 리스트
        this.noCheckedEventList = new HashMap<String, Event>();

        // [cycle 1] : groupEventArrayList 의 모든 항목(Event 객체)을 checkBox 로 만들기
        for (int index=0; index < groupEventArrayList.size() ; index++ ) {

            // check box 생성
            CheckBox eventItem = createEventItem(groupEventArrayList.get(index), (this.standardId + index)) ;

            // check box 를 관리 리스트에 추가
            addItemToEventItemList(groupEventArrayList.get(index).getKey(), eventItem);

            addItemToEventItemList2(eventItem);

            // check box 를 eventListWrapper 에 추가하여 화면에 표시하기
            addViewToEventListWrapper(eventItem);

        } // [cycle 1]
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private CheckBox createEventItem(Event event, int id) {

        // [iv/C]HashMap<String, Integer> : id 를 eventItemIdList 에 추가하기
        addItemIdToEventItemIdList(event.getKey(), id);

        // [lv/C]CheckBox : id 를 부여받은 CheckBox 생성하기
        MaterialCheckBox eventItem = new MaterialCheckBox(activity);
        eventItem.setId(id);
        eventItem.setText(event.getEventName());
        eventItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                // [check 1] : true -> checkedEventList 에 event 키 값으로 추가 / false -> checkedEventList 에 추가 되었는지 확인
                if (b) {

                    eventItem.setBackgroundResource(R.color.colorBackgroundGray);

//                    // checkedEventList 에 event 를 추가한다.
//                    includeItemToCheckedEventList(event.getKey(), event);
//
//                    // background : colorBackgroundGray 로 변경
//                    compoundButton.setBackgroundResource(R.color.colorBackgroundGray);

                } else {

                    eventItem.setBackgroundResource(R.color.colorBackgroundWhite);

//                    // [check 2] : id 를 key 로 하여 추가된 데이터가 있는지 검사한다.
//                    if (getItemToCheckedEventList(event.getKey()) != null) {
//
//                        // checkedEventList 에서 해당 event 를 삭제한다.
//                        excludeItemToCheckedEventList(event.getKey());
//
//                        // background : colorBackgroundWhite 로 변경
//                        compoundButton.setBackgroundResource(R.color.colorBackgroundWhite);
//
//                    } // [check 2]

                } // [check 1]
            }
        });

        return eventItem;
    }

    private void addItemIdToEventItemIdList(String key, int id) {
        this.eventItemIdList.put(key, id);
    }

    private void addItemToEventItemList(String key, CheckBox eventItem) {

        this.eventItemList.put(key, eventItem);
    }

    private void addItemToEventItemList2( CheckBox eventItem) {
        this.eventItemList2.add(eventItem);
    }


    private void addViewToEventListWrapper(CheckBox eventItem) {
        this.eventListWrapper.addView(eventItem);
    }

    private void includeItemToCheckedEventList(String key, Event event) {
        this.checkedEventList.put(key, event);
    }

    private void excludeItemToCheckedEventList(String key) {
        this.checkedEventList.remove(key);
    }

    private Event getItemToCheckedEventList(String key) {
        return this.checkedEventList.get(key);
    }

    public EventResultSet getEventResultSet() {
        EventResultSet eventResultSet = new EventResultSet();

        // [cycle 1] : eventItemList 의 각 항목(CheckBox)을 검사한다.
        for (int index=0; index < eventItemList2.size() ; index++ ) {

            // [check 1] : 각 항목의 체크여부를 검사하여 eventResultSet 을 설정한다. /  groupEventArrayList 와 1:1 대응된다. 그러므로 해당 index 의 Event 객체를 가져와서 추가한다.
            if (eventItemList2.get(index).isChecked()) {
                
                // [lv/C]EventResultSet : 선택된 항목으로 추가
                eventResultSet.addSelectedEventArrayList(this.groupEventArrayList.get(index));
            } else {
                // [lv/C]EventResultSet : 선택되지 않은 항목으로 추가
                eventResultSet.addNoSelectedEventArrayList(this.groupEventArrayList.get(index));
            } // [check 1]

        } // [cycle 1]

        return eventResultSet;
    } // End of method [getEventResultSet]


    public ArrayList<Event> getCheckEventList() {

        ArrayList<Event> checkedEventList = new ArrayList<Event>();

        return checkedEventList;
    } // End of method [getCheckEventList]

    public ArrayList<Event> getNoCheckEventList() {

        ArrayList<Event> noCheckedEventList = new ArrayList<Event>();

        return noCheckedEventList;
    } // End of method [getNoCheckEventList]


//    /**
//     * [method] 하나의 그룹의 eventDataArrayList 로 checkBox 를 만들고 관리하기 위한 id 를 따로 보관한다.
//     */
//    private CheckBox[] makeGroupCheckBox(MaterialCardView groupWrapper, LinearLayout groupItemWrapper, ArrayList<Event> groupEventArrayList, int groupStandardId) {
//        final String METHOD_NAME = "[makeGroupCheckBox] ";
//
//        // [lv/C]CheckBox : CheckBox 객체를 저장할 변수
//        CheckBox[] groupCheckBoxList = new CheckBox[groupEventArrayList.size()];
//
//        // [check 1] : true(groupEventArrayList 의 size 가 0) -> groupWrapper 를 GONE / false -> groupEventArrayList 의 내용을 groupItemWrapper 에 CheckBox 를 만들어 추가한다.
//        if (groupEventArrayList.isEmpty()) {
//
//            // [lv/C]MaterialCardView : 이 그룹의 wrapper 를 GONE
//            groupWrapper.setVisibility(View.GONE);
//
//        } else {
//
//            // [cycle 1] : groupEventArrayList 의 모든 데이터를 CheckBox 로 표시하기
//            for (int index = 0; index < groupEventArrayList.size(); index++) {
//
//                int finalIndex = index;
//
//                // [lv/i]id : id 만들기
//                int id = muscleAreaStandardId + groupStandardId + index;
//
//                // [lv/C]CheckBox : id 를 부여받은 CheckBox 생성하기
//                MaterialCheckBox eventCheckBox = new MaterialCheckBox(getActivity());
//                eventCheckBox.setId(id);
//                eventCheckBox.setText(groupEventArrayList.get(index).getEventName());
//                eventCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                        // [check 1] : true -> checkedEventList 에 event 키 값으로 추가 / false -> checkedEventList 에 추가 되었는지 확인
//                        if (b) {
//
//                            // [iv/C]HashMap<Integer, Event> : id 를 key 로 하여 추가한다.
//                            checkedEventList.put(id, groupEventArrayList.get(finalIndex));
//
//                            // [lv/C]CheckBox : 색 변경
//                            eventCheckBox.setBackgroundResource(R.color.colorBackgroundGray);
//
//                        } else {
//
//                            // [check 2] : id 를 key 로 하여 추가된 데이터가 있는지 검사한다.
//                            if (checkedEventList.get(id) != null) {
//
//                                // [iv/C]HashMap<Integer, Event> : id 를 key 하여 추가된 데이터가 있으면 삭제한다.
//                                checkedEventList.remove(id);
//
//                                // [lv/C]CheckBox : 기존의 색으로 변경
//                                eventCheckBox.setBackgroundResource(R.color.colorBackgroundWhite);
//
//                            } else {
//                            } // [check 2]
//                        } // [check 1]
//                    }
//                });
//
//                // [lv/C]LinearLayout : 해당 group 의 itemWrapper 에 위의 CheckBox 를 추가한다.
//                groupItemWrapper.addView(eventCheckBox);
//
//                // [lv/C]CheckBox : a group 의 CheckBox 를 관리하는 리스트에 저장
//                groupCheckBoxList[index] = eventCheckBox;
//
//            } // [cycle 1]
//
//        } // [check 1]
//
//        return groupCheckBoxList;
//    } // End of method [makeGroupCheckBox]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private Activity activity;
        private LayoutInflater inflater;
        private GroupType groupType;
        private ArrayList<Event> groupEventArrayList;
        private int standardId;

        // constructor
        public Builder(Activity activity) {
            this.activity = activity;
        }

        // setter
        public Builder setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
            return this;
        }

        public Builder setGroupType(GroupType groupType) {
            this.groupType = groupType;
            return this;
        }

        public Builder setGroupEventArrayList(ArrayList<Event> groupEventArrayList) {
            this.groupEventArrayList = groupEventArrayList;
            return this;
        }

        public Builder setStandardId(int standardId) {
            this.standardId = standardId;
            return this;
        }

        // init
        public DirectSelectionGroupItem init() {
            return new DirectSelectionGroupItem(this);
        }
    }
}
