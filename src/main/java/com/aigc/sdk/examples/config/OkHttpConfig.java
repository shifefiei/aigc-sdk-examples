package com.aigc.sdk.examples.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttpConfig {

    private static Integer connectTimeout = 30;

    private static Integer readTimeout = 120;

    private static Integer writeTimeout = 60;

    private static Integer maxIdleConnections = 200;

    private static Long keepAliveDuration = 300L;

    public static OkHttpClient getClient() {

        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(createPool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(new OkHttpInterceptor())
                .build();
    }

    public static ConnectionPool createPool() {
        return new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.SECONDS);
    }

}
