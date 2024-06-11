package com.aigc.sdk.examples.bean.v2.background;

import com.aigc.sdk.examples.bean.v1.voice.PageRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class BackgroundElementRequestV2 extends PageRequest implements Serializable {

    /**
     * 300 - 系统内置视频背景
     * 301 - 系统内置图片背景
     * 330 - 用户上传图片背景
     * 331 - 用户上传视频背景
     */
    private List<Integer> backgroundTypes;

    /**
     * 0-系统内置背景元素  1-用户上传背景元素
     */
    private Integer custom = 0;

}
