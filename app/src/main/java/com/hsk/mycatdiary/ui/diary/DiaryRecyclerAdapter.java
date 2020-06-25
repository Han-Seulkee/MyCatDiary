package com.hsk.mycatdiary.ui.diary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.R;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder> {

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent, false);

        DiaryViewHolder holder = new DiaryViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        holder.tvDay.setText("");
        holder.tvTitle.setText("");
        holder.tvDate.setText("");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDay;
        public TextView tvTitle;
        public TextView tvDate;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.tvDay);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
