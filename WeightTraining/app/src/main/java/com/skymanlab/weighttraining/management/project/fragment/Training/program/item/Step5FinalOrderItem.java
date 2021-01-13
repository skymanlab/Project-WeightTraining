package com.skymanlab.weighttraining.management.project.fragment.Training.program.item;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;

public class Step5FinalOrderItem {

    // instance variable
    private LayoutInflater inflater;
    int contentWrapperColor;
    private Event event;
    private int eventNumber;
    OnItemClickListener itemClickListener;

    // instance variable
    private View item;

    // instance variable
    private MaterialCardView contentWrapper;
    private TextView number;
    private TextView muscleAreaNumber;

    // constructor
    private Step5FinalOrderItem(Builder builder) {
        this.inflater = builder.inflater;
        this.contentWrapperColor = builder.contentWrapperColor;
        this.event = builder.event;
        this.eventNumber = builder.eventNumber;
        this.itemClickListener = builder.itemClickListener;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public View getItem() {
        return item;
    }

    public MaterialCardView getContentWrapper() {
        return contentWrapper;
    }

    public TextView getNumber() {
        return number;
    }

    public TextView getMuscleAreaNumber() {
        return muscleAreaNumber;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= create =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 을 만들고 설정을 초기화한다.
     */
    public void createItem() {

        // [iv/C]View : item 생성하기
        this.item = inflateItem();

        // item 안의 내용물(widget)을 연결한다.
        connectWidget(this.item);

        // [iv/C]View : item 의 초기 내용을 설정한다.
        initItem();


    } // End of method [makeItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inflate =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * inflater 를 이용하여 include_step_final_order_item.xml layout 을 inflate 한 view 를 반환다.
     *
     * @return final order 의 한 항목인 item 이다.
     */
    private View inflateItem() {

        return inflater.inflate(R.layout.include_step5_final_order_item, null);

    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 의 모든 widget 을 사용하기 위해 연결(연관)시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidget(View view) {

        // [1] contentWrapper
        connectWidgetOfContentWrapper(view);

        // [2] number
        connectWidgetOfNumber(view);

        // [3] muscleAreaNumber
        connectWidgetOfMuscleAreaNumber(view);

    }


    /**
     * item 의 내용물을 감싸고 있는 [1] contentWrapper 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfContentWrapper(View view) {

        // [iv/C]MaterialCardView : contentWrapper connect
        this.contentWrapper = (MaterialCardView) view.findViewById(R.id.include_step5_final_order_item_content_wrapper);

    } // End of method [connectWidgetOfContentWrapper]

    /**
     * item 의 내용물 중 순서(번호)를 나타내는 [2] number 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfNumber(View view) {

        // [iv/C]TextView : number connect
        this.number = (TextView) view.findViewById(R.id.include_step5_final_order_item_number);

    } // End of method [connectWidgetOfNumber]


    /**
     * item 의 내용물 중 근육 부위 순서(번호)를 나타내는 [3] muscleAreaNumber 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfMuscleAreaNumber(View view) {

        // [iv/C]TextView : muscleAreaNumber connect
        this.muscleAreaNumber = (TextView) view.findViewById(R.id.include_step5_final_order_item_muscle_area_number);

    } // End of method [connectWidgetOfMuscleAreaNumber]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= init widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 의 초기 내용을 설정한다.
     */
    private void initItem() {

        // item click listener :
        this.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (itemClickListener != null) {

                    // event 의 key 를 넘겨주어서 이 key 로 다음 일을 수행하도록 한다.
                    itemClickListener.excludeFinalOrderItem(item, event, contentWrapperColor);

                } else {
                    throw new NullPointerException("OnItemClickListener 가 생성되지 않았습니다. Builder 를 통해 OnItemClickListener 를 입력해주세요.");
                }
            }
        });

        // item 안의 widget 들의 초기 내용을 설정한다.
        initWidget();

    } // End of method [initWidgetOfItem]


    /**
     * item 의 각 widget 들의 초기 상태를 설정(준비)한다.
     */
    private void initWidget() {

        // [1] contentWrapper
        initWidgetOfContentWrapper();

        // [2] moreInfo
        initWidgetOfNumber();

        // [3] muscleAreaNumber
        initWidgetOfMuscleAreaNumber();

    } // End of method [initWidget]


    /**
     * [1] contentWrapper 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfContentWrapper() {

        // background : muscleArea 에 맞는 색으로 변경
        this.contentWrapper.setCardBackgroundColor(contentWrapperColor);


    } // End of method [initWidgetOfContentWrapper]


    /**
     * [] contentWrapper 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfNumber() {

    } // End of method initWidgetOfNumber


    /**
     * [] contentWrapper 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfMuscleAreaNumber() {

        // text
        this.muscleAreaNumber.setText(makeMuscleAreaNumber());

    } // End of method [initWidgetOfMuscleAreaNumber]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= etc =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private String makeMuscleAreaNumber() {

        // [lv/C]StringBuilder :
        StringBuilder muscleAreaNumber = new StringBuilder();

        // muscleArea 를 한글로 변환한 문자열 추가
        muscleAreaNumber.append(DataManager.convertHanguleOfMuscleArea(this.event.getMuscleArea()));

        // 공백 하나 추가
        muscleAreaNumber.append(" ");

        // eventNumber 추가
        muscleAreaNumber.append(this.eventNumber);

        return muscleAreaNumber.toString();

    } // End of method [makeMuscleAreaNumber]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnItemClickListener {
         void excludeFinalOrderItem(View item, Event event, int contentWrapperColor);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        LayoutInflater inflater;
        int contentWrapperColor;
        Event event;
        int eventNumber;

        // instance variable
        OnItemClickListener itemClickListener;

        // constructor
        public Builder() {

        }

        // setter
        public Builder setInflater(LayoutInflater inflater) {
            this.inflater = inflater;
            return this;
        }

        public Builder setContentWrapperColor(int contentWrapperColor) {
            this.contentWrapperColor = contentWrapperColor;
            return this;
        }

        public Builder setEvent(Event event) {
            this.event = event;
            return this;
        }

        public Builder setEventNumber(int eventNumber) {
            this.eventNumber = eventNumber;
            return this;
        }

        public Builder setItemClickListener(OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public Step5FinalOrderItem init() {
            return new Step5FinalOrderItem(this);
        }
    }
}
