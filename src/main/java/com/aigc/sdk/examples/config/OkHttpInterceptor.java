package com.aigc.sdk.examples.config;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttpInterceptor implements Interceptor {

    // dafei
    private static final String apiToken = "22AB8D68F48C7C89B4D551822C43C037";


    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newRequestBuilder = request.newBuilder();

        //为Header设置公共参数
        newRequestBuilder.addHeader("apiToken", apiToken);
        newRequestBuilder.addHeader("Language", "zh");

        RequestBody body = request.body();

        //为Post表单请求设置公共参数
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (int i = 0; i < formBody.size(); i++) {
                formBodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }

            newRequestBuilder.post(formBodyBuilder.build());

        } else if (body instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) body;
            MultipartBody.Builder newMultipartBodyBuilder = new MultipartBody.Builder();
            newMultipartBodyBuilder.addFormDataPart("apiToken", apiToken);

            for (int i = 0; i < multipartBody.size(); i++) {
                MultipartBody.Part part = multipartBody.part(i);
                newMultipartBodyBuilder.addPart(part);
            }
            newRequestBuilder.post(multipartBody);
        }
        return chain.proceed(newRequestBuilder.build());

    }
}
