package com.aigc.sdk.examples.bean.v2.ai;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CopywritingRequestV2 implements Serializable {

    private String keywords;

    private String language;

    private Integer wordCount = 200;

    // 营销文案来源 1=口播视频创建 ; 2=AI营销文案生成
    private Integer businessSource;

    // 请求模型 0=Azure ; 1=Claude3 ;  3=KIMI
    // 中文：0 和 3 ；其他以外的是 0 和 1
    private Integer requestModel;
}
