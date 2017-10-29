package com.pegasus.pegpay;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.Telephony.Carriers.PASSWORD;

/**
 * Created by Zed on 4/16/2016.
 */
public class AccountManager {
    private static final String ACCOUNT_PREFS = "account_preferences";
    private static final String AVATAR_PATH = "avatar_path";
    private static final String ID_PATH = "id_path";
    private static final String ACCOUNT_TYPE = "account_type";
    private static final String FULLNAME ="setFullName";
    private static final String LAST_NAME = "last_name";
    private static final String FIRST_NAME = "first_name";
    private  static final String NATIONALITY = "nationality";
    private  static final String NIN = "NIN";
    private static final String EMAIL = "email";
    private static final String ID_TYPE = "id_type";
    private static final String ID_NUMBER = "id_number";
    private static final String SEX = "sex";
    private static final String DOB = "dob";
    private static final String LICENCE = "business_licence";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String MERCHANT_ID = "merchant_id";
    private static final String AMOUNT = "amount";
    private  static final String PASSWORD = "password";
    private static final String ACCOUNT_BALANCE = "account_balance";
    private static final String LOGGED_IN = "logged_in";
    private static SharedPreferences preferences;

    private static Pattern pattern;
    private static Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static void setFullName(Context cxt, String name){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FULLNAME, name);
        editor.commit();
    }
    public static String getFullName(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(FULLNAME, "");
    }

    public static void saveAccountType(Context cxt, int type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ACCOUNT_TYPE, type);
        editor.commit();
    }

    public static int getAccountType(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getInt(ACCOUNT_TYPE, 0);
    }

    public static void saveFirstName(Context cxt, String name){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIRST_NAME, name);
        editor.commit();
    }

    public static String getFirstName(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(FIRST_NAME, null).trim();
    }

    public static void saveNationality(Context cxt, String name){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NATIONALITY, name);
        editor.commit();
    }

    public static String getNationality(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(NATIONALITY, null).trim();
    }

    public static void saveIDType(Context cxt, int type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_TYPE, String.valueOf(type));
        editor.commit();
    }

    public static String getIDType(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(ID_TYPE, null);
    }

    public static String getIDNumber(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(ID_NUMBER, null);
    }

    public static void saveIDNumber(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_NUMBER, type);
        editor.commit();
    }

    public static String getDOB(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(DOB, null);
    }

    public static void saveDOB(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(DOB, type);
        editor.commit();
    }

    public static String getSex(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(SEX, null);
    }

    public static void saveSex(Context cxt, int type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SEX, String.valueOf(type));
        editor.commit();
    }

    public static String getLastName(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(LAST_NAME, null).trim();
    }

    public static void saveLastName(Context cxt, String name){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LAST_NAME, name);
        editor.commit();
    }

    public static String getEmail(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(EMAIL, null);
    }

    public static void saveEmail(Context cxt, String email){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public static String getPhone(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(PHONE_NUMBER, null);
    }

    public static void savePhone(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PHONE_NUMBER, type);
        editor.commit();
    }

    public static String getPassword(Context cxt) {
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(PASSWORD, null);
    }
    public static void savePassword(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASSWORD, type);
        editor.commit();
    }

    public static String getMerchantId(Context cxt) {
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(MERCHANT_ID, null);
    }
    public static void saveMerchantId(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(MERCHANT_ID, type);
        editor.commit();
    }

    public static String getAccountBalance(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(ACCOUNT_BALANCE, null);
    }

    public static void saveAccountBalance(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACCOUNT_BALANCE, String.valueOf(type));
        editor.commit();
    }
    public static void saveAmount(Context cxt, String type){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AMOUNT, type);
        editor.commit();
    }

    public static String getAmount(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(AMOUNT, null);
    }




    public static String getAvatarPath(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(AVATAR_PATH, null);
    }

    public static void saveAvatarPath(Context cxt, String path){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AVATAR_PATH, path);
        editor.commit();
    }

    public static String getIDPath(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(ID_PATH, null);
    }

    public static void saveIDPath(Context cxt, String path){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_PATH, path);
        editor.commit();
    }

    public static String getLicencePath(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getString(LICENCE, null);
    }

    public static void saveLicencePath(Context cxt, String path){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LICENCE, path);
        editor.commit();
    }

    public static boolean isLoggedIn(Context cxt){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        return preferences.getBoolean(LOGGED_IN, false);
    }

    public static void saveLoggedIn(Context cxt, boolean loggedin){
        preferences = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOGGED_IN, loggedin);
        editor.commit();
    }

    public static void logout(Context cxt){
        SharedPreferences prefs = cxt.getSharedPreferences(ACCOUNT_PREFS, 0);
        SharedPreferences.Editor e = prefs.edit();
        e.remove(LOGGED_IN);
        e.remove(ID_PATH);
        e.remove(AVATAR_PATH);
        e.remove(FIRST_NAME);
       e.remove(ACCOUNT_BALANCE);
        e.remove(ID_TYPE);
        e.remove(ID_NUMBER);
        e.remove(LAST_NAME);
        e.remove(SEX);
        e.remove(PHONE_NUMBER);
        e.remove(PASSWORD);
        e.remove(EMAIL);
        e.remove(NATIONALITY);
        e.remove(NIN);
        e.remove(DOB);
        e.remove(AMOUNT);
        e.remove(MERCHANT_ID);

        e.commit();
    }


}



