package com.skymanlab.weighttraining.management.project.fragment.More.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.skymanlab.weighttraining.R;
import com.skymanlab.weighttraining.management.notice.Notice;

import java.util.ArrayList;

public class NoticeRvAdapter extends RecyclerView.Adapter<NoticeRvAdapter.ViewHolder> {

    // instance variable
    private ArrayList<Notice> noticeArrayList;

    // constructor
    public NoticeRvAdapter(ArrayList<Notice> noticeArrayList) {
        this.noticeArrayList = noticeArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_notice_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // content wrapper
        holder.contentWrapper.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        // type
        holder.type.setText(noticeArrayList.get(position).getType());

        // title
        holder.title.setText(noticeArrayList.get(position).getTitle());

        // date
        holder.date.setText(noticeArrayList.get(position).getDate());

        // message
        holder.message.setText(noticeArrayList.get(position).getMessage());

    }

    @Override
    public int getItemCount() {
        return noticeArrayList.size();
    }

    // ================================================= ViewHolder =================================================
    public class ViewHolder extends RecyclerView.ViewHolder {

        // instance variable
        private MaterialCardView contentWrapper;
        private TextView type;
        private TextView title;
        private TextView date;
        private TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contentWrapper = (MaterialCardView) itemView.findViewById(R.id.custom_list_noticeItem_content_wrapper);
            type = (TextView) itemView.findViewById(R.id.custom_list_noticeItem_type);
            title = (TextView) itemView.findViewById(R.id.custom_list_noticeItem_title);
            date = (TextView) itemView.findViewById(R.id.custom_list_noticeItem_date);
            message = (TextView) itemView.findViewById(R.id.custom_list_noticeItem_message);

        }
    }
}
