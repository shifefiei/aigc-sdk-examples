package com.aigc.sdk.examples.bean.v2.voice;

import lombok.Data;

import java.io.Serializable;

@Data
public class AzureVoiceStyleParamV2 implements Serializable {

    private String style;
    private String styleName;

}
