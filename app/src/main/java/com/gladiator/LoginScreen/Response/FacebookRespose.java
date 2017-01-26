package com.gladiator.LoginScreen.Response;

import com.gladiator.LoginScreen.Model.UserMo;
import com.gladiator.Network.BaseResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

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

        final RealmResults<UserMo> puppies = myRealm.where(UserMo.class).distinct("email");
        puppies.size();

        for (int i = 0; i < puppies.size(); i++) {
            puppies.deleteFromRealm(i);
        }

        // Create an object
        UserMo userMo = myRealm.createObject(UserMo.class);
        userMo.setUserId(response.optString("id"));
        userMo.setName(response.optString("name"));
        userMo.setFirstName(response.optString("first_name"));
        userMo.setLastName("last_name");
        userMo.setEmail(response.optString("email"));
        userMo.setGender(response.optString("gender"));
        userMo.setDob("birthday");
        JSONObject pictureObject = response.optJSONObject("picture");
        if (pictureObject != null) {
            JSONObject pictureDataObject = pictureObject.optJSONObject("data");
            if (pictureDataObject != null) {
                userMo.setPicture(pictureDataObject.optString("url"));
            }
        }
        JSONObject coverObject = response.optJSONObject("cover");
        if (coverObject != null) {
            userMo.setCover(coverObject.optString("source"));
        }
        myRealm.commitTransaction();

    }
}
