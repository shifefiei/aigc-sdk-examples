package com.aigc.sdk.examples.bean.v1.avatar;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanAvatarListResult implements Serializable {
    private Integer digitalHumanId;
    private Integer supportTypeId;
    private Integer digitalHumanThumbnailId;
    private Integer digitalHumanPhotoId;

}
