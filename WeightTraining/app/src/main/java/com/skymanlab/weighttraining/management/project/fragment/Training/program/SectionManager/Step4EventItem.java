package com.skymanlab.weighttraining.management.project.fragment.Training.program.SectionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.project.data.DataManager;

public class Step4EventItem {

    // instance variable
    private LayoutInflater inflater;
    private int contentWrapperColor;
    private Event event;
    private int eventNumber;
    private OnItemClickListener itemClickListener;

    // instance variable
    private View item;

    // instance variable
    private MaterialCardView contentWrapper;        // [1] content wrapper
    private ImageView moreInfo;                     // [2] more info
    private TextView muscleAreaNumber;              // [3] muscle area number
    private TextView eventName;                     // [4] event name

    // constructor
    public Step4EventItem(Builder builder) {
        this.inflater = builder.inflater;
        this.contentWrapperColor = builder.contentWrapperColor;
        this.event = builder.event;
        this.eventNumber = builder.eventNumber;
        this.itemClickListener = builder.itemClickListener;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= getter =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * event 의 한 item 을 반환
     *
     * @return item
     */
    public View getItem() {
        return item;
    }


    /**
     * item 의 내용물을 감사고 있는 [1] contentWrapper 를 반환
     *
     * @return contentWrapper MaterialCardView widget
     */
    public MaterialCardView getContentWrapper() {
        return contentWrapper;
    }


    /**
     * item 의 내용물 중 [2] moreInfo 를 반환
     *
     * @return moreInfo ImageView widget
     */
    public ImageView getMoreInfo() {
        return moreInfo;
    }


    /**
     * item 의 내용물 중 [3] muscleAreaNumber 를 반환
     *
     * @return muscleAreaNumber TextView widget
     */
    public TextView getMuscleAreaNumber() {
        return muscleAreaNumber;
    }


    /**
     * item 의 내용물 중 [4] eventName 를 반환
     *
     * @return eventName TextView widget
     */
    public TextView getEventName() {
        return eventName;
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= create =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 을 만들고 설정을 초기화한다.
     */
    public void createItem() {

        // [iv/C]View : item 을 생성하기
        this.item = inflateItem();

        // item 안의 widget
        connectWidget(this.item);

        // [iv/C]View : item 의 초기 내용을 설정한다.
        initItem();

    } // End of method [createItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= inflate =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * inflater 를 이용하여 include_step_event_item.xml layout 을 inflate 한 view 를 반환다.
     *
     * @return event 의 한 항목인 item 이다.
     */
    private View inflateItem() {
        return inflater.inflate(R.layout.include_step4_event_item, null);
    } // End of method [inflateItem]


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= connect widget =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    /**
     * item 의 모든 widget 을 사용하기 위해 연결(연관)시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidget(View view) {

        // [1] content wrapper
        connectWidgetOfContentWrapper(view);

        // [2] more info
        connectWidgetOfMoreInfo(view);

        // [3] muscle area number
        connectWidgetOfMuscleAreaNumber(view);

        // [4] event name
        connectWidgetOfEventName(view);

    } // End of method [connectWidget]


    /**
     * item 의 내용물을 감싸고 있는 [1] contentWrapper 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfContentWrapper(View view) {

        // [iv/C]MaterialCardView : contentWrapper connect
        this.contentWrapper = (MaterialCardView) view.findViewById(R.id.include_step4_event_item_content_wrapper);

    } // End of method [connectWidgetOfContentWrapper]


    /**
     * item 의 내용물 중 정보 더보기를 나타내는 [2] moreInfo 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfMoreInfo(View view) {

        // [iv/C]ImageView : moreInfo connect
        this.moreInfo = (ImageView) view.findViewById(R.id.include_step4_event_item_more_info);

    } // End of method [connectWidgetOfMoreInfo]


    /**
     * item 의 내용물 중 근육 부위 순서를 나타내는 [3] muscleAreaNumber 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfMuscleAreaNumber(View view) {

        // [iv/C]TextView : muscleAreaNumber connect
        this.muscleAreaNumber = (TextView) view.findViewById(R.id.include_step4_event_item_muscle_area_number);

    } // End of method [connectWidgetOfMuscleAreaNumber]


    /**
     * item 의 내용물 중 종목 이름을 나타내는 [4] eventName 을 widget 과 연결시킨다.
     *
     * @param view inflate 한 view
     */
    private void connectWidgetOfEventName(View view) {

        // [iv/C]TextView : eventName connect
        this.eventName = (TextView) view.findViewById(R.id.include_step4_event_item_event_name);

    } // End of method [connectWidgetOfEventName]


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
                    itemClickListener.includeFinalOrderItem(item, contentWrapper, event.getKey());

                } else {
                    throw new NullPointerException("OnItemClickListener 가 생성되지 않았습니다. Builder 를 통해 OnItemClickListener 를 입력해주세요.");
                }
            }
        });

        // item 안의 widget 들의 초기 내용을 설정한다.
        initWidget();

    } // End of method [initItem]


    /**
     * item 의 각 widget 들의 초기 상태를 설정(준비)한다.
     */
    private void initWidget() {

        // [1] contentWrapper
        initWidgetOfContentWrapper();

        // [2] moreInfo
        initWidgetOfMoreInfo();

        // [3] muscleAreaNumber
        initWidgetOfMuscleAreaNumber();

        // [4] eventName
        initWidgetOfEventName();

    } // End of method [initWidget]


    /**
     * [1] contentWrapper 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfContentWrapper() {

        // background : muscleArea 에 맞는 색으로 변경
        this.contentWrapper.setCardBackgroundColor(contentWrapperColor);

    } // End of method [initWidgetOfContentWrapper]


    /**
     * [2] moreInfo 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfMoreInfo() {

        // click listener
        this.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    } // End of method [initWidgetOfMoreInfo]


    /**
     * [3] muscleAreaNumber 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfMuscleAreaNumber() {

        // text
        this.muscleAreaNumber.setText(makeMuscleAreaNumber());

    } // End of method [initWidgetOfMuscleAreaNumber]


    /**
     * [4] eventName 의 초기 상태를 설정(준비) 한다.
     */
    private void initWidgetOfEventName() {

        // text
        this.eventName.setText(event.getEventName());

    } // End of method [initWidgetOfEventName]


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


    /**
     * item 에 LayoutParams 를 적용한다.
     */
    public void applyLayoutParams(GridLayout.LayoutParams params) {

        // [iv/C]View :
        this.item.setLayoutParams(params);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface OnItemClickListener {
        void includeFinalOrderItem(View item, MaterialCardView contentWrapper, String eventKey);
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= builder =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public static class Builder {

        // instance variable
        private LayoutInflater inflater;
        private Event event;
        private int eventNumber;
        private int contentWrapperColor;

        // instance variable : click listener
        private OnItemClickListener itemClickListener;

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

        public Step4EventItem init() {
            return new Step4EventItem(this);
        }
    }
}
