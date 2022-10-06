package com.kasimkartal866.mybookmedia.common;
import android.content.Context;
import android.content.SharedPreferences;
public class MyPref {
    private static MyPref instance;
    Context context = App.getContext();
    private static final String PREF_USER_NAME = "username";
    private static final String PREF_USER_ID = "userid";
    private SharedPreferences pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    private SharedPreferences.Editor editor = pref.edit();
    public static MyPref getInstance() {
        if (instance == null)
            instance = new MyPref();
        return instance;
    }
    public void saveUserName(String userName) {
        editor.putString(PREF_USER_NAME, userName).apply();
    }
    public String getUserName() {
        return pref.getString(PREF_USER_NAME, "");
    }
    public void saveUserId(int userId) {
        editor.putInt(PREF_USER_ID, userId).apply();
    }
    public int getUserId() {
        return pref.getInt(PREF_USER_ID, -1);
    }
    public void clearUserDate() {
        editor.putString(PREF_USER_NAME, "").apply();
        editor.putInt(PREF_USER_ID, -1).apply();
    }
}