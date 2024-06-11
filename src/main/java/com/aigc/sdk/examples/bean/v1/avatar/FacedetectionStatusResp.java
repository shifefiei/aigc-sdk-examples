package com.aigc.sdk.examples.bean.v1.avatar;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FacedetectionStatusResp implements Serializable {

    private Integer fileId;

    private Integer status;

    private Integer faceFileId;

}
