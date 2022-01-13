package com.cyty.mall.http;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 14:28
 * @描述
 */

public class HttpConfig {

    public interface RequestKey {
        String FORM_KEY_TOKEN = "token";
        String FORM_KEY_ID = "id";
        String FORM_KEY_USER_ID = "userId";
        String FORM_KEY_IDS = "ids";
        String FORM_KEY_PAGE_INDEX = "pageNum";
        String FORM_KEY_PAGE_SIZE = "pageSize";
        String FORM_KEY_SEARCH = "search";
        String FORM_KEY_PHONE = "phone";
        String FORM_KEY_NAME = "name";
        String FORM_KEY_REGION = "region";
    }
}

