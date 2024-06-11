package com.aigc.sdk.examples.bean.v1.avatar;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanMsgResponse implements Serializable {

    private Integer fileId;

    private Integer supportTypeId;

    private Integer digitalHumanId;


    private Integer digitalHumanThumbnailId;

    private Integer digitalHumanPhotoId;

    // 缩略头像
    private String digitalHumanThumbnailUrl;

    // 数字人全身照
    private String digitalHumanPhotoUrl;

}
