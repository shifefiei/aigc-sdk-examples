package com.aigc.sdk.examples.bean.v2.voice;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VoiceInfoResponseV2 implements Serializable {


    private String languageType;

    private List<VoiceItemV2> voices;
}
