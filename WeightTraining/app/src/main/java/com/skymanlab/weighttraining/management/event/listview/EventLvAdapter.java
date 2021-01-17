package com.skymanlab.weighttraining.management.event.listview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.dialog.EventDialog;
import com.skymanlab.weighttraining.management.project.data.DataFormatter;
import com.skymanlab.weighttraining.management.project.data.DataManager;

import java.util.ArrayList;

public class EventLvAdapter extends BaseAdapter {

    // constructor
    private static final String CLASS_NAME = "[EL] EventItemLvAdapter ";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private ArrayList<Boolean> isFolded;
    private Activity targetActivity;
    private FragmentManager fragmentManager;
    private String uid;

    // constructor
    public EventLvAdapter(Activity targetActivity, FragmentManager fragmentManager, String uid) {
        this.targetActivity = targetActivity;
        this.fragmentManager = fragmentManager;
        this.uid = uid;
    }

    // setter
    public void setEventArrayList(ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
    }

    public void setIsFolded(ArrayList<Boolean> isFolded) {
        this.isFolded = isFolded;
    }

    @Override
    public int getCount() {
        return eventArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String METHOD_NAME = "[getView] ";

        // [lv/C]Context : context 가져오기
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_event_item, parent, false);
        }

        // widget mapping
        TextView count = (TextView) convertView.findViewById(R.id.custom_list_event_item_count);
        TextView eventName = (TextView) convertView.findViewById(R.id.custom_list_event_item_event_name);
        TextView modify = (TextView) convertView.findViewById(R.id.custom_list_event_item_modify);
        TextView delete = (TextView) convertView.findViewById(R.id.custom_list_event_item_delete);

        TextView equipmentType = (TextView) convertView.findViewById(R.id.custom_list_event_item_equipment_type);
        TextView groupType = (TextView) convertView.findViewById(R.id.custom_list_event_item_group_type);
        TextView properWeight = (TextView) convertView.findViewById(R.id.custom_list_event_item_proper_weight);
        TextView maxWeight = (TextView) convertView.findViewById(R.id.custom_list_event_item_max_weight);

        // [lv/C]Event : 하나의 리스트에 표시할 데이터
        Event event = (Event) getItem(position);

        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "===>>> Adapter 의 event 내용확인 <<<===");
