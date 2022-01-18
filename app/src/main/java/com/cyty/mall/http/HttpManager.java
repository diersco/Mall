package com.cyty.mall.http;

import android.content.Context;

import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.util.MkUtils;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 14:13
 * @描述
 */
public class HttpManager {
    private static HttpManager mInstance;
    private HttpEngine mHttpEngine = new HttpEngine();
    private String mPackageName;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mPackageName = context.getPackageName();
    }


    /**
     * 获取首页数据
     *
     * @param callback callback
     */
    public void getHomePageData(HttpEngine.HttpResponseResultCallback<HttpResponse.getHomePageDataResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_HOME_PAGE_DATA)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getHomePageDataResponse.class, callback);
    }

    /**
     * 获取文章
     *
     * @param id       分类id 1=关于我们,2=用户协议,3=隐私政策,4=签到说明,5=积分说明
     * @param callback callback
     */
    public void getArticle(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.getArticleResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_ARTICLE)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getArticleResponse.class, callback);
    }

    /**
     * banner
     *
     * @param callback callback
     */
    public void getBanner(HttpEngine.HttpResponseResultCallback<HttpResponse.ClassIfPageBannerResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_BANNER)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.ClassIfPageBannerResponse.class, callback);
    }

    /**
     * 获取分类
     */
    public void getClassificationCommodity(HttpEngine.HttpResponseResultListCallback<HttpResponse.ClassificationCommodityResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_CLASSIFY)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.ClassificationCommodityResponse.class, callback);
    }


    /**
     * 获取商品列表
     *
     * @param id        分类id
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param search    搜索信息
     * @param callback
     */
    public void getGoodsList(int id, int pageIndex, int pageSize, String search, HttpEngine.HttpResponseResultListCallback<HttpResponse.GoodsListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_SEARCH, search);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_GOODS_LIST)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.GoodsListResponse.class, callback);
    }

    /**
     * 获取商品详情
     *
     * @param id
     * @param callback
     */
    public void getGoodsInfo(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.GoodsInfoResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_GOODS_INFO)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.GoodsInfoResponse.class, callback);
    }

    /**
     * 获取地址列表
     *
     * @param id        分类id
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param search    搜索信息
     * @param callback
     */
    public void getAddressList(int id, int pageIndex, int pageSize, String search, HttpEngine.HttpResponseResultListCallback<HttpResponse.addressListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_SEARCH, search);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_ADDRESS_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.addressListResponse.class, callback);
    }

    /**
     * 新增或者修改地址
     *
     * @param defaults        默认  1是默认 2不是
     * @param detailedAddress 详细地址
     * @param id              收货地址编号
     * @param name            姓名
     * @param phone           电话号码
     * @param region          地区
     * @param callback
     */
    public void addOrReviseAddressList(int defaults, String detailedAddress, int id, String name, String phone, String region, HttpEngine.HttpResponseResultCallback<HttpResponse.addOrReviseAddressResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("defaults", defaults + "");
        jsonObject.addProperty("detailedAddress", detailedAddress);
        if (id > 0) {
            jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        }
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_NAME, name);
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PHONE, phone);
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_REGION, region);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ADD_MALL_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.addOrReviseAddressResponse.class, callback);
    }

    /**
     * 选择默认地址
     *
     * @param defaults 默认  1是默认 2不是
     * @param id
     */
    public void reviseDefaultsAddress(int id, int defaults, HttpEngine.HttpResponseResultCallback<HttpResponse.reviseDefaultsAddressResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("defaults", defaults + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ADD_MALL_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.reviseDefaultsAddressResponse.class, callback);
    }

    /**
     * 获取默认地址
     */
    public void getDefaultsAddress(HttpEngine.HttpResponseResultCallback<HttpResponse.getDefaultsAddressResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_DEFAULT_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getDefaultsAddressResponse.class, callback);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void deleteAddress(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.deleteAddressResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_REMOVE_MALL_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.deleteAddressResponse.class, callback);
    }

    /**
     * 获取验证码
     *
     * @param cellPhoneNumber 手机号
     * @param callback
     */
    public void sendSmsCode(String cellPhoneNumber, HttpEngine.HttpResponseResultCallback<HttpResponse.SendSmsCodeResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cellPhoneNumber", cellPhoneNumber);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SEND_SMS_CODE)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.SendSmsCodeResponse.class, callback);
    }


    /**
     * 验证码登录
     *
     * @param cellPhoneNumber 手机号
     * @param code            验证啊
     * @param callback
     */
    public void smsLogin(String cellPhoneNumber, String code, HttpEngine.HttpResponseResultCallback<HttpResponse.SmsLoginResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cellPhoneNumber", cellPhoneNumber);
        jsonObject.addProperty("code", code);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SMS_LOGIN)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.SmsLoginResponse.class, callback);
    }


    /**
     * 获取用户信息
     */
    public void getUserInfo(HttpEngine.HttpResponseResultCallback<HttpResponse.getUserInfoResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_USER_INFO)
