package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.AppraiseAdapter;
import com.cyty.mall.adapter.GoodsBannerAdapter;
import com.cyty.mall.adapter.OrderUserAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AppraiseInfo;
import com.cyty.mall.bean.GoodsInfo;
import com.cyty.mall.bean.OrderUserInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.event.MainJumpEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.StringUtils;
import com.cyty.mall.view.GoodsFormatPopup;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 秒杀商品详情页面
 */
public class SeckillGoodsDetailActivity extends BaseActivity {


    @BindView(R.id.banner_class)
    Banner bannerClass;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_spike_price)
    TextView tvSpikePrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_sales)
    TextView tvSales;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.tv_evaluation_num)
    TextView tvEvaluationNum;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview_num)
    RecyclerView recyclerviewNum;
    @BindView(R.id.tv_is_evaluation)
    TextView tvIsEvaluation;
    @BindView(R.id.recyclerview_evaluation)
    RecyclerView recyclerviewEvaluation;
    @BindView(R.id.tv_more_evaluation)
    TextView tvMoreEvaluation;
    //商品编号
    private int goodsId;

    private GoodsInfo goodsInfo;
    private List<String> imgList = new ArrayList<>();
    private List<OrderUserInfo> orderUserInfoList = new ArrayList<>();
    private List<AppraiseInfo> appraiseInfoList = new ArrayList<>();
    private GoodsBannerAdapter goodsBannerAdapter;
    private OrderUserAdapter orderUserAdapter;
    private AppraiseAdapter appraiseAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_seckill_goods_detail;
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, SeckillGoodsDetailActivity.class);
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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getSeckillGoodsInfoById(goodsId);
                getOrderUserList();
                getAppraiseList();
                refreshLayout.finishRefresh();
            }
        });
        getSeckillGoodsInfoById(goodsId);
        getOrderUserList();
        getAppraiseList();
    }

    /**
     * 购买人数
     */
    private void getOrderUserList() {
        HttpManager.getInstance().getOrderUserList(goodsId, 1, 100,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getOrderUserListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getOrderUserListResponse data) {
                        if (result) {
                            if (data.rows != null) {
                                orderUserInfoList = data.rows;
                                initAdapter();
                            }
                        } else {
                            ToastUtils.show(message);
                        }
                    }

                });
    }

    private void initAdapter() {
        orderUserAdapter = new OrderUserAdapter(orderUserInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewNum.setLayoutManager(layoutManager);
        recyclerviewNum.setItemAnimator(new DefaultItemAnimator());
        recyclerviewNum.setAdapter(orderUserAdapter);
    }

    /**
     * 获取商品评价
     */
    private void getAppraiseList() {
        HttpManager.getInstance().getAppraiseList(goodsId, 1, 1,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getAppraiseListResponse>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getAppraiseListResponse data) {
                        if (result) {
                            if (data.rows != null) {
                                appraiseInfoList = data.rows;
                                if (appraiseInfoList.size() > 0) {
                                    tvIsEvaluation.setVisibility(View.GONE);
                                    tvMoreEvaluation.setVisibility(View.VISIBLE);
                                    tvEvaluationNum.setText("商品评价（" + totalNum + "）");
                                    recyclerviewEvaluation.setVisibility(View.VISIBLE);
                                    initAppraiseAdapter();
                                } else {
                                    tvMoreEvaluation.setVisibility(View.GONE);
                                    tvIsEvaluation.setVisibility(View.VISIBLE);
                                    tvEvaluationNum.setText("商品评价（0）");
                                    recyclerviewEvaluation.setVisibility(View.GONE);

                                }
                            }
                        } else {
                            ToastUtils.show(message);
                        }
                    }

                });
    }

    private void initAppraiseAdapter() {
        appraiseAdapter = new AppraiseAdapter(appraiseInfoList);
        recyclerviewEvaluation.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewEvaluation.setItemAnimator(new DefaultItemAnimator());
        recyclerviewEvaluation.setAdapter(appraiseAdapter);
    }

    /**
     * 获取商品详情
     */
    private void getSeckillGoodsInfoById(int goodsId) {
        HttpManager.getInstance().getSeckillGoodsInfoById(goodsId,
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
        if (!StringUtils.isEmpty(goodsInfo.getAtlas())) {
            String[] split = goodsInfo.getAtlas().split(",");
            imgList = Arrays.asList(split);
            initBanner();
        }
        tvSpikePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (goodsInfo.getPriceSpike() >= 0) tvGoodsPrice.setText("￥" + goodsInfo.getPriceSpike());
        if (goodsInfo.getPrice() >= 0) tvSpikePrice.setText("￥" + goodsInfo.getPrice());
        if (!StringUtils.isEmpty(goodsInfo.getDetails())) tvGoodsName.setText(goodsInfo.getTitle());
        if (goodsInfo.getSalesVolume() >= 0) tvSales.setText("销量：" + goodsInfo.getSalesVolume());
        if (goodsInfo.getTotalStock() >= 0) tvInventory.setText("库存：" + goodsInfo.getTotalStock());
        if (!TextUtils.isEmpty(goodsInfo.getDetails())) {
            String content = goodsInfo.getDetails();
            mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
        }
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
                .setIndicator(new RoundLinesIndicator(mContext));
        bannerClass.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
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


    @OnClick({R.id.tv_shopping_cart, R.id.tv_add_to_cart, R.id.tv_buy_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_shopping_cart:
                finish();
                EventBus.getDefault().post(new MainJumpEvent(2));
                break;
            case R.id.tv_add_to_cart:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 2).showPopupWindow();
                }
                break;
            case R.id.tv_buy_now:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 5).showPopupWindow();
                }
                break;
        }
    }


    @Override
    protected void setStatusBar() {

        StatusBarUtil.setTransparentForImageView(this,null);
        setLightStatusBarForM(this, true);
    }


    @OnClick(R.id.tv_more_evaluation)
    public void onViewClicked() {

        ProductEvaluationListActivity.startActivity(mContext, goodsId);
    }
}