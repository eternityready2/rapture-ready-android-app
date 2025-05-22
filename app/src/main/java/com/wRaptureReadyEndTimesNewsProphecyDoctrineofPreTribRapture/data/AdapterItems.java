package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data;

import androidx.annotation.DrawableRes;

public class AdapterItems {
    public String title;
    public String url;
    public @DrawableRes int iconRes;
    public String icon;

    public AdapterItems(String title,  @DrawableRes int iconRes, String url) {
        this.title = title;
        this.url = url;
        this.iconRes = iconRes;
    }

    public AdapterItems(String title, String icon, String url) {
        this.title = title;
        this.url = url;
        this.icon = icon;
    }
}
