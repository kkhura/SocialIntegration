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
import com.gladiator.LoginScreen.Model.UserMo;
import com.gladiator.LoginScreen.Response.FacebookRespose;
import com.gladiator.R;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private long mTransactionId;
    private FacebookRespose loginFacebookResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_friends", "user_friends"));

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginButton = (LoginButton) findViewById(R.id.loginBtn);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                Log.d(TAG, "onCurrentAccessTokenChanged: " + oldAccessToken);
                Log.d(TAG, "onCurrentAccessTokenChanged: " + currentAccessToken);
                accessToken = currentAccessToken;
                if (accessToken != null) {
                    getProfileInformation();
                }
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken == null) {
                    loginFaceBook();
                }
                break;
            case R.id.btnGson:
                GraphRequest graphRequest1 = new GraphRequest(
                        accessToken,
                        "/" + accessToken.getUserId() + "/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Log.d(TAG, "onCompleted: " + response.getJSONArray());
                            }
                        }
                );
                Bundle parameters1 = new Bundle();
                parameters1.putString("fields", "id,name,list_type,owner");
                graphRequest1.setParameters(parameters1);
                graphRequest1.executeAsync();
                break;
        }
    }

    private void loginFaceBook() {
        loginButton.setReadPermissions("user_friends", "email", "public_profile");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        getProfileInformation();
                    }
                });
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, LoginActivity.this.getResources().getString(R.string.login_canceled), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProfileInformation() {
        GraphRequest graphRequest = new GraphRequest(
                accessToken,
                "/" + accessToken.getUserId(),
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response == null || response.getJSONObject() == null) {
                            return;
                        }
                        loginFacebookResponse = new FacebookRespose(response.getJSONObject(), mTransactionId);
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,cover,relationship_status,picture,first_name,last_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public long getTransactionId() {
        mTransactionId = System.currentTimeMillis();
        return mTransactionId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public UserMo getDatabase() {
        try {
//            MyApplication.getInstance();
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
