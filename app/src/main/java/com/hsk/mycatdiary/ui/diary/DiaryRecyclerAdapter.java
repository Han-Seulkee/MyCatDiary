package com.hsk.mycatdiary.ui.diary;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.R;

import java.util.ArrayList;

public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryRecyclerAdapter.DiaryViewHolder> {
    private ArrayList<DiaryListData> datas;
    private ArrayList<String> idxs;

    public void setData(ArrayList<DiaryListData> list ) {datas = list;}
    public void setIdx(ArrayList<String> idx) {idxs = idx;}

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary,parent, false);

        DiaryViewHolder holder = new DiaryViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, final int position) {
        DiaryListData data = datas.get(position);

        holder.tvDay.setText(data.getDay());
        holder.tvTitle.setText(data.getTitle());
        holder.tvDate.setText(data.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sId = Long.parseLong(idxs.get(position));
                Intent intent = new Intent(v.getContext(),DiaryContent.class);
                intent.putExtra("id",sId);
                v.getContext().startActivity(intent);
            }
        });

        getItemPositoin(position);
    }

    public int getItemPositoin(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
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
