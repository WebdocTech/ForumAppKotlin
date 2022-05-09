package com.webdoc.Essentials;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_IS_Register = "isRegister";
    private static final String KEY_IS_FirstTime = "isFirstTime";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_Pin = "userPin";
    private static final String KEY_ApplicationUserId = "applicationUserId";

    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PHONE = "userPhone";
    private static final String KEY_USER_City = "userCity";
    private static final String KEY_USER_Address = "userAddress";

    public static Preferences mPref;
    public static Preferences getInstance(Context mContext){
        if (mPref == null){
            mPref = new Preferences(mContext);
        }
        return mPref;
    }


    public Preferences(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("apna_darzi", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void clearSharedPreferences() {
        editor.clear();
        editor.commit();
    }

    public Boolean getKeyIsLogin() {
        return prefs.getBoolean(KEY_IS_LOGIN, false);
    }

    public void setKeyIsLogin(Boolean keyIsLogin) {
        editor.putBoolean(KEY_IS_LOGIN, keyIsLogin);
        editor.commit();
    }

    public Boolean getKeyIsRegister(){
        return prefs.getBoolean(KEY_IS_Register, false);
    }

    public void setKeyIsRegister(Boolean keyIsRegister){
        editor.putBoolean(KEY_IS_Register, keyIsRegister);
        editor.commit();
    }

    public Boolean getKeyIsFirstTime(){
        return prefs.getBoolean(KEY_IS_FirstTime, true);
    }

    public void setKeyIsFirstTime(Boolean keyIsFirstTime){
        editor.putBoolean(KEY_IS_FirstTime, keyIsFirstTime);
        editor.commit();
    }

    public String getKeyUserName() {
        return prefs.getString(KEY_USER_NAME, "");
    }

    public void setKeyUserName(String FName) {
        editor.putString(KEY_USER_NAME, FName);
        editor.commit();
    }

    public String getKeyUserPhone() {
        return prefs.getString(KEY_USER_PHONE, "");
    }

    public void setKeyUserPhone(String keyUserPhone) {
        editor.putString(KEY_USER_PHONE, keyUserPhone);
        editor.commit();
    }

    public String getKeyUserEmail() {
        return prefs.getString(KEY_USER_EMAIL, "");
    }

    public void setKeyUserEmail(String keyEmail) {
        editor.putString(KEY_USER_EMAIL, keyEmail);
        editor.commit();
    }

    public String getKEY_USER_City() {
        return prefs.getString(KEY_USER_City, "");
    }

    public void setKEY_USER_City(String city) {
        editor.putString(KEY_USER_City, city);
        editor.commit();
    }

    public String getKEY_USER_Pin() {
        return prefs.getString(KEY_USER_Pin, "");
    }

    public void setKEY_USER_Pin(String pin) {
        editor.putString(KEY_USER_Pin, pin);
        editor.commit();
    }

    public String getKEY_ApplicationUserId() {
        return prefs.getString(KEY_ApplicationUserId, "");
    }

    public void setKEY_ApplicationUserId(String appUserId) {
        editor.putString(KEY_ApplicationUserId, appUserId);
        editor.commit();
    }

    public String getKEY_USER_Address() {
        return prefs.getString(KEY_USER_Address, "");
    }

    public void setKEY_USER_Address(String address) {
        editor.putString(KEY_USER_Address, address);
        editor.commit();
    }


}
