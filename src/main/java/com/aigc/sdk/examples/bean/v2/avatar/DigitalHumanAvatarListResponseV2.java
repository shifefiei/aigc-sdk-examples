package com.aigc.sdk.examples.bean.v2.avatar;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanAvatarListResponseV2 implements Serializable {

    private Integer digitalHumanId;

    private Integer supportTypeId;

    private Integer digitalHumanThumbnailId;

    private String digitalHumanThumbnailUrl;

    private Integer digitalHumanPhotoId;

    // 115-系统内置的克隆数字人 112-客户专业定制的克隆人 114-极速复刻的克隆人
    private Integer uploadTypeId;

}