//                .addHeader(Constant.AUTHORIZATION, "Bearer" + MkUtils.decodeString(MKParameter.TOKEN))
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getUserInfoResponse.class, callback);
    }

    /**
     * 个人中心未读标志数量
     */
    public void selectReadNews(HttpEngine.HttpResponseResultCallback<HttpResponse.selectReadNewsResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_READ_NEWS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectReadNewsResponse.class, callback);
    }

    /**
     * 获取消息列表
     *
     * @param id        分类id
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void getNewList(int id, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.getNewsListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_NEWS_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getNewsListResponse.class, callback);
    }


    /**
     * 获取消息详情
     */
    public void getNewDetail(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.getNewsDetailResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_NEWS_BY_ID)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getNewsDetailResponse.class, callback);
    }

    /**
     * 获取优惠券列表
     *
     * @param id        分类id
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void getCouponsList(int id, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.getCouponsListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_MALL_COUPONS_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getCouponsListResponse.class, callback);
    }


    /**
     * 收藏
     *
     * @param id 规格id
     */
    public void collections(String id, HttpEngine.HttpResponseResultCallback<HttpResponse.collectionResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_MALL_COLLECTION)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.collectionResponse.class, callback);
    }

    /**
     * 获取收藏列表
     *
     * @param id        分类id
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param search    搜索
     * @param callback
     */
    public void getCollectionsList(int id, int pageIndex, int pageSize, String search, HttpEngine.HttpResponseResultListCallback<HttpResponse.getCollectionListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_SEARCH, search);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_MALL_COLLECTION_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getCollectionListResponse.class, callback);
    }

    /**
     * 确认订单
     */
    public void confirmOrder(String ids, HttpEngine.HttpResponseResultCallback<HttpResponse.confirmOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_IDS, ids);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_CONFIRM_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.confirmOrderResponse.class, callback);
    }

    /**
     * 计算金额
     *
     * @param id  优惠券编号
     * @param ids 总金额
     */
    public void calculatedAmount(int id, String ids, HttpEngine.HttpResponseResultCallback<HttpResponse.calculatedAmountResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id);
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_IDS, ids);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_CALCULATED_AMOUNT)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.calculatedAmountResponse.class, callback);
    }


    /**
     * 创建订单
     *
     * @param addressId    地址id
     * @param shoppingCart 是否是购物车 1是 2 不是
     * @param paymentType  支付方式 1微信 2支付宝
     * @param callback
     */
    public void createOrder(int addressId, String goodsInfo, int shoppingCart, int paymentType, HttpEngine.HttpResponseResultCallback<HttpResponse.createOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("addressId", addressId);
        jsonObject.addProperty("goodsInfo", goodsInfo);
        jsonObject.addProperty("shoppingCart", shoppingCart);
        jsonObject.addProperty("paymentType", paymentType);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_CREATE_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.createOrderResponse.class, callback);
    }

    /**
     * 支付宝支付
     *
     * @param callback
     */
    public void orderPay(String ids, String id, HttpEngine.HttpResponseResultCallback<HttpResponse.OrderPayResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ids", ids);
        jsonObject.addProperty("id", id);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_PAY)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.OrderPayResponse.class, callback);
    }

    /**
     * 微信支付
     *
     * @param callback
     */
    public void WXOrderPay(String ids, String id, HttpEngine.HttpResponseResultCallback<HttpResponse.WXOrderPayResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ids", ids);
        jsonObject.addProperty("id", id);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_PAY)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.WXOrderPayResponse.class, callback);
    }

    /**
     * 购物车列表
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void selectShoppingCartList(int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.selectShoppingCartListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SHOPPING_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectShoppingCartListResponse.class, callback);
    }

    /**
     * 加入购物车
     *
     * @param id       规格id
     * @param pageNum  商品数量
     * @param callback
     */
    public void addShoppingCart(String id, String pageNum, HttpEngine.HttpResponseResultCallback<HttpResponse.addShoppingCartResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("pageNum", pageNum);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ADD_SHOPPING_CART)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.addShoppingCartResponse.class, callback);
    }

    /**
     * 我的积分变动流水列表
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void selectMallFlowList(int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallFlowListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_MALL_FLOW_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectMallFlowListResponse.class, callback);
    }

    /**
     * 积分商城
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void getIntegralGoodsList(int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.getIntegralGoodsListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_INTEGRAL_GOODS_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getIntegralGoodsListResponse.class, callback);
    }

    /**
     * 签到
     *
     * @param callback
     */
    public void signIn(HttpEngine.HttpResponseResultCallback<HttpResponse.signInResponse> callback) {
        JsonObject jsonObject = new JsonObject();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SIGN_IN)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.signInResponse.class, callback);
    }

    /**
     * 签到记录
     *
     * @param callback
     */
    public void signInRecord(HttpEngine.HttpResponseResultCallback<HttpResponse.signInRecordResponse> callback) {
        JsonObject jsonObject = new JsonObject();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SIGN_IN_RECORD)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.signInRecordResponse.class, callback);
    }

    public void cancelRequest(String cancelUrl) {
        mHttpEngine.cancel(cancelUrl);
    }
}
