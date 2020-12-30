package com.skyman.weighttrainingpro.management.event.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.skyman.weighttrainingpro.R;

public class CheckableEventProgram extends LinearLayout implements Checkable {


    public CheckableEventProgram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        CheckBox eventChecker = (CheckBox) findViewById(R.id.cu_event_program_item_ch_event);

        return eventChecker.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {

        CheckBox eventChecker = (CheckBox) findViewById(R.id.cu_event_program_item_ch_event);

        if (eventChecker.isChecked() != checked) {
            eventChecker.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
        CheckBox eventChecker = (CheckBox) findViewById(R.id.cu_event_program_item_ch_event);


        setChecked(eventChecker.isChecked() ? false : true);
    }


}
