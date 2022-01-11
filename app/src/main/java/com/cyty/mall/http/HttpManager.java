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
     * 获取文章
     *
     * @param id       分类id 1=关于我们,2=用户协议,3=隐私政策,4=签到说明,5=积分说明
     * @param callback callback
     */
    public void getArticle(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.ClassIfPageBannerResponse> callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_GET_ARTICLE)
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.ClassIfPageBannerResponse.class, callback);
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
    public void reviseDefaultsAddress(int id, int defaults, HttpEngine.HttpResponseResultCallback<HttpResponse.reviseDefaultsAddress> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("defaults", defaults + "");
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_ADD_MALL_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.reviseDefaultsAddress.class, callback);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    public void deleteAddress(int id, HttpEngine.HttpResponseResultCallback<HttpResponse.deleteAddress> callback) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(HttpConfig.RequestKey.FORM_KEY_ID, id + "");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), String.valueOf(jsonObject));
        Request request = new Request.Builder().url(ServerApiConstants.URL_REMOVE_MALL_ADDRESS)
                .addHeader("Authorization", "Bearer " + MkUtils.decodeString(MKParameter.TOKEN))
                .post(requestBody)
                .build();
        mHttpEngine.request(request, HttpResponse.deleteAddress.class, callback);
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
     * 获取用户信息
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

    public void cancelRequest(String cancelUrl) {
        mHttpEngine.cancel(cancelUrl);
    }
}
