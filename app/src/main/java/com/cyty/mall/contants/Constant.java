package com.cyty.mall.contants;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/5 11:02
 * @描述
 */
public interface Constant {
    String WEI_XIN_APP_ID = "wx6495a900821f9933";
    //号码认证
    String WEI_UMENG_APP_ID = "pNdrnKRU7UdomFdfj/XUErAfa4NPUZEL6LJkFx/VMlGkYRZhCKWW0vID5hdnTNDJ1GXK80qpWfBJqldGRefLsi8U5TpJW9sYvinoAnyIIdzAyYETH84Q9CuWZgCGSTWnUy7pdb8y2W9cVLPWoduczRXpDXLaIOW+yRXT9V1hWJvoOqm7v7DS/2RRvQHAHvwhYJvKi8uPU/kLb56uUE3ZWZMWaKrlqXgySBe467Q9p8lb9J5yQbVLiRPV7lU7PFKZI0FtMk7bgKDiQ+VLKfeH2mhsirs6Bh2AwqiU7huArQK8Jw0CQy949w==";
    /**
     * 手机号正则
     */
    String PHONE_REGEX = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
    String AUTHORIZATION = "Authorization";
    String INTENT_DATA = "intent_data";
    String INTENT_TYPE = "intent_type";
    String INTENT_ID = "intent_id";
    String INTENT_PRICE = "intent_price";
    public static final String[] TYPES = {"全屏（竖屏）", "全屏（横屏）", "弹窗（竖屏）",
            "弹窗（横屏）", "底部弹窗", "自定义View", "自定义View（Xml）"};
    public enum UI_TYPE {
        /**
         * 全屏（竖屏）
         */
        FULL_PORT,
        /**
         * 全屏（横屏）
         */
        FULL_LAND,
        /**
         * 弹窗（竖屏）
         */
        DIALOG_PORT,
        /**
         * "弹窗（横屏）
         */
        DIALOG_LAND,
        /**
         * 底部弹窗
         */
        DIALOG_BOTTOM,
        /**
         * 自定义View
         */
        CUSTOM_VIEW,
        /**
         * 自定义View（Xml）
         */
        CUSTOM_XML
    }

    public static final String THEME_KEY = "theme";

    public static final String LOGIN_TYPE = "login_type";
    public static final int LOGIN = 1;
    public static final int LOGIN_DELAY = 2;
}
