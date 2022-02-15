package com.cyty.mall.http;

import android.content.Context;

import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.util.MkUtils;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private Context mContext;

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
        this.mContext = context;
    }

    /**
     * 上传图片
     *
     * @param callback
     */
    public void uploadImg(File file, HttpEngine.HttpResponseResultCallback<HttpResponse.uploadImgResponse> callback) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        multipartBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request request = new Request.Builder().url(ServerApiConstants.URL_OSS_UPLOAD)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.uploadImgResponse.class, callback, mContext);
    }

    /**
     * 获取多张图片
     *
     * @param callback callback
     */
    public void uploadImgs(List<String> mImgUrls, HttpEngine.HttpResponseResultCallback<HttpResponse.uploadImgsResponse> callback) {
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        for (int i = 0; i < mImgUrls.size(); i++) {
            File file = new File(mImgUrls.get(i));
            if (file != null) {
                multipartBodyBuilder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            }
        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request request = new Request.Builder().url(ServerApiConstants.URL_OSS_UPLOADS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.uploadImgsResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getHomePageDataResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getArticleResponse.class, callback, mContext);
    }

    /**
     * banner
     *
     * @param callback callback
     */
    public void getBanner(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.ClassIfPageBannerResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_BANNER)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.ClassIfPageBannerResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.ClassificationCommodityResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.GoodsListResponse.class, callback, mContext);
    }

    /**
     * 搜索
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param search    搜索信息
     * @param callback
     */
    public void searchGoods(int pageIndex, int pageSize, String search, HttpEngine.HttpResponseResultListCallback<HttpResponse.GoodsListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_SEARCH, search);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_GOODS_LIST)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.GoodsListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.GoodsInfoResponse.class, callback, mContext);
    }

    /**
     * 获取秒杀商品详情
     *
     * @param id
     * @param callback
     */
    public void getSeckillGoodsInfoById(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.GoodsInfoResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_SECKILL_GOODS_INFO)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.GoodsInfoResponse.class, callback, mContext);
    }

    /**
     * 获取积分详情
     *
     * @param id
     * @param callback
     */
    public void getIntegralGoodsInfoById(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.getIntegralGoodsInfoByIdResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_INTEGRAL_GOODS_INFO)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getIntegralGoodsInfoByIdResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.addressListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.addOrReviseAddressResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.reviseDefaultsAddressResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getDefaultsAddressResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.deleteAddressResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.SendSmsCodeResponse.class, callback, mContext);
    }

    /**
     * 获取绑定验证码
     *
     * @param cellPhoneNumber 手机号
     * @param callback
     */
    public void sendCode(String cellPhoneNumber, HttpEngine.HttpResponseResultCallback<HttpResponse.SendCodeResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cellPhoneNumber", cellPhoneNumber);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SEND_CODE)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.SendCodeResponse.class, callback, mContext);
    }

    /**
     * 获取绑定验证码
     *
     * @param cellPhoneNumber 手机号
     * @param callback
     */
    public void bindMobileNumber(String cellPhoneNumber, String code, String headPortrait, String unionId, String wechatNickname,
                                 HttpEngine.HttpResponseResultCallback<HttpResponse.bindMobileNumberResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cellPhoneNumber", cellPhoneNumber);
        jsonObject.addProperty("code", code);
        jsonObject.addProperty("headPortrait", headPortrait);
        jsonObject.addProperty("unionId", unionId);
        jsonObject.addProperty("wechatNickname", wechatNickname);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_BIND_PHONE)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.bindMobileNumberResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.SmsLoginResponse.class, callback, mContext);
    }

    /**
     * 微信登录
     *
     * @param callback
     */
    public void weChatLogin(String headPortrait, String unionId, String weChatNickname, HttpEngine.HttpResponseResultCallback<HttpResponse.weChatLoginResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("headPortrait", headPortrait);
        jsonObject.addProperty("unionId", unionId);
        jsonObject.addProperty("wechatNickname", weChatNickname);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_WE_CHAT_LOGIN)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.weChatLoginResponse.class, callback, mContext);
    }

    /**
     * 一键登录
     *
     * @param callback
     */
    public void easyLogin(String token, HttpEngine.HttpResponseResultCallback<HttpResponse.easyLoginResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, "2");
        jsonObject.addProperty("search", token);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_EASY_LOGIN)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.easyLoginResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getUserInfoResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.selectReadNewsResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getNewsListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getNewsDetailResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getCouponsListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.collectionResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getCollectionListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.confirmOrderResponse.class, callback, mContext);
    }

    /**
     * 确认秒杀订单
     */
    public void seckillConfirmOrder(String ids, HttpEngine.HttpResponseResultCallback<HttpResponse.confirmOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_IDS, ids);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SEC_KILL_CONFIRM_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.confirmOrderResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.calculatedAmountResponse.class, callback, mContext);
    }

    /**
     * 秒杀计算金额
     *
     * @param id  优惠券编号
     * @param ids 总金额
     */
    public void seckillCalculatedAmount(int id, String ids, HttpEngine.HttpResponseResultCallback<HttpResponse.calculatedAmountResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id);
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_IDS, ids);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SEC_KILL_CALCULATED_AMOUNT)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.calculatedAmountResponse.class, callback, mContext);
    }

    /**
     * 创建订单
     *
     * @param addressId    地址id
     * @param shoppingCart 是否是购物车 1是 2 不是
     * @param paymentType  支付方式 1微信 2支付宝
     * @param callback
     */
    public void createOrder(int addressId, String goodsInfo, int shoppingCart, int paymentType, int couponId, HttpEngine.HttpResponseResultCallback<HttpResponse.createOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("addressId", addressId);
        jsonObject.addProperty("goodsInfo", goodsInfo);
        jsonObject.addProperty("shoppingCart", shoppingCart);
        jsonObject.addProperty("paymentType", paymentType);
        if (couponId > 0) {
            jsonObject.addProperty("couponId", couponId);
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_CREATE_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.createOrderResponse.class, callback, mContext);
    }

    /**
     * 创建秒杀订单
     *
     * @param addressId    地址id
     * @param shoppingCart 是否是购物车 1是 2 不是
     * @param paymentType  支付方式 1微信 2支付宝
     * @param callback
     */
    public void seckillCreateOrder(int addressId, String goodsInfo, int shoppingCart, int paymentType, int couponId, HttpEngine.HttpResponseResultCallback<HttpResponse.createOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("addressId", addressId);
        jsonObject.addProperty("goodsInfo", goodsInfo);
        jsonObject.addProperty("shoppingCart", shoppingCart);
        jsonObject.addProperty("paymentType", paymentType);
        if (couponId > 0) {
            jsonObject.addProperty("couponId", couponId);
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SEC_KILL_CREATE_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.createOrderResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.OrderPayResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.WXOrderPayResponse.class, callback, mContext);
    }

    /**
     * 订单列表
     *
     * @param id        订单状态（0全部 1待支付 2待发货 3待收货 4已完成 5已取消）
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void selectMallPaymentOrderList(int id, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallPaymentOrderListResponse> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_ORDER_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectMallPaymentOrderListResponse.class, callback, mContext);
    }

    /**
     * 订单详情
     */
    public void selectMallOrderDetailsById(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.selectMallOrderDetailsByIdResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_ORDER_DETAILS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectMallOrderDetailsByIdResponse.class, callback, mContext);
    }

    /**
     * 退款详情
     */
    public void selectAfterSaleById(String id, HttpEngine.HttpResponseResultCallback<HttpResponse.selectAfterSaleByIdResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_SELECTED_AFTER_SALE)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectAfterSaleByIdResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.selectShoppingCartListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.addShoppingCartResponse.class, callback, mContext);
    }
    /**
     * 删除购物车
     *
     * @param callback
     */
    public void deleteShoppingCart(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.deleteShoppingCartResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_DELETE_SHOPPING_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.deleteShoppingCartResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.selectMallFlowListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.getIntegralGoodsListResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.signInResponse.class, callback, mContext);
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
        mHttpEngine.request(request, HttpResponse.signInRecordResponse.class, callback, mContext);
    }

    /**
     * 获取首页秒杀
     *
     * @param callback
     */
    public void getSeckillGoodsList(HttpEngine.HttpResponseResultListCallback<HttpResponse.getSeckillGoodsListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_SEC_KILL)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getSeckillGoodsListResponse.class, callback, mContext);
    }

    /**
     * 获取秒杀排期商品
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void selectSchedulingList(String starTime, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.selectSchedulingListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("search", starTime);
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_SCHEDULING)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectSchedulingListResponse.class, callback, mContext);
    }

    /**
     * 购买人数
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void getOrderUserList(int id, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.getOrderUserListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_ORDER_USER_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getOrderUserListResponse.class, callback, mContext);
    }

    /**
     * 商品评价
     *
     * @param pageIndex 分页
     * @param pageSize  每页个数
     * @param callback
     */
    public void getAppraiseList(int id, int pageIndex, int pageSize, HttpEngine.HttpResponseResultListCallback<HttpResponse.getAppraiseListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_INDEX, pageIndex + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_PAGE_SIZE, pageSize + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_APPRAISE_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getAppraiseListResponse.class, callback, mContext);
    }

    /**
     * 常见问题
     *
     * @param callback
     */
    public void getProblemList(HttpEngine.HttpResponseResultListCallback<HttpResponse.getProblemListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_PROBLEM_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getProblemListResponse.class, callback, mContext);
    }

    /**
     * 物流信息
     *
     * @param callback
     */
    public void queryLogistics(int orderID, HttpEngine.HttpResponseResultCallback<HttpResponse.queryLogisticsResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, orderID + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_QUERY_LOGISTICS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.queryLogisticsResponse.class, callback, mContext);
    }

    /**
     * 立即兑换
     *
     * @param callback
     */
    public void pointsExchangeCommodity(String addressId, String specId, HttpEngine.HttpResponseResultCallback<HttpResponse.pointsExchangeCommodityResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("addressId", addressId);
        jsonObject.addProperty("specId", specId);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_INTEGRAL_EXCHANGE_COMMODITY)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.pointsExchangeCommodityResponse.class, callback, mContext);
    }


    /**
     * 兑换列表
     *
     * @param callback
     */
    public void selectMallExchangeList(HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallExchangeListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_INTEGRAL_EXCHANGE_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectMallExchangeListResponse.class, callback, mContext);
    }

    /**
     * 会员权益
     *
     * @param callback
     */
    public void selectMallMember(HttpEngine.HttpResponseResultCallback<HttpResponse.selectMallMemberResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_MALL_MEMBER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectMallMemberResponse.class, callback, mContext);
    }

    /**
     * 售后
     *
     * @param callback
     */
    public void afterSale(String appeal, String appealPicture, String orderDetailsId, String salesType, HttpEngine.HttpResponseResultCallback<HttpResponse.afterSaleResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("appeal", appeal);
        jsonObject.addProperty("appealPicture", appealPicture);
        jsonObject.addProperty("orderDetailsId", orderDetailsId);
        jsonObject.addProperty("salesType", salesType);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_AFTER_SALE)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.afterSaleResponse.class, callback, mContext);
    }

    /**
     * 秒杀排期
     *
     * @param callback
     */
    public void selectSeckillLis(HttpEngine.HttpResponseResultListCallback<HttpResponse.selectSeckillListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_SELECT_SEC_KILL_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.selectSeckillListResponse.class, callback, mContext);
    }

    /**
     * 退款与售后
     *
     * @param callback
     */
    public void getMallAftermarketList(HttpEngine.HttpResponseResultListCallback<HttpResponse.getMallAftermarketListResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_AFTER_MARKET_LIST)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.getMallAftermarketListResponse.class, callback, mContext);
    }

    /**
     * 修改个人信息
     *
     * @param callback
     */
    public void updateUserInfo(String cellPhoneNumber, String dateBirth, String headPortrait, String nickname, HttpEngine.HttpResponseResultCallback<HttpResponse.updateUserInfoResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("cellPhoneNumber", cellPhoneNumber);
        jsonObject.addProperty("dateBirth", dateBirth);
        jsonObject.addProperty("headPortrait", headPortrait);
        jsonObject.addProperty("nickname", nickname);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_UPDATE_USER_INFO)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.updateUserInfoResponse.class, callback, mContext);
    }

    /**
     * 评价
     *
     * @param callback
     */
    public void addAppraise(String comment, String commentPicture, String orderDetailsId, HttpEngine.HttpResponseResultCallback<HttpResponse.addAppraiseResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("comment", comment);
        jsonObject.addProperty("commentPicture", commentPicture);
        jsonObject.addProperty("orderDetailsId", orderDetailsId);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_ADD_APPRAISE)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.addAppraiseResponse.class, callback, mContext);
    }

    /**
     * 删除售后记录
     *
     * @param callback
     */
    public void deleteAfterSale(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.deleteAfterSaleResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_DELETE_AFTER_SALE)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.deleteAfterSaleResponse.class, callback, mContext);
    }

    /**
     * 取消售后
     *
     * @param callback
     */
    public void cancelAfterSale(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.cancelAfterSaleResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_CANCEL_AFTER_SALE)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.cancelAfterSaleResponse.class, callback, mContext);
    }

    /**
     * 确认收货
     *
     * @param callback
     */
    public void confirmReceipts(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.confirmReceiptsResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_CONFIRM_RECEIPTS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.confirmReceiptsResponse.class, callback, mContext);
    }

    /**
     * 取消订单
     *
     * @param callback
     */
    public void cancelOrder(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.cancelOrderResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_CANCEL_ORDER)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.cancelOrderResponse.class, callback, mContext);
    }

    /**
     * 填写订单编号
     *
     * @param callback
     */
    public void addReturnWaybill(int id, String ids, HttpEngine.HttpResponseResultCallback<HttpResponse.addReturnWaybillResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_IDS, ids + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ORDER_ADD_RETURN_WAY)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.addReturnWaybillResponse.class, callback, mContext);
    }

    public void cancelRequest(String cancelUrl) {
        mHttpEngine.cancel(cancelUrl);
    }
}
