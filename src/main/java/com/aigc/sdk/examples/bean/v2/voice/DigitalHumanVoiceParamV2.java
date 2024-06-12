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
     * 以下参数供：web页面任务详情页展示使用,如果要传值，请保持和试听接口传参一致
     */
    private Integer prosodyRate;
    private Integer prosodyPitch;
    private String voiceName = "";
    private String style;
    private String styleName;
    private String copyContent;
    // languageType 是语音列表返回的，对应视频提交接口的：languageCategory
    private String languageType = "";
    // 对应的是发音人名称

}

