package com.hsk.mycatdiary.ui.bucket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hsk.mycatdiary.R;

public class BucketFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bucket, container, false);

        final TextView textView = root.findViewById(R.id.text_bucket);
        return root;
    }
}
