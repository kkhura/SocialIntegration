package com.kkhura.homescreen.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.kkhura.homescreen.response.ProductRespose;
import com.kkhura.loginscreen.response.FacebookRespose;
import com.kkhura.network.BusProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Kailash Khurana on 3/22/2017.
 */

public class ReadFileTask extends AsyncTask<Void, Void, Void> {
    private ProgressDialog progressDialog;
    private Context context;
    private String json;
    private long mTransactionId;
    private ProductRespose productRespose;

    public ReadFileTask(Context context, long mTransactionId) {
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.mTransactionId = mTransactionId;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        productRespose = new ProductRespose(loadJSONFromAsset(), mTransactionId);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        BusProvider.getInstance().post(productRespose);
        super.onPostExecute(aVoid);
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
}
