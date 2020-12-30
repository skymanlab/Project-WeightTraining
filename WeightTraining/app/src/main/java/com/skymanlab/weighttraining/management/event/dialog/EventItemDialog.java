package com.skymanlab.weighttraining.management.event.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.event.data.Event;

import java.util.ArrayList;

public class EventItemDialog extends DialogFragment {

    // constance
    public static final String CLASS_NAME = "[ED]_EventItemModificationDialog2";
    public static final Display CLASS_LOG_DISPLAY_POWER = Display.ON;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private int position;

    // instance variable
    private LinearLayout titleWrapper;
    private Spinner equipmentType;
    private Spinner groupType;
    private EditText eventName;
    private EditText properWeight;
    private EditText maxWeight;

    // instance variable
    private AlertDialog.Builder builder;

    // constructor
    public EventItemDialog(ArrayList<Event> eventArrayList, int position, AlertDialog.Builder builder) {
        this.eventArrayList = eventArrayList;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-= layout setting =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        // [lv/C]LinearLayoutInflate : layout 부풀리기???
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // [lv/C]View : layoutInflater 을 통해 custom layout 가져오기
        View view = inflater.inflate(R.layout.custom_dialog_event_item, null);

        // [method] : widget mapping
        mappingWidget(view);

        // [iv/C]builder : 위의 view 를 AlertDialog 의 커스텀 레이아웃으로
        this.builder.setView(view);

        return this.builder.create();
    }



    /**
     * [method] widget mapping
     */
    private void mappingWidget(View view) {

        // [iv/C]LinearLayout : titleWrapper mapping
        this.titleWrapper = (LinearLayout) view.findViewById(R.id.cud_event_item_modification_title_wrapper);

        // [iv/C]Spinner : equipmentType mapping
        this.equipmentType = (Spinner) view.findViewById(R.id.cud_event_item_modification_equipment_type);

        // [iv/C]Spinner : equipmentType mapping
        this.groupType = (Spinner) view.findViewById(R.id.cud_event_item_modification_group_type);

        // [ic/C]EditText : eventName mapping
        this.eventName = (EditText) view.findViewById(R.id.cud_event_item_modification_event_name);

        // [ic/C]EditText : properWeight mapping
        this.properWeight = (EditText) view.findViewById(R.id.cud_event_item_modification_proper_weight);

        // [ic/C]EditText : maxWeight mapping
        this.maxWeight = (EditText) view.findViewById(R.id.cud_event_item_modification_max_weight);


    } // End of method [mappingWidget]
}
