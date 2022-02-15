package com.cyty.mall.fragment;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.activity.FlashSaleActivity;
import com.cyty.mall.activity.GoodsDetailActivity;
import com.cyty.mall.activity.SearchActivity;
import com.cyty.mall.activity.SeckillGoodsDetailActivity;
import com.cyty.mall.adapter.HomeSpikeAdapter;
import com.cyty.mall.adapter.ImageBannerAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.ClassIfPageBannerInfo;
import com.cyty.mall.bean.ClassificationCommodity;
import com.cyty.mall.bean.GoodsListInfo;
import com.cyty.mall.bean.HomeSecKillGoodsInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.http.ServerApiConstants;
import com.cyty.mall.view.GridSpacingItemDecoration;
import com.hjq.toast.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

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
import butterknife.OnClick;

/**
 * main-分类
 */
public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.banner_class)
    Banner banner;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    private ImageBannerAdapter imageBannerAdapter;
    private List<ClassIfPageBannerInfo.ClassifPageBannerListBean> classIfPageBannerList = new ArrayList<>();
    private List<ClassificationCommodity> classificationCommodityList = new ArrayList<>();
    private List<HomeSecKillGoodsInfo.ListBean> listBeanList = new ArrayList<>();
    private HomeSpikeAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_classification;
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        getClassificationCommodity();
        getClassifyPage();
        selectSchedulingList();
    }

    /**
     * 获取banner数据
     */
    private void getClassifyPage() {
        HttpManager.getInstance().getBanner(1,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.ClassIfPageBannerResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.ClassIfPageBannerResponse data) {
                        if (result) {
                            classIfPageBannerList = data.data.getClassifPageBannerList();
//                            ToastUtils.show(classIfPageBannerList.get(0).getResourceLink());
                            initBanner();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 加载banner
     */
    private void initBanner() {
        imageBannerAdapter = new ImageBannerAdapter(classIfPageBannerList, mActivity);
        banner.setAdapter(imageBannerAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(true)
                .setIndicator(new RoundLinesIndicator(mActivity));
        banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                if (classIfPageBannerList.get(position).getGoodsId()>0) {
                    GoodsDetailActivity.startActivity(mActivity,classIfPageBannerList.get(position).getGoodsId());
                }

            }
        });
    }

    /**
     * 获取分类
     */
    private void getClassificationCommodity() {
        HttpManager.getInstance().getClassificationCommodity(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.ClassificationCommodityResponse>() {
                    @Override
                    public void onResponse(boolean result, int total, String message, HttpResponse.ClassificationCommodityResponse data) {
                        if (result) {
                            classificationCommodityList = data.rows;
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
        for (int i = 0; i < classificationCommodityList.size(); i++) {
            mFragments.add(ClassificationSubpageFragment.newInstance(classificationCommodityList.get(i).getId()));
        }
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mFragments);
        viewPager.setOffscreenPageLimit(classificationCommodityList.size());
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
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return classificationCommodityList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(mActivity);
                commonPagerTitleView.setContentView(R.layout.title_view_classification);
                final TextView tvType = (TextView) commonPagerTitleView.findViewById(R.id.tv_type);
                final TextView tvRemark = (TextView) commonPagerTitleView.findViewById(R.id.tv_remark);
                final View view = (View) commonPagerTitleView.findViewById(R.id.view);
                tvRemark.setText(classificationCommodityList.get(index).getSynopsis());
                tvType.setText(classificationCommodityList.get(index).getTitle());
                view.setVisibility(index + 1 == classificationCommodityList.size() ? View.GONE : View.VISIBLE);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        tvType.setTextColor(getResources().getColor(R.color.default_font_purple));
                        tvRemark.setTextColor(getResources().getColor(R.color.default_font_purple));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        tvType.setTextColor(getResources().getColor(R.color.default_font_gray_333));
                        tvRemark.setTextColor(getResources().getColor(R.color.default_font_gray_999));
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

    /**
     * 获取秒杀
     */
    private void selectSchedulingList() {
        HttpManager.getInstance().getSeckillGoodsList(
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.getSeckillGoodsListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.getSeckillGoodsListResponse data) {
                        if (result) {
                            listBeanList = data.data.getList();
                            initAdapter();
                        } else {
                            ToastUtils.show(message);
                        }
                    }

                });
    }

    private void initAdapter() {
        mAdapter = new HomeSpikeAdapter(listBeanList);
        recyclerview.setLayoutManager(new GridLayoutManager(mActivity, 4));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HomeSecKillGoodsInfo.ListBean listBean = listBeanList.get(position);
                if (listBean.getGoodsId()>0) {
                    SeckillGoodsDetailActivity.startActivity(mActivity, listBean.getGoodsId());
                }


            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        HttpManager instance = HttpManager.getInstance();
        if (instance != null) {
            instance.cancelRequest(ServerApiConstants.URL_GET_CLASSIFY);
            instance.cancelRequest(ServerApiConstants.URL_GET_BANNER);
        }
    }

    @OnClick({R.id.tv_more, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                FlashSaleActivity.startActivity(mActivity);
                break;
            case R.id.tv_search:
                SearchActivity.startActivity(mActivity);
                break;
        }
    }
}