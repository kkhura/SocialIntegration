package com.kkhura.homescreen.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.kkhura.baseactivity.NavigationDrawerActivity;
import com.kkhura.R;
import com.kkhura.homescreen.background.GetDataFromDatabase;
import com.kkhura.homescreen.background.ReadFileTask;
import com.kkhura.homescreen.model.ProductModel;
import com.kkhura.homescreen.response.ProductRespose;
import com.kkhura.network.BusProvider;
import com.kkhura.network.ThreadManager;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * This is the activity that is providing the list of open chats in Fisike
 *
 * @author kkhurana
 */
public class HomeActivity extends NavigationDrawerActivity {

    private long transactionId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BusProvider.getInstance().register(this);

        getDataFromDatabase();
    }

    private void getDataFromDatabase() {
        transactionId = System.currentTimeMillis();
        new GetDataFromDatabase(this, transactionId).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }


    private void getDataFromServer() {
        showProgressDialog();

        transactionId = System.currentTimeMillis();
        ThreadManager.getDefaultExecutorService().submit(new ReadFileTask(this, transactionId));
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }


    @Subscribe
    public void onProductResponse(final ProductRespose productRespose) {
        if (transactionId != productRespose.getTransactionId()) {
            return;
        }
        if (productRespose.getProductModelArrayList() == null || productRespose.getProductModelArrayList().size() < 1) {
            getDataFromServer();
            return;
        }
        dismissProgressDialog();
        ArrayList<ProductModel> productModelArrayList = productRespose.getProductModelArrayList();
        for (int i = 0; i < productModelArrayList.size(); i++) {
            System.out.println(productModelArrayList.get(i).getItemName());
        }
    }

    @Override
    public void setNavigationDrawerAdapter() {

    }
}