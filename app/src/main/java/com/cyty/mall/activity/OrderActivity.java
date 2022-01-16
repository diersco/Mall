package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cyty.mall.R;
import com.cyty.mall.adapter.CommPagerAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.fragment.OrderFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderActivity extends BaseActivity {


    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //Fragment集合
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    //标题
    private String[] mTitles = {"全部", "待付款", "待发货", "待收货", "已完成", "已取消"};
    private CommPagerAdapter mAdapter;
    //跳转的di
    private int type;

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
        tvTitle.setText("我的订单");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_order;
    }

    public static void startActivity(Context mContext, int type) {
        Intent mIntent = new Intent(mContext, OrderActivity.class);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, 0);
        initTab();
    }

    @Override
    protected void initData() {

    }


    private void initTab() {
        mFragments.clear();
        //添加Fragment
        mFragments.add(OrderFragment.newInstance(0));
        mFragments.add(OrderFragment.newInstance(1));
        mFragments.add(OrderFragment.newInstance(2));
        mFragments.add(OrderFragment.newInstance(3));
        mFragments.add(OrderFragment.newInstance(4));
        mFragments.add(OrderFragment.newInstance(5));
        mAdapter = new CommPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setOffscreenPageLimit(6);
        viewPager.setAdapter(mAdapter);
        slideTab.setViewPager(viewPager, mTitles);//tab和ViewPager进行关联
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}