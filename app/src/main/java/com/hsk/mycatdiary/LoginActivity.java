package com.hsk.mycatdiary;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    Button login;

    EditText editCatname, editBirth, editHospital;
    ImageButton btnDatepick;

    DataBaseHelper dbHelper;

    SimpleDateFormat dayFormat, monthFormat, yearFormat;
    String nYear, nMonth, nDay;
    int ty, tm, td, y, m, d, CATAGE;

    String BIRTH, CATNAME, HOSPITAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login);
        editCatname = findViewById(R.id.editCatname);
        editBirth = findViewById(R.id.editBirth);
        editHospital = findViewById(R.id.editHospital);
        btnDatepick = findViewById(R.id.btnDatepick);

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

        //날짜 선택
        btnDatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
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

                CATAGE = ty - y;
                BIRTH = y + "-" + mForm + "-" + dForm;
                editBirth.setText(BIRTH); //선택된 날짜로 설정
            }
        }, ty, tm, td);
        datePickerDialog.show();
    }

    public void onLogin(View v) {
        //db insert&리사이클러뷰 추가,업데이트
        CATNAME = editCatname.getText().toString();
        HOSPITAL = editHospital.getText().toString();

        if (CATNAME.equals("")) {
            Toast.makeText(getApplicationContext(), "고양이 이름을 입력하세요", Toast.LENGTH_SHORT).show();
            editCatname.requestFocus();
        } else if (BIRTH.equals("")) {
            Toast.makeText(getApplicationContext(), "고양이의 생일을 입력하세요", Toast.LENGTH_SHORT).show();
            editBirth.requestFocus();
        } else if (HOSPITAL.equals("")) {
            Toast.makeText(getApplicationContext(), "동물병원의 연락처를 입력하세요", Toast.LENGTH_SHORT).show();
            editHospital.requestFocus();
        } else {
            dbHelper.insertCat(CATNAME, CATAGE, BIRTH, HOSPITAL);

            finish();
        }
    }


}
