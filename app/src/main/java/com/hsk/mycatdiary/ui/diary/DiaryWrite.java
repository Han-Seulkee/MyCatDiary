package com.hsk.mycatdiary.ui.diary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryWrite extends AppCompatActivity {
    Toolbar toolbar;

    Button btnCommit;
    ImageButton btnDatepick;
    RadioGroup rgState;
    EditText editTitle, editDate, editContent;

    SimpleDateFormat dayFormat, monthFormat, yearFormat;
    String nYear, nMonth, nDay;
    int ty, tm, td, y, m, d;

    String TITLE, DATE, STATE, CONTENT;
    String tt, dt, ct;
    long id;

    DataBaseHelper dbHelper;

    RecyclerView rv;
    DiaryRecyclerAdapter adapter;
    ArrayList<DiaryListData> data;
    ArrayList<String> idx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarywrite);
        //상단탭 취소버튼
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnCommit = findViewById(R.id.btnCommit);
        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editContent = findViewById(R.id.editContent);
        btnDatepick = findViewById(R.id.btnDatepick);
        rgState = findViewById(R.id.rgState);

        Intent intent = getIntent();
        int viewID = intent.getIntExtra("rv", 0);
        tt = intent.getStringExtra("title");
        dt = intent.getStringExtra("date");
        ct = intent.getStringExtra("content");
        id = intent.getLongExtra("id", 0);
        final boolean isEdit = intent.getBooleanExtra("edit", false);

        rv = findViewById(viewID);
        data = new ArrayList<>();
        idx = new ArrayList<String>();
        dbHelper = new DataBaseHelper(getApplicationContext());

        //오늘날짜
        Date now = Calendar.getInstance().getTime();
        dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        nYear = yearFormat.format(now);
        nMonth = monthFormat.format(now);
        nDay = dayFormat.format(now);
        //오늘날짜를 int로 변형 -> DatePicker에서 기본날짜로 설정
        ty = Integer.parseInt(nYear);
        tm = Integer.parseInt(nMonth) - 1;
        td = Integer.parseInt(nDay);

        //제목, 날짜는 기본으로 오늘 날짜로 설정
        editTitle.setText(nYear + "." + nMonth + "." + nDay);
        editDate.setText(nYear + "-" + nMonth + "-" + nDay);

        //날짜 선택
        DATE = editDate.getText().toString();
        btnDatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });

        //기본 선택값
        RadioButton d = findViewById(rgState.getCheckedRadioButtonId());
        STATE = d.getText().toString();
        //다른 상태 선택시
        rgState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton st = findViewById(checkedId);
                STATE = st.getText().toString();
            }
        });


        if (isEdit) editMode();
        //완료버튼으로 글 저장
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db insert&리사이클러뷰 추가,업데이트
                TITLE = editTitle.getText().toString();
                CONTENT = editContent.getText().toString();
                if (TITLE.equals("")) {
                    Toast.makeText(getApplicationContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                    editTitle.requestFocus();
                } else if (CONTENT.equals("")) {
                    Toast.makeText(getApplicationContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    editContent.requestFocus();
                } else {
                    if (isEdit) {
                        dbHelper.updateDiary(id, TITLE, DATE, CONTENT, STATE);

                        finish();
                    } else {
                        dbHelper.insertDiary(TITLE, DATE, CONTENT, STATE);

                        finish();
                    }
                }
            }
        });

    }

    //날짜 선택
    private void datePick() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear + 1;
                d = dayOfMonth;

                //숫자 포맷 설정
                DecimalFormat myFormatter = new DecimalFormat("##,00");
                String mForm = myFormatter.format(m);
                String dForm = myFormatter.format(d);

                DATE = y + "-" + mForm + "-" + dForm; //db에 저장할 형식으로 저장

                editDate.setText(DATE); //선택된 날짜로 설정
            }
        }, ty, tm, td);
        datePickerDialog.show();
    }

    void editMode() {
        editTitle.setText(tt);
        editDate.setText(dt);
        editContent.setText(ct);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                AlertDialog.Builder cancel = new AlertDialog.Builder(DiaryWrite.this);
                cancel.setMessage("작성을 취소하시겠습니까?");
                cancel.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                cancel.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                cancel.show();

                return true;
        }
        return true;
    }
}
