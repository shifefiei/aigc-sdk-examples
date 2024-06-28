package com.aigc.sdk.examples.test.v2;

import com.aigc.sdk.examples.bean.v2.ai.CopywritingRequestV2;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.JacksonUtil;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AI 智能营销文案测试用例
 */
public class MainAiCopywritingTestV2 {

    private static Logger log = LoggerFactory.getLogger(MainAiCopywritingTestV2.class);
    private static String aiCopywritingUrl = CommonConstant.HOST + "/apis/digitalhuman/copywriting/v2/stream/ai";

    /**
     * 流式响应文案
     */
    @Test
    public void testAiCopywriting() {

        OkHttpClient client = OkHttpConfig.getClient();

        CopywritingRequestV2 req = new CopywritingRequestV2();
        req.setBusinessSource(1);
        req.setKeywords("紧急");
        req.setWordCount(400);
        req.setLanguage("Chinese");
        // 智能填 1 和 2
        req.setRequestModel(2);
        doRequest(client, req);

        System.out.println("\n-------------------------------");

        CopywritingRequestV2 req2 = new CopywritingRequestV2();
        req2.setBusinessSource(2);
        req2.setKeywords("Good Night");
        req2.setWordCount(200);
        req2.setLanguage("English");
        req2.setRequestModel(2);
        doRequest(client, req2);

    }

    private void doRequest(OkHttpClient client, CopywritingRequestV2 req) {
        RequestBody body = RequestBody.create(JacksonUtil.toJSONString(req), MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(aiCopywritingUrl).post(body).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            try (InputStream responseStream = response.body().byteStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = responseStream.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            log.error("error 。。。。", e);
        }
    }

}
