package com.aigc.sdk.examples.bean.v1.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanCoreParam implements Serializable {

    private Integer bbDigitalHumanId;       // bbDigitalHumanId
    private Integer bbDigitalHumanSizeTypeId = 202;
    private Integer bbDigitalHumanVedioSizeTypeId = 201;
    private String copyContent;             //copyContent
    private String copyKeywords;
    private String languageCategory;        //languageCategory
    private String languageType;            //languageType
    private String languageTypeName;        //languageTypeName
    private Integer speakPitch = 0;
    private Integer speakRate = 0;
    private String speakStyle;
    private String speakStyleName;
    private String taskName;
    private boolean isSystem;               // isSystem
    private Integer voicePackageDuration;   // voicePackageDuration
    private Integer voicePackageFileId;     // voicePackageFileId,
    private String voicePackageSource;      //voicePackageSource,#upload_voice"azure",
    private String textToVoiceTaskId;       //textToVoicetaskId,
    private String maskType;                //maskType,
    private String audioSourceType;         //audioSourceType #audio"text"


}
