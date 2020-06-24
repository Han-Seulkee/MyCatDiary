package com.hsk.mycatdiary.ui.bucket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BucketViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BucketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("장바구니!!!!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}