package com.skymanlab.weighttraining.management.project.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;

public class FragmentTopBarManager extends FragmentSectionManager implements FragmentSectionInitializable {

    // constant
    private static final String CLASS_NAME = "[PF] FragmentTopBarManager";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private String titleContent;

    // instance variable
    private StartButtonListener startButtonListener;
    private EndButtonListener endButtonListener;

    // instance variable
    private TextView title;
    private ImageView startImage;
    private TextView startText;
    private ImageView endImage1;
    private ImageView endImage2;
    private TextView endText;

    // constructor
    public FragmentTopBarManager(Fragment fragment, View view, String titleContent) {
        super(fragment, view);
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
        this.endImage1 = getView().findViewById(R.id.include_top_bar_end_image_1);

        // [iv/C]ImageView : startImage connect
        this.endImage2 = getView().findViewById(R.id.include_top_bar_end_image_2);

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
        final String METHOD_NAME = "[initWidgetOfStartButton]";

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

                        AlertDialog dialog = startButtonListener.setStartButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

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

                        AlertDialog dialog = startButtonListener.setStartButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            } // [check 2]

        } else {
            throw new NullPointerException("StarButtonListener 를 구현한 객체를 setStarButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]

    }

    public void initWidgetOfEndButton(String title) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<method start> content = " + title);

        // [check 1] : EndButtonListener 를 구현한 객체가 존재할 때만 end button 의 초기 내용을 설정한다.
        if (this.endButtonListener != null) {

            // [check 2] : title 객체가 존재할 때만
            if (title != null) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // endText 설정
                // [iv/C]TextView : endText init. -> setText : title  / click 할 수 있도록 / visible / click listener
                this.endText.setText(title);
                this.endText.setClickable(true);
                this.endText.setVisibility(View.VISIBLE);
                this.endText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = endButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            } else {
                throw new RuntimeException("title 을 입력하지 않았습니다.");
            } // [check 2]

        } else {
            throw new NullPointerException("EndButtonListener 를 구현한 객체를 setEndButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]
    }


    public void initWidgetOfEndTextButton(String content, EndButtonListener endButtonListener) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<method start> content = " + content);

        // [check 1] : EndButtonListener 를 구현한 객체가 존재할 때만 end button 의 초기 내용을 설정한다.
        if (endButtonListener != null) {

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

                        AlertDialog dialog = endButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            }

        } else {
            throw new NullPointerException("EndButtonListener 를 구현한 객체를 setEndButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]
    }

    public void initWidgetOfEndImageButton(int resId, EndButtonListener endButtonListener) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<method start> resource id = " + resId);

        // [check 1] : EndButtonListener 를 구현한 객체가 존재할 때만 end button 의 초기 내용을 설정한다.
        if (endButtonListener != null) {

            // [check 2] : content 객체가 존재할 때만
            if (0 < resId) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // [ ImageView | endImage1 ]
                this.endImage1.setImageResource(resId);
                this.endImage1.setClickable(true);
                this.endImage1.setVisibility(View.VISIBLE);
                this.endImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = endButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            } else {
                throw new RuntimeException("End Image Button 의 표시할 resource id 가 입력되지 않았습니다.");
            }

        } else {
            throw new NullPointerException("EndButtonListener 를 구현한 객체를 setEndButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]
    }


    public void initWidgetOfEndImageButton(int firstResId, EndButtonListener firstEndButtonListener, int secondResId, EndButtonListener secondEndButtonListener) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";

        // [check 1] : EndButtonListener 를 구현한 객체가 존재할 때만 end button 의 초기 내용을 설정한다.
        if (firstEndButtonListener != null && secondEndButtonListener != null) {

            // [check 2] : content 객체가 존재할 때만
            if (0 < firstResId && 0 < secondResId) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // [ ImageView | endImage1 ]
                this.endImage1.setImageResource(firstResId);
                this.endImage1.setClickable(true);
                this.endImage1.setVisibility(View.VISIBLE);
                this.endImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = firstEndButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

                // [ ImageView | endImage2 ]
                this.endImage2.setImageResource(secondResId);
                this.endImage2.setClickable(true);
                this.endImage2.setVisibility(View.VISIBLE);
                this.endImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = secondEndButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });


            } else {
                throw new RuntimeException("End Image Button 의 표시할 resource id 가 입력되지 않았습니다.");
            }

        } else {
            throw new NullPointerException("EndButtonListener 를 구현한 객체를 setEndButtonListener() method 를 사용하여 설정해주세요!");
        } // [check 1]
    }


    // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= interface =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public interface StartButtonListener {
        AlertDialog setStartButtonClickListener();
    }

    public interface EndButtonListener {
        AlertDialog setEndButtonClickListener();
    }
}
