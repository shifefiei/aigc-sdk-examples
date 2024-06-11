package com.aigc.sdk.examples.bean.v1.voice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class UploadFileRequest implements Serializable {

    private MultipartFile file;


}
