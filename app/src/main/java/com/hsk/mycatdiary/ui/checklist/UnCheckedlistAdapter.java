package com.hsk.mycatdiary.ui.checklist;

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

public class UnCheckedlistAdapter extends RecyclerView.Adapter<UnCheckedlistAdapter.UnCheckedlistViewHolder> {
    private ArrayList<uCheckedlistListData> datas;
    private ArrayList<String> idxs;

    public void setData(ArrayList<uCheckedlistListData> list ) {datas = list;}
    public void setIdx(ArrayList<String> idx) {idxs = idx;}

    DataBaseHelper dbHelper;

    @NonNull
    @Override
    public UnCheckedlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uncheckedlist,parent,false);

        UnCheckedlistViewHolder holder = new UnCheckedlistViewHolder(view);
        dbHelper = new DataBaseHelper(parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnCheckedlistViewHolder holder, final int position) {
        uCheckedlistListData data = datas.get(position);

        holder.cb.setText(data.getTodo());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long sId = Long.parseLong(idxs.get(position));
                dbHelper.updateTodoState(sId, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class UnCheckedlistViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb;
        public UnCheckedlistViewHolder(@NonNull View itemView) {
            super(itemView);

            cb = itemView.findViewById(R.id.cbUnChecked);
        }
    }
}
