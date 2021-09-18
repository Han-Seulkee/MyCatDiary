package com.hsk.mycatdiary.ui.checklist;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChecklistFragment extends Fragment {
    ViewFlipper vf;
    TextView testTv;
    ImageButton btnPrev, btnNext;

    //list는 todo목록, idx는 todo아이디
    ArrayList<String> list, idx;
    ArrayList<String> checkedIdx, unCheckedIdx;
    ArrayList<CheckedlistListData> checkedList; //리사이클러뷰에 올릴 데이터 리스트
    ArrayList<uCheckedlistListData> unCheckedList;

    DataBaseHelper dbHelper;

    RecyclerView rvCheckedlist, rvUnCheckedlist;
    LinearLayoutManager lm1, lm2;
    CheckedlistAdapter cadapter;
    UnCheckedlistAdapter uadapter;


    String time, nowIndex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_checklist, container, false);
        rvCheckedlist = root.findViewById(R.id.rvCheckedlist);
        rvUnCheckedlist = root.findViewById(R.id.rvUnCheckedlist);
        cadapter = new CheckedlistAdapter();
        uadapter = new UnCheckedlistAdapter();

        vf = root.findViewById(R.id.vf);
        btnPrev = root.findViewById(R.id.btnPrev);
        btnNext = root.findViewById(R.id.btnNext);

        testTv = root.findViewById(R.id.testTv);

        dbHelper = new DataBaseHelper(getContext());

        list = new ArrayList<>();
        idx = new ArrayList<>();
        checkedList = new ArrayList<>();
        checkedIdx = new ArrayList<>();
        unCheckedList = new ArrayList<>();
        unCheckedIdx = new ArrayList<>();

        //체크리스트 불러오기
        getCheckList();


        //뷰플리퍼에 체크리스트(체크박스) 동적으로 추가
        final CheckBox cb[] = new CheckBox[list.size()];
        for (int i = 0; i < list.size(); i++) {
            cb[i] = new CheckBox(getContext());
            cb[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            cb[i].setButtonDrawable(R.drawable.checkbox_check);
            cb[i].setText(list.get(i));
            cb[i].setTextSize(30);
            vf.addView(cb[i]);
            final String cbIdx = idx.get(i);
            final int j = i;

            //뷰플리퍼안 체크리스트에 체크를 했을때 체크리스트완료목록 갱신
            cb[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
                        nowIndex = cbIdx;
                        dbHelper.updateTodoState(nowIndex, 1);

                        cadapter.addData(new CheckedlistListData(list.get(j)));
                        Log.i("리스트 누름 : ", list.get(j));
                        Log.i("인덱스 : ", String.valueOf(cbIdx));
                        vf.removeView(cb[j]);

                    }
                }
            });
        }

        btnPrev.setOnClickListener(onClick);
        btnNext.setOnClickListener(onClick);

        //완료된상태의 체크리스트 불러오기
        getChecked();
        //미완료된 상태의 체크리스트 불러오기
        getUnChecked();

        lm1 = new LinearLayoutManager(this.getContext());
        cadapter.setData(checkedList);
        //cadapter.setIdx(checkedIdx);
        rvCheckedlist.setAdapter(cadapter);
        rvCheckedlist.setLayoutManager(lm1);

        lm2 = new LinearLayoutManager(this.getContext());
        uadapter.setData(unCheckedList);
        //uadapter.setIdx(unCheckedIdx);
        rvUnCheckedlist.setAdapter(uadapter);
        rvUnCheckedlist.setLayoutManager(lm2);

        return root;
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNext:
                    vf.showNext();
                    break;
                case R.id.btnPrev:
                    vf.showPrevious();
                    break;
            }
        }
    };


    //시간에 맞게 조회, 리스트에 저장
    void getCheckList() {
        //Date now = Calendar.getInstance().getTime();
        long now = System.currentTimeMillis();
        Date newDate = new Date(now);

        SimpleDateFormat hour = new SimpleDateFormat("HH");
        String nTime = hour.format(newDate);
        int s = Integer.parseInt(nTime);
        //int s = 6;
        testTv.setText("지금은" + s + "시");   //테스트용
        if (s >= 6 && s < 9) {
            time = "1st";
        } else if (s >= 9 && s < 12) {
            time = "2nd";
        } else if (s >= 12 && s < 15) {
            time = "3rd";
        } else if (s >= 15 && s < 18) {
            time = "4th";
        } else if (s >= 18 && s < 24) {
            time = "5th";
        } else reset();


        Cursor c = dbHelper.showTodo(time);
        list.clear();
        idx.clear();
        while (c.moveToNext()) {
            String tidx = c.getString(c.getColumnIndex("_id"));
            list.add(c.getString(c.getColumnIndex("todo")));
            idx.add(tidx);
        }
    }

    void getChecked() {
        Cursor c = dbHelper.showComplete();
        checkedList.clear();
        checkedIdx.clear();
        while (c.moveToNext()) {
            String tidx = c.getString(c.getColumnIndex("_id"));
            String td = c.getString(c.getColumnIndex("todo"));
            checkedList.add(new CheckedlistListData(td, tidx));
            //checkedIdx.add(tidx);
        }
    }

    void getUnChecked() {
        Cursor c = dbHelper.showDonot(/*time*/);
        unCheckedList.clear();
        unCheckedIdx.clear();
        while (c.moveToNext()) {
            String tidx = c.getString(c.getColumnIndex("_id"));
            String td = c.getString(c.getColumnIndex("todo"));
            unCheckedList.add(new uCheckedlistListData(td, tidx));
            //unCheckedIdx.add(tidx);
        }
    }

    void reset() {
        dbHelper.resetTodo();
    }
}
