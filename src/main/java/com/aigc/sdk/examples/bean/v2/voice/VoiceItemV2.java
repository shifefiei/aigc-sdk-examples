package com.aigc.sdk.examples.bean.v2.voice;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VoiceItemV2 implements Serializable {

    private String id;
    private String name;
    private Integer defaultVoiceFileId;
    private Integer isSystem;
    private List<AzureVoiceStyleParamV2> voiceStyles;
    /**
     * 音色分类值： 1、3、4、21
     */
    private Integer voiceType;
}
