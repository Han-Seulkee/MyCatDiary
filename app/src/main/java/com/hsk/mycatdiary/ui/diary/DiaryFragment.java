package com.hsk.mycatdiary.ui.diary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

import java.util.ArrayList;

public class DiaryFragment extends Fragment {
    RecyclerView rv;
    DiaryRecyclerAdapter adapter;
    LinearLayoutManager layoutManager;

    FloatingActionButton btnAdd;

    DataBaseHelper dbHelper;

    ArrayList<DiaryListData> data;
    ArrayList<String> idx;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        rv = root.findViewById(R.id.rvDiarylist);
        btnAdd = root.findViewById(R.id.btnAdd);
        dbHelper = new DataBaseHelper(getContext());
        layoutManager = new LinearLayoutManager(this.getContext());
        adapter = new DiaryRecyclerAdapter();

        data = new ArrayList<>();
        idx = new ArrayList<String>();
        getDataBase();

        adapter.setData(data);
        adapter.setIdx(idx);
        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DiaryWrite.class);
                startActivity(intent);
            }
        });

        return root;
    }


    public void getDataBase() {
        Cursor c = dbHelper.selectDiary();
        data.clear();
        idx.clear();
        while (c.moveToNext()){
            String tidx = c.getString(c.getColumnIndex("_id"));
            String title = c.getString(c.getColumnIndex("title"));
            String date = c.getString(c.getColumnIndex("date"));
            String day = date.substring(8);
            data.add(new DiaryListData(day, title, date));
            idx.add(tidx);
        }
        adapter.notifyDataSetChanged();
    }
}
