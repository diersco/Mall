package com.cyty.mall.event;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/18 15:56
 * @描述
 */
public class WeChartPayEvent {

    private int errCode;

    public WeChartPayEvent(int errCode) {
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}

