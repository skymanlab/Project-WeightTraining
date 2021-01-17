package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

public class AllGroupRandomItem  {

    // instance variable
    private Activity activity;
    private LayoutInflater inflater;
    private MuscleArea muscleArea;
    private GroupingEventData groupingEventData;

    // instance variable
    private View item;

    // instance variable : widget
    private MaterialCardView contentWrapper;    // [1]
    private MaterialTextView title;             // [2]
    private AppCompatSpinner allGroupSpinner;   // [3]

    // constructor
    private AllGroupRandomItem(Builder builder) {
        this.activity = builder.activity;
        this.inflater = builder.inflater;
        this.muscleArea = builder.muscleArea;
        this.groupingEventData = builder.groupingEventData;
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

    public AppCompatSpinner getAllGroupSpinner() {
        return allGroupSpinner;
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
        return inflater.inflate(R.layout.include_maker_type_all_group_random_item, null);
    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void connectWidget(View view) {

        // [1] contentWrapper
        connectWidgetOfContentWrapper(view);

        // [2] title
        connectWidgetOfTitle(view);

        // [3] allGroupSpinner
        connectWidgetOfAllGroupSpinner(view);

    } // End of method [connectWidget]


    private void connectWidgetOfContentWrapper(View view) {

        this.contentWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_all_group_random_item_content_wrapper);

    } // End of method [connectWidgetOfContentWrapper]


    private void connectWidgetOfTitle(View view) {

        this.title = (MaterialTextView) view.findViewById(R.id.include_maker_type_all_group_random_item_title);

    } // End of method [connectWidgetOfTitle]


    private void connectWidgetOfAllGroupSpinner(View view) {

        this.allGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_all_group_random_item_spinner);

    } // End of method [connectWidgetOfAllGroupSpinner]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void initItem() {

    }

    private void initWidget() {

        // [1] contentWrapper
        initWidgetOfContentWrapper();

        // [2] muscleArea
        initWidgetOfTitle();

        // [3] allGroupSpinner
        initWidgetOfAllGroupSpinner();

    } // End of method [initWidget]


    private void initWidgetOfContentWrapper() {

    } // End of method [initWidgetOfContentWrapper]


    private void initWidgetOfTitle() {

        // text
        this.title.setText(DataManager.convertHanguleOfMuscleArea(muscleArea));

    } // End of method [initWidgetOfTitle]


    private void initWidgetOfAllGroupSpinner() {

        // adapter
        this.allGroupSpinner.setAdapter(initAdapter(this.groupingEventData.getAllSize()));

    } // End of method [initWidgetOfAllGroupSpinner]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    /**
     * @param size groupingEventData 의 size
     * @return groupingEventData 의 모든 항목
     */
    private ArrayAdapter<Integer> initAdapter(int size) {

        // [lv/C]ArrayList<Integer> :
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(activity, R.layout.support_simple_spinner_dropdown_item);

        // [cycle 1] :
        for (int index = 0; index <= size; index++) {

            // [lv/C]ArrayAdapter<Integer> : size 만큼 index 를 추가한다.
            adapter.add(index);

        } // [cycle 1]

        return adapter;

    } // End of method [setAdapterOfAllSpinner]


    /**
     * allGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 allGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return allGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfAllGroupSpinner() {

        if (0 < this.allGroupSpinner.getSelectedItemPosition()) {
            return this.allGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfAllGroupSpinner]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private Activity activity;
        private LayoutInflater inflater;
        private GroupingEventData groupingEventData;
        private MuscleArea muscleArea;

        // constructor
        public Builder(Activity activity) {
            this.activity = activity;
        }

        // setter
        public Builder setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
            return this;
        }

        public Builder setGroupingEventData(GroupingEventData groupingEventData) {
            this.groupingEventData = groupingEventData;
            return this;
        }

        public Builder setMuscleArea(MuscleArea muscleArea) {
            this.muscleArea = muscleArea;
            return this;
        }

        // create
        public AllGroupRandomItem create() {
            return new AllGroupRandomItem(this);
        }
    }

}
