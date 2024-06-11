package com.aigc.sdk.examples.bean.v1.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class AzureVoiceStyleParam implements Serializable {

    private String style;
    private String styleName;

}
