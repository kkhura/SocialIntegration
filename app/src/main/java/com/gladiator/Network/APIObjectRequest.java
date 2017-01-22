package com.gladiator.Network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gladiator.Constant.NetworkConstants;

import org.json.JSONObject;

/**
 * Created by Kailash Khurana on 8/18/2016.
 */
public class APIObjectRequest extends JsonObjectRequest {

    public APIObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(NetworkConstants.TIMEOUT, NetworkConstants.RETRY, NetworkConstants.BACKOFF));
    }

}
