package com.aigc.sdk.examples.bean.v2.voice;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanVoiceParamV2 implements Serializable {

    // 是否时系统语音 1:是,0:否
    private Integer isSystem;
    // 试听文件时长
    private Integer voicePackageDuration;
    // 试听文件ID
    private Integer voicePackageFileId;

    /**
     * web页面任务详情页展示使用
     */
    private Integer prosodyRate = 0;
    private Integer prosodyPitch = 0;
    private String style;
    private String styleName;
    private String copyContent;
    // languageType 是语音列表返回的，对应视频提交接口的：languageCategory
    private String languageType = "";
    // 对应的是发音人名称
    private String voiceName = "";
}

