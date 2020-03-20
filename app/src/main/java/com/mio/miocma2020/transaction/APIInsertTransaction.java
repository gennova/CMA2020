package com.mio.miocma2020.transaction;

import com.mio.miocma2020.PublicURL;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInsertTransaction {
    String REGIURL = PublicURL.getAPIURL();
    @FormUrlEncoded
    @POST("addtrans.php")
    Call<String> InsertTransaction(
            @Field("cnoid") String cnoid,
            @Field("fnoid") String fnoid,
            @Field("prodid") String prodid,
            @Field("prodweight") double weight,
            @Field("rdate") String rdate,
            @Field("flot") String flot,
            @Field("qrcode") String qrcode
    );
}
