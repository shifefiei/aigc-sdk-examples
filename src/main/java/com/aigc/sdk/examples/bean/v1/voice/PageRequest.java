package com.aigc.sdk.examples.bean.v1.voice;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PageRequest implements Serializable {

    /** 当前页 */
    protected Integer pageIndex = 1;

    /** 每页显示条数 */
    protected Integer pageSize = 10;

}
