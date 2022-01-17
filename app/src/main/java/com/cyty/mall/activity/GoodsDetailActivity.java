package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.view.KeyEventDispatcher;

import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.adapter.GoodsBannerAdapter;
import com.cyty.mall.adapter.ImageBannerAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.StringUtils;
import com.cyty.mall.view.GoodsFormatPopup;
import com.jaeger.library.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品详情页面
 */
public class GoodsDetailActivity extends BaseActivity {


    @BindView(R.id.banner_class)
    Banner bannerClass;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_sales)
    TextView tvSales;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.tv_evaluation_num)
    TextView tvEvaluationNum;
    @BindView(R.id.webView)
    WebView mWebView;
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
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
//        isUseEventBus(true);
        goodsId = getIntent().getIntExtra(Constant.INTENT_ID, 0);
        initWebView();
    }

    @Override
    protected void initData() {
        getGoodsInfo(goodsId);
    }

    /**
     * 获取商品详情
     */
    private void getGoodsInfo(int goodsId) {
        HttpManager.getInstance().getGoodsInfo(goodsId,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.GoodsInfoResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.GoodsInfoResponse data) {
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
        if(!goodsInfo.getAtlas().isEmpty()) {
            String[] split = goodsInfo.getAtlas().split(",");
            imgList = Arrays.asList(split);
            initBanner();
        }
        if (goodsInfo.getPrice() >= 0) tvGoodsPrice.setText("￥" + goodsInfo.getPrice());
        if (!goodsInfo.getDetails().isEmpty()) tvGoodsName.setText(goodsInfo.getTitle());
        if (goodsInfo.getSalesVolume() >= 0) tvSales.setText("销量：" + goodsInfo.getSalesVolume());
        if (goodsInfo.getTotalStock() >= 0) tvInventory.setText("库存：" + goodsInfo.getTotalStock());
        if (goodsInfo.getCollection() == 1) {
            ivCollect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_goods_collect));
        } else {
            ivCollect.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_goods_un_collect));
        }
        if (!TextUtils.isEmpty(goodsInfo.getDetails())) {
            String content = goodsInfo.getDetails();
            mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
        }
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, GoodsDetailActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initToolBar() {

    }
    /**
     * 加载banner
     */
    private void initBanner() {
        goodsBannerAdapter = new GoodsBannerAdapter(imgList, mContext);
        bannerClass.setAdapter(goodsBannerAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(true)
                .setIndicator(new CircleIndicator(mContext));
        bannerClass.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }
        });
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


    @OnClick({R.id.iv_collect, R.id.iv_share, R.id.tv_shopping_cart, R.id.tv_add_to_cart, R.id.tv_buy_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_collect:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 3).showPopupWindow();
                }
                break;
            case R.id.iv_share:
                break;
            case R.id.tv_shopping_cart:
                break;
            case R.id.tv_add_to_cart:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 2).showPopupWindow();
                }
                break;
            case R.id.tv_buy_now:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 1).showPopupWindow();
                }
                break;
        }
    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setTransparent(this);
    }
}