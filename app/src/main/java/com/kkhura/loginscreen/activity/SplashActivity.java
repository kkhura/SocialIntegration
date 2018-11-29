package com.kkhura.loginscreen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kkhura.homescreen.activity.HomeActivity;
import com.kkhura.loginscreen.model.UserMo;
import com.kkhura.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getDatabase() == null || getDatabase().isSalutation()) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public UserMo getDatabase() {
        try {
            Realm myRealm = Realm.getDefaultInstance();
            RealmResults<UserMo> results1 =
                    myRealm.where(UserMo.class).findAll();
            for (UserMo c : results1) {
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
