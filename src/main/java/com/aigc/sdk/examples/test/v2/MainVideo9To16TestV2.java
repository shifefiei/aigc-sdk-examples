//package com.aigc.sdk.examples.test.v2;
//
//import com.aigc.sdk.examples.bean.v1.avatar.DigitalHumanAvatarListResponse;
//import com.aigc.sdk.examples.bean.v1.video.DigitalHumanVideoAddResponse;
//import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
//import com.aigc.sdk.examples.bean.v2.DigitalHumanParamV2;
//import com.aigc.sdk.examples.bean.v2.DigitalHumanVideoAddRequestV2;
//import com.aigc.sdk.examples.bean.v2.background.DigitalHumanBackgroundParamV2;
//import com.aigc.sdk.examples.bean.v2.voice.*;
//import com.aigc.sdk.examples.common.CommonConstant;
//import com.aigc.sdk.examples.common.MaskTypeEnum;
//import com.aigc.sdk.examples.config.OkHttpConfig;
//import com.aigc.sdk.examples.test.v1.MainAvatarDemoTest;
//import com.aigc.sdk.examples.test.v1.MainVoiceDemoTest;
//import com.aigc.sdk.examples.util.JacksonUtil;
//import com.aigc.sdk.examples.util.OkHttpUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//import java.util.Random;
//
//public class MainVideo9To16TestV2 {
//
//    private static Logger log = LoggerFactory.getLogger(MainVideo9To16TestV2.class);
//
//    private MainAvatarDemoTest mainAvatarDemo = new MainAvatarDemoTest();
//    private MainVoiceDemoTest mainVoiceDemo = new MainVoiceDemoTest();
//    private static final String ASPECT_RATIO = "9:16";
//    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());
//
//    private static String submitTaskUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/add";
//
//    private MainVoiceTestV2 mainVoiceTestV2 = new MainVoiceTestV2();
//
//
//    @Test
//    public void testCirclePhotoVideo() {
//
//        // 语音列表
//        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
//        requestV2.setLanguage("Chinese");
//        requestV2.setIsSystem(1);
//        List<VoiceInfoResponseV2> voiceListV2 = mainVoiceTestV2.getVoiceListV2(requestV2, 3);
//
//        VoiceItemV2 voiceItemV2 = voiceListV2.get(0).getVoices().get(0);
//
//        // 语音试听
//        DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
//        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceTestV2.tryToListenAudioFileV2(voiceListV2.get(0),audition);
//
//        // 数字人形象
//        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 397);
//
//        // 生成数字人视频
//        DigitalHumanVideoAddRequestV2 request = new DigitalHumanVideoAddRequestV2();
//        request.setTaskName(ASPECT_RATIO + "-API-V2" + new Random().nextInt(10));
//        request.setAspectRatio(ASPECT_RATIO);
//
//        this.createVideoAddRequest(voiceListV2.get(0), voiceAuditionResponse, avatarResponse, request);
//        request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());
//
//        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
//        System.out.println(response);
//    }
//
//    private static void createVideoAddRequest(VoiceInfoResponseV2 responseV2, DigitalHumanVoiceAuditionResponse voiceAuditionResponse, DigitalHumanAvatarListResponse avatarResponse, DigitalHumanVideoAddRequestV2 request) {
//        DigitalHumanParamV2 humanParam = new DigitalHumanParamV2();
//        request.setDigitalHuman(humanParam);
//        humanParam.setDigitalHumanId(avatarResponse.getDigitalHumanId());
//        humanParam.setDigitalHumanThumbnailId(avatarResponse.getDigitalHumanThumbnailId());
//        humanParam.setDigitalHumanPhotoId(avatarResponse.getDigitalHumanPhotoId());
//        humanParam.setMaskType(MaskTypeEnum.NO_MASK.getCode());
//        humanParam.setSupportTypeId(avatarResponse.getSupportTypeId());
//
//
//        DigitalHumanVoiceParamV2 voiceParam = new DigitalHumanVoiceParamV2();
//        request.setDigitalHumanVoice(voiceParam);
//
//        VoiceItemV2 voiceItem = responseV2.getVoices().get(0);
//
//        voiceParam.setCopyContent(CommonConstant.text);
//        voiceParam.setVoicePackageDuration(voiceAuditionResponse.getFileDuration());
//        voiceParam.setVoicePackageFileId(voiceAuditionResponse.getFileId());
//        voiceParam.setLanguageType(responseV2.getLanguageType());
//        voiceParam.setVoiceName(voiceItem.getName());
//
//        List<AzureVoiceStyleParamV2> voiceStyles = voiceItem.getVoiceStyles();
//        if (!CollectionUtils.isEmpty(voiceStyles)) {
//            AzureVoiceStyleParamV2 azureVoiceStyleParamV2 = voiceStyles.get(0);
//            voiceParam.setStyle(azureVoiceStyleParamV2.getStyle());
//            voiceParam.setStyleName(azureVoiceStyleParamV2.getStyleName());
//        }
//        voiceParam.setIsSystem(voiceItem.getIsSystem());
//
//        DigitalHumanBackgroundParamV2 backgroundParamV2 = new DigitalHumanBackgroundParamV2();
//        backgroundParamV2.setBackgroundUrl("https://aigc-test-cdn.kreadoai.com/digitalhuman/system_background/photo/2024/5/7/online_recommend_1-1-1.webp");
//        backgroundParamV2.setBackgroundType(301);
//        request.setBackgroundParam(backgroundParamV2);
//
//        if (voiceItem.getIsSystem() != 1) {
//            voiceParam.setVoiceName(voiceItem.getName());
//        }
//
//    }
//
//    public DigitalHumanVideoAddResponse submitDigitalHumanTask(DigitalHumanVideoAddRequestV2 humanTask) {
//
//        String param = JacksonUtil.toJSONString(humanTask);
//        log.info(" submitDigitalHumanTask request param : {} ", param);
//
//        String submitTaskResult = okHttpUtil.doPostJson(submitTaskUrl, param);
//        JSONObject jsonObject = JSONObject.parseObject(submitTaskResult);
//        if (!"200".equals(jsonObject.getString("code"))) {
//            log.error(" submitDigitalHumanTask error , msg = {} ", submitTaskResult);
//            return null;
//        }
//
//        DigitalHumanVideoAddResponse response = JSON.parseObject(jsonObject.getString("data"), DigitalHumanVideoAddResponse.class);
//        return response;
//    }
//
//}
