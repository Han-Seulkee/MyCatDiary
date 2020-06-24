package com.hsk.mycatdiary.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hsk.mycatdiary.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    int gallery_thumbnail[] = {android.R.drawable.btn_plus, android.R.drawable.btn_radio};

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.dodo1);
        imgs.add(R.drawable.dodo2);
        imgs.add(R.drawable.dodo3);
        imgs.add(R.drawable.dodo4);

        //뷰페이저로 한화면에 여러개 안되나?
        //*갤러리 썸네일을 보여주고싶음-recycler view로 바꾸기? 파일dir에서 사진썸네일 3개불러와서 셋팅?
        ViewPager viewPager = root.findViewById(R.id.viewPager);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;//dp
        int margin = (int) (dpValue * d);
        viewPager.setPadding(margin, 0, margin, 0);
        viewPager.setPageMargin(margin/2);

        for (int i = 0; i < imgs.size(); i++) {
            ThumbnailFragment tf = new ThumbnailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", imgs.get(i));
            tf.setArguments(bundle);
            fragmentAdapter.addItem(tf);
        }
        fragmentAdapter.notifyDataSetChanged();

        //카메라어플 실행하기 *찍은사진은 파일생성후 그곳에 저장

        //뷰모델
        /*homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);*/

        //final TextView textView = root.findViewById(R.id.text_home);

        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    class FragmentAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments = new ArrayList<>();

        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }

    }

}
