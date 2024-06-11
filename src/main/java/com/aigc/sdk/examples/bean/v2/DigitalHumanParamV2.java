package com.aigc.sdk.examples.bean.v2;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanParamV2 implements Serializable {

    private Integer digitalHumanId;

    // 100 图片   101 视频
    private Integer supportTypeId;

    // 照片数字人图片形状： circle - 圆形 、rect - 矩形 、nomask - 保持原状
    private String maskType = "noMask";

    // 缩略头像ID
    private Integer digitalHumanThumbnailId;

    // 数字人全身照
    private Integer digitalHumanPhotoId;


}
