package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.ArticleInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.StringUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到
 */
public class SignInActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_ask)
    TextView tvAsk;
    @BindView(R.id.tv_sign_in_num)
    TextView tvSignInNum;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_score_dynamics)
    TextView tvScoreDynamics;
    @BindView(R.id.webView)
    WebView mWebView;
    private int type = 4;
    private ArticleInfo mArticleInfo;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_sign_in;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, SignInActivity.class);
        mContext.startActivity(mIntent);
    }


    @Override
    protected void initView() {
        initWebView();
    }

    @Override
    protected void initData() {
        signIn();
        signInRecord();
        getArticle();
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, null);
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
     * 签到
     */
    private void signIn() {
        HttpManager.getInstance().signIn(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.signInResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.signInResponse data) {
                        if (result) {
                            ToastUtils.show("签到成功！");
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 签到记录
     */
    private void signInRecord() {
        HttpManager.getInstance().signInRecord(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.signInRecordResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.signInRecordResponse data) {
                        if (result) {
                            ToastUtils.show("签到成功！");
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
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

    @OnClick({R.id.tv_ask, R.id.tv_score_dynamics})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ask:
                break;
            case R.id.tv_score_dynamics:
                MyScoresActivity.startActivity(mContext);
                break;
        }
    }
}