package com.aigc.sdk.examples.bean.v1.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserInfoRequest implements Serializable {

    private String userEmail;

}
