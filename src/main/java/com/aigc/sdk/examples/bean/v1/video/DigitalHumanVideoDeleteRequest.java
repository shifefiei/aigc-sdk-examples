package com.aigc.sdk.examples.bean.v1.video;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class DigitalHumanVideoDeleteRequest implements Serializable {


    private List<Integer> taskIdList;
}
