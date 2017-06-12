package com.kkhura.homescreen.background;

import android.content.Context;
import android.os.AsyncTask;

import com.kkhura.homescreen.model.ProductModel;
import com.kkhura.homescreen.response.ProductRespose;
import com.kkhura.network.BusProvider;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kailash Khurana on 6/12/2017.
 */

public class GetDataFromDatabase extends AsyncTask<Void, Void, Void> {
    private long mTransactionId;
    private ArrayList<ProductModel> productModelList = new ArrayList<>();

    public GetDataFromDatabase(Context context, long mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    @Override
    protected Void doInBackground(Void... params) {
        RealmResults<ProductModel> all = Realm.getDefaultInstance().where(ProductModel.class).findAll();
        productModelList.addAll(Realm.getDefaultInstance().copyFromRealm(all));
        return null;
    }

    @Override
    protected void onPostExecute(Void mVoid) {
        super.onPostExecute(mVoid);
        BusProvider.getInstance().post(new ProductRespose(productModelList, mTransactionId));
    }
}
