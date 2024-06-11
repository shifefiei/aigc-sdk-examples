package com.aigc.sdk.examples.bean.v1.video;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class DigitalHumanVideoListResponse implements Serializable {

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


    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
