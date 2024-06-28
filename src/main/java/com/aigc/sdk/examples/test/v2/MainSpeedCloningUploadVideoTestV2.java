package com.aigc.sdk.examples.test.v2;

import com.aigc.sdk.examples.bean.v1.video.DigitalHumanVideoAddResponse;
import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
import com.aigc.sdk.examples.bean.v2.DigitalHumanParamV2;
import com.aigc.sdk.examples.bean.v2.DigitalHumanVideoAddRequestV2;
import com.aigc.sdk.examples.bean.v2.avatar.DigitalHumanAvatarListRequestV2;
import com.aigc.sdk.examples.bean.v2.avatar.DigitalHumanAvatarListResponseV2;
import com.aigc.sdk.examples.bean.v2.background.DigitalHumanBackgroundParamV2;
import com.aigc.sdk.examples.bean.v2.voice.*;
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
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 克隆数字人视频上传测试用例
 */
public class MainSpeedCloningUploadVideoTestV2 {

    private static Logger log = LoggerFactory.getLogger(MainSpeedCloningUploadVideoTestV2.class);
    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());
    private static String uploadVideoUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/uploadCloneVideo";
    private static String getUploadVideoUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/getUploadCloneVideoStatus";
    private static String submitTaskUrl = CommonConstant.HOST + "/apis/digitalhuman/video/v2/add";
    private MainVoiceTestV2 mainVoiceTestV2 = new MainVoiceTestV2();
    private MainAvatarTestV2 mainAvatarTestV2 = new MainAvatarTestV2();

    /**
     * 极速克隆上传视频
     */
    @Test
    public void testUploadVideo() {
        Map<String, String> map = new HashMap<>();
        map.put("requestId", UUID.randomUUID().toString());
        map.put("videoUrl", "https://aigc-test-cdn.kreadoai.com/digitalhuman/video/2023/8/30/105-2.mp4");

        String param = JacksonUtil.toJSONString(map);
        log.info(" testUploadVideo request param : {} ", param);

        String result = okHttpUtil.doPostJson(uploadVideoUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" testUploadVideo error , msg = {} ", result);
        }
        log.info(" testUploadVideo request result : {} ", result);
    }

    /**
     * 查询上传状态
     */
    @Test
    public void testGetUploadVideo() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", "1806692440211488770");

        String param = JacksonUtil.toJSONString(map);
        log.info(" testGetUploadVideo request param : {} ", param);

        String result = okHttpUtil.doPostJson(getUploadVideoUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" testGetUploadVideo error , msg = {} ", result);
        }
        log.info(" testGetUploadVideo request result : {} ", result);
    }

    /**
     * 提交极速克隆数字人的视频合成
     * 该方法只是模拟整个流程。视频上传和视频合成是两个业务流程，该demo的写法是错误的
     */
    @Test
    public void testSubmitSpeedCloneVideo() {
        // 上传视频
        Map<String, String> map = new HashMap<>();
        map.put("requestId", UUID.randomUUID().toString());
        map.put("videoUrl", "https://aigc-test-cdn.kreadoai.com/digitalhuman/video/2024/4/9/778-1.mp4");

        String param = JacksonUtil.toJSONString(map);
        String result = okHttpUtil.doPostJson(uploadVideoUrl, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" 极速克隆视频上传失败 , msg = {} ", result);
        }

        // 查询上传状态
        String taskId = JSONObject.parseObject(jsonObject.getString("data")).getString("taskId");
        Map<String, String> map2 = new HashMap<>();
        map2.put("taskId", taskId);

        String param2 = JacksonUtil.toJSONString(map2);
        String result2 = okHttpUtil.doPostJson(getUploadVideoUrl, param2);

        JSONObject jsonObject2 = JSONObject.parseObject(result2);
        String code = jsonObject2.getString("code");
        if (code.equals("200")) {

            String data = jsonObject2.getString("data");
            JSONObject jsonObjectData = JSONObject.parseObject(data);

            int count = 0;
            Integer taskStatus = jsonObjectData.getInteger("taskStatus");
            while (true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (count == 60) {
                    System.out.println("超时......");
                    break;
                }
                count++;
                System.out.println("============" + count);

                // 成功
                if (taskStatus == 3) {
                    Integer digitalHumanId = jsonObjectData.getInteger("digitalHumanId");
                    System.out.println("极速克隆数字人形象ID: " + digitalHumanId);

                    // 先查询看数字人形象在库里有么有，没有的化等一会儿再查询
                    DigitalHumanAvatarListRequestV2 req = new DigitalHumanAvatarListRequestV2();
                    req.setSupportTypeId(101);
                    req.setCloneDigitalHuman(1);
                    req.setPageSize(1000);

                    List<DigitalHumanAvatarListResponseV2> avatarList = mainAvatarTestV2.getAvatarList(req);
                    List<DigitalHumanAvatarListResponseV2> collect = avatarList.stream().filter(e -> e.getDigitalHumanId().equals(digitalHumanId)).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(collect)) {

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        avatarList = mainAvatarTestV2.getAvatarList(req);
                        collect = avatarList.stream().filter(e -> e.getDigitalHumanId().equals(digitalHumanId)).collect(Collectors.toList());
                    }

                    if(!CollectionUtils.isEmpty(collect)) {
                        createVideoRequest(digitalHumanId);
                    }
                    break;
                }

                // 失败
                if (taskStatus == 4) {
                    System.out.println("失败......");
                    break;
                }


                String result3 = okHttpUtil.doPostJson(getUploadVideoUrl, JacksonUtil.toJSONString(map2));
                JSONObject jsonObject3 = JSONObject.parseObject(result3);
                String code3 = jsonObject3.getString("code");
                if (!code3.equals("200")) {
                    System.out.println("接口调用失败。。。。。");
                } else {
                    String data1 = jsonObject3.getString("data");
                    jsonObjectData = JSONObject.parseObject(data1);
                    taskStatus = jsonObjectData.getInteger("taskStatus");
                }

            }
        }

    }


    public void createVideoRequest(Integer digitalHumanId) {
        // 语音列表
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = mainVoiceTestV2.getVoiceListV2(requestV2, 21);

        // 语音试听
        DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = mainVoiceTestV2.tryToListenAudioFileV2(voiceListV2.get(0), audition);

        // 查询极速克隆数字人形象
        DigitalHumanAvatarListRequestV2 req = new DigitalHumanAvatarListRequestV2();
        req.setSupportTypeId(101);
        req.setCloneDigitalHuman(1);
        req.setPageSize(1000);

        List<DigitalHumanAvatarListResponseV2> avatarList = mainAvatarTestV2.getAvatarList(req);
        List<DigitalHumanAvatarListResponseV2> collect = avatarList.stream().filter(e -> e.getDigitalHumanId().equals(digitalHumanId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            System.out.println("数字人形象为空 。。。");
            return;
        }
        DigitalHumanAvatarListResponseV2 avatarResponse = collect.get(0);

        // 生成数字人视频
        DigitalHumanVideoAddRequestV2 request = new DigitalHumanVideoAddRequestV2();
        request.setTaskName("API-V2-clone" + new Random().nextInt(10));
        request.setAspectRatio("9:16");

        this.createCloneVideoAddRequest(voiceListV2.get(0), voiceAuditionResponse, avatarResponse, request);
        System.out.println("视频提交请求入参：" + JacksonUtil.toJSONString(request));

        DigitalHumanVideoAddResponse response = this.submitDigitalHumanTask(request);
        System.out.println(response);
    }

    private void createCloneVideoAddRequest(VoiceInfoResponseV2 responseV2,
                                            DigitalHumanVoiceAuditionResponse voiceAuditionResponse,
                                            DigitalHumanAvatarListResponseV2 avatarResponse,
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

        // 设置背景信息
        DigitalHumanBackgroundParamV2 backgroundParamV2 = new DigitalHumanBackgroundParamV2();

        // 绿幕背景视频设置
        backgroundParamV2.setBackgroundColor("rgb,0,255,0");
        backgroundParamV2.setBackgroundType(302);

        request.setBackgroundParam(backgroundParamV2);
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

}
