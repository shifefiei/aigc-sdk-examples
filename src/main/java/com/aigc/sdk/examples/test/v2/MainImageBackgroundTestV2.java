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
import com.aigc.sdk.examples.test.v1.MainVoiceDemoTest;
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
 * 数字人图片背景合成测试用例
 */
public class MainImageBackgroundTestV2 {

    private static Logger log = LoggerFactory.getLogger(MainImageBackgroundTestV2.class);

    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());
    private static final String ASPECT_RATIO = "9:16";
    private MainAvatarDemoTest mainAvatarDemo = new MainAvatarDemoTest();
    private MainVoiceDemoTest mainVoiceDemo = new MainVoiceDemoTest();

    private static String submitTaskUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/add";
    private static String getBackgroundUrl = CommonConstant.HOST + "/apis/digitalhuman/background/v2/backgroundList";

    private MainVoiceTestV2 mainVoiceTestV2 = new MainVoiceTestV2();
    private MainAvatarTestV2 mainAvatarTestV2 = new MainAvatarTestV2();


    /**
     * 获取图片背景列表
     */
    @Test
    public void testGetBackgroundElement() {
        getBackgroundElementResponseV2();
    }


    /**
     * 以文件ID的方式提交图片背景任务
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
        request.setTaskName("API-V2-ID图片" + new Random().nextInt(10));
        request.setAspectRatio("16:9");

        this.createVideoAddRequest(voiceListV2.get(0), voiceAuditionResponse, avatarResponse, request);

        // 设置背景信息
        DigitalHumanBackgroundParamV2 backgroundParamV2 = new DigitalHumanBackgroundParamV2();
        backgroundParamV2.setBackgroundUrl(backgroundElement.getBackgroundUrl());
        backgroundParamV2.setBackgroundType(backgroundElement.getBackgroundType());
        // 文件ID方式
        backgroundParamV2.setUploadWay(1);
        backgroundParamV2.setHeight(backgroundElement.getHeight());
        backgroundParamV2.setWidth(backgroundElement.getWidth());
        backgroundParamV2.setBackgroundThumbnailUrl(backgroundElement.getBackgroundThumbnailUrl());
        request.setBackgroundParam(backgroundParamV2);

        //request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());
        System.out.println("视频提交请求入参：" + JacksonUtil.toJSONString(request));

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    /**
     * 以公网URL的方式提交图片背景任务
     */
    @Test
    public void test9To16PhotoVideo2() {
        // 语音列表
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = mainVoiceTestV2.getVoiceListV2(requestV2, 21);

        // 语音试听
        DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceTestV2.tryToListenAudioFileV2(voiceListV2.get(0), audition);

        // 数字人形象
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 402);


        //List<BackgroundElementResponseV2> backgroundElementList = getBackgroundElementResponseV2();
        //BackgroundElementResponseV2 backgroundElement = backgroundElementList.get(1);

        // 生成数字人视频
        DigitalHumanVideoAddRequestV2 request = new DigitalHumanVideoAddRequestV2();
        request.setTaskName("API-V2-公网URL图片" + new Random().nextInt(10));
        request.setAspectRatio("9:16");

        this.createVideoAddRequest(voiceListV2.get(0), voiceAuditionResponse, avatarResponse, request);
        request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());

        // 设置背景信息
        DigitalHumanBackgroundParamV2 backgroundParamV2 = new DigitalHumanBackgroundParamV2();

        // backgroundParamV2.setBackgroundUrl("https://aigc-test-cdn.kreadoai.com/digitalhuman/system_background/photo/2024/5/7/advertising_e-commerce_1-6-4.webp");
        //backgroundParamV2.setBackgroundType(301);

        backgroundParamV2.setBackgroundUrl("https://aigc-test-cdn.kreadoai.com/digitalhuman/image/2024/6/cd0918f22d4b42d99ea3eb07749aee3b.jpg");
        backgroundParamV2.setBackgroundType(330);

        // 公网URL方法
        backgroundParamV2.setUploadWay(2);
        request.setBackgroundParam(backgroundParamV2);

        //request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());
        System.out.println("视频提交请求入参：" + JacksonUtil.toJSONString(request));

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
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

    private void createVideoAddRequest(VoiceInfoResponseV2 responseV2,
                                       DigitalHumanVoiceAuditionResponse voiceAuditionResponse,
                                       DigitalHumanAvatarListResponse avatarResponse,
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
        voiceParam.setLanguageType(responseV2.getLanguageType());

        List<VoiceItemV2> voices = responseV2.getVoices();
        // 上传音频时没有音色相关的信息，所以这里需要判断下音色是否为空
        if (!CollectionUtils.isEmpty(voices)) {
            VoiceItemV2 voiceItem = voices.get(0);
            voiceParam.setVoiceName(voiceItem.getName());
            List<AzureVoiceStyleParamV2> voiceStyles = voiceItem.getVoiceStyles();
            if (!CollectionUtils.isEmpty(voiceStyles)) {
                AzureVoiceStyleParamV2 azureVoiceStyleParamV2 = voiceStyles.get(0);
                voiceParam.setStyle(azureVoiceStyleParamV2.getStyle());
                voiceParam.setStyleName(azureVoiceStyleParamV2.getStyleName());
            }
            voiceParam.setIsSystem(voiceItem.getIsSystem());

            if (voiceItem.getIsSystem() != 1) {
                voiceParam.setVoiceName(voiceItem.getName());
            }
        } else {
            voiceParam.setIsSystem(0);
        }


    }


    private static List<BackgroundElementResponseV2> getBackgroundElementResponseV2() {
        BackgroundElementRequestV2 backgroundElementRequestV2 = new BackgroundElementRequestV2();
        backgroundElementRequestV2.setBackgroundTypes(Lists.newArrayList(330));
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
        System.out.println("图片视频：" + result);
        List<BackgroundElementResponseV2> videoDataList = JSON.parseArray(pageData.get("data").toString(), BackgroundElementResponseV2.class);
        System.out.println(videoDataList);
        return videoDataList;
    }


}
