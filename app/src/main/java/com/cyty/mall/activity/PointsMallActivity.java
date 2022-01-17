package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 积分商城
 */
public class PointsMallActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_points_mall;
    }

    @Override
    protected void initView() {

    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, PointsMallActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("积分商城");
    }

    @Override
    protected void initData() {
        getIntegralGoodsList();
    }
    /**
     * 积分商城
     */
    private void getIntegralGoodsList() {
        HttpManager.getInstance().getIntegralGoodsList(pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getIntegralGoodsList>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getIntegralGoodsList data) {
                        if (state != STATE_MORE) {
//                            cartGoodsInfoList.clear();
                        }
                        if (result) {
//                            if (data.rows != null) {
//                                cartGoodsInfoList.addAll(data.rows);
//                                total = totalNum;
//                                if (data.rows.size() > 0) {
//                                    showContent();
//                                } else {
//                                    showEmpty();
//                                }
//                            }
//                            showShoppingCart();
                        } else {
//                            showShoppingCart();
//                            if (state != STATE_MORE) {
//                                cartGoodsInfoList.clear();
//                            }
                            ToastUtils.show(message);
                        }
//                        refreshLayout.finishRefresh();
//                        refreshLayout.finishLoadMore();
                    }

                });

    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}