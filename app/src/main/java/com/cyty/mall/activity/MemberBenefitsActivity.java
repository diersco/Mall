package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.adapter.MemberBannerAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.MemberBenefitsInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.DensityUtil;
import com.cyty.mall.util.StringUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 会员权益
 */
public class MemberBenefitsActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.seek_bar)
    AppCompatSeekBar mSeekBar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_vip_grade)
    TextView tvVipGrade;
    @BindView(R.id.tv_condition)
    TextView tvCondition;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;

    private MemberBenefitsInfo memberBenefitsInfo;
    private MemberBannerAdapter mAdapter;
    private List<MemberBenefitsInfo.ListBean> listBeans = new ArrayList<>();

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_member_benefits;
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, MemberBenefitsActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(toolbar.getLayoutParams());
        lp.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(lp);
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(layoutContent.getLayoutParams());
        lps.setMargins(0, getStatusBarHeight() + DensityUtil.dip2px(mContext, 320), 0, 0);
        layoutContent.setLayoutParams(lps);

        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        initWebView();
    }

    @Override
    protected void initData() {

        selectMallMember();
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
     * 会员权益
     */
    private void selectMallMember() {
        HttpManager.getInstance().selectMallMember(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.selectMallMemberResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.selectMallMemberResponse data) {
                        if (result) {
                            memberBenefitsInfo = data.data;
                            listBeans = memberBenefitsInfo.getList();
                            initMallMember();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void initMallMember() {
        tvVip.setText(memberBenefitsInfo.getGradeName());
        tvHint.setText(memberBenefitsInfo.getGradeName2());
        mSeekBar.setProgress(memberBenefitsInfo.getConsumptionAmount());
        mSeekBar.setMax(memberBenefitsInfo.getReConsumptionAmount());
        MemberBenefitsInfo.ListBean listBean = listBeans.get(0);
        tvVipGrade.setText(listBean.getGradeName());
        tvCondition.setText("身份晋升：消费" + listBean.getConsumptionAmount() + "元");
        if (!TextUtils.isEmpty(listBean.getBenefitDescription())) {
            String content = listBean.getBenefitDescription();
            mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
        }
        initBanner();
    }

    /**
     * 加载banner
     */
    private void initBanner() {

        mAdapter = new MemberBannerAdapter(listBeans, mContext);
        banner.setAdapter(mAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(false)
                .setIndicator(new RoundLinesIndicator(mContext));
        banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

            }
        });
        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                MemberBenefitsInfo.ListBean listBean = listBeans.get(position);
                tvVipGrade.setText(listBean.getGradeName());
                tvCondition.setText("身份晋升：消费" + listBean.getConsumptionAmount() + "元");
                if (!TextUtils.isEmpty(listBean.getBenefitDescription())) {
                    String content = listBean.getBenefitDescription();
                    mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageView(this, null);
    }
}