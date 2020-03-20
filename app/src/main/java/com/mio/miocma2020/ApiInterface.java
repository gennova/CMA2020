package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    String JSONURL = PublicURL.getAPIURL();

    @GET("employee.php")
    Call<String> getString();

}
