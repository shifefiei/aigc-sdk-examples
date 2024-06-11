package com.aigc.sdk.examples.bean.v1.avatar;

import com.aigc.sdk.examples.bean.v1.voice.PageRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class DigitalHumanAvatarListRequest extends PageRequest {

    // 100 - 照片  101 - 真人
    private Integer supportTypeId;

    // 数字人形象ID
    private Integer digitalHumanId;

    //女性-211，男性-210
    private List<Integer> tagIds;

    // 欧美-141，亚裔-140，日韩-142，东南亚-143，印度-145，中东-144，非洲-146
    private Integer areaTypeId;

}
