package com.aigc.sdk.examples.bean.v2.background;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DigitalHumanBackgroundParamV2 implements Serializable {

    /**
     * 300 - 系统内置视频背景
     * 301 - 系统内置图片背景
     * 330 - 用户上传图片背景
     * 331 - 用户上传视频背景
     * 302 - 颜色背景
     */
    private Integer backgroundType;

    /**
     * 背景上传方式 1-文件ID 2-公网文件URL
     */
    private Integer uploadWay;

    /**
     * 背景元素ID
     */
    private Integer backgroundId;

    /**
     * 背景元素尺寸高
     */
    private Integer height;

    /**
     * 背景元素尺寸宽
     */
    private Integer width;

    /**
     * 对应的背景元素公网地址
     */
    private String backgroundUrl;

    /**
     * 文件缩略图地址
     */
    private String backgroundThumbnailUrl;

    /**
     * 视频背景色设置
     * 1.如绿幕视频 : rgb,0,255,0
     */
    private String backgroundColor;
}