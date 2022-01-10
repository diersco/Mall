package com.cyty.mall.http;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 14:14
 * @描述
 */
public class ServerApiConstants {
    /**
     * base url
     */
    public static final String BASE_URL = "http://47.108.236.123:9528";
    /**
     * 分类页数据——banner——分类
     **/
    public static final String URL_GET_BANNER = BASE_URL + "/stateless/getClassifPageData";
    /**
     * 获取商品分类
     */
    public static final String URL_GET_CLASSIFY = BASE_URL + "/stateless/getClassifyList";
    /**
     * 获取商品列表
     */
    public static final String URL_GET_GOODS_LIST = BASE_URL + "/stateless/getGoodsList";
    /**
     * 查询文章
     */
    public static final String URL_GET_ARTICLE = BASE_URL + "/stateless/getArticle";
    /**
     * 获取商品详情
     */
    public static final String URL_GET_GOODS_INFO = BASE_URL + "/stateless/getGoodsInfoById";
    /**
     * 查询地址列表
     */
    public static final String URL_GET_ADDRESS_LIST = BASE_URL + "/basic/selectMallAddressesList";

    /**
     * 获取首页数据
     */
    public static final String URL_GET_HOME_PAGE_DATA = BASE_URL + "/stateless/getHomePageData";
    /**
     * 根据商品编号获取购买人列表（分页）
     */
    public static final String URL_GET_ORDER_USER_LIST = BASE_URL + "/stateless/getOrderUserList";
    /**
     * 查询常见问题
     */
    public static final String URL_GET_PROBLEM = BASE_URL + "/stateless/getProblemById";
    /**
     * 获取常见问题列表
     */
    public static final String URL_GET_PROBLEM_LIST = BASE_URL + "/stateless/getProblemlist";
    /**
     * 查询分类页秒杀商品
     */
    public static final String URL_GET_SECKILL_Goods_LIST = BASE_URL + "/stateless/getSeckillGoodsList";
    /**
     * 发送验证码
     */
    public static final String URL_SEND_SMS_CODE = BASE_URL + "/api/auth/sendSmsCode";
    /**
     * 验证码登录
     */
    public static final String URL_SMS_LOGIN = BASE_URL + "/api/auth/smsLogin";
    /**
     * 获取用户信息
     */
    public static final String URL_GET_USER_INFO = BASE_URL + "/basic/getUserInfo";
    /**
     * 个人中心未读标志数量
     */
    public static final String URL_SELECT_READ_NEWS = BASE_URL + "/basic/selectReadNews";
}
