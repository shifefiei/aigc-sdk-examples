package com.aigc.sdk.examples.bean.v1.video;

import com.aigc.sdk.examples.bean.v1.param.DigitalHumanParam;
import com.aigc.sdk.examples.bean.v1.param.DigitalHumanVoiceParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanVideoAddRequest implements Serializable {

    private String taskName;
    /**
     * 视频背景色设置
     * 1.如绿幕视频 : rgb,0,255,0
     */
    private String backgroundColor;

    // 屏幕比例  16:9 或者 9:16 , 默认值是 16:9
    private String aspectRatio = "16:9";

    private DigitalHumanParam digitalHuman;

    private DigitalHumanVoiceParam digitalHumanVoice;
}
