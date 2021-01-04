package com.skymanlab.weighttraining.management.project.fragment.Training.list.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.developer.Display;
import com.skymanlab.weighttraining.management.developer.LogManager;
import com.skymanlab.weighttraining.management.event.data.Event;
import com.skymanlab.weighttraining.management.event.dialog.EventModificationDialog2;
import com.skymanlab.weighttraining.management.project.data.DataManager;

import java.util.ArrayList;

public class EventListRvAdapter extends RecyclerView.Adapter<EventListRvAdapter.ViewHolder> {

    // constant
    private static final String CLASS_NAME = "[PFTLA] EventListRvAdapter";
    private static final Display CLASS_LOG_DISPLAY_POWER = Display.OFF;

    // instance variable
    private ArrayList<Event> eventArrayList;
    private Activity activity;
    private FragmentManager fragmentManager;

    // constructor
    public EventListRvAdapter(ArrayList<Event> eventArrayList, Activity activity, FragmentManager fragmentManager) {
        this.eventArrayList = eventArrayList;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // [lv/C]View : create a new view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_event_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // count, eventName, equipmentType, groupType, properWeight, maxWeight
        holder.getCount().setText((position + 1) + "");
        holder.getEventName().setText(eventArrayList.get(position).getEventName());
        holder.getEquipmentType().setText(DataManager.convertHanguleOfEquipmentType(eventArrayList.get(position).getEquipmentType()));
        holder.getGroupType().setText(DataManager.convertHanguleOfGroupType(eventArrayList.get(position).getGroupType()));
        holder.getProperWeight().setText(eventArrayList.get(position).getProperWeight() + "");
        holder.getMaxWeight().setText(eventArrayList.get(position).getMaxWeight() + "");

        // modify click listener
        holder.getModify().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // [method] : 수정 과정을 진행
                setClickListenerOfModify(position);
            }
        });

        // delete click listener
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
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

        // [lv/C]EventModificationDialog2 :
        EventModificationDialog2 dialog = new EventModificationDialog2(this.activity, FirebaseAuth.getInstance().getCurrentUser().getUid(), eventArrayList, position);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "EventModificationDialog");

    } // End of method [setClickListenerOfModify]


    /**
     * [method] delete click listener
     */
    private void setClickListenerOfDelete(int position) {

        final String METHOD_NAME = "[setClickListenerOfDelete] ";

        // [lv/C]AlertDialog : Builder 로 AlertDialog 객체 생성 / 초기설정
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.elra_alert_delete_title)
                .setMessage(R.string.elra_alert_delete_message)
                .setPositiveButton(R.string.elra_alert_delete_bt_positive, new DialogInterface.OnClickListener() {
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

                                    // "삭제가 완료되었습니다." Toast 메시지 표시
                                    Toast.makeText(activity, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                } else {

                                    // "삭제를 실패하였습니다." Toast 메시지 표시
                                    Toast.makeText(activity, "삭제를 실패하였습니다.", Toast.LENGTH_SHORT).show();

                                } // [check 1]
                            }
                        });


                    }
                })
                .setNegativeButton(R.string.elra_alert_delete_bt_negative, new DialogInterface.OnClickListener() {
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

            // [method] : widget mapping
            mappingWidget(itemView);
        }

        // getter
        public TextView getCount() {
            return count;
        }

        public TextView getEventName() {
            return eventName;
        }

        public TextView getEquipmentType() {
            return equipmentType;
        }

        public TextView getGroupType() {
            return groupType;
        }

        public TextView getProperWeight() {
            return properWeight;
        }

        public TextView getMaxWeight() {
            return maxWeight;
        }

        public TextView getModify() {
            return modify;
        }

        public TextView getDelete() {
            return delete;
        }

        /**
         * [method] widget mapping
         */
        private void mappingWidget(View itemView) {

            count = (TextView) itemView.findViewById(R.id.cu_event_item_count);
            eventName = (TextView) itemView.findViewById(R.id.cu_event_item_event_name);

            equipmentType = (TextView) itemView.findViewById(R.id.cu_event_item_equipment_type);
            groupType = (TextView) itemView.findViewById(R.id.cu_event_item_group_type);
            properWeight = (TextView) itemView.findViewById(R.id.cu_event_item_proper_weight);
            maxWeight = (TextView) itemView.findViewById(R.id.cu_event_item_max_weight);
            modify = (TextView) itemView.findViewById(R.id.cu_event_item_modify);
            delete = (TextView) itemView.findViewById(R.id.cu_event_item_delete);
        }
    }
}
