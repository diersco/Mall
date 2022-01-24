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
//    public static final String BASE_URL = "http://47.108.236.123:9528";
    public static final String BASE_URL = " https://appmall.ciyuantiaoyue.com/prod-api/";
    /**
     * 上传图片
     */
    public static final String URL_OSS_UPLOADS = BASE_URL + "/ossUploads";
    /**
     * 上传图片
     */
    public static final String URL_OSS_UPLOAD = BASE_URL + "/ossUpload";
    /**
     * 获取首页数据
     */
    public static final String URL_GET_HOME_PAGE_DATA = BASE_URL + "/stateless/getHomePageData";
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
     * 商品评价
     */
    public static final String URL_GET_APPRAISE_LIST = BASE_URL + "/stateless/getAppraiseList";
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
     * 发送验证码
     */
    public static final String URL_SEND_SMS_CODE = BASE_URL + "/api/auth/sendSmsCode";
    /**
     * 验证码登录
     */
    public static final String URL_SMS_LOGIN = BASE_URL + "/api/auth/smsLogin";
    /**
     * 微信登录
     */
    public static final String URL_WE_CHAT_LOGIN = BASE_URL + "/api/auth/WeChatLogin";
    /**
     * 一键登录
     */
    public static final String URL_EASY_LOGIN = BASE_URL + "/api/auth/easyLogin";
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
     * 支付订单
     */
    public static final String URL_ORDER_PAY = BASE_URL + "/api/order/paymentOrder/pay";
    /**
     * 订单列表
     */
    public static final String URL_SELECT_ORDER_LIST = BASE_URL + "/api/order/selectMallPaymentOrderList";
    /**
     * 订单详情
     */
    public static final String URL_SELECT_ORDER_DETAILS = BASE_URL + "/api/order/selectMallOrderDetailsById";
    /**
     * 物流信息
     */
    public static final String URL_QUERY_LOGISTICS = BASE_URL + "/api/order/queryLogistics";
    /**
     * 确认收货
     */
    public static final String URL_CONFIRM_RECEIPTS = BASE_URL + "/api/order/confirmReceipts";
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
    /**
     * 获取积分详情
     */
    public static final String URL_GET_INTEGRAL_GOODS_INFO = BASE_URL + "/stateless/getIntegralGoodsInfoById";
    /**
     * 立即兑换
     */
    public static final String URL_GET_INTEGRAL_EXCHANGE_COMMODITY = BASE_URL + "/api/integral/pointsExchangeCommodity";
    /**
     * 兑换列表
     */
    public static final String URL_GET_INTEGRAL_EXCHANGE_LIST = BASE_URL + "/api/integral/selectMallExchangetList";
    /**
     * 签到
     */
    public static final String URL_SIGN_IN = BASE_URL + "/api/integral/signIn";
    /**
     * 签到记录数据
     */
    public static final String URL_SIGN_IN_RECORD = BASE_URL + "/api/integral/signInRecord";
    /**
     * 售后列表
     */
    public static final String URL_AFTER_MARKET_LIST = BASE_URL + "/api/order/getMallAftermarketList";
    /**
     * 获取首页秒杀
     */
    public static final String URL_SELECT_SEC_KILL = BASE_URL + "/stateless/getSeckillGoodsList";
    /**
     * 获取秒杀排期
     */
    public static final String URL_SELECT_SEC_KILL_LIST = BASE_URL + "/api/seckillOrder/selectSeckillList";
    /**
     * 获取秒杀排期商品
     */
    public static final String URL_SELECT_SCHEDULING = BASE_URL + "/api/seckillOrder/selectSchedulingList";
    /**
     * 根据商品ID获取秒杀商品详情
     */
    public static final String URL_GET_SECKILL_GOODS_INFO = BASE_URL + "/api/seckillOrder/getSeckillGoodsInfoById";
    /**
     * 创建秒杀订单
     */
    public static final String URL_SEC_KILL_CREATE_ORDER = BASE_URL + "/api/seckillOrder/createOrder";
    /**
     * 确认秒杀订单
     */
    public static final String URL_SEC_KILL_CONFIRM_ORDER = BASE_URL + "/api/seckillOrder/confirmOrder";
    /**
     * 确认秒杀计算金额
     */
    public static final String URL_SEC_KILL_CALCULATED_AMOUNT = BASE_URL + "/api/seckillOrder/calculatedAmount";
    /**
     * 会员权益
     */
    public static final String URL_MALL_MEMBER = BASE_URL + "/basic/selectMallMember";
    /**
     * 售后
     */
    public static final String URL_ORDER_AFTER_SALE = BASE_URL + "/api/order/afterSale";
    /**
     * 修改个人信息
     */
    public static final String URL_UPDATE_USER_INFO = BASE_URL + "/basic/updateUserInfo";
    /**
     * 评价
     */
    public static final String URL_ORDER_ADD_APPRAISE = BASE_URL + "/api/order/addAppraise";

}
