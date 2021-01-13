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
    private ArrayList<CheckBox> eventItemList;

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
        this.title.setText(DataManager.convertHanguleOfGroupType(groupType));
    }


    private void initWidgetOfEventListWrapper() {

        // [iv/C]ArrayList<CheckBox> :
        this.eventItemList = new ArrayList<CheckBox>();

        // [cycle 1] : groupEventArrayList 의 모든 항목(Event 객체)을 checkBox 로 만들기
        for (int index=0; index < groupEventArrayList.size() ; index++ ) {

            // check box 생성
            CheckBox eventItem = createEventItem(groupEventArrayList.get(index), (this.standardId + index)) ;

            // check box 를 관리 리스트에 추가
            addItemToEventItemList(eventItem);

            // check box 를 eventListWrapper 에 추가하여 화면에 표시하기
            addViewToEventListWrapper(eventItem);

        } // [cycle 1]
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private CheckBox createEventItem(Event event, int id) {

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

                } else {

                    eventItem.setBackgroundResource(R.color.colorBackgroundWhite);

                } // [check 1]
            }
        });

        return eventItem;
    }

    private void addItemToEventItemList( CheckBox eventItem) {
        this.eventItemList.add(eventItem);
    }


    private void addViewToEventListWrapper(CheckBox eventItem) {
        this.eventListWrapper.addView(eventItem);
    }

    public EventResultSet getEventResultSet() {
        EventResultSet eventResultSet = new EventResultSet();

        // [cycle 1] : eventItemList 의 각 항목(CheckBox)을 검사한다.
        for (int index=0; index < eventItemList.size() ; index++ ) {

            // [check 1] : 각 항목의 체크여부를 검사하여 eventResultSet 을 설정한다. /  groupEventArrayList 와 1:1 대응된다. 그러므로 해당 index 의 Event 객체를 가져와서 추가한다.
            if (eventItemList.get(index).isChecked()) {
                
                // [lv/C]EventResultSet : 선택된 항목으로 추가
                eventResultSet.addSelectedEventArrayList(this.groupEventArrayList.get(index));
            } else {
                // [lv/C]EventResultSet : 선택되지 않은 항목으로 추가
                eventResultSet.addNoSelectedEventArrayList(this.groupEventArrayList.get(index));
            } // [check 1]

        } // [cycle 1]

        return eventResultSet;
    } // End of method [getEventResultSet]


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
