package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICollector {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("collectors.php")
    Call<String> getString();
}
