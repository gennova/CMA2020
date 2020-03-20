package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIFlot {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("flotcode.php")
    Call<String> getString();
}
