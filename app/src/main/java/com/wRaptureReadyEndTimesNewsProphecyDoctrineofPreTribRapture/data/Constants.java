package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.BuildConfig;

public class Constants {
    public static String BASE_URL =
            BuildConfig.DEBUG ? "http://192.168.107.237:8010" : "https://app.eternityready.com";
    public static String BASE_IMAGE_PATH = BASE_URL;

    public static void resetBaseImagePath() {
        BASE_IMAGE_PATH = BASE_URL;
    }

    public static void setBaseImagePath(String path) {
        BASE_IMAGE_PATH = path;
    }
}
