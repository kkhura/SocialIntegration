package com.gladiator.Network;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kailash Khurana on 1/24/2017.
 */
public class BaseResponse {

    private long mTransactionId;
    private String mResponse;
    protected String mMessage;
    protected boolean mIsSuccessful;

    public BaseResponse(JSONArray response, long transactionId) {
        mTransactionId = transactionId;
        mIsSuccessful = true;
        mResponse = response.toString();
        mMessage = "";
    }

    public BaseResponse(JSONObject response, long transactionId) {
        mTransactionId = transactionId;
        mIsSuccessful = true;
        mResponse = response.toString();
        mMessage = "";
    }

    public BaseResponse(Exception exception, long transactionId) {
        mTransactionId = transactionId;
        mIsSuccessful = false;
        if (exception instanceof VolleyError) {
            if (((VolleyError) exception).networkResponse != null) {
                NetworkResponse response = ((VolleyError) exception).networkResponse;
                int statusCode = response.statusCode;
            } else if (exception instanceof NetworkError) {
            } else {
            }
        } else {
        }
    }

    public long getTransactionId() {
        return mTransactionId;
    }

    public String getResponse() {
        return mResponse;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isSuccessful() {
        return mIsSuccessful;
    }

}
