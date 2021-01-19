package com.skymanlab.weighttraining.management.project.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.project.exception.ListenerInstanceNotExistException;

public class FragmentTopBarManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PF] FragmentTopBarManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private StartButtonListener startButtonListener;
    private EndButtonListener endButtonListener;

    // instance variable
    private TextView title;
    private ImageView startImage;
    private TextView startText;
    private ImageView endImage;
    private TextView endText;

    // instance variable
    private String titleContent;

    // constructor
    public FragmentTopBarManager(Activity activity, View view, String titleContent) {
        super(activity, view);
        this.titleContent = titleContent;
    }

    // setter
    public void setStartButtonListener(StartButtonListener startButtonListener) {
        this.startButtonListener = startButtonListener;
    }

    public void setEndButtonListener(EndButtonListener endButtonListener) {
        this.endButtonListener = endButtonListener;
    }

    @Override
    public void connectWidget() {

        // [iv/C]TextView : title connect
        this.title = (TextView) getView().findViewById(R.id.include_top_bar_title);

        // [iv/C]ImageView : startImage connect
        this.startImage = getView().findViewById(R.id.include_top_bar_start_image);

        // [iv/C]TextView : startText connect
        this.startText = (TextView) getView().findViewById(R.id.include_top_bar_start_text);

        // [iv/C]ImageView : startImage connect
        this.endImage = getView().findViewById(R.id.include_top_bar_end_image);

        // [iv/C]TextView : endText connect
        this.endText = (TextView) getView().findViewById(R.id.include_top_bar_end_text);

    }

    @Override
    public void initWidget() {
        final String METHOD_NAME = "[initWidget] ";

        // [method] : title 표시하기 위한 과정진행
        initWidgetOfTitle();

    }

    /**
     * title widget 의 초기 내용을 설정한다.
     */
    private void initWidgetOfTitle() {

        // [check 1] : title 표시할 건지 titleContent 객체의 유무로 파악
        if (this.titleContent != null) {

            // [iv/C]TextView : title 표시하기 / 색상 BLACK 으로 변경
            this.title.setText(titleContent);

        } // [check 1]

    } // End of method [initWidgetOfTitle]


    public void initWidgetOfStartButton(String content) {
        final String METHOD_NAME ="[initWidgetOfStartButton]" ;

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<content> 내용 = " + content);
        // [check 1] : StarButtonListener 를 구현한 객체가 존재할 때만 start button 의 초기 내용을 설정한다.
        if (this.startButtonListener != null) {

            // [check 2] : content 객체가 존재할 때만
            if (content != null) {

                // starText 설정 :
                // [iv/C]TextView : startText init. -> setText : content  /  click 할 수 있도록 / visible / click listener
                this.startText.setText(content);
                this.startText.setClickable(true);
                this.startText.setVisibility(View.VISIBLE);
                this.startText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startButtonListener.setStartButtonClickListener();
                    }
                });

            } else {

                // startImage 설정
                // [iv/C]ImageView : startImage init. -> click 할 수 있도록 / visible / click listener
                this.startImage.setClickable(true);
                this.startImage.setVisibility(View.VISIBLE);
                this.startImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startButtonListener.setStartButtonClickListener();
                    }
                });

            } // [check 2]

        } else {
            throw new NullPointerException("StarButtonListener 를 구현한 객체를 setStarButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]

    }

    public void initWidgetOfEndButton(String content){
        final String METHOD_NAME = "[initWidgetOfEndButton] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<method start> content = " + content);

        // [check 1] : EndButtonListener 를 구현한 객체가 존재할 때만 end button 의 초기 내용을 설정한다.
        if (this.endButtonListener != null) {

            // [check 2] : content 객체가 존재할 때만
            if (content != null) {
                
                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // endText 설정
                // [iv/C]TextView : endText init. -> setText : content  / click 할 수 있도록 / visible / click listener
                this.endText.setText(content);
                this.endText.setClickable(true);
                this.endText.setVisibility(View.VISIBLE);
                this.endText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        endButtonListener.setEndButtonClickListener();
                    }
                });

            } else {

                // endImage 설정
                // [iv/C]ImageView : endImage init. -> click 할 수 있도록 / visible / click listener
                this.endImage.setClickable(true);
                this.endImage.setVisibility(View.VISIBLE);
                this.endImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        endButtonListener.setEndButtonClickListener();
                    }
                });

            } // [check 2]

        } else {
            throw new NullPointerException("EndButtonListener 를 구현한 객체를 setEndButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface StartButtonListener{
        void setStartButtonClickListener();
    }

    public interface EndButtonListener {
        void setEndButtonClickListener();
    }
}
