package com.skymanlab.weighttraining.management.project.fragment;

import android.graphics.drawable.Drawable;
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


    // ============================================================== start button ==============================================================

    /**
     * StartButton 에 String 이 null 일 때와 아닐 때를 구분하여
     * null 이 아니면 해당 문자열로
     * null 이면 이미 있는 ImageView 로
     *
     * @param text
     * @param startButtonListener
     */
    public void initWidgetOfStartButton(String text, StartButtonListener startButtonListener) {
        final String METHOD_NAME = "[initWidgetOfStartButton]";

        // 리스너는 꼭 등록해주세요!
        if (startButtonListener != null) {

            // text 문자열이 null 이면 '<' 이미지로 대체합니다.
            if (text != null) {

                // StartButton 은 TextView !
                this.startText.setText(text);
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

                // StartButton 은 ImageView !
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
            }

        } else {
            throw new RuntimeException("StarButtonListener 객체를 등록해주세요. null 객체는 안되요.");
        } // [check 1]

    }


    // ============================================================== end button ==============================================================

    /**
     * EndButton : TextView 일 때
     *
     * @param text
     * @param endButtonListener
     */
    public void initWidgetOfEndButton(String text, EndButtonListener endButtonListener) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";

        // 리스너는 꼭 등록해주세요!
        if (endButtonListener != null) {

            // text 는 꼭 입력해주세요.
            if (text != null && !text.equals("")) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // EndButton 은 TextView 로!
                this.endText.setText(text);
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
                throw new RuntimeException("text 를 입력해주세요!");
            } // [check 2]

        } else {
            throw new RuntimeException("EndButtonListener 객체를 등록해주세요. null 객체는 안되요.");
        } // [check 1]

    }


    /**
     * EndButton : ImageView 가 하나 일 때
     *
     * @param image
     * @param endButtonListener
     */
    public void initWidgetOfEndButton(Drawable image, EndButtonListener endButtonListener) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";

        // 리스너는 꼭 등록해주세요!
        if (endButtonListener != null) {

            // image 는 꼭 입력해주세요.
            if (image != null) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");
                // StartButton 은 ImageView !
                this.endImage1.setClickable(true);
                this.endImage1.setVisibility(View.VISIBLE);
                this.endImage1.setImageDrawable(image);
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
                throw new RuntimeException("image 의 drawable 을 입력해주세요!");
            } // [check 2]

        } else {
            throw new RuntimeException("EndButtonListener 객체를 등록해주세요. null 객체는 안되요.");
        } // [check 1]

    }


    /**
     * EndButton : ImageView 가 2개 일 때
     *
     * @param image_1
     * @param image_2
     * @param endButtonListener_1
     * @param endButtonListener_2
     */
    public void initWidgetOfEndButton(Drawable image_1,
                                      EndButtonListener endButtonListener_1,
                                      Drawable image_2,
                                      EndButtonListener endButtonListener_2) {
        final String METHOD_NAME = "[initWidgetOfEndButton] ";

        // 리스너는 꼭 등록해주세요!
        if (endButtonListener_1 != null && endButtonListener_2 != null) {

            // image 는 꼭 입력해주세요.
            if (image_1 != null && image_2 != null) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // StartButton 은 ImageView  !
                // 1번 이미지
                this.endImage1.setClickable(true);
                this.endImage1.setVisibility(View.VISIBLE);
                this.endImage1.setImageDrawable(image_1);
                this.endImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = endButtonListener_1.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

                // StartButton 은 ImageView  !
                // 2번 이미지
                this.endImage2.setClickable(true);
                this.endImage2.setVisibility(View.VISIBLE);
                this.endImage2.setImageDrawable(image_2);
                this.endImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = endButtonListener_2.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            } else {
                throw new RuntimeException("1번 2번 이미지를 입력해주세요!");
            } // [check 2]

        } else {
            throw new RuntimeException("1번과 2번 EndButtonListener 객체를 등록해주세요. null 객체는 안되요.");
        } // [check 1]

    }


    public void initWidgetOfEndButton(String text,
                                      EndButtonListener textEndButtonListener,
                                      Drawable image_1,
                                      EndButtonListener imageEndButtonListener_1,
                                      Drawable image_2,
                                      EndButtonListener imageEndButtonListener_2) {

        final String METHOD_NAME = "[initWidgetOfEndButton] ";

        // 리스너는 꼭 등록해주세요!
        if (textEndButtonListener != null && imageEndButtonListener_1 != null && imageEndButtonListener_2 != null) {

            // image 는 꼭 입력해주세요.
            if (text != null && image_1 != null && image_2 != null) {

                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "<check 2> 최종 수행하기 전");

                // StartButton 은 TextView  !
                this.endText.setText(text);
                this.endText.setClickable(true);
                this.endText.setVisibility(View.VISIBLE);
                this.endText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = textEndButtonListener.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

                // StartButton 은 ImageView  !
                // 1번 이미지
                this.endImage1.setClickable(true);
                this.endImage1.setVisibility(View.VISIBLE);
                this.endImage1.setImageDrawable(image_1);
                this.endImage1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = imageEndButtonListener_1.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

                // StartButton 은 ImageView  !
                // 2번 이미지
                this.endImage2.setClickable(true);
                this.endImage2.setVisibility(View.VISIBLE);
                this.endImage2.setImageDrawable(image_2);
                this.endImage2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog dialog = imageEndButtonListener_2.setEndButtonClickListener();

                        if (dialog != null) {
                            dialog.show();
                        }

                    }
                });

            } else {
                throw new RuntimeException("text, image 1번과 2번 이미지를 입력해주세요!");
            } // [check 2]

        } else {
            throw new RuntimeException("text, image 1번과 2번 EndButtonListener 객체를 등록해주세요. null 객체는 안되요.");
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
