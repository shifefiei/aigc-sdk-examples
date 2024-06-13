package com.aigc.sdk.examples.test.v2;

import com.aigc.sdk.examples.bean.v1.voice.DigitalHumanVoiceAuditionResponse;
import com.aigc.sdk.examples.bean.v2.voice.*;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.JacksonUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 音色列表相关测试
 */
public class MainVoiceTestV2 {

    private static Logger log = LoggerFactory.getLogger(MainVoiceTestV2.class);

    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    // 文本转语音试听
    private static String auditionUrl = CommonConstant.HOST + "/apis/digitalhuman/voice/v2/audition";
    // 获取语音
    private static String voiceListUrl = CommonConstant.HOST + "/apis/digitalhuman/voice/v2/list";

    /**
     * 获取语音列表
     */
    @Test
    public void testVoiceList() {
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = getVoiceListV2(requestV2, 0);
        System.out.println(JSON.toJSONString(voiceListV2));
    }

    // 试听voiceType=4 的音色
    @Test
    public void testListenAudioFileV2() {
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = getVoiceListV2(requestV2, 4);
        for (VoiceInfoResponseV2 responseV2 : voiceListV2) {
            List<VoiceItemV2> list = responseV2.getVoices().stream().filter(e -> e.getId().equals("BV700_V2_streaming")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
                audition.setVolume(250);
                DigitalHumanVoiceAuditionResponse digitalHumanVoiceAuditionResponse = tryToListenAudioFileV2(responseV2, audition);
                System.out.println("试听结果：" + JacksonUtil.toJSONString(digitalHumanVoiceAuditionResponse));
            }
        }
    }

    // 试听voiceType=21 的音色
    @Test
    public void test11LabListenAudioFileV2() {
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = getVoiceListV2(requestV2, 21);
        for (VoiceInfoResponseV2 responseV2 : voiceListV2) {
            List<VoiceItemV2> list = responseV2.getVoices().stream().filter(e -> e.getId().equals("eYO9Ven76ACQ8Me4zQK4")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
                DigitalHumanVoiceAuditionResponse digitalHumanVoiceAuditionResponse = tryToListenAudioFileV2(responseV2, audition);
                System.out.println("试听结果：" + JacksonUtil.toJSONString(digitalHumanVoiceAuditionResponse));
            }
        }
    }

    // 试听voiceType=1 的音色
    @Test
    public void testMicrosoftListenAudioFileV2() {
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = getVoiceListV2(requestV2, 1);
        for (VoiceInfoResponseV2 responseV2 : voiceListV2) {
            List<VoiceItemV2> list = responseV2.getVoices().stream().filter(e -> e.getId().equals("zh-TW-HsiaoChenNeural")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
                DigitalHumanVoiceAuditionResponse digitalHumanVoiceAuditionResponse = tryToListenAudioFileV2(responseV2, audition);
                System.out.println("试听结果：" + JacksonUtil.toJSONString(digitalHumanVoiceAuditionResponse));
            }
        }
    }

    // 试听voiceType=3 的音色
    @Test
    public void testAliyunListenAudioFileV2() {
        LanguageVoiceListRequestV2 requestV2 = new LanguageVoiceListRequestV2();
        requestV2.setLanguage("Chinese");
        requestV2.setIsSystem(1);
        List<VoiceInfoResponseV2> voiceListV2 = getVoiceListV2(requestV2, 3);
        for (VoiceInfoResponseV2 responseV2 : voiceListV2) {
            List<VoiceItemV2> list = responseV2.getVoices().stream().filter(e -> e.getId().equals("dahu")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(list)) {
                DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
                DigitalHumanVoiceAuditionResponse digitalHumanVoiceAuditionResponse = tryToListenAudioFileV2(responseV2, audition);
                System.out.println("试听结果：" + JacksonUtil.toJSONString(digitalHumanVoiceAuditionResponse));
            }
        }
    }

