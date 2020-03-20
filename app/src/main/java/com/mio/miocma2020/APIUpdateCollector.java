package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIUpdateCollector {
    String REGIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("updatecollector.php")
    Call<String> getUserRegi(
            @Field("code") String code,
            @Field("name") String name,
            @Field("cert") int cert,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("region") int region,
            @Field("nik") String nik,
            @Field("address") String address
    );
}
