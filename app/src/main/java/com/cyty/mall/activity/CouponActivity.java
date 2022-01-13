package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cyty.mall.R;
import com.cyty.mall.adapter.CommPagerAdapter;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.fragment.CouponFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的优惠卷
 */
public class CouponActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    //Fragment集合
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    //标题
    private String[] mTitles = {"未使用", "已使用", "已过期"};
    private CommPagerAdapter mAdapter;

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("我的优惠券");
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_coupon;
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, CouponActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void initView() {
        initTab();
    }

    @Override
    protected void initData() {

    }

    private void initTab() {
        mFragments.clear();
        //添加Fragment
        mFragments.add(CouponFragment.newInstance(1));
        mFragments.add(CouponFragment.newInstance(2));
        mFragments.add(CouponFragment.newInstance(3));
        mAdapter = new CommPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(mAdapter);
        slideTab.setViewPager(viewPager, mTitles);//tab和ViewPager进行关联
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }

}