package com.mio.miocma2020;
import android.content.Context;
import android.content.SharedPreferences;

public class CollectorHelper {
    private final String INTRO = "intro";
    private final String CID = "cid";
    private final String CNO = "no";
    private final String NAME = "name";
    private final String CNIK = "nik";
    private final String CAddress = "address";
    private final String CCERT = "cert";
    private final String CGender = "gender";
    private final String CDOB = "dob";
    private final String CMPhone = "phone";
    private final String CEMAIL = "email";
    private final String COTP = "otp";
    private final String CREGIONID = "regionid";
    private final String CFOTO = "foto";
    private SharedPreferences app_prefs;
    private Context context;

    public CollectorHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putIsLogin(boolean loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putNO(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CNO, loginorout);
        edit.commit();
    }
    public String getNO() {
        return app_prefs.getString(CNO, "");
    }

    public void putDOB(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CDOB, loginorout);
        edit.commit();
    }
    public String getDOB() {
        return app_prefs.getString(CDOB, "");
    }

    public void putPhone(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CMPhone, loginorout);
        edit.commit();
    }
    public String getCMPhone() {
        return app_prefs.getString(CMPhone, "");
    }

    public void putEmail(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CEMAIL, loginorout);
        edit.commit();
    }
    public String getEmail() {
        return app_prefs.getString(CEMAIL, "");
    }

    public void putNik(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CNIK, loginorout);
        edit.commit();
    }
    public String getNik() {
        return app_prefs.getString(CNIK, "");
    }

    public void putAddress(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CAddress, loginorout);
        edit.commit();
    }
    public String getAddress() {
        return app_prefs.getString(CAddress, "");
    }

    public void putFoto(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CFOTO, loginorout);
        edit.commit();
    }
    public String getFoto() {
        return app_prefs.getString(CFOTO, "");
    }
    public void putGender(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CGender, loginorout);
        edit.commit();
    }
    public String getGender() {
        return app_prefs.getString(CGender, "");
    }

    public void putCERT(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CCERT, loginorout);
        edit.commit();
    }
    public String getCERT() {
        return app_prefs.getString(CCERT, "");
    }

    public void putRegionID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CREGIONID, loginorout);
        edit.commit();
    }
    public String getCREGIONID() {
        return app_prefs.getString(CREGIONID, "");
    }
}
