package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static ExecutorService singleThreadExecutor;

    private static void initExecutor() {
        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public static ExecutorService getSingleThreadExecutor() {
        if (singleThreadExecutor == null) { initExecutor(); }
        return singleThreadExecutor;
    }

    public static void shutdown() {
        singleThreadExecutor.shutdown();
        singleThreadExecutor = null;
    }
}
