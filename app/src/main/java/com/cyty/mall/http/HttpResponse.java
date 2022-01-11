package com.cyty.mall.http;

import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.bean.ClassIfPageBannerInfo;
import com.cyty.mall.bean.ClassificationCommodity;
import com.cyty.mall.bean.GoodsListInfo;
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
//    /**
//     * 分类页数据——banner——分类
//     **/
//    @POST("/stateless/getClassifPageData")
//    Observable<StatusCode<Object>> getClassifPageData(
//            @Body RequestBean requestBean);

    /**
     * 获取分类页数据——banner——分类
     */
    public class ClassIfPageBannerResponse extends HttpResponse {
        public ClassIfPageBannerInfo data;
    }

    /**
     * 获取商品
     */
    public class ClassificationCommodityResponse extends HttpResponse {
        public List<ClassificationCommodity> rows;
    }

    /**
     * 获取商品列表
     */
    public class GoodsListResponse extends HttpResponse {
        public List<GoodsListInfo> rows;
    }

    /**
     * 获取地址列表
     */
    public class addressListResponse extends HttpResponse {
        public List<AddressInfo> rows;
    }

    /**
     * 新增或修改地址
     */
    public class addOrReviseAddressResponse extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 选择默认地址
     */
    public class reviseDefaultsAddress extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 删除
     */
    public class deleteAddress extends HttpResponse {
        public AddressInfo data;
    }

    /**
     * 获取验证码
     */
    public class SendSmsCodeResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 验证码登录
     */
    public class SmsLoginResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 获取用户信息
     */
    public class getUserInfoResponse extends HttpResponse {
        public UserInfo data;
    }

    /**
     * 个人中心未读标志数量
     */
    public class selectReadNewsResponse extends HttpResponse {
        public UserInfo data;
    }
}
