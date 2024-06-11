package com.aigc.sdk.examples.bean.v1.video;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class DigitalHumanVideoDetailResponse implements Serializable {


    private Integer taskId;


    private String taskName;


    private Integer taskStatusTypeId;


    private String targetFileUrl;


    private String targetFileThumbnailUrl;


    private Long surplusProcessSecond;


    private String voicePackageDuration;


    private Integer voicePackageDurationSecond;


    private String voicePackageSource;


    private Integer supportTypeId;


    private String languageCategory;


    private String languageType;


    private String languageTypeName;


    private String speakStyle;


    private String speakStyleName;


    private String speakPitch;


    private String speakRate;


    private String copyContent;


    private LocalDateTime createTime;


    private LocalDateTime updateTime;
}
