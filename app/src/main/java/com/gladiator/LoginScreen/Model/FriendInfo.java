package com.gladiator.LoginScreen.Model;

import io.realm.RealmObject;

/**
 * Created by Kailash Khurana on 8/25/2016.
 */
public class FriendInfo extends RealmObject {
    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