//        LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, event);

        // [method] : modify, delete, fold 의 click listener 이벤트 설정하기
        setClickListenerOfModify(modify, position);
        setClickListenerOfDelete(delete, position);

        // widget initial data setting
        count.setText((position + 1) + ".");
        eventName.setText(event.getEventName());

        equipmentType.setText(DataManager.convertHanguleOfEquipmentType(event.getEquipmentType()));
        groupType.setText(DataManager.convertHanguleOfGroupType(event.getGroupType()));
        properWeight.setText(DataFormatter.setWeightFormat(event.getProperWeight()));
        maxWeight.setText(DataFormatter.setWeightFormat(event.getMaxWeight()));

        return convertView;

    } // End of method [getView]


    /**
     * [method] modify 의 click listener 를 설정하기
     */
    private void setClickListenerOfModify(TextView modify, final int position) {

        final String METHOD_NAME = "[setClickListenerOfModify] ";
        final EventLvAdapter adapter = this;

        // [lv/C]TextView : modify 의 click listener 를 설정한다.
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [lv/C]String : EventDialog 에서 제공하는 setArguments() 메소드를 사용하여, arguments 항목을 설정한다.
                String[] arguments = EventDialog.setArguments(targetActivity.getString(R.string.custom_dialog_event_update_title),
                        targetActivity.getString(R.string.custom_dialog_event_update_positive_title),
                        targetActivity.getString(R.string.custom_dialog_event_update_negative_title));

                // [lv/C]EventDialog : event dialog 를 생성
                EventDialog dialog = EventDialog.newInstance(eventArrayList.get(position).getMuscleArea(), eventArrayList.get(position), arguments);
                dialog.setDatabaseListener(new EventDialog.DatabaseListener() {
                    @Override
                    public void setDatabaseListener(Event event) {
                        // EventDialog 에서 제동하는 database 에 event 내용을 수정하는 메소드를 실행한다.
                        dialog.updateContent(event, EventDialog.MESSAGE_TYPE_NONE);
                    }

                });
                dialog.setCancelable(false);
                dialog.show(fragmentManager, null);

            }
        });

    } // End of method [setClickListenerOfModify]


    /**
     * [method] delete 의 click listener 을 설정하기
     */
    private void setClickListenerOfDelete(TextView delete, final int position) {

        final String METHOD_NAME = "[setClickListenerOfDelete] ";

        // [lv/C]TextView : delete click listener
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // [lv/C]AlertDialog.Builder : builder 객체 생성 및 초기 설절
                AlertDialog.Builder builder = new AlertDialog.Builder(targetActivity);
                builder.setTitle(R.string.event_lv_adapter_alert_delete_title)
                        .setMessage(R.string.event_lv_adapter_alert_delete_message)
                        .setPositiveButton(R.string.event_lv_adapter_alert_delete_bt_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>>>>> 삭제할 event 의 내용");
                                LogManager.displayLogOfEvent(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, eventArrayList.get(position));

                                // [iv/C]EventDbManager :
                                int result = 10101;

                                LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "result = " + result);

                                // [check 1] : 해당 데이터를 삭제를 성공하였다.
                                if (result == 1) {

                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/true : 데이터베이스에서 삭제 완료하였습니다. <=");
                                    // [lv/C]ArrayList<Event> : eventArrayList 에서 해당 position 의 event 제거하기
                                    eventArrayList.remove(position);

                                    // [method] : 변경된 내용을 adapter 를 통해 반영하기
                                    notifyDataSetChanged();

                                    // [lv/C]Toast : 삭제가 완료되었습니다.
                                    Toast.makeText(targetActivity, R.string.event_lv_adapter_snack_db_delete_success, Toast.LENGTH_SHORT).show();

                                } else {
                                    LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, "=> check_1/false : 데이터베이스에서 삭제하지 못 했어! <=");

                                    // [lv/C]Toast : 실패하였습니다.
                                    Toast.makeText(targetActivity, R.string.event_lv_adapter_snack_db_delete_error, Toast.LENGTH_SHORT).show();

                                } // [check 1]

                            }
                        })
                        .setNegativeButton(R.string.event_lv_adapter_alert_delete_bt_negative, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        })
                        .show();

            }
        });

    } // End of method [setClickListenerOfDelete]


    /**
     * [method] fold 의 click listener 을 설정하기
     */
    private void setClickListenerOfFold(int position, ImageView fold, LinearLayout contentWrapper) {

        final String METHOD_NAME = "[setClickListenerOfFold] ";


        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>> " + position + " 번째  >>>   값 = " + isFolded.get(position));
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>> isFolded size = " + isFolded.size());
        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>>>>>> eventArrayList size = " + eventArrayList.size());

        // [check 1] : 해당 section 을 보여주고 있나요?
        if (this.isFolded.get(position)) {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 접힘 -> 펼쳐짐 >>>>");
            // 해당 section 을 보여준다.
            // [iv/C]ArrayList<Boolean> : false(펼쳐짐) 로 변경
            this.isFolded.set(position, false);

            // [lv/C]LinearLayout : 해당 contentWrapper 를 보여준다.(VISIBLE)
            contentWrapper.setVisibility(LinearLayout.VISIBLE);

            // [lv/C]ImageView : 해당 이미지를 △ 모양으로 변경
            fold.setImageResource(R.drawable.ic_baseline_expand_less_24);


        } else {

            LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> 펼쳐짐 -> 접힘 >>>>");
            // 해당 section 을 보여주지 않는다.
            // [iv/C]ArrayList<Boolean> : true(접힘) 로 변경
            this.isFolded.set(position, true);

            // [lv/C]LinearLayout : 해당 contentWrapper 를 없앤다.(GONE)
            contentWrapper.setVisibility(LinearLayout.GONE);

            // [lv/C]ImageView : 해당 이미지를 ▽ 모양으로 변경
            fold.setImageResource(R.drawable.ic_baseline_expand_more_24);

        } // [check 1]

    } // End of method [setClickListenerOfFold]

}
