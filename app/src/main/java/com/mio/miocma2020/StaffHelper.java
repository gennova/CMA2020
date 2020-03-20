package com.mio.miocma2020;

import android.content.Context;
import android.content.SharedPreferences;

public class StaffHelper {
    private final String LOGIN = "login";
    private final String NO = "no";
    private final String NAME = "name";
    private final String NIK = "nik";
    private final String POSITION = "position";
    private final String ADDRESS = "address";
    private final String GENDER = "gender";
    private final String PHONE = "phone";
    private final String EMAIL = "email";
    private final String FOTO = "foto";
    private final String ROLE = "role";
    private SharedPreferences app_prefs;
    private Context context;

    public StaffHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared_staff",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginState) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(LOGIN, loginState);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(LOGIN, false);
    }

    public void putStaffNO(String staffNo) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NO, staffNo);
        edit.commit();
    }
    public String getStaffNO() {
        return app_prefs.getString(NO, "");
    }
    public void putStaffName(String name) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, name);
        edit.commit();
    }
    public String getStaffName() {
        return app_prefs.getString(NAME, "");
    }
    public void putStaffNIK(String staffNik) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NIK, staffNik);
        edit.commit();
    }
    public String getStaffNik() {
        return app_prefs.getString(NIK, "");
    }
    public void putStaffPosition(String position) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(POSITION, position);
        edit.commit();
    }
    public String getStaffPosition() {
        return app_prefs.getString(POSITION, "");
    }
    public void putStaffAddress(String address) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ADDRESS, address);
        edit.commit();
    }
    public String getStaffAddress() {
        return app_prefs.getString(ADDRESS, "");
    }
    public void putStaffGender(String gender) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(GENDER, gender);
        edit.commit();
    }
    public String getStaffGender() {
        return app_prefs.getString(GENDER, "");
    }
    public void putStaffPhone(String phone) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PHONE, phone);
        edit.commit();
    }
    public String getStaffPhone() {
        return app_prefs.getString(PHONE, "");
    }
    public void putStaffEmail(String email) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(EMAIL, email);
        edit.commit();
    }
    public String getStaffEmail() {
        return app_prefs.getString(EMAIL, "");
    }
    public void putStaffPhoto(String foto) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(FOTO, foto);
        edit.commit();
    }
    public String getStaffPhoto() {
        return app_prefs.getString(FOTO, "");
    }
    public void putStaffRole(String position) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ROLE, position);
        edit.commit();
    }
    public String getStaffRole() {
        return app_prefs.getString(ROLE, "");
    }
}
