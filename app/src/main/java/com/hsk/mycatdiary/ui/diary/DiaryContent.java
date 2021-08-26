package com.hsk.mycatdiary.ui.diary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

public class DiaryContent extends AppCompatActivity {
    TextView title, date, state, content;

    ImageButton btnEdit, btnDelete;

    DataBaseHelper dbHelper;

    DiaryRecyclerAdapter adapter;
    long id;
    String tt, dt, st, ct;

    @Override
    protected void onCreate(@Nullable android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diarycontent);

        title = findViewById(R.id.diaryTitle);
        date = findViewById(R.id.diaryDate);
        state = findViewById(R.id.diaryState);
        content = findViewById(R.id.diaryContent);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        adapter = new DiaryRecyclerAdapter();
        dbHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);

        showContent(id);

        btnEdit.setOnClickListener(onClick);
        btnDelete.setOnClickListener(onClick);
    }

    void showContent(long id) {
        Cursor c = dbHelper.showDiary(id);
        while (c.moveToNext()) {
            tt = c.getString(c.getColumnIndex("title"));
            dt = c.getString(c.getColumnIndex("date"));
            st = c.getString(c.getColumnIndex("catState"));
            ct = c.getString(c.getColumnIndex("content"));
            title.setText(tt);
            date.setText(dt);
            state.setText(st);
            content.setText(ct);
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnEdit:
                    Intent intent = new Intent(DiaryContent.this, DiaryWrite.class);
                    intent.putExtra("edit", true);
                    intent.putExtra("title",tt);
                    intent.putExtra("date",dt);
                    intent.putExtra("content",ct);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    break;
                case R.id.btnDelete:
                    AlertDialog.Builder delete = new AlertDialog.Builder(DiaryContent.this);
                    delete.setMessage("삭제하시겠습니까?");
                    delete.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_SHORT).show();
                            dbHelper.deleteDiary(id);
                            adapter.notifyDataSetChanged();
                            finish();
                        }
                    });
                    delete.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    delete.show();

                    break;
            }
        }
    };
}
