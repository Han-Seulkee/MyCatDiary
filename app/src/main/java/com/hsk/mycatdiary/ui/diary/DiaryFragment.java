package com.hsk.mycatdiary.ui.diary;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsk.mycatdiary.R;

public class DiaryFragment extends Fragment {
    RecyclerView rv;
    DiaryRecyclerAdapter adapter;
    LinearLayoutManager layoutManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_diary, container, false);

        rv = root.findViewById(R.id.rvDiarylist);
        adapter = new DiaryRecyclerAdapter();
        layoutManager = new LinearLayoutManager(this.getContext());

        rv.setAdapter(adapter);
        rv.setLayoutManager(layoutManager);


        return root;
    }

    public class MyDBHelper extends SQLiteOpenHelper {


    }
}
