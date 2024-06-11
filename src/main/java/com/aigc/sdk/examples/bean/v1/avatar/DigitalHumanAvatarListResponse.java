package com.aigc.sdk.examples.bean.v1.avatar;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanAvatarListResponse implements Serializable {

    private Integer digitalHumanId;

    private Integer supportTypeId;

    private Integer digitalHumanThumbnailId;

    private String digitalHumanThumbnailUrl;

    private Integer digitalHumanPhotoId;

}
