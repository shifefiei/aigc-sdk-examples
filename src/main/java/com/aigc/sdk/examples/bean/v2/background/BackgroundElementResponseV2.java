package com.aigc.sdk.examples.bean.v2.background;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
public class BackgroundElementResponseV2 implements Serializable {


    /**
     * 330 - 图片背景
     * 331 - 视频背景
     */
    private Integer backgroundType;

    /**
     * 背景元素ID
     */
    private Integer backgroundId;

    /**
     * 背景元素文件ID
     */
    private Integer backgroundFileId;

    /**
     * 背景元素原始文件
     */
    private String backgroundUrl;

    /**
     * 文件缩略图地址
     */
    private String backgroundThumbnailUrl;

    /**
     * 背景元素尺寸高
     */
    private Integer height;

    /**
     * 背景元素尺寸宽
     */
    private Integer width;

    /**
     * 0-系统默认
     * 1-用户自定义
     */
    private Integer custom;


}
