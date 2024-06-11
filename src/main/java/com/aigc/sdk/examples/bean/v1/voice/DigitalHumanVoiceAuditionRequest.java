package com.aigc.sdk.examples.bean.v1.voice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanVoiceAuditionRequest implements Serializable {


    private String languageType;


    private String content;


    private String voiceId;


    private String voiceStyle;

    private Integer prosodyRate = 0;


    private Integer prosodyPitch = 0;

    private Boolean isSystem;

}
