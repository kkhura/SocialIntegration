package com.gladiator.HomeScreen.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.gladiator.BaseActivity.NavigationDrawerActivity;
import com.gladiator.R;

/**
 * This is the activity that is providing the list of open chats in Fisike
 *
 * @author kkhurana
 */
public class HomeActivity extends NavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void setNavigationDrawerAdapter() {

    }
}