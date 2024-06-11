package com.aigc.sdk.examples.test.v2;

import com.aigc.sdk.examples.bean.v1.avatar.DigitalHumanAvatarListResponse;
import com.aigc.sdk.examples.bean.v1.video.DigitalHumanVideoAddResponse;
import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
import com.aigc.sdk.examples.bean.v2.DigitalHumanParamV2;
import com.aigc.sdk.examples.bean.v2.DigitalHumanVideoAddRequestV2;
import com.aigc.sdk.examples.bean.v2.background.BackgroundElementRequestV2;
import com.aigc.sdk.examples.bean.v2.background.BackgroundElementResponseV2;
import com.aigc.sdk.examples.bean.v2.background.DigitalHumanBackgroundParamV2;
import com.aigc.sdk.examples.bean.v2.voice.*;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.common.MaskTypeEnum;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.test.v1.MainAvatarDemoTest;
import com.aigc.sdk.examples.util.JacksonUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * 视频背景元素合成视频相关测试
 */
public class MainVideoBackgroundTestV2 {


    private static Logger log = LoggerFactory.getLogger(MainVideoBackgroundTestV2.class);

    private MainAvatarDemoTest mainAvatarDemo = new MainAvatarDemoTest();

    private static final String ASPECT_RATIO = "9:16";
    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    private static String submitTaskUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/add";
    private static String getBackgroundUrl = CommonConstant.HOST + "/apis/digitalhuman/background/v2/backgroundList";

    private MainVoiceTestV2 mainVoiceTestV2 = new MainVoiceTestV2();

    /**
     * 获取视频背景列表
     */
    @Test
    public void testGetBackgroundElement() {
        getBackgroundElementResponseV2();
    }

    /**
     * 提交视频背景任务
     */
    @Test
    public void test9To16PhotoVideo() {

        // 语音列表
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = mainVoiceTestV2.getVoiceListV2(requestV2, 21);

        // 语音试听
        DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceTestV2.tryToListenAudioFileV2(voiceListV2.get(0), audition);

        // 数字人形象
        //DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 3650);
        //DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 3567);
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 402);

        // 获取视频背景
        List<BackgroundElementResponseV2> backgroundElementList = getBackgroundElementResponseV2();
        BackgroundElementResponseV2 backgroundElement = backgroundElementList.get(1);

        // 生成数字人视频
        DigitalHumanVideoAddRequestV2 request = new DigitalHumanVideoAddRequestV2();
        request.setTaskName("API-V2" + new Random().nextInt(10));
        request.setAspectRatio("16:9");

        this.createVideoAddRequest(voiceListV2.get(0), voiceAuditionResponse, avatarResponse, backgroundElement, request);
        request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    private void createVideoAddRequest(VoiceInfoResponseV2 responseV2,
                                       DigitalHumanVoiceAuditionResponse voiceAuditionResponse,
                                       DigitalHumanAvatarListResponse avatarResponse,
                                       BackgroundElementResponseV2 backgroundElement,
                                       DigitalHumanVideoAddRequestV2 request) {
        // 设置数字人信息
        DigitalHumanParamV2 humanParam = new DigitalHumanParamV2();
        request.setDigitalHuman(humanParam);
        humanParam.setDigitalHumanId(avatarResponse.getDigitalHumanId());
        humanParam.setDigitalHumanThumbnailId(avatarResponse.getDigitalHumanThumbnailId());
        humanParam.setDigitalHumanPhotoId(avatarResponse.getDigitalHumanPhotoId());
        humanParam.setMaskType(MaskTypeEnum.NO_MASK.getCode());
        humanParam.setSupportTypeId(avatarResponse.getSupportTypeId());

        // 设置语音信息
        DigitalHumanVoiceParamV2 voiceParam = new DigitalHumanVoiceParamV2();
        request.setDigitalHumanVoice(voiceParam);

        voiceParam.setCopyContent(CommonConstant.text);
        voiceParam.setVoicePackageDuration(voiceAuditionResponse.getFileDuration());
        voiceParam.setVoicePackageFileId(voiceAuditionResponse.getFileId());

        VoiceItemV2 voiceItem = responseV2.getVoices().get(0);
        voiceParam.setLanguageType(responseV2.getLanguageType());
        voiceParam.setVoiceName(voiceItem.getName());

        List<AzureVoiceStyleParamV2> voiceStyles = voiceItem.getVoiceStyles();
        if (!CollectionUtils.isEmpty(voiceStyles)) {
            AzureVoiceStyleParamV2 azureVoiceStyleParamV2 = voiceStyles.get(0);
            voiceParam.setStyle(azureVoiceStyleParamV2.getStyle());
            voiceParam.setStyleName(azureVoiceStyleParamV2.getStyleName());
        }
        voiceParam.setIsSystem(voiceItem.getIsSystem());

        // 设置背景信息
        DigitalHumanBackgroundParamV2 backgroundParamV2 = new DigitalHumanBackgroundParamV2();
        backgroundParamV2.setBackgroundUrl(backgroundElement.getBackgroundUrl());
        backgroundParamV2.setBackgroundType(backgroundElement.getBackgroundType());
        backgroundParamV2.setUploadWay(1);
        backgroundParamV2.setHeight(backgroundElement.getHeight());
        backgroundParamV2.setWidth(backgroundElement.getWidth());
        backgroundParamV2.setBackgroundThumbnailUrl(backgroundElement.getBackgroundThumbnailUrl());

        request.setBackgroundParam(backgroundParamV2);

        if (voiceItem.getIsSystem() != 1) {
            voiceParam.setVoiceName(voiceItem.getName());
        }

    }

    public DigitalHumanVideoAddResponse submitDigitalHumanTask(DigitalHumanVideoAddRequestV2 humanTask) {

        String param = JacksonUtil.toJSONString(humanTask);
        log.info(" submitDigitalHumanTask request param : {} ", humanTask);

        String submitTaskResult = okHttpUtil.doPostJson(submitTaskUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(submitTaskResult);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" submitDigitalHumanTask error , msg = {} ", submitTaskResult);
            return null;
        }

        DigitalHumanVideoAddResponse response = JSON.parseObject(jsonObject.getString("data"), DigitalHumanVideoAddResponse.class);
        return response;
    }

    private static List<BackgroundElementResponseV2> getBackgroundElementResponseV2() {
        BackgroundElementRequestV2 backgroundElementRequestV2 = new BackgroundElementRequestV2();
        backgroundElementRequestV2.setBackgroundTypes(Lists.newArrayList(331));
        backgroundElementRequestV2.setCustom(1);

        String param = JacksonUtil.toJSONString(backgroundElementRequestV2);
        log.info(" testGetBackgroundElement request param : {} ", param);

        String result = okHttpUtil.doPostJson(getBackgroundUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" testGetBackgroundElement error , msg = {} ", result);
            return null;
        }

        JSONObject pageData = JSONObject.parseObject(jsonObject.getString("data"));
        System.out.println("背景视频：" + result);
        List<BackgroundElementResponseV2> videoDataList = JSON.parseArray(pageData.get("data").toString(), BackgroundElementResponseV2.class);
        System.out.println(videoDataList);
        return videoDataList;
    }


}
