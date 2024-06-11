package com.aigc.sdk.examples.bean.v1.user;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserInfoResponse {

    // k币余额
    private BigDecimal balance;

    // 会员过期时间
    private LocalDateTime vipExpireTime;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getVipExpireTime() {
        return vipExpireTime;
    }

    public void setVipExpireTime(LocalDateTime vipExpireTime) {
        this.vipExpireTime = vipExpireTime;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "balance=" + balance +
                ", vipExpireTime=" + vipExpireTime +
                '}';
    }
}
