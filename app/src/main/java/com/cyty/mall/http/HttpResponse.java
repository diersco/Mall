package com.cyty.mall.http;

import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.bean.ArticleInfo;
import com.cyty.mall.bean.CartGoodsInfo;
import com.cyty.mall.bean.ClassIfPageBannerInfo;
import com.cyty.mall.bean.ClassificationCommodity;
import com.cyty.mall.bean.CollectionInfo;
import com.cyty.mall.bean.ConfirmOrderInfo;
import com.cyty.mall.bean.CouponInfo;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.bean.GoodsListInfo;
import com.cyty.mall.bean.HomeDataInfo;
import com.cyty.mall.bean.HomeSecKillGoodsInfo;
import com.cyty.mall.bean.MyIntegralInfo;
import com.cyty.mall.bean.NewsInfo;
import com.cyty.mall.bean.OrderDetailInfo;
import com.cyty.mall.bean.OrderListInfo;
import com.cyty.mall.bean.ScoreGoodsInfo;
import com.cyty.mall.bean.SignInInfo;
import com.cyty.mall.bean.SignInfo;
import com.cyty.mall.bean.UserInfo;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 14:00
 * @描述
 */
public class HttpResponse {
    public String msg;
    public int code;
    public int total;


    /**
     * 获取首页
     */
    public static class getHomePageDataResponse extends HttpResponse {
        public HomeDataInfo data;
    }

    /**
     * 获取文章
     */
    public static class getArticleResponse extends HttpResponse {
        public ArticleInfo data;
    }

    /**
     * 获取分类页数据——banner——分类
     */
    public static class ClassIfPageBannerResponse extends HttpResponse {
        public ClassIfPageBannerInfo data;
    }

    /**
     * 获取商品
     */
    public static class ClassificationCommodityResponse extends HttpResponse {
        public List<ClassificationCommodity> rows;
    }

    /**
     * 获取商品列表
     */
    public static class GoodsListResponse extends HttpResponse {
        public List<GoodsListInfo> rows;
    }

    /**
     * 获取商品详情
     */
    public static class GoodsInfoResponse extends HttpResponse {
        public GoodsInfo data;
    }

    /**
     * 获取地址列表
     */
    public static class addressListResponse extends HttpResponse {
        public List<AddressInfo> rows;
    }

    /**
     * 新增或修改地址
     */
    public static class addOrReviseAddressResponse extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 选择默认地址
     */
    public static class reviseDefaultsAddressResponse extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 获取默认地址
     */
    public static class getDefaultsAddressResponse extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 删除
     */
    public static class deleteAddressResponse extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 获取验证码
     */
    public static class SendSmsCodeResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 验证码登录
     */
    public static class SmsLoginResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 微信登录
     */
    public static class weChatLoginResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 一键登录
     */
    public static class easyLoginResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 获取用户信息
     */
    public static class getUserInfoResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 个人中心未读标志数量
     */
    public static class selectReadNewsResponse extends HttpResponse {
        public int data;
    }

    /**
     * 获取消息列表
     */
    public static class getNewsListResponse extends HttpResponse {
        public List<NewsInfo> rows;
    }

    /**
     * 获取消息详情
     */
    public static class getNewsDetailResponse extends HttpResponse {
        public NewsInfo data;
    }

    /**
     * 获取优惠券列表
     */
    public static class getCouponsListResponse extends HttpResponse {
        public List<CouponInfo> rows;
    }

    /**
     * 收藏
     */
    public static class collectionResponse extends HttpResponse {
        public GoodsInfo data;
    }

    /**
     * 获取收藏列表
     */
    public static class getCollectionListResponse extends HttpResponse {
        public List<CollectionInfo> rows;
    }

    /**
     * 确认订单
     */
    public static class confirmOrderResponse extends HttpResponse {
        public ConfirmOrderInfo data;
    }

    /**
     * 计算金额
     */
    public static class calculatedAmountResponse extends HttpResponse {
        public ConfirmOrderInfo data;
    }

    /**
     * 创建订单
     */
    public static class createOrderResponse extends HttpResponse {
        public String data;
    }

    /**
     * 支付宝支付订单
     */
    public static class OrderPayResponse extends HttpResponse {
        public String data;
    }

    /**
     * 微信支付订单
     */
    public static class WXOrderPayResponse extends HttpResponse {
        public SignInfo data;
    }

    /**
     * 订单列表
     */
    public static class selectMallPaymentOrderListResponse extends HttpResponse {
        public List<OrderListInfo> rows;
    }

    /**
     * 订单详情
     */
    public static class selectMallOrderDetailsByIdResponse extends HttpResponse {
        public OrderDetailInfo data;
    }

    /**
     * 加入购物车
     */
    public static class addShoppingCartResponse extends HttpResponse {
        public ConfirmOrderInfo data;
    }

    /**
     * 购物车列表
     */
    public static class selectShoppingCartListResponse extends HttpResponse {
        public List<CartGoodsInfo> rows;
    }

    /**
     * 我的积分变动流水列表
     */
    public static class selectMallFlowListResponse extends HttpResponse {
        public List<MyIntegralInfo> rows;
    }

    /**
     * 积分商城
     */
    public static class getIntegralGoodsListResponse extends HttpResponse {
        public List<ScoreGoodsInfo> rows;
    }

    /**
     * 签到
     */
    public static class signInResponse extends HttpResponse {
        public SignInInfo data;
    }

    /**
     * 签到记录
     */
    public static class signInRecordResponse extends HttpResponse {
        public SignInInfo data;
    }

    /**
     * 获取秒杀排期商品
     */
    public static class selectSchedulingListResponse extends HttpResponse {
        public List<ScoreGoodsInfo> rows;
    }

    /**
     * 首页秒杀
     */
    public static class getSeckillGoodsListResponse extends HttpResponse {
        public List<HomeSecKillGoodsInfo.ListBean> rows;
    }
}
