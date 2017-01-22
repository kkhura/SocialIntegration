package com.gladiator.LoginScreen.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.tv.TvInputService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gladiator.LoginScreen.Model.FriendInfo;
import com.gladiator.LoginScreen.Model.UserMo;
import com.gladiator.R;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile","user_friends","user_friends"));

        setContentView(R.layout.activity_main);
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
                    login();
                }
                break;
            case R.id.btnGson:
                new GraphRequest(
                        accessToken,
                        "/"+ accessToken.getUserId(),
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                Log.d(TAG, "onCompleted: "+response.getJSONArray());
                            }
                        }
                ).executeAsync();
                new GraphRequest(
                        accessToken,
                        "/"+ accessToken.getUserId()+"/friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                Log.d(TAG, "onCompleted: "+response.getJSONArray());
                            }
                        }
                ).executeAsync();
                getGson();
                break;
            case R.id.btnGson1:
                getDatabase();
                break;
        }
    }

    @Override
    protected void onResume() {
        getHeader(this);
        super.onResume();
    }

    public String getHeader(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return "V1|" + BuildConfig.VERSION_NAME + "|" + Build.BRAND + "|" + Build.MODEL + "|" + width + "X" + height + "|" + "Android " + "|" + Build.VERSION.RELEASE + "|" + carrierName + "|" + getNetworkClass(context);
    }

    private String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; //not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "Unknown";
            }
        }
        return "Unknown";
    }


    private void getGson() {
        try {
//            MyApplication.getInstance();
            Realm myRealm = Realm.getDefaultInstance();
            myRealm.beginTransaction();

            // Create an object
            UserMo userMo = myRealm.createObject(UserMo.class);
            userMo.setFirstName("Hello");
            userMo.setLastName("World");
            userMo.setDob("a/b/c");
            RealmList list = new RealmList();
            FriendInfo friendInfo = myRealm.createObject(FriendInfo.class);
            friendInfo.setFirstName("1");
            list.add(friendInfo);
            friendInfo.setFirstName("2");
            list.add(friendInfo);
            friendInfo.setFirstName("3");
            list.add(friendInfo);
            userMo.setFriendList(list);
            long nextID = ((long) (myRealm.where(UserMo.class).max("userId")) + 1);
            userMo.setUserId(nextID);

            myRealm.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
        }
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        if (response != null && response.getJSONObject() != null) {
                            Log.d(TAG, "onCompleted: " + response.getJSONObject().toString());
                        }
                    }
                }
        ).executeAsync();

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        if (object != null) {
                            Log.d(TAG, "onCompleted: " + object.toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link'");
        request.setParameters(parameters);
        request.executeAsync();
    }


    String get_id, get_name, get_gender, get_email, get_birthday, get_locale, get_location;

    private void login() {
//        loginButton.setReadPermissions("public_profile", "email", "user_friends", "user_birthday");
//        loginButton.setReadPermissions("user_location");
//        loginButton.setReadPermissions("user_photos");
//        loginButton.setReadPermissions("user_relationships");
//        loginButton.setReadPermissions("user_education_history");
//        loginButton.setReadPermissions("user_about_me");
//        loginButton.setReadPermissions("user_work_history");

//        loginButton.setReadPermissions("user_friends","user_friends","email","user_friends");
//                loginButton.setReadPermissions("email");

        // Other app specific specialization

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Set<String> permissions = AccessToken.getCurrentAccessToken().getPermissions();
                Set<String> declinedPermissions = AccessToken.getCurrentAccessToken().getDeclinedPermissions();
                Log.d("TAG", "onSuccess: ");
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("TAG", "onCancel: ");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("TAG", "onError: ");
            }
        });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    public void getDatabase() {
        try {
//            MyApplication.getInstance();
            Realm myRealm = Realm.getDefaultInstance();
            RealmResults<UserMo> results1 =
                    myRealm.where(UserMo.class).findAll();
            for (UserMo c : results1) {
                Log.d("results1", c.getFirstName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}