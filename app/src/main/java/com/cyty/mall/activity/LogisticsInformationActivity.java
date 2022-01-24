package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.adapter.LogisticsInformationAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.LogisticsInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 物流信息
 */
public class LogisticsInformationActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_express_delivery)
    TextView tvExpressDelivery;
    @BindView(R.id.tv_order_numbering)
    TextView tvOrderNumbering;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private int id;
    private String information;
    private List<LogisticsInfo.DataBean> dataBeanList = new ArrayList<>();
    private LogisticsInformationAdapter mAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("物流详情");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_logistics_information;
    }

    public static void startActivity(Context mContext, int id) {
        Intent mIntent = new Intent(mContext, LogisticsInformationActivity.class);
        mIntent.putExtra(Constant.INTENT_ID, id);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra(Constant.INTENT_ID, 0);
    }

    @Override
    protected void initData() {
        queryLogistics();
    }


    /**
     * 物流信息
     */
    private void queryLogistics() {
        HttpManager.getInstance().queryLogistics(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.queryLogisticsResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.queryLogisticsResponse data) {
                        if (result) {
                            information = data.data.replaceAll("\\\\", "");
                            //首先创建一个Gson对象
                            Gson gson = new Gson();
                            //Gson类中有一个方法 fromJSon 参数分别为Json类型的字符串 还有T类型的.class
                            //此方法会返回一个T类型的值
                            LogisticsInfo logisticsInfo = gson.fromJson(information, LogisticsInfo.class);
                            tvOrderNumbering.setText(logisticsInfo.getNu());
                            dataBeanList = logisticsInfo.getData();
                            initAdapter();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void initAdapter() {
        mAdapter = new LogisticsInformationAdapter(dataBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }


    @OnClick(R.id.tv_copy)
    public void onViewClicked() {
    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}