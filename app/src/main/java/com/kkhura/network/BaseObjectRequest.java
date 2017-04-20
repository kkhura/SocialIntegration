package com.kkhura.network;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.concurrent.Callable;

/**
 * Created by Kailash Khurana on 8/18/2016.
 */
public abstract class BaseObjectRequest implements Callable<Void>, Response.Listener<JSONObject>, Response.ErrorListener {

    @Override
    public Void call() throws Exception {
        doInBackground();
        return null;
    }

    public abstract void doInBackground();

}
