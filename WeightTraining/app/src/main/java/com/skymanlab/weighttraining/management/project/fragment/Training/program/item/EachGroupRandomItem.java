package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.program.data.GroupingEventData;
import com.skymanlab.weighttraining.management.project.data.DataManager;
import com.skymanlab.weighttraining.management.project.data.type.MuscleArea;

public class EachGroupRandomItem {

    // instance variable
    private Activity activity;
    private LayoutInflater inflater;
    private MuscleArea muscleArea;
    private GroupingEventData groupingEventData;

    // instance variable
    private View item;

    // instance variable : widget
    private MaterialCardView contentWrapper;        // [1]
    private MaterialTextView title;                 // [2]
    private MaterialCardView aGroupWrapper;         // [3]
    private MaterialCardView bGroupWrapper;         // [4]
    private MaterialCardView cGroupWrapper;         // [5]
    private MaterialCardView dGroupWrapper;         // [6]
    private MaterialCardView eGroupWrapper;         // [7]
    private AppCompatSpinner aGroupSpinner;         // [8]
    private AppCompatSpinner bGroupSpinner;         // [9]
    private AppCompatSpinner cGroupSpinner;         // [10]
    private AppCompatSpinner dGroupSpinner;         // [11]
    private AppCompatSpinner eGroupSpinner;         // [12]

