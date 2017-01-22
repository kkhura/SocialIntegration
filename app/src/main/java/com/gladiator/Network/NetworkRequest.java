package com.gladiator.Network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.ExecutorDelivery;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ResponseDelivery;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.concurrent.Executors;

/**
 * Created by Kailash Khurana on 8/18/2016.
 */
public class NetworkRequest {

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    private static final String   SMALL_CACHE_DIR  = "MphRx-data-cache";
    private static final String   LARGE_CACHE_DIR  = "MphRx-image-cache";
    private static final int      SMALL_CACHE_SIZE = 5 * 1024 * 1024;
    private static final int      LARGE_CACHE_SIZE = 50 * 1024 * 1024;

    private static RequestQueue sGeneralRequestQueue;
    private static RequestQueue sSynchronousResponseRequestQueue;
    private static ImageLoader sImageLoader;
    private static LruBitmapCache sImageCache;

    public static void init(Context context) {

        File cacheDir1 = new File(context.getCacheDir(), SMALL_CACHE_DIR);
        Cache sDiskCache1 = new DiskBasedCache(cacheDir1, SMALL_CACHE_SIZE);
        File cacheDir2 = new File(context.getCacheDir(), LARGE_CACHE_DIR);
        Cache sDiskCache2 = new DiskBasedCache(cacheDir2, LARGE_CACHE_SIZE);

        ResponseDelivery delivery = new ExecutorDelivery(Executors.newFixedThreadPool(4));

        sGeneralRequestQueue = new RequestQueue(sDiskCache1, new BasicNetwork(new HurlStack()), 4, delivery);
        sGeneralRequestQueue.start();

        sSynchronousResponseRequestQueue = Volley.newRequestQueue(context, SMALL_CACHE_SIZE);
        RequestQueue sImageQueue = new RequestQueue(sDiskCache2, new BasicNetwork(new HurlStack()), 4);
        sImageQueue.start();
        sImageCache = new LruBitmapCache();
        sImageLoader = new ImageLoader(sImageQueue, sImageCache);
    }

    public static RequestQueue getGeneralRequestQueue() {
        return sGeneralRequestQueue;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public static RequestQueue getSynchronousResponseRequestQueue() {
        return sSynchronousResponseRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getSynchronousResponseRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     * @param
     */
    public static <T> void addToRequestQueue(JsonObjectRequest req) {
        // set the default tag if tag is empty
        req.setTag(TAG);
        getSynchronousResponseRequestQueue().add(req);
    }


    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public static void cancelPendingRequests(Object tag) {
        getSynchronousResponseRequestQueue().cancelAll(tag);
    }

    public static ImageLoader getImageLoader() {
        return sImageLoader;
    }

    public static LruBitmapCache getImageCache() {
        return sImageCache;
    }
}
