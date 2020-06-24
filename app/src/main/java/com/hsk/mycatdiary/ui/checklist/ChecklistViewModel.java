package com.hsk.mycatdiary.ui.checklist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChecklistViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChecklistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("체크리스트!!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}