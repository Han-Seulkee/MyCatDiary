package com.hsk.mycatdiary.ui.checklist;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChecklistFragment extends Fragment {
    ViewFlipper vf;
    RecyclerView rv;

    CheckBox tvTodo;
    String time;

    ImageButton btnPrev, btnNext;

    ArrayList<String> idx, list, checkedList;
    DataBaseHelper dbHelper;
    long nowIndex;
    int i = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checklist, container, false);
        vf = root.findViewById(R.id.vf);
        rv = root.findViewById(R.id.rvCheckedlist);
        tvTodo = root.findViewById(R.id.tvTodo);
        btnPrev = root.findViewById(R.id.btnPrev);
        btnNext = root.findViewById(R.id.btnNext);

        dbHelper = new DataBaseHelper(getContext());

        list = new ArrayList<>();
        idx = new ArrayList<>();

        checkedList = new ArrayList<>();


        getCheckList();

        btnPrev.setOnClickListener(onClick);
        btnNext.setOnClickListener(onClick);

        tvTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                nowIndex = Long.parseLong(idx.get(i));
                dbHelper.updateTodoState(nowIndex, 1);
                checkedList.add((String) tvTodo.getText());
            }
        });

        return root;
    }

    View.OnClickListener onClick = new View.OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNext:
                    if(!list.isEmpty())
                        tvTodo.setText(list.get(i++));
                    break;
                case R.id.btnPrev:
                    if(!list.isEmpty())
                        tvTodo.setText(list.get(--i));
                    break;
            }
        }
    };

    //시간에 맞게 조회, 리스트에 저장
    void getCheckList() {
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String nTime = hour.format(now);
        int s = Integer.parseInt(nTime)+10;

        if (s >= 6 && s < 9) {
            time = "1st";
        } else if (s >= 9 && s < 12) {
            time = "2nd";
        } else if (s >= 12 && s < 15) {
            time = "3rd";
        } else if (s >= 15 && s < 18) {
            time = "4th";
        } else if (s >= 18 && s < 21) {
            time = "5th";
        } else ;

        Cursor c = dbHelper.showTodo(time);
        list.clear();
        idx.clear();
        while(c.moveToNext()){
            String tidx = c.getString(c.getColumnIndex("_id"));
            list.add(c.getString(c.getColumnIndex("todo")));
            idx.add(tidx);
        }
        if(! list.isEmpty()) {tvTodo.setText(list.get(0)); tvTodo.setEnabled(true);}
        else {btnPrev.setVisibility(View.INVISIBLE);btnNext.setVisibility(View.INVISIBLE);}
    }
}
