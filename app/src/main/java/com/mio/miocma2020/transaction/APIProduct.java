package com.mio.miocma2020.transaction;

import com.mio.miocma2020.PublicURL;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIProduct {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("product.php")
    Call<String> getString();
}
