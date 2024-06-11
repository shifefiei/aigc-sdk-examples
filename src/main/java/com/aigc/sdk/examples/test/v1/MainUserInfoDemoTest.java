package com.aigc.sdk.examples.test.v1;

import com.aigc.sdk.examples.bean.v1.user.UserInfoRequest;
import com.aigc.sdk.examples.bean.v1.user.UserInfoResponse;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.JacksonUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户信息相关测试
 */
public class MainUserInfoDemoTest {

    private static Logger log = LoggerFactory.getLogger(MainUserInfoDemoTest.class);
    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());
    private static String userInfoUrl = CommonConstant.HOST + "/apis/digitalhuman/user/v1/getInfo";


    // 查询用户账号到期时间以及账号余额
    @Test
    public void getUserInfo() {
        String email = "1021376538@qq.com";
        UserInfoResponse response = doGetUserInfo(email);
        System.out.println(response);
    }

    private static UserInfoResponse doGetUserInfo(String userEmail) {
        UserInfoRequest request = new UserInfoRequest();
        request.setUserEmail(userEmail);

        String userInfoResult = okHttpUtil.doPostJson(userInfoUrl, JacksonUtil.toJSONString(request));
        JSONObject jsonObject = JSONObject.parseObject(userInfoResult);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", userInfoResult);
            return null;
        }

        log.info("result : {}", userInfoResult);

        UserInfoResponse response = JSONObject.parseObject(jsonObject.getString("data"), UserInfoResponse.class);
        return response;
    }

}
