package com.hsk.mycatdiary.ui.bucket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hsk.mycatdiary.R;

public class BucketFragment extends Fragment {

    private BucketViewModel bucketViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bucketViewModel =
                ViewModelProviders.of(this).get(BucketViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bucket, container, false);

        final TextView textView = root.findViewById(R.id.text_bucket);
        bucketViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
