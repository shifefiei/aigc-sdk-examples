package com.aigc.sdk.examples.bean.v1.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanVoiceParam implements Serializable {

    private String copyContent;
    private String languageCategory = "";
    private String languageType = "";
    private String languageTypeName = "";

    // 是否时系统语音
    private Boolean isSystem;

    /**
     * azure-微软语音，upload_voice-用户上传，model-用户自己的克隆语音
     */
    //private String voicePackageSource;

    // 试听文件时长
    private Integer voicePackageDuration;

    // 试听文件ID
    private Integer voicePackageFileId;

    private Integer prosodyRate = 0;

    private Integer prosodyPitch = 0;


    // assistant
    private String style;

    // assistant-助理
    private String styleName;

}
