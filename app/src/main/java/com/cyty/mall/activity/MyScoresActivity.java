package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的积分页面
 */
public class MyScoresActivity extends BaseActivity {


    @BindView(R.id.img_unite_return)
    ImageView imgUniteReturn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_to_exchange)
    TextView tvToExchange;
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

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, MyScoresActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_my_scores;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        selectMallFlowList();
    }

    @Override
    protected void initToolBar() {

    }
    /**
     * 我的积分变动流水列表
     */
    private void selectMallFlowList() {
        HttpManager.getInstance().selectMallFlowList(pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallFlowList>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectMallFlowList data) {
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

    @OnClick(R.id.img_unite_return)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, false);
        StatusBarUtil.setTransparent(this);
    }


    @OnClick(R.id.tv_to_exchange)
    public void onExchangeViewClicked() {
        PointsMallActivity.startActivity(mContext);
    }
}