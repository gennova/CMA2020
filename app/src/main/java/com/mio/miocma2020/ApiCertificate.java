package com.mio.miocma2020;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCertificate {
    String JSONURL = PublicURL.getAPIURL();

    @GET("cert.php")
    Call<String> getString();
}
