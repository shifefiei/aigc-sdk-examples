package com.aigc.sdk.examples.bean.v2.voice;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanVoiceAuditionRequestV2 implements Serializable {

    private String languageType;

    private String content;

    private String voiceId;

    private String voiceStyle;

    private Integer prosodyRate;

    private Integer prosodyPitch;

    private Integer isSystem;

    private Integer volume;

    /**
     * 音色分类值： 1、3、4、21
     */
    private Integer voiceType = 1;

}
