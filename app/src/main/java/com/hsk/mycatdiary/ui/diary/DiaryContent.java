package com.hsk.mycatdiary.ui.diary;

import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

public class DiaryContent extends AppCompatActivity {
    TextView title, date, state, content;

    DataBaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarycontent);

        title = findViewById(R.id.diaryTitle);
        date = findViewById(R.id.diaryDate);
        state = findViewById(R.id.diaryState);
        content = findViewById(R.id.diaryContent);

        dbHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id",0);

        showContent(id);
    }

    void showContent(long id) {
        Cursor c = dbHelper.showDiary(id);
        while (c.moveToNext()){
            title.setText(c.getString(c.getColumnIndex("title")));
            date.setText(c.getString(c.getColumnIndex("date")));
            state.setText(c.getString(c.getColumnIndex("catState")));
            content.setText(c.getString(c.getColumnIndex("content")));
        }
    }
}
