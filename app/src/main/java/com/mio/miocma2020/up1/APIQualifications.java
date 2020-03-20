package com.mio.miocma2020.up1;

import com.mio.miocma2020.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIQualifications {
    String REGIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("addqualifications.php")
    Call<String> InsertTransaction(
            @Field("flotcode") String flotcode,
            @Field("weightc1") double w1,
            @Field("weightc2") double w2,
            @Field("weightc3r") double w3r,
            @Field("processingno") int processingno,
            @Field("staffno") String staffno,
            @Field("noterejected") String noterejected
    );
}
