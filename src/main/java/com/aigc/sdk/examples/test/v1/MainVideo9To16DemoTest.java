package com.aigc.sdk.examples.test.v1;

import com.aigc.sdk.examples.bean.v1.avatar.DigitalHumanAvatarListResponse;
import com.aigc.sdk.examples.bean.v1.avatar.DigitalHumanMsgResponse;
import com.aigc.sdk.examples.bean.v1.param.DigitalHumanParam;
import com.aigc.sdk.examples.bean.v1.param.DigitalHumanVoiceParam;
import com.aigc.sdk.examples.bean.v1.param.VoiceItem;
import com.aigc.sdk.examples.bean.v1.video.DigitalHumanVideoAddRequest;
import com.aigc.sdk.examples.bean.v1.video.DigitalHumanVideoAddResponse;
import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
import com.aigc.sdk.examples.bean.v1.voice.UploadFileResponse;
import com.aigc.sdk.examples.bean.v1.voice.VoiceInfoResponse;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.common.MaskTypeEnum;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.JacksonUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

/**
 * 提交生成 9:16 的数字人视频测试
 */
public class MainVideo9To16DemoTest {

    private static Logger log = LoggerFactory.getLogger(MainVideo9To16DemoTest.class);
    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    private static final String ASPECT_RATIO = "9:16";
    private MainAvatarDemoTest mainAvatarDemo = new MainAvatarDemoTest();
    private MainVoiceDemoTest mainVoiceDemo = new MainVoiceDemoTest();

    private static String submitTaskUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v1/add";


