package com.krinotech.trackkit.application;

import androidx.annotation.NonNull;

import com.krinotech.data.contract.RedditApi;
import com.krinotech.data.contract.RedditAuthService;
import com.krinotech.data.contract.TrackkitDao;
import com.krinotech.data.room.TrackkitDatabase;
import com.krinotech.trackkit.BaseApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.krinotech.data.contract.ApiContract.BASE_OAUTH2_URL;
import static com.krinotech.data.contract.ApiContract.BASE_REDDIT_URL;
import static com.krinotech.data.contract.ApiContract.HEADER_AUTHORIZATION;
import static com.krinotech.data.contract.ApiContract.REQUEST_HEADER_NAME;
import static com.krinotech.data.contract.ApiContract.REQUEST_HEADER_TYPE;

public class TrackkitApplicationMock extends BaseApplication {
    @Override
    protected @NotNull TrackkitDao getTrackkitDao() {
        TrackkitDatabase trackkitDatabase = TrackkitDatabase.getRoomDatabase(this);
        return trackkitDatabase.trackkitDao();
    }

    @Override
    protected @NotNull Executor getExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @NonNull
    @Override
    protected RedditApi getRedditApi() {
        return new Retrofit.Builder()
                .client(okHttpClientInterceptor())
                .baseUrl(BASE_OAUTH2_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditApi.class);
    }

    @NonNull
    @Override
    protected RedditAuthService getRedditAuthService() {
        return new Retrofit
                .Builder()
                .baseUrl(BASE_REDDIT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RedditAuthService.class);
    }



    public OkHttpClient okHttpClientInterceptor() {
        Interceptor interceptor = chain -> {
            Request.Builder requestBuilder = chain.request()
                    .newBuilder()
                    .header(REQUEST_HEADER_NAME, REQUEST_HEADER_TYPE);
            String token = trackkitPreferences.getAccessToken();
            if(token.isEmpty()) {
                throw new IOException("Token is empty");
            }
            requestBuilder.addHeader(HEADER_AUTHORIZATION, token);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
        return new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();
    }
}
