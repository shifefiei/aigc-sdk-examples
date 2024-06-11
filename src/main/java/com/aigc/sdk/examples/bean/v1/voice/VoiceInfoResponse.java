package com.aigc.sdk.examples.bean.v1.voice;


import com.aigc.sdk.examples.bean.v1.param.VoiceItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VoiceInfoResponse implements Serializable {


    private String language;

    private List<VoiceItem> voices;
}
