package com.kkhura.adapter;

/**
 * Created by kkhurana on 26/01/17.
 */

public class NavDrawerItem {

    private String title;
    private String navIcon;
    private boolean isCountVisible;
    private String count;
    private int identifier;

    public NavDrawerItem(String title, String navIcon, int identifier){
        this.title = title;
        this.navIcon = navIcon;
        this.identifier = identifier;
    }


    public NavDrawerItem(String title, String navIcon, boolean isCountVisible, String count){
        this.title = title;
        this.navIcon = navIcon;
        this.isCountVisible = isCountVisible;
        this.count = count;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResId() {
        return navIcon;
    }

    public void setResId(String resId) {
        this.navIcon = navIcon;
    }

    public boolean isCountVisible() {
        return isCountVisible;
    }

    public void setCountVisible(boolean isCountVisible) {
        this.isCountVisible = isCountVisible;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