    // 克隆音色音色
    @Test
    public void testCloneListenAudioFileV2() {
        DigitalHumanVoiceAuditionRequestV2 audition = new DigitalHumanVoiceAuditionRequestV2();
        audition.setIsSystem(0);
        audition.setContent(CommonConstant.text);
        audition.setLanguageType("Chinese (Mandarin, Simplified) - 普通话");
        audition.setVoiceId("S_3JPg5aMP");
        audition.setVoiceType(4);

        String param = JSON.toJSONString(audition);
        log.info(" tryToListenAudioFileV2 param , param = {} ", param);

        String auditionResult = okHttpUtil.doPostJson(auditionUrl, param);
        JSONObject auditionJsonObject = JSONObject.parseObject(auditionResult);
        if (!"200".equals(auditionJsonObject.getString("code"))) {
            log.error(" tryToListenAudioFileV2 error , msg = {} ", auditionResult);
            return;
        }

        log.info("试听文件返回结果：{}", auditionResult);

        String auditionData = auditionJsonObject.getString("data");
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = JSON.parseObject(auditionData, DigitalHumanVoiceAuditionResponse.class);
        System.out.println("试听结果：" + JacksonUtil.toJSONString(voiceAuditionResponse));
    }


    public DigitalHumanVoiceAuditionResponse tryToListenAudioFileV2(VoiceInfoResponseV2 responseV2, DigitalHumanVoiceAuditionRequestV2 audition) {

        VoiceItemV2 voiceItem = responseV2.getVoices().get(0);

        audition.setIsSystem(voiceItem.getIsSystem());
        audition.setContent(CommonConstant.text);

        // Chinese (Mandarin, Simplified) - 普通话
        audition.setLanguageType(responseV2.getLanguageType());
        audition.setVoiceId(voiceItem.getId());
        audition.setVoiceType(voiceItem.getVoiceType());
        // fixme : 克隆语音没有 voice styles ，所以不传
        if (!CollectionUtils.isEmpty(voiceItem.getVoiceStyles())) {
            AzureVoiceStyleParamV2 azureVoiceStyleParam = voiceItem.getVoiceStyles().get(0);
            audition.setVoiceStyle(azureVoiceStyleParam.getStyle());
        }

        String param = JSON.toJSONString(audition);
        log.info(" tryToListenAudioFileV2 param , param = {} ", param);

        String auditionResult = okHttpUtil.doPostJson(auditionUrl, param);

        JSONObject auditionJsonObject = JSONObject.parseObject(auditionResult);
        if (!"200".equals(auditionJsonObject.getString("code"))) {
            log.error(" tryToListenAudioFileV2 error , msg = {} ", auditionResult);
            return null;
        }

        log.info("试听文件返回结果：{}", auditionResult);

        String auditionData = auditionJsonObject.getString("data");
        DigitalHumanVoiceAuditionResponse voiceAuditionResponse = JSON.parseObject(auditionData, DigitalHumanVoiceAuditionResponse.class);
        return voiceAuditionResponse;
    }


    public List<VoiceInfoResponseV2> getVoiceListV2(LanguageVoiceListRequestV2 requestV2, Integer voiceType) {
        String voiceList = okHttpUtil.doPostJson(voiceListUrl, JSON.toJSONString(requestV2));
        JSONObject jsonObject = JSONObject.parseObject(voiceList);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" getVoiceListV2 error , msg = {} ", voiceList);
            throw new RuntimeException("请求失败");
        }

        System.out.println("语音列表结果：" + voiceList);

        List<VoiceInfoResponseV2> voiceInfoResponse = JSON.parseArray(jsonObject.getString("data"), VoiceInfoResponseV2.class);
        List<VoiceInfoResponseV2> result = Lists.newArrayList();

        for (VoiceInfoResponseV2 response : voiceInfoResponse) {
            VoiceInfoResponseV2 r = new VoiceInfoResponseV2();
            r.setLanguageType(response.getLanguageType());

            List<VoiceItemV2> voices = response.getVoices();
            List<VoiceItemV2> res = new ArrayList<>();
            for (VoiceItemV2 voice : voices) {
                if (voice.getVoiceType() == voiceType) {
                    VoiceItemV2 item = new VoiceItemV2();
                    BeanUtils.copyProperties(voice, item);
                    res.add(item);
                }
            }
            r.setVoices(res);
            if (r.getVoices().size() != 0) {
                result.add(r);
            }
        }


        return result;
    }

}
