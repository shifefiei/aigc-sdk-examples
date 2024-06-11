package com.aigc.sdk.examples.bean.v1.video;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanVideoAddResponse implements Serializable {

    // 任务ID
    private Integer taskId;
    private String taskName;
    private Integer digitalHumanId;
    private Integer supportTypeId;
}
