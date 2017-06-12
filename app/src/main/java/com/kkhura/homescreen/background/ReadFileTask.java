package com.kkhura.homescreen.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.VolleyError;
import com.kkhura.R;
import com.kkhura.homescreen.response.ProductRespose;
import com.kkhura.loginscreen.response.FacebookRespose;
import com.kkhura.network.BaseObjectRequest;
import com.kkhura.network.BusProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Kailash Khurana on 3/22/2017.
 */

public class ReadFileTask extends BaseObjectRequest {
    private Context context;
    private String json;
    private long mTransactionId;
    private ProductRespose productRespose;

    public ReadFileTask(Context context, long mTransactionId) {
        this.context = context;
        this.mTransactionId = mTransactionId;
    }

    public JSONObject loadJSONFromAsset() {
        try {
            InputStream is = context.getAssets().open("jsonSample.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void doInBackground() {
        productRespose = new ProductRespose(loadJSONFromAsset(), mTransactionId);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        BusProvider.getInstance().post(null);
    }

    @Override
    public void onResponse(JSONObject response) {
        BusProvider.getInstance().post(productRespose);
    }
}
