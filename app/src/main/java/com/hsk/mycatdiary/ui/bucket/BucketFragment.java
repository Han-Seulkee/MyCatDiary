package com.hsk.mycatdiary.ui.bucket;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

public class BucketFragment extends Fragment {
    RadioGroup rg;
    FloatingActionButton add;

    LinearLayout feed, snack, sand, etc;

    DataBaseHelper dbHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_bucket, container, false);
        rg = root.findViewById(R.id.tag);
        feed = root.findViewById(R.id.feed);
        snack = root.findViewById(R.id.snack);
        sand = root.findViewById(R.id.sand);
        etc = root.findViewById(R.id.etc);
        add = root.findViewById(R.id.btnAdd);

        dbHelper = new DataBaseHelper(getContext());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tag1: //해당 카테고리만 보여주기 visibility
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_addbucket, null);
                builder.setView(view);
                final Button btnA = (Button) view.findViewById(R.id.btnA);
                final EditText edGoods = (EditText) view.findViewById(R.id.edGoods);
                final EditText edTag = (EditText) view.findViewById(R.id.edTag);
                final EditText edLink = (EditText) view.findViewById(R.id.edLink);

                final AlertDialog dialog = builder.create();
                btnA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String goods = edGoods.getText().toString();
                        String tag = edTag.getText().toString();
                        String link = edLink.getText().toString();
                        dbHelper.insertBucket(goods, tag, link);

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        getBucket();

        return root;
    }

    void getBucket() {
        final Cursor c = dbHelper.showBucket();
        while (c.moveToNext()) {
            Button btn = new Button(getContext());
            btn.setText(c.getString(c.getColumnIndex("goods")));
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c.moveToFirst();
                    String l = c.getString(c.getColumnIndex("link"));
                    Uri webpage = Uri.parse(l);
                    if (!l.startsWith("http://") && !l.startsWith("https://")) {
                        webpage = Uri.parse("http://" + l);
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                    getContext().startActivity(intent);
                }
            });
            feed.addView(btn, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
    }
}
