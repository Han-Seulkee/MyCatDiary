package com.hsk.mycatdiary.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.hsk.mycatdiary.DataBaseHelper;
import com.hsk.mycatdiary.R;

public class HomeFragment extends Fragment {
    ImageButton btnShortcut, call, camera, dict;

    TextView tv1;

    DataBaseHelper dbHelper;
    String catName, catBirth, num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        btnShortcut = root.findViewById(R.id.btnCheck);
        call = root.findViewById(R.id.call);
        camera = root.findViewById(R.id.camera);
        dict = root.findViewById(R.id.dict);
        tv1 = root.findViewById(R.id.tv1);

        dbHelper = new DataBaseHelper(getContext());

        getCatInfo();

        /*String[] temp = catBirth.split("-");
        int m=Integer.parseInt(temp[1]);
        int d=Integer.parseInt(temp[2]);

        Calendar today = Calendar.getInstance();
        Calendar d_day = Calendar.getInstance();
        d_day.set(m, d);
        long l_d_day = d_day.getTimeInMillis()/(24*60*60*1000);
        long l_today = today.getTimeInMillis()/(24*60*60*1000);
        long substract = l_today - l_d_day;
        String dday = Long.toString(substract);*/

        tv1.setText(tv1.getText().toString().replace("!!",catName));

        btnShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(R.id.action_navigation_home_to_navigation_checklist);
            }
        });


        call.setOnClickListener(onClick);
        camera.setOnClickListener(onClick);
        dict.setOnClickListener(onClick);

        return root;
    }

    void getCatInfo() {
        Cursor c = dbHelper.selectCat();
        while (c.moveToNext()){
            catName = c.getString(c.getColumnIndex("catName"));
            catBirth = c.getString(c.getColumnIndex("catBirth"));
            num = c.getString(c.getColumnIndex("hos_tel"));
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.call:
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+num));
                    startActivity(intent);
                    break;
                case R.id.camera:
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent2);
                    break;
                case R.id.dict:
                    Uri uri = Uri.parse("https://mypetlife.co.kr/category/catlab/");
                    Intent intent3 = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent3);
                    break;
            }
        }
    };

}
