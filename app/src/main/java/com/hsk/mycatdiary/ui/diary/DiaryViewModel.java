package com.hsk.mycatdiary.ui.diary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiaryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiaryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("일지!!!!!!!!!!!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}