package com.aigc.sdk.examples.test.v1;

import com.aigc.sdk.examples.bean.v1.param.AzureVoiceStyleParam;
import com.aigc.sdk.examples.bean.v1.param.VoiceItem;
import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionRequest;
import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
import com.aigc.sdk.examples.bean.v1.voice.UploadFileResponse;
import com.aigc.sdk.examples.bean.v1.voice.VoiceInfoResponse;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.FileUtil;
import com.aigc.sdk.examples.util.JacksonUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 音色相关测试
 */
public class MainVoiceDemoTest {

    private static Logger log = LoggerFactory.getLogger(MainVoiceDemoTest.class);

    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    // 文本转语音试听
    private static String auditionUrl = CommonConstant.HOST + "/apis/digitalhuman/voice/v1/audition";
    // 上传语音文件
    private static String uploadVoiceUrl = CommonConstant.HOST + "/apis/digitalhuman/voice/v1/uploadVoice";
    // 获取语音
    private static String voiceListUrl = CommonConstant.HOST + "/apis/digitalhuman/voice/v1/list";

    @Test
    public void testVoiceListV1() {
        // 获取音色列表方法
        List<VoiceInfoResponse> voiceList = getVoiceListV1();
        // 在返回的结果中过滤出克隆音色
        for (VoiceInfoResponse voiceInfoResponse : voiceList) {
            List<VoiceItem> voices = voiceInfoResponse.getVoices();
            List<VoiceItem> cloneVoice = voices.stream().filter(e -> !e.isSystem()).collect(Collectors.toList());
            System.out.println("语种：" + voiceInfoResponse.getLanguage() + " ; 克隆音色：" + JacksonUtil.toJSONString(cloneVoice));
        }
        //System.out.println(JSON.toJSONString(voiceList));
    }

    /**
     * 试听微软语言
     */
    @Test
    public void testTryToListenAudioFileV1() {
        List<VoiceInfoResponse> voiceList = getVoiceListV1();
        VoiceItem chineseVoice = getChineseVoice(voiceList);

        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = tryToListenAudioFileV1(chineseVoice);

        System.out.println(JSON.toJSONString(voiceAuditionResponse));
    }

    /**
     * 试听克隆语言
     */
    @Test
    public void testTryToListenCloneAudioFileV1() {
        List<VoiceInfoResponse> voiceList = getVoiceListV1();
        System.out.println(voiceList);
        VoiceItem chineseVoice = getCloneVoice(voiceList);

        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = tryToListenAudioFileV1(chineseVoice);

        System.out.println(JSON.toJSONString(voiceAuditionResponse));
    }

    @Test
    public void testUploadCustomVoiceV1() {
        UploadFileResponse response = getUploadCustomVoiceV1();
        System.out.println(JSON.toJSONString(response));
    }

    public UploadFileResponse getUploadCustomVoiceV1() {
        //String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/1word.mp3";
        String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/5.mp3";
        File file = FileUtil.urlToMultipartFile(fileUrl, "5");
        String originalFilename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        String uploadFileData = okHttpUtil.doUploadFile(uploadVoiceUrl, file, originalFilename);
        JSONObject auditionJsonObject = JSONObject.parseObject(uploadFileData);
        if (!"200".equals(auditionJsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", auditionJsonObject);
            return null;
        }

        log.info("音频驱动上传结果 = {}", uploadFileData);

        UploadFileResponse response = JSONObject.parseObject(auditionJsonObject.getString("data"), UploadFileResponse.class);
        return response;

    }

    /**
     * 获取普通微软语音
     */
    public VoiceItem getChineseVoice(List<VoiceInfoResponse> voiceLis) {
        VoiceInfoResponse voiceInfoResponse = voiceLis.get(1);
        //VoiceItem voiceItem = voiceInfoResponse.getVoices().get(17);
        //VoiceItem voiceItem = voiceInfoResponse.getVoices().get(0);
        VoiceItem voiceItem = voiceInfoResponse.getVoices().get(2);
        List<AzureVoiceStyleParam> voiceStyles = voiceItem.getVoiceStyles();
        if (!CollectionUtils.isEmpty(voiceStyles)) {
            AzureVoiceStyleParam azureVoiceStyleParam = voiceStyles.get(0);
            voiceItem.setStyleName(azureVoiceStyleParam.getStyleName());
            voiceItem.setStyle(azureVoiceStyleParam.getStyle());
        }

        voiceItem.setLanguageType(voiceInfoResponse.getLanguage());
        return voiceItem;
    }

    /**
     * 获取克隆语音
     */
    public VoiceItem getCloneVoice(List<VoiceInfoResponse> voiceLis) {
        VoiceInfoResponse voiceInfoResponse = voiceLis.get(1);
        VoiceItem voiceItem = voiceInfoResponse.getVoices().get(0);

        List<AzureVoiceStyleParam> voiceStyles = voiceItem.getVoiceStyles();
        if (!CollectionUtils.isEmpty(voiceStyles)) {
            AzureVoiceStyleParam azureVoiceStyleParam = voiceStyles.get(0);
            voiceItem.setStyleName(azureVoiceStyleParam.getStyleName());
            voiceItem.setStyle(azureVoiceStyleParam.getStyle());
        }


        voiceItem.setLanguageType(voiceInfoResponse.getLanguage());
        return voiceItem;
    }

    public DigitalHumanVoiceAuditionResponse tryToListenAudioFileV1(VoiceItem voiceItem) {

        DigitalHumanVoiceAuditionRequest audition = new DigitalHumanVoiceAuditionRequest();
        audition.setIsSystem(voiceItem.isSystem());
        audition.setContent(CommonConstant.text);

        // Chinese (Mandarin, Simplified) - 普通话
        audition.setLanguageType(voiceItem.getLanguageType());
        audition.setProsodyPitch(0);
        audition.setProsodyRate(0);
        audition.setVoiceId(voiceItem.getId());
        // fixme : 克隆语音没有 voice styles ，所以不传
        if (!CollectionUtils.isEmpty(voiceItem.getVoiceStyles())) {
            AzureVoiceStyleParam azureVoiceStyleParam = voiceItem.getVoiceStyles().get(0);
            audition.setVoiceStyle(azureVoiceStyleParam.getStyle());
        }

        String param = JSON.toJSONString(audition);
        log.info(" tryToListenAudioFileV1 param , param = {} ", param);

        String auditionResult = okHttpUtil.doPostJson(auditionUrl, param);

        JSONObject auditionJsonObject = JSONObject.parseObject(auditionResult);
        if (!"200".equals(auditionJsonObject.getString("code"))) {
            log.error(" tryToListenAudioFileV1 error , msg = {} ", auditionResult);
            return null;
        }

        log.info("试听文件返回结果：{}", auditionResult);

        String auditionData = auditionJsonObject.getString("data");
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = JSON.parseObject(auditionData, DigitalHumanVoiceAuditionResponse.class);
        return voiceAuditionResponse;
    }

    public List<VoiceInfoResponse> getVoiceListV1() {
        String voiceList = okHttpUtil.doGet(voiceListUrl);
        JSONObject jsonObject = JSONObject.parseObject(voiceList);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" getVoiceListV1 error , msg = {} ", voiceList);
            throw new RuntimeException("请求失败");
        }

        List<VoiceInfoResponse> voiceInfoResponse = JSON.parseArray(jsonObject.getString("data"), VoiceInfoResponse.class);
        return voiceInfoResponse;
    }


}
