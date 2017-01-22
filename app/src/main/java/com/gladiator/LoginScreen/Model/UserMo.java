package com.gladiator.LoginScreen.Model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kailash Khurana on 8/11/2016.
 */
public class UserMo extends RealmObject {
    @PrimaryKey
    private long userId;
    private String firstName;
    private String lastName;
    private String dob;
    private RealmList<FriendInfo> friendList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public RealmList getFriendList() {
        return friendList;
    }

    public void setFriendList(RealmList friendList) {
        this.friendList = friendList;
    }
}
