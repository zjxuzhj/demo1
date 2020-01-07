package com.zhj.demo1.api;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;


/**
 * Created by HongJay on 2017/12/27.
 */

public class BaseInterceptor implements Interceptor {
    public final static String X_BMOB_APPLICATION_ID = "92b20b26c6cd96638faeea2ebc309b83";
    public final static String X_BMOB_REST_API_KEY = "cd35bb1567753c81b61d6e9d4a285502";
    public static String TAG = "BaseInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader("content-type", "application/json")
                .addHeader("X-Bmob-Application-Id", X_BMOB_APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", X_BMOB_REST_API_KEY)
                .build();
        return chain.proceed(newRequest);
    }
}
