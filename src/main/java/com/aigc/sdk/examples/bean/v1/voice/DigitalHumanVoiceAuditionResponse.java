package com.aigc.sdk.examples.bean.v1.voice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class DigitalHumanVoiceAuditionResponse implements Serializable {


    private Integer fileId;


    private Integer textToVoiceTaskId;


    private BigDecimal costMoney;


    private int fileDuration;

    private String fileUrl;
}
