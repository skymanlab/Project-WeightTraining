package com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.dialog.EventDialog;
import com.skymanlab.weighttraining.management.project.data.DataManager;

import java.util.ArrayList;

public class EachMuscleAreaListRvAdapter extends RecyclerView.Adapter<EachMuscleAreaListRvAdapter.ViewHolder> {

    // constant
    private static final String CLASS_NAME = "[PFTLA] EachMuscleAreaListRvAdapter";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private Fragment fragment;
    private ArrayList<Event> eventArrayList;

    // constructor
    public EachMuscleAreaListRvAdapter(Fragment fragment, ArrayList<Event> eventArrayList) {
        this.eventArrayList = eventArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // count, eventName, equipmentType, groupType, properWeight, maxWeight
        holder.count.setText((position + 1) + "");
        holder.eventName.setText(eventArrayList.get(position).getEventName());
        holder.equipmentType.setText(DataManager.convertHanguleOfEquipmentType(eventArrayList.get(position).getEquipmentType()));
        holder.groupType.setText(DataManager.convertHanguleOfGroupType(eventArrayList.get(position).getGroupType()));
        holder.properWeight.setText(eventArrayList.get(position).getProperWeight() + "");
        holder.maxWeight.setText(eventArrayList.get(position).getMaxWeight() + "");

        // modify click listener
        holder.modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 수정 과정을 진행
                setClickListenerOfModify(position);
            }
        });

        // delete click listener
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 삭제 과정을 진행
                setClickListenerOfDelete(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }


    /**
     * [method] modify click listener
     */
    private void setClickListenerOfModify(int position) {
        final String METHOD_NAME = "[setClickListenerOfModify] ";

        // [lv/C]String : EventDialog 에서 제공하는 setArguments() 메소드를 사용하여, arguments 항목을 설정한다.
        String[] arguments = EventDialog.setArguments(fragment.getString(R.string.custom_dialog_event_update_title),
                fragment.getString(R.string.custom_dialog_event_update_positive_title),
                fragment.getString(R.string.custom_dialog_event_update_negative_title));

        // [lv/C]EventDialog : event dialog 를 생성
        EventDialog dialog = EventDialog.newInstance(eventArrayList.get(position).getMuscleArea(), eventArrayList.get(position), arguments);
        dialog.setDatabaseListener(new EventDialog.DatabaseListener() {
            @Override
            public void setDatabaseListener(Event event) {
                // EventDialog 에서 제동하는 database 에 event 내용을 수정하는 메소드를 실행한다.
                dialog.updateContent(event, EventDialog.MESSAGE_TYPE_SNACK_BAR);
            }

        });
        dialog.setCancelable(false);
        dialog.show(fragment.getActivity().getSupportFragmentManager(), null);

    } // End of method [setClickListenerOfModify]


    /**
     * [method] delete click listener
     */
    private void setClickListenerOfDelete(int position) {

        final String METHOD_NAME = "[setClickListenerOfDelete] ";

        // [lv/C]AlertDialog : Builder 로 AlertDialog 객체 생성 / 초기설정
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
        builder.setTitle(R.string.each_list_rv_adapter_alert_delete_title)
                .setMessage(R.string.each_list_rv_adapter_alert_delete_message)
                .setPositiveButton(R.string.each_list_rv_adapter_alert_delete_bt_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> uid = " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> muscle area = " + eventArrayList.get(position).getMuscleArea());
                        LogManager.displayLog(CLASS_LOG_DISPLAY_POWER, CLASS_NAME, METHOD_NAME, ">>>> key = " + eventArrayList.get(position).getKey());

                        // [lv/C]DatabaseReference :
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("event");
                        db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(eventArrayList.get(position).getMuscleArea().toString()).child(eventArrayList.get(position).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                // [check 1] : 에러 발생 안 함
                                if (error == null) {

                                    // "삭제가 완료되었습니다." snack bar 메시지 표시
                                    Snackbar.make(fragment.getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.each_list_rv_adapter_snack_db_delete_success, Snackbar.LENGTH_SHORT).show();

                                } else {

                                    // "삭제를 실패하였습니다." snack bar 메시지 표시
                                    Snackbar.make(fragment.getActivity().findViewById(R.id.nav_home_bottom_bar), R.string.each_list_rv_adapter_snack_db_delete_error, Snackbar.LENGTH_SHORT).show();

                                } // [check 1]
                            }
                        });


                    }
                })
                .setNegativeButton(R.string.each_list_rv_adapter_alert_delete_bt_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    } // End of method [setClickListenerOfDelete]


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        TextView count;
        TextView eventName;

        // instance variable
        TextView equipmentType;
        TextView groupType;
        TextView properWeight;
        TextView maxWeight;
        TextView modify;
        TextView delete;

        // constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            count = (TextView) itemView.findViewById(R.id.custom_list_event_item_count);
            eventName = (TextView) itemView.findViewById(R.id.custom_list_event_item_event_name);

            equipmentType = (TextView) itemView.findViewById(R.id.custom_list_event_item_equipment_type);
            groupType = (TextView) itemView.findViewById(R.id.custom_list_event_item_group_type);
            properWeight = (TextView) itemView.findViewById(R.id.custom_list_event_item_proper_weight);
            maxWeight = (TextView) itemView.findViewById(R.id.custom_list_event_item_max_weight);
            modify = (TextView) itemView.findViewById(R.id.custom_list_event_item_modify);
            delete = (TextView) itemView.findViewById(R.id.custom_list_event_item_delete);

        }
    }
}
