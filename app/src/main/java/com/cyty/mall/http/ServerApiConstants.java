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
     * 新增或修改地址
     */
    public static final String URL_ADD_MALL_ADDRESS = BASE_URL + "/basic/addMallAddresses";
    /**
     * 删除地址
     */
    public static final String URL_REMOVE_MALL_ADDRESS = BASE_URL + "/basic/removeMallAddresses";
    /**
     * 获取默认地址
     */
    public static final String URL_GET_DEFAULT_ADDRESS = BASE_URL + "/basic/selectDefaultMallSeting";

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
    /**
     * 查询消息管理列表（分页）
     */
    public static final String URL_GET_NEWS_LIST = BASE_URL + "/basic/getNewsList";
    /**
     * 查询消息详情
     */
    public static final String URL_GET_NEWS_BY_ID = BASE_URL + "/basic/getNewsById";
    /**
     * 获取优惠券列表
     */
    public static final String URL_GET_MALL_COUPONS_LIST = BASE_URL + "/basic/getMallCouponsList";
    /**
     * 收藏或取消收藏
     */
    public static final String URL_MALL_COLLECTION = BASE_URL + "/basic/collection";
    /**
     * 获取收藏列表
     */
    public static final String URL_GET_MALL_COLLECTION_LIST = BASE_URL + "/basic/getMallCollectionList";
    /**
     * 确认订单信息
     */
    public static final String URL_ORDER_CONFIRM_ORDER = BASE_URL + "/api/order/confirmOrder";
    /**
     * 计算金额
     */
    public static final String URL_CALCULATED_AMOUNT = BASE_URL + "/api/order/calculatedAmount";
    /**
     * 创建订单
     */
    public static final String URL_CREATE_ORDER = BASE_URL + "/api/order/createOrder";
    /**
     * 加入购物车
     */
    public static final String URL_ADD_SHOPPING_CART = BASE_URL + "/api/cart/addShoppingCart";
    /**
     * 购物车列表
     */
    public static final String URL_SHOPPING_LIST = BASE_URL + "/api/cart/selectShoppingCartList";
    /**
     * 我的积分变动流水列表
     */
    public static final String URL_SELECT_MALL_FLOW_LIST = BASE_URL + "/api/integral/selectMallFlowtList";
    /**
     * 获取积分商城
     */
    public static final String URL_GET_INTEGRAL_GOODS_LIST = BASE_URL + "/stateless/getIntegralGoodsList";
}
