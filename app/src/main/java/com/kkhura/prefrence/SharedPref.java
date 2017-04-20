package com.kkhura.prefrence;

/**
 * Created by kkhurana on 26/01/17.
 */

public class SharedPref {
    private static boolean sideDrawerShown;

    public static boolean isSideDrawerShown() {
        return sideDrawerShown;
    }

    public static void setSideDrawerShown(boolean sideDrawerShown) {
        SharedPref.sideDrawerShown = sideDrawerShown;
    }
}
