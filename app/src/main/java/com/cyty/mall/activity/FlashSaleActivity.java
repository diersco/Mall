package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.SkillTypeInfo;
import com.cyty.mall.fragment.FlashSaleFragment;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.view.WrapContentHeightViewPager;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 限时抢购
 */
public class FlashSaleActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<SkillTypeInfo> skillTypeInfoList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onNetReload(View v) {

    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, FlashSaleActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_flash_sale;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        selectMallExchangeList();
    }

    @Override
    protected void initToolBar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("限时抢购");
    }

    /**
     * 秒杀类型
     */
    private void selectMallExchangeList() {
        HttpManager.getInstance().selectSeckillLis(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectSeckillListResponse>() {
                    @Override
                    public void onResponse(boolean result, int total, String message, HttpResponse.selectSeckillListResponse data) {

                        if (result) {
                            skillTypeInfoList = data.data;
                            initTab();
                            initMagicIndicator();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    private void initTab() {
        mFragments.clear();
        for (int i = 0; i < skillTypeInfoList.size(); i++) {
//            mFragments.add(FlashSaleFragment.newInstance(skillTypeInfoList.get(i).getStartTime()));
            mFragments.add(FlashSaleFragment.newInstance(skillTypeInfoList.get(i).getStartTime()));
        }
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(skillTypeInfoList.size());
        viewPager.setAdapter(mViewPagerAdapter);
    }

    // viewpager适配器
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;

        ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmentList = list;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mContext);
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return skillTypeInfoList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(mContext);
                commonPagerTitleView.setContentView(R.layout.title_view_skill);
                final TextView tvTime = (TextView) commonPagerTitleView.findViewById(R.id.tv_time);
                final TextView tvType = (TextView) commonPagerTitleView.findViewById(R.id.tv_type);
                final View view = (View) commonPagerTitleView.findViewById(R.id.view);
                final LinearLayout layoutContent = (LinearLayout) commonPagerTitleView.findViewById(R.id.layout_content);
                tvTime.setText(skillTypeInfoList.get(index).getStartTime());
                switch (skillTypeInfoList.get(index).getSeckillState()) {
                    case 1:
                        tvType.setText("即将开始");
                        break;
                    case 2:
                        tvType.setText("抢购中");
                        break;
                    case 3:
                        tvType.setText("已结束");
                        break;
                }
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {

                        layoutContent.setBackgroundResource(R.drawable.ic_skill_selected);
                        tvType.setTextColor(getResources().getColor(R.color.white));
                        tvTime.setTextColor(getResources().getColor(R.color.white));
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        lp.setMargins(0, 0, 0, 8);
//                        layoutContent.setLayoutParams(lp);
                        view.setVisibility(View.VISIBLE);
                        layoutContent.setBackgroundColor(Color.parseColor("#333333"));
                        tvType.setTextColor(Color.parseColor("#99ffffff"));
                        tvTime.setTextColor(Color.parseColor("#99ffffff"));
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}