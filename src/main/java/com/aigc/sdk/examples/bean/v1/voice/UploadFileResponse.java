package com.aigc.sdk.examples.bean.v1.voice;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadFileResponse implements Serializable {
    private Integer fileId;

    private Integer bizType;

    public UploadFileResponse() {
    }

    public UploadFileResponse(Integer fileId, Integer bizType) {
        this.fileId = fileId;
        this.bizType = bizType;
    }
}
