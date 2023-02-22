package com.hsk.mycatdiary.ui.checklist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

import java.util.ArrayList;

public class CheckedlistAdapter extends RecyclerView.Adapter<CheckedlistAdapter.CheckedlistViewHolder> {
    private ArrayList<CheckedlistListData> datas;
    String idxs;
    //private ArrayList<String> idxs;

    public void setData(ArrayList<CheckedlistListData> list) {
        datas = list;
    }

    //public void setIdx(ArrayList<String> idx) {idxs = idx;}
    public void addData(CheckedlistListData data) {
        datas.add(0, data);
        notifyItemRangeInserted(0, datas.size());
        //notifyDataSetChanged();
        Log.i("시부엉", String.valueOf(getItemCount()));
    }

    public void updateData(String p, CheckedlistListData data){
        int position = Integer.parseInt(p);
        datas.set(position, data);
        notifyItemChanged(position);

    }

    DataBaseHelper dbHelper;

    @NonNull
    @Override
    public CheckedlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checkedlist, parent, false);

        CheckedlistViewHolder holder = new CheckedlistViewHolder(view);
        dbHelper = new DataBaseHelper(parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckedlistViewHolder holder, final int position) {
        CheckedlistListData data = datas.get(position);
        idxs = data.getIdx();
        holder.cb.setText(data.getTodo());
        Log.i("todo : ", data.getTodo());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String sId = idxs;
                dbHelper.updateTodoState(sId, 0);

                datas.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datas.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CheckedlistViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb;

        public CheckedlistViewHolder(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cbChecked);
        }
    }
}
