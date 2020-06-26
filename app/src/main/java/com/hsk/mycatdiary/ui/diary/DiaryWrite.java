package com.hsk.mycatdiary.ui.diary;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

import com.hsk.mycatdiary.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryWrite extends AppCompatActivity {
    Toolbar  toolbar;

    Button btnCommit;
    ImageButton btnDatepick;
    RadioGroup state;
    EditText editTitle,editDate,editContent;
    String nYear,nMonth, nDay;
    int ty, tm, td, y, m, d;

    SimpleDateFormat dayFormat,monthFormat, yearFormat;
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
        state = findViewById(R.id.state);

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
        tm = Integer.parseInt(nMonth);
        td = Integer.parseInt(nDay);

        //제목, 날짜는 기본으로 오늘 날짜로 설정
        editTitle.setText(nYear+"."+nMonth+"."+nDay);
        editDate.setText(nYear+"."+nMonth+"."+nDay);

        //날짜 선택
        btnDatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });

        //상태 선택
        state.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton st = findViewById(checkedId);
                Toast.makeText(getApplicationContext(),st.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //내용

        //완료버튼으로 글 저장
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db insert&리사이클러뷰 추가,업데이트
            }
        });

    }

    //날짜 선택
    private void datePick() {
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                y = year;
                m = monthOfYear+1;
                d = dayOfMonth;

                //숫자 포맷 설정
                DecimalFormat myFormatter = new DecimalFormat("##,00");
                String mForm = myFormatter.format(m);
                String dForm = myFormatter.format(d);

                editDate.setText(y+"."+mForm+"."+dForm); //선택된 날짜로 설정
            }
        },ty, tm, td);
        datePickerDialog.show();
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
