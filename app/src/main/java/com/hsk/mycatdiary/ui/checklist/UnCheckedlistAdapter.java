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

public class UnCheckedlistAdapter extends RecyclerView.Adapter<UnCheckedlistAdapter.UnCheckedlistViewHolder> {
    private ArrayList<uCheckedlistListData> datas;
    String idxs;
    //private ArrayList<String> idxs;
    private CheckedlistAdapter ca;

    public void setData(ArrayList<uCheckedlistListData> list) {
        datas = list;
    }

    //public void setIdx(ArrayList<String> idx) { idxs = idx; }

    public void addData(uCheckedlistListData data) {
        datas.add(0, data);
        notifyItemRangeInserted(0, datas.size());
    }

    DataBaseHelper dbHelper;

    @NonNull
    @Override
    public UnCheckedlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_uncheckedlist, parent, false);

        UnCheckedlistViewHolder holder = new UnCheckedlistViewHolder(view);
        dbHelper = new DataBaseHelper(parent.getContext());


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UnCheckedlistViewHolder holder, final int position) {
        uCheckedlistListData data = datas.get(position);
        idxs = data.getIdx();
        final String todo = data.getTodo();
        ca = new CheckedlistAdapter();
        holder.cb.setText(todo);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.i("시부엉", String.valueOf(getItemCount()));
                String sId = idxs;
                dbHelper.updateTodoState(sId, 1);
                ca.addData(new CheckedlistListData(todo, idxs));
                Log.i("untodo : ", todo);

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

    public class UnCheckedlistViewHolder extends RecyclerView.ViewHolder {
        public CheckBox cb;

        public UnCheckedlistViewHolder(@NonNull View itemView) {
            super(itemView);

            cb = itemView.findViewById(R.id.cbUnChecked);
        }
    }
}
