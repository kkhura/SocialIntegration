package com.kkhura.homescreen.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.kkhura.baseactivity.NavigationDrawerActivity;
import com.kkhura.R;
import com.kkhura.homescreen.background.ReadFileTask;

/**
 * This is the activity that is providing the list of open chats in Fisike
 *
 * @author kkhurana
 */
public class HomeActivity extends NavigationDrawerActivity {

    private long transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        transactionId = System.currentTimeMillis();
        new ReadFileTask(this, transactionId).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void setNavigationDrawerAdapter() {

    }
}