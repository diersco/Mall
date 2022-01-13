package com.cyty.mall.util;

/**
 * 数字和文字处理
 * Created by zst on 2017/6/28.
 */

public class DigitUtil {

    /**
     * 手机号中间四位隐藏
     *
     * @param phone
     */
    public static String phoneHide(String phone) {
        String phoneHide = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneHide;
    }

    /**
     * 身份证中间8位隐藏
     * 隐藏出生年月
     *
     * @param idCard
     */
    public static String idCardHide(String idCard) {
        String idCardHide = idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})", "$1*****$2");
        return idCardHide;
    }


}