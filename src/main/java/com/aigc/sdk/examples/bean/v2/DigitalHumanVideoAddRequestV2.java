package com.aigc.sdk.examples.bean.v2;

import com.aigc.sdk.examples.bean.v2.background.DigitalHumanBackgroundParamV2;
import com.aigc.sdk.examples.bean.v2.voice.DigitalHumanVoiceParamV2;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanVideoAddRequestV2 implements Serializable {

    private String taskName;

    /**
     * 画布比例：16:9 或者 9:16 , 默认值是 16:9
     */
    private String aspectRatio = "16:9";

    // 视频背景元素
    private DigitalHumanBackgroundParamV2 backgroundParam;

    // 数字人信息
    private DigitalHumanParamV2 digitalHuman;

    // 音色信息
    private DigitalHumanVoiceParamV2 digitalHumanVoice;

}