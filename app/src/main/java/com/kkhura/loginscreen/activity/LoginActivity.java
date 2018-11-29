package com.kkhura.loginscreen.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kkhura.R;
import com.kkhura.homescreen.activity.HomeActivity;
import com.kkhura.loginscreen.model.UserMo;
import com.kkhura.loginscreen.response.FacebookRespose;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private Button btnGson;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile", "user_friends", "user_friends"));

        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.loginBtn);

        btnGson = (Button) findViewById(R.id.btnGson);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.kkhura.emart",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
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
                        getProfileInformation();
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

        /*GraphRequest graphRequest = new GraphRequest(
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
                        if (loginFacebookResponse.getUserMo() != null && loginFacebookResponse.getUserMo().isSalutation()) {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }

                    }
                }
        );*/
        /*Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,cover,relationship_status,picture,first_name,last_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();*/

        handleFaceBookLogin();

    }

    private void handleFaceBookLogin() {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
