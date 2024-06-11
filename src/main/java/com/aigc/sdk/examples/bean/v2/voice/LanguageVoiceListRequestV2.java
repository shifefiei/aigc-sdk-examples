package com.aigc.sdk.examples.bean.v2.voice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LanguageVoiceListRequestV2 implements Serializable {

    private String language;

    // 是否为系统音色 1:是,0:否
    private Integer isSystem;

}