    /**
     * 上传自定义照片数字人，然后生成说话视频
     */
    @Test
    public void testUploadPhotoVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 上传自定义照片
        String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/api-1.jpg";
        UploadFileResponse uploadResponse = mainAvatarDemo.getUploadAvatarFileV1(fileUrl);
        Integer uploadAvatarStatus = mainAvatarDemo.getUploadAvatarStatusV1(uploadResponse.getFileId());
        while (true) {
            uploadAvatarStatus = mainAvatarDemo.getUploadAvatarStatusV1(uploadResponse.getFileId());
            if (uploadAvatarStatus == 1) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 获取照片和数字人ID关系
        DigitalHumanMsgResponse photoDigital = mainAvatarDemo.getPhotoDigitalHumanId(uploadResponse.getFileId());

        // 数字人形象入参设置
        DigitalHumanAvatarListResponse avatarResponse = new DigitalHumanAvatarListResponse();
        avatarResponse.setDigitalHumanId(photoDigital.getDigitalHumanId());
        avatarResponse.setSupportTypeId(photoDigital.getSupportTypeId());
        avatarResponse.setDigitalHumanPhotoId(photoDigital.getDigitalHumanPhotoId());
        avatarResponse.setDigitalHumanThumbnailId(photoDigital.getDigitalHumanThumbnailId());

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Upload-Photo" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);

        request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());


        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }


    /**
     * 创建默认白色背景的普通照片数字人
     */
    @Test
    public void testNormalPhotoVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 402);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Normal" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);


        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    /**
     * 创建蒙版矩形照片数字人
     */
    @Test
    public void testRectPhotoVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 402);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "API-Rect" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);
        request.getDigitalHuman().setMaskType(MaskTypeEnum.RECT.getCode());

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    /**
     * 创建蒙版圆形照片数字人
     */
    @Test
    public void testCirclePhotoVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(100, 402);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Circle" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);
        request.getDigitalHuman().setMaskType(MaskTypeEnum.CIRCLE.getCode());

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }


    /**
     * 创建普通视频数数字人
     */
    @Test
    public void testNormalDigitalHumanVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        Integer digitalHumanId = 5;
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(101, digitalHumanId);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Normal" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    /**
     * 上传自定义音频驱动照片数字人说话
     */
    @Test
    public void testUploadVoicePhotoVideo() {

        // 音频驱动
        UploadFileResponse uploadCustomVoice = mainVoiceDemo.getUploadCustomVoiceV1();
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = new DigitalHumanVoiceAuditionResponse();
        voiceAuditionResponse.setFileId(uploadCustomVoice.getFileId());

        // 数字人形象
        Integer humanId = 5;
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(101, humanId);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "API-Upload-Voice" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(new VoiceItem(), voiceAuditionResponse, avatarResponse, request);
        request.getDigitalHuman().setMaskType(MaskTypeEnum.RECT.getCode());

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }


    /**
     * 创建绿幕视频数数字人
     */
    @Test
    public void testNormalGreenDigitalHumanVideoByCloneVoice() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getCloneVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        Integer digitalHumanId = 5;
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(101, digitalHumanId);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Normal-Green-Clone" + new Random().nextInt(10));
        request.setBackgroundColor("rgb,0,255,0");
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }


    /**
     * 创建有实景背景的数字人视频
     */
    @Test
    public void testHaveBackgroundDigitalHumanVideo() {

        // 语音列表
        List<VoiceInfoResponse> voiceList = mainVoiceDemo.getVoiceListV1();
        VoiceItem voiceItem = mainVoiceDemo.getChineseVoice(voiceList);

        // 语音试听
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceDemo.tryToListenAudioFileV1(voiceItem);

        // 数字人形象
        Integer digitalHumanId = 54;
        DigitalHumanAvatarListResponse avatarResponse = mainAvatarDemo.getAvatarListV1(101, digitalHumanId);

        // 生成数字人视频
        DigitalHumanVideoAddRequest request = new DigitalHumanVideoAddRequest();
        request.setBackgroundColor(null);
        request.setTaskName(ASPECT_RATIO + "-API-Normal" + new Random().nextInt(10));
        request.setAspectRatio(ASPECT_RATIO);

        this.createVideoAddRequest(voiceItem, voiceAuditionResponse, avatarResponse, request);

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    private static void createVideoAddRequest(VoiceItem voiceItem, DigitalHumanVoiceAuditionResponse voiceAuditionResponse, DigitalHumanAvatarListResponse avatarResponse, DigitalHumanVideoAddRequest request) {
        DigitalHumanParam humanParam = new DigitalHumanParam();
        request.setDigitalHuman(humanParam);
        humanParam.setDigitalHumanId(avatarResponse.getDigitalHumanId());
        humanParam.setDigitalHumanThumbnailId(avatarResponse.getDigitalHumanThumbnailId());
        humanParam.setDigitalHumanPhotoId(avatarResponse.getDigitalHumanPhotoId());
        humanParam.setMaskType(MaskTypeEnum.NO_MASK.getCode());
        humanParam.setSupportTypeId(avatarResponse.getSupportTypeId());


        DigitalHumanVoiceParam voiceParam = new DigitalHumanVoiceParam();
        request.setDigitalHumanVoice(voiceParam);

        voiceParam.setCopyContent(CommonConstant.text);
        voiceParam.setVoicePackageDuration(voiceAuditionResponse.getFileDuration());
        voiceParam.setVoicePackageFileId(voiceAuditionResponse.getFileId());
        voiceParam.setLanguageCategory(voiceItem.getLanguageType());
        voiceParam.setLanguageType(voiceItem.getId());
        voiceParam.setLanguageTypeName(voiceItem.getId());
        voiceParam.setStyle(voiceItem.getStyle());
        voiceParam.setStyleName(voiceItem.getStyleName());
        voiceParam.setIsSystem(voiceItem.isSystem());

//        if (voiceItem.isSystem()) {
//            String styleName = voiceItem.getStyleName();
//            if (!StringUtils.isEmpty(styleName)) {
//                voiceParam.setLanguageTypeName(voiceItem.getId() + "-" + voiceItem.getStyleName());
//            } else {
//                voiceParam.setLanguageTypeName(voiceItem.getId());
//            }
//        } else {
//            voiceParam.setLanguageTypeName(voiceItem.getName());
//        }

        if (!voiceItem.isSystem()) {
            voiceParam.setLanguageTypeName(voiceItem.getName());
        }


    }


    public DigitalHumanVideoAddResponse submitDigitalHumanTask(DigitalHumanVideoAddRequest humanTask) {

        String param = JacksonUtil.toJSONString(humanTask);
        log.info(" submitDigitalHumanTask request param : {} ", param);

        String submitTaskResult = okHttpUtil.doPostJson(submitTaskUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(submitTaskResult);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" submitDigitalHumanTask error , msg = {} ", submitTaskResult);
            return null;
        }

        DigitalHumanVideoAddResponse response = JSON.parseObject(jsonObject.getString("data"), DigitalHumanVideoAddResponse.class);
        return response;
    }


}
