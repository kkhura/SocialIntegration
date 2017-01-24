package com.gladiator.LoginScreen.Response;

import com.gladiator.LoginScreen.Model.FriendInfo;
import com.gladiator.LoginScreen.Model.UserMo;
import com.gladiator.Network.BaseResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Kailash Khurana on 1/24/2017.
 */

public class FacebookRespose extends BaseResponse {
    private UserMo userMo;

    public FacebookRespose(JSONObject response, long transactionId) {
        super(response, transactionId);
        parseResponse(response);
    }

    public FacebookRespose(Exception exception, long transactionId) {
        super(exception, transactionId);
    }

    private void parseResponse(JSONObject response) {
        Realm myRealm = Realm.getDefaultInstance();
        myRealm.beginTransaction();

        // Create an object
        UserMo userMo = myRealm.createObject(UserMo.class);

        myRealm.commitTransaction();

    }
}
