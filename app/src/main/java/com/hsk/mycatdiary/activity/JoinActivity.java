package com.hsk.mycatdiary.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hsk.mycatdiary.R;

public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

//오늘날짜
//        Date now = Calendar.getInstance().getTime();
//        dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//        monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
//        yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
//        //오늘날짜를 int로 변형 -> DatePicker에서 기본날짜로 설정
//        nYear = Integer.parseInt(yearFormat.format(now));
//        nMonth = Integer.parseInt(monthFormat.format(now)) - 1;
//        nDay = Integer.parseInt(dayFormat.format(now));

        //날짜 선택
//        btnDatepick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datePick();
//            }
//        });

    }

    //날짜 선택
//    private void datePick() {
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                int y, m, d;
//                y = year;
//                m = monthOfYear + 1;
//                d = dayOfMonth;
//
//                //숫자 포맷 설정
//                DecimalFormat myFormatter = new DecimalFormat("##,00");
//                String mForm = myFormatter.format(m);
//                String dForm = myFormatter.format(d);
//
//                CATAGE = nYear - y;
//                BIRTH = y + "-" + mForm + "-" + dForm;
//                editBirth.setText(BIRTH); //선택된 날짜로 설정
//            }
//        }, nYear, nMonth, nDay);
//        datePickerDialog.show();
//    }

    //db insert&리사이클러뷰 추가,업데이트
//        CATNAME = editCatname.getText().toString();
//        HOSPITAL = editHospital.getText().toString();
//
//        if (CATNAME.equals("")) {
//            Toast.makeText(getApplicationContext(), "고양이 이름을 입력하세요", Toast.LENGTH_SHORT).show();
//            editCatname.requestFocus();
//        } else if (BIRTH.equals("")) {
//            Toast.makeText(getApplicationContext(), "고양이의 생일을 입력하세요", Toast.LENGTH_SHORT).show();
//            editBirth.requestFocus();
//        } else if (HOSPITAL.equals("")) {
//            Toast.makeText(getApplicationContext(), "동물병원의 연락처를 입력하세요", Toast.LENGTH_SHORT).show();
//            editHospital.requestFocus();
//        } else {
//            dbHelper.insertCat(CATNAME, CATAGE, BIRTH, HOSPITAL);
//
//            finish();
//        }
}
