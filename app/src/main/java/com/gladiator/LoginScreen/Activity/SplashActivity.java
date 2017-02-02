package com.gladiator.LoginScreen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gladiator.HomeScreen.Activity.HomeActivity;
import com.gladiator.LoginScreen.Model.UserMo;
import com.gladiator.LoginScreen.Response.FacebookRespose;
import com.gladiator.R;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getDatabase().isSalutation()) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
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
