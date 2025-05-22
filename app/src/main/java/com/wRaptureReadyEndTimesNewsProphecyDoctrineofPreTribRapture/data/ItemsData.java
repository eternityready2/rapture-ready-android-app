package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data;


import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ItemsData {

    private static final MutableLiveData<List<Pair<String, List<ButtonItem>>>> items =
            new MutableLiveData<>();

    public static LiveData<List<Pair<String, List<ButtonItem>>>> getItems() {
        return items;
    }

    public static void setItems(List<Pair<String, List<ButtonItem>>> list) {
        items.postValue(list);
    }

}
