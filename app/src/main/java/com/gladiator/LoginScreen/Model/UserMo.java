package com.gladiator.LoginScreen.Model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Kailash Khurana on 8/11/2016.
 */
public class UserMo extends RealmObject {

    @PrimaryKey
    private String email;
    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String name;
    private String dob;
    private String mobileNumber;
    private String gender;
    private String picture;
    private String cover;
    private boolean salutation;
    private RealmList<FriendInfo> friendList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public RealmList<FriendInfo> getFriendList() {
        return friendList;
    }

    public void setFriendList(RealmList<FriendInfo> friendList) {
        this.friendList = friendList;
    }

    public boolean isSalutation() {
        return salutation;
    }

    public void setSalutation(boolean salutation) {
        this.salutation = salutation;
    }
}
