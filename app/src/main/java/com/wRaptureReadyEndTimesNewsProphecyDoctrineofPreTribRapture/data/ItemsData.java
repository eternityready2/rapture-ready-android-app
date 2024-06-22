package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data;


import androidx.annotation.DrawableRes;

public class ItemsData {

    public String title;
    public int icon;
    public String url;

    public ItemsData(String title, @DrawableRes int icon, String url) {
        this.title = title;
        this.icon = icon;
        this.url = url;
    }

}
