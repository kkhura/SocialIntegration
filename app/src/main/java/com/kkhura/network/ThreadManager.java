package com.kkhura.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kailash Khurana on 8/18/2016.
 */
public class ThreadManager {

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static ExecutorService defaultExecutorService = new ThreadPoolExecutor(
            NUMBER_OF_CORES, NUMBER_OF_CORES, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    private ThreadManager() {
    }

    public static ExecutorService getDefaultExecutorService() {
        return defaultExecutorService;
    }
}
