package com.krinotech.data.contract;

import com.krinotech.data.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RedditAuthService {

    @POST(ApiContract.AUTH_ENDPOINT)
    Call<AuthResponse> redditAuthenthicate(@Header(ApiContract.HEADER_AUTHORIZATION) String credentials,
                                           @Header(ApiContract.HEADER_CONTENT_TYPE) String contentType,
                                           @Body String body);

}
