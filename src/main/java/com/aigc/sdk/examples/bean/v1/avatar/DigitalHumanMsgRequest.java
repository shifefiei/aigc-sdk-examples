package com.aigc.sdk.examples.bean.v1.avatar;

import lombok.Data;

import java.io.Serializable;

@Data
public class DigitalHumanMsgRequest implements Serializable {

    // 101 - 查数字人信息  100 - 查询上传的照片和数字人的关系
    private Integer supportTypeId;
    private Integer digitalHumanId;
    private Integer fileId;


}
