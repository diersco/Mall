package com.cyty.mall.util;

/**
 * @创建者 misJackLee
 * @创建时间 2020/11/27 17:05
 * @描述
 */
public class StringUtils {
    public static String getHtmlData(String bodyHTML) {
        String head =
                "<head>"
                        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                        + "<style>img{max-width: 100%; width:100%;}*{margin:0px;}</style>"
                        + "</head>";
        return "<!DOCTYPE html>" + "<html lang=\"en\">" + head + "<body>" + bodyHTML + "</body>" + "</html>";
    }

}
