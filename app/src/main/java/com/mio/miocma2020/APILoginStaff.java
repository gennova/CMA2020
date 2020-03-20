package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APILoginStaff {
    String LOGINURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("simpleloginstaff.php")
    Call<String> getUserLogin(

            @Field("username") String uname,
            @Field("password") String password
    );
}
