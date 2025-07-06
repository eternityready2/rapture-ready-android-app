package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static ExecutorService getSingleThreadExecutor() {
        return singleThreadExecutor;
    }

    public static void shutdown() {
        singleThreadExecutor.shutdown();
    }
}
