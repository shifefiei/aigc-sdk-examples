package com.aigc.sdk.examples.test.v2;

import com.aigc.sdk.examples.bean.v2.avatar.DigitalHumanAvatarListRequestV2;
import com.aigc.sdk.examples.bean.v2.avatar.DigitalHumanAvatarListResponseV2;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.test.v1.MainAvatarDemoTest;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MainAvatarTestV2 {

    private static Logger log = LoggerFactory.getLogger(MainAvatarDemoTest.class);

    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    private static String avatarListUrl = CommonConstant.HOST + "/apis/digitalhuman/avatar/v2/list";


    @Test
    public void testAvatarList() {
        DigitalHumanAvatarListRequestV2 req = new DigitalHumanAvatarListRequestV2();
        req.setSupportTypeId(100);
        req.setCloneDigitalHuman(1);
        req.setPageSize(1000);

        // req.setAreaTypeId(140);
        //req.setTagIds(Lists.newArrayList(211));

        List<DigitalHumanAvatarListResponseV2> avatarList = getAvatarList(req);
        System.out.println("==========================");
        System.out.println(avatarList);
    }

    public List<DigitalHumanAvatarListResponseV2> getAvatarList(DigitalHumanAvatarListRequestV2 request) {

        String avatarResultList = okHttpUtil.doPostJson(avatarListUrl, JSONObject.toJSONString(request));
        JSONObject jsonObject = JSONObject.parseObject(avatarResultList);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", avatarResultList);
            return null;
        }

        String pageData = jsonObject.getString("data");

        System.out.println("数字人形象列表：" + pageData);

        JSONObject pageJsonObject = JSONObject.parseObject(pageData);

        List<DigitalHumanAvatarListResponseV2> avatarList = JSONObject.parseArray(pageJsonObject.getString("data"), DigitalHumanAvatarListResponseV2.class);

        return avatarList;
    }

}
