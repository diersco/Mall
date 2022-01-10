package com.cyty.mall.util;

import com.cyty.mall.contants.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/5 11:01
 * @描述
 */

public class AppUtils {
    /**
     * 判断是否手机号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern compileImage = Pattern.compile(Constant.PHONE_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compileImage.matcher(phoneNumber);
        if (matcher.matches()) {
            //是手机号
            return true;
        }
        return false;
    }
}
