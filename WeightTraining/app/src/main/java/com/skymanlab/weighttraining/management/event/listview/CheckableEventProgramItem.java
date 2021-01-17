package com.skymanlab.weighttraining.management.event.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.skymanlab.weighttraining.R;


public class CheckableEventProgramItem extends LinearLayout implements Checkable {


    public CheckableEventProgramItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isChecked() {
        CheckBox eventChecker = (CheckBox) findViewById(R.id.custom_list_event_program_item_checker);

        return eventChecker.isChecked();
    }

    @Override
    public void setChecked(boolean checked) {

        CheckBox eventChecker = (CheckBox) findViewById(R.id.custom_list_event_program_item_checker);

        if (eventChecker.isChecked() != checked) {
            eventChecker.setChecked(checked);
        }
    }

    @Override
    public void toggle() {
        CheckBox eventChecker = (CheckBox) findViewById(R.id.custom_list_event_program_item_checker);


        setChecked(eventChecker.isChecked() ? false : true);
    }


}