    // constructor
    private EachGroupRandomItem(Builder builder) {
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

    public void setTitle(MaterialTextView title) {
        this.title = title;
    }

    public MaterialCardView getAGroupWrapper() {
        return aGroupWrapper;
    }

    public MaterialCardView getBGroupWrapper() {
        return bGroupWrapper;
    }

    public MaterialCardView getCGroupWrapper() {
        return cGroupWrapper;
    }

    public MaterialCardView getDGroupWrapper() {
        return dGroupWrapper;
    }

    public MaterialCardView getEGroupWrapper() {
        return eGroupWrapper;
    }

    public AppCompatSpinner getAGroupSpinner() {
        return aGroupSpinner;
    }

    public AppCompatSpinner getBGroupSpinner() {
        return bGroupSpinner;
    }

    public AppCompatSpinner getCGroupSpinner() {
        return cGroupSpinner;
    }

    public AppCompatSpinner getDGroupSpinner() {
        return dGroupSpinner;
    }

    public AppCompatSpinner getEGroupSpinner() {
        return eGroupSpinner;
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
        return inflater.inflate(R.layout.include_maker_type_each_group_random_item, null);
    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void connectWidget(View view) {

        // [1] contentWrapper
        connectWidgetOfContentWrapper(view);

        // [2] title
        connectWidgetOfTitle(view);

        // a group
        connectWidgetOfAGroup(view);

        // b group
        connectWidgetOfBGroup(view);

        // c group
        connectWidgetOfCGroup(view);

        // d group
        connectWidgetOfDGroup(view);

        // e group
        connectWidgetOfEGroup(view);

        if (groupingEventData.getAGroupEventArrayList().isEmpty()
                && groupingEventData.getBGroupEventArrayList().isEmpty()
                && groupingEventData.getCGroupEventArrayList().isEmpty()
                && groupingEventData.getDGroupEventArrayList().isEmpty()
                && groupingEventData.getEGroupEventArrayList().isEmpty()) {

            aGroupWrapper.setVisibility(View.GONE);
            bGroupWrapper.setVisibility(View.GONE);
            cGroupWrapper.setVisibility(View.GONE);
            dGroupWrapper.setVisibility(View.GONE);
            eGroupWrapper.setVisibility(View.GONE);
        }

    } // End of method [connectWidget]


    private void connectWidgetOfContentWrapper(View view) {

        this.contentWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_content_wrapper);

    } // End of method [connectWidgetOfContentWrapper]


    private void connectWidgetOfTitle(View view) {

        this.title = (MaterialTextView) view.findViewById(R.id.include_maker_type_each_group_random_item_title);

    } // End of method [connectWidgetOfTitle]


    private void connectWidgetOfAGroup(View view) {

        // wrapper
        this.aGroupWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_a_group_wrapper);

        // spinner
        this.aGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_each_group_random_item_a_group_spinner);


    } // End of method [connectWidgetOfAGroup]


    private void connectWidgetOfBGroup(View view) {

        // wrapper
        this.bGroupWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_b_group_wrapper);

        // spinner
        this.bGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_each_group_random_item_b_group_spinner);

    } // End of method [connectWidgetOfBGroup]


    private void connectWidgetOfCGroup(View view) {

        // wrapper
        this.cGroupWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_c_group_wrapper);

        // spinner
        this.cGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_each_group_random_item_c_group_spinner);

    } // End of method [connectWidgetOfCGroup]


    private void connectWidgetOfDGroup(View view) {

        // wrapper
        this.dGroupWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_d_group_wrapper);

        // spinner
        this.dGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_each_group_random_item_d_group_spinner);

    } // End of method [connectWidgetOfDGroup]


    private void connectWidgetOfEGroup(View view) {

        // wrapper
        this.eGroupWrapper = (MaterialCardView) view.findViewById(R.id.include_maker_type_each_group_random_item_e_group_wrapper);

        // spinner
        this.eGroupSpinner = (AppCompatSpinner) view.findViewById(R.id.include_maker_type_each_group_random_item_e_group_spinner);

    } // End of method [connectWidgetOfEGroup]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void initItem() {

    }

    private void initWidget() {

        // [1] contentWrapper
        initWidgetOfContentWrapper();

        // [2] title
        initWidgetOfTitle();

        // aGroup
        initWidgetOfAGroup();

        // bGroup
        initWidgetOfBGroup();

        // cGroup
        initWidgetOfCGroup();

        // dGroup
        initWidgetOfDGroup();

        // eGroup
        initWidgetOfEGroup();

    } // End of method [initWidget]


    private void initWidgetOfContentWrapper() {

    } // End of method [initWidgetOfContentWrapper]


    private void initWidgetOfTitle() {

        // text
        this.title.setText(DataManager.convertHanguleOfMuscleArea(muscleArea));

    } // End of method [initWidgetOfTitle]


    private void initWidgetOfAGroup() {

        if (this.groupingEventData.getAGroupEventArrayList() != null) {
            if (!this.groupingEventData.getAGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : wrapper GONE
                this.aGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]AppCompatSpinner : sinner adapter
                this.aGroupSpinner.setAdapter(initAdapter(groupingEventData.getAGroupEventArrayList().size()));

            }
        }

    } // End of method [initWidgetOfAGroup]


    private void initWidgetOfBGroup() {

        if (this.groupingEventData.getBGroupEventArrayList() != null) {
            if (!this.groupingEventData.getBGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : wrapper GONE
                this.bGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]AppCompatSpinner : sinner adapter
                this.bGroupSpinner.setAdapter(initAdapter(groupingEventData.getBGroupEventArrayList().size()));
            }
        }

    } // End of method [initWidgetOfBGroup]


    private void initWidgetOfCGroup() {

        if (this.groupingEventData.getCGroupEventArrayList() != null) {
            if (!this.groupingEventData.getCGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : wrapper GONE
                this.cGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]AppCompatSpinner : sinner adapter
                this.cGroupSpinner.setAdapter(initAdapter(groupingEventData.getCGroupEventArrayList().size()));
            }
        }

    } // End of method [initWidgetOfCGroup]


    private void initWidgetOfDGroup() {

        if (this.groupingEventData.getDGroupEventArrayList() != null) {
            if (!this.groupingEventData.getDGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : wrapper GONE
                this.dGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]AppCompatSpinner : sinner adapter
                this.dGroupSpinner.setAdapter(initAdapter(groupingEventData.getDGroupEventArrayList().size()));
            }
        }

    } // End of method [initWidgetOfDGroup]


    private void initWidgetOfEGroup() {

        if (this.groupingEventData.getEGroupEventArrayList() != null) {
            if (!this.groupingEventData.getEGroupEventArrayList().isEmpty()) {

                // [iv/C]MaterialCardView : wrapper GONE
                this.eGroupWrapper.setVisibility(View.VISIBLE);

                // [iv/C]AppCompatSpinner : sinner adapter
                this.eGroupSpinner.setAdapter(initAdapter(groupingEventData.getEGroupEventArrayList().size()));
            }
        }

    } // End of method [initWidgetOfEGroup]


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
     * aGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 aGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return aGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfAGroupSpinner() {

        if (0 < this.aGroupSpinner.getSelectedItemPosition()) {
            return this.aGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfAGroupSpinner]


    /**
     * bGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 bGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return bGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfBGroupSpinner() {

        if (0 < this.bGroupSpinner.getSelectedItemPosition()) {
            return this.bGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfBGroupSpinner]


    /**
     * cGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 cGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return cGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfCGroupSpinner() {

        if (0 < this.cGroupSpinner.getSelectedItemPosition()) {
            return this.cGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfCGroupSpinner]


    /**
     * dGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 dGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return dGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfDGroupSpinner() {

        if (0 < this.dGroupSpinner.getSelectedItemPosition()) {
            return this.dGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfDGroupSpinner]


    /**
     * eGroupSpinner 의 position 은 그 position 의 값과 같으므로 각 위치의 값을 가져온다. 하지만 만약 eGroupSpinner 에서 -1 값이 넘어오면 0 으로 치환하여 반환한다.
     *
     * @return eGroupSpinner 의 item. 즉, 랜덤으로 선택할 개수
     */
    public int getItemOfEGroupSpinner() {

        if (0 < this.eGroupSpinner.getSelectedItemPosition()) {
            return this.eGroupSpinner.getSelectedItemPosition();
        } else {
            return 0;
        }

    } // End of method [getItemOfEGroupSpinner]


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
        public EachGroupRandomItem create() {
            return new EachGroupRandomItem(this);
        }
    }
}
