package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.NewsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;

import butterknife.BindView;

/**
 * 消息详情页面
 */
public class NotificationDetailActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_message_title)
    TextView tvMessageTitle;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private int id;
    private NewsInfo newsInfo;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvTitle.setText("消息通知");
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, NotificationDetailActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_notification_detail;
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initData() {
        getNewDetail();
    }


    /**
     * 获取消息详情
     */
    private void getNewDetail() {
        HttpManager.getInstance().getNewDetail(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getNewsDetailResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getNewsDetailResponse data) {
                        if (result) {
                            newsInfo = data.data;
                            intNewsInfo();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void intNewsInfo() {
        if (!newsInfo.getNewsTitle().isEmpty()) {
            tvMessageTitle.setText(newsInfo.getNewsTitle());
        }
        if (!newsInfo.getNewsContent().isEmpty()) {
            tvMessage.setText(newsInfo.getNewsContent());
        }
    }
}