package com.aigc.sdk.examples.bean.v1.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VoiceItem implements Serializable {

    private String id;
    private String name;
    private Integer defaultVoiceFileId;
    private boolean isSystem;
    private List<AzureVoiceStyleParam> voiceStyles;
    private String languageType;

    // assistant
    private String style;

    // assistant-助理
    private String styleName;

}
