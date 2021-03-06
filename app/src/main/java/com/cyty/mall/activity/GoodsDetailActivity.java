package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.cyty.mall.event.RefreshGoodsEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.DisplayUtils;
import com.cyty.mall.util.EncodingUtils;
import com.cyty.mall.util.GlideUtil;
import com.cyty.mall.util.ImageUtil;
import com.cyty.mall.util.StringUtils;
import com.cyty.mall.view.GoodsFormatPopup;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ??????????????????
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
    @BindView(R.id.iv_save)
    ImageView ivSave;
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
    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;
    //????????????
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
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        isUseEventBus(true);
        goodsId = getIntent().getIntExtra(Constant.INTENT_ID, 0);
        initWebView();
    }

    @Override
    protected void initData() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getGoodsInfo(goodsId);
                getOrderUserList();
                getAppraiseList();
                refreshLayout.finishRefresh();
            }
        });
        getGoodsInfo(goodsId);
        getOrderUserList();
        getAppraiseList();
    }

    /**
     * ????????????
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
     * ??????????????????
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
                                    tvEvaluationNum.setText("???????????????" + totalNum + "???");
                                    recyclerviewEvaluation.setVisibility(View.VISIBLE);
                                    initAppraiseAdapter();
                                } else {
                                    tvMoreEvaluation.setVisibility(View.GONE);
                                    tvIsEvaluation.setVisibility(View.VISIBLE);
                                    tvEvaluationNum.setText("???????????????0???");
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
     * ??????????????????
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
                            ToastUtils.show(message);
                        }
                    }
                });
    }


    /**
     * ????????????
     */
    @SuppressLint("SetTextI18n")
    private void showData(GoodsInfo goodsInfo) {
        if (!StringUtils.isEmpty(goodsInfo.getVideos())){
            videoPlayer.setVisibility(View.VISIBLE);
            bannerClass.setVisibility(View.GONE);
            initVideo();
        }else {
            videoPlayer.setVisibility(View.GONE);
            bannerClass.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(goodsInfo.getAtlas())) {
                String[] split = goodsInfo.getAtlas().split(",");
                imgList = Arrays.asList(split);
                initBanner();
            }
        }

        if (goodsInfo.getPrice() >= 0) tvGoodsPrice.setText("???" + goodsInfo.getPrice());
        if (!StringUtils.isEmpty(goodsInfo.getDetails())) tvGoodsName.setText(goodsInfo.getTitle());
        if (goodsInfo.getSalesVolume() >= 0) tvSales.setText("?????????" + goodsInfo.getSalesVolume());
        if (goodsInfo.getTotalStock() >= 0) tvInventory.setText("?????????" + goodsInfo.getTotalStock());
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
     * ??????banner
     */
    private void initBanner() {
        goodsBannerAdapter = new GoodsBannerAdapter(imgList, mContext);
        bannerClass.setAdapter(goodsBannerAdapter).addBannerLifecycleObserver(this)//???????????????????????????
                .isAutoLoop(true)
                .setIndicator(new RoundLinesIndicator(mContext));
        bannerClass.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        bannerClass.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }
        });
    }
    /**
     * ????????????
     */
    private void initVideo() {
        //??????title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //???????????????
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //????????????
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
        //????????????????????????
        videoPlayer.setIsTouchWiget(false);
        //????????????
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.with(mContext).displayImage(goodsInfo.getVideoss(), imageView);
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.setUp(goodsInfo.getVideos(), true, "");
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
        settings.setUseWideViewPort(true); // ?????????
        settings.setAllowFileAccess(true); // ??????????????????
        settings.setSupportZoom(false); // ????????????
        settings.setLoadWithOverviewMode(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // ?????????????????????
    }

    private final String url = "https://appmall.ciyuantiaoyue.com/h5/index.html?goodsId=%1s";

    @OnClick({R.id.iv_save, R.id.iv_collect, R.id.iv_share, R.id.tv_shopping_cart, R.id.tv_add_to_cart, R.id.tv_buy_now, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_save:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int with = DisplayUtils.dip2px(mContext, 40);
                        Bitmap bitmap = EncodingUtils.createQRCode(String.format(url, goodsInfo.getGoodsId()), with, with);
                        Bitmap backBitmap = getBitmap(goodsInfo.getThumbnail());
                        Bitmap saveBitmap = ImageUtil.combineBitmap(backBitmap, bitmap);
                        if (ImageUtil.saveBitmap(mContext, saveBitmap, "shareImage")) {
                            ToastUtils.show("????????????!");
                        } else {
                            ToastUtils.show("????????????!");
                        }
                    }
                }).start();
                break;
            case R.id.iv_collect:
                if (goodsInfo != null) {
                    new GoodsFormatPopup(mContext, goodsInfo, 3).showPopupWindow();
                }
                break;
            case R.id.iv_share:
                share(goodsInfo.getTitle(), goodsInfo.getDetails(), goodsInfo.getGoodsId() + "");
                break;
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
                    new GoodsFormatPopup(mContext, goodsInfo, 1).showPopupWindow();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    public Bitmap getBitmap(String imgUrl) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        URL url = null;
        try {
            url = new URL(imgUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                //??????????????????
                inputStream = httpURLConnection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                byte buffer[] = new byte[1024 * 8];
                int len = -1;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                byte[] bu = outputStream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(bu, 0, bu.length);
                return bitmap;
            } else {
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    @Override
    protected void setStatusBar() {

        StatusBarUtil.setTransparentForImageView(this, null);
        setLightStatusBarForM(this, true);
    }


    @Override
    public void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoPlayer != null) {
            videoPlayer.release();
            //????????????
            videoPlayer.setVideoAllCallBack(null);
        }

        GSYVideoManager.releaseAllVideos();
    }

    /**
     * ??????
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshGoodsEvent(RefreshGoodsEvent event) {
        getGoodsInfo(goodsId);
    }

    @OnClick(R.id.tv_more_evaluation)
    public void onViewClicked() {
        ProductEvaluationListActivity.startActivity(mContext, goodsId);
    }
}