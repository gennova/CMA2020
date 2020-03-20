package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRegion {
    String JSONURL = PublicURL.getAPIURL();;

    @GET("region.php")
    Call<String> getString();
}
