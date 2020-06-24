package com.hsk.mycatdiary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hsk.mycatdiary.R;

public class ThumbnailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thumbnail,container,false);

        ImageView imageView = view.findViewById(R.id.imageView);

        if(getArguments()!=null) {
            Bundle args = getArguments();
            imageView.setImageResource(args.getInt("imgRes"));
            int k;
        }

        return view;
    }
}
