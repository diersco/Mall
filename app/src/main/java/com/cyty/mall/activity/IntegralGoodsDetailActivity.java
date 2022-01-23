package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyty.mall.R;
import com.cyty.mall.adapter.GoodsBannerAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.StringUtils;
import com.cyty.mall.view.GoodsFormatPopup;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分商品详情页面
 */
public class IntegralGoodsDetailActivity extends BaseActivity {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_sales)
    TextView tvSales;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    //商品编号
    private int goodsId;
    private GoodsInfo goodsInfo;
    private List<String> imgList = new ArrayList<>();
    private GoodsBannerAdapter goodsBannerAdapter;
    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_integral_goods_detail;
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, IntegralGoodsDetailActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        goodsId = getIntent().getIntExtra(Constant.INTENT_ID, 0);
        initWebView();
    }

    @Override
    protected void initData() {
        getGoodsInfo(goodsId);
    }

    @Override
    protected void initToolBar() {

    }

    /**
     * 获取商品详情
     */
    private void getGoodsInfo(int goodsId) {
        HttpManager.getInstance().getIntegralGoodsInfoById(goodsId,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getIntegralGoodsInfoByIdResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getIntegralGoodsInfoByIdResponse data) {
                        if (result) {
                            goodsInfo = data.data;
                            showData(goodsInfo);
                        } else {

                        }
                    }
                });
    }

    /**
     * 展示数据
     */
    @SuppressLint("SetTextI18n")
    private void showData(GoodsInfo goodsInfo) {
        if (!StringUtils.isEmpty(goodsInfo.getAtlas())) {
            String[] split = goodsInfo.getAtlas().split(",");
            imgList = Arrays.asList(split);
            initBanner();
        }
        if (goodsInfo.getIntegral() >= 0) tvGoodsPrice.setText(goodsInfo.getIntegral()+ "积分");
        if (!StringUtils.isEmpty(goodsInfo.getDetails())) tvGoodsName.setText(goodsInfo.getTitle());
        if (goodsInfo.getSalesVolume() >= 0) tvSales.setText("销量：" + goodsInfo.getSalesVolume());
        if (goodsInfo.getTotalStock() >= 0) tvInventory.setText("库存：" + goodsInfo.getTotalStock());
        if (!TextUtils.isEmpty(goodsInfo.getDetails())) {
            String content = goodsInfo.getDetails();
            mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        mWebView.setVerticalScrollBarEnabled(false);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setGeolocationEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(false); // 支持缩放
        settings.setLoadWithOverviewMode(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
    }
    /**
     * 加载banner
     */
    private void initBanner() {
        goodsBannerAdapter = new GoodsBannerAdapter(imgList, mContext);
        banner.setAdapter(goodsBannerAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(true)
                .setIndicator(new CircleIndicator(mContext));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }
        });
    }
    @OnClick(R.id.tv_sure)
    public void onViewClicked() {
        if (goodsInfo != null) {
            new GoodsFormatPopup(mContext, goodsInfo, 4).showPopupWindow();
        }
    }
}