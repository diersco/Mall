package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.ArticleInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.StringUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * 通用 隐私协议/用户协议
 */
public class CommonActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView mWebView;
    // 从哪里跳进来的  1=关于我们,2=用户协议,3=隐私政策
    private int type;
    private ArticleInfo mArticleInfo;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_common;
    }

    public static void startActivity(Context mContext, int type) {
        Intent mIntent = new Intent(mContext, CommonActivity.class);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, 0);
        switch (type) {
            case 1:
                tvTitle.setText("关于我们");
                break;
            case 2:
                tvTitle.setText("用户协议");
                break;
            case 3:
                tvTitle.setText("隐私政策");
                break;
            case 5:
                tvTitle.setText("签到规则");
                break;

        }
    }

    @Override
    protected void initData() {
        initWebView();
        getArticle();
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
     * 获取文章数据
     */
    private void getArticle() {
        HttpManager.getInstance().getArticle(type,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getArticleResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getArticleResponse data) {
                        if (result) {
                            mArticleInfo = data.data;
                            if (!TextUtils.isEmpty(mArticleInfo.getArticleContent())) {
                                String content = mArticleInfo.getArticleContent();
                                mWebView.loadDataWithBaseURL(null, StringUtils.getHtmlData(content), "text/html", "UTF-8", null);
                            }
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}