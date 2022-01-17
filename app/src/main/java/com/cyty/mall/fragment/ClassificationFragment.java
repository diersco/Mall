package com.cyty.mall.fragment;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cyty.mall.R;
import com.cyty.mall.activity.SearchActivity;
import com.cyty.mall.adapter.ImageBannerAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.ClassIfPageBannerInfo;
import com.cyty.mall.bean.ClassificationCommodity;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.http.ServerApiConstants;
import com.hjq.toast.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
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
    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_more)
    TextView tvMore;


    private ImageBannerAdapter imageBannerAdapter;
    private List<ClassIfPageBannerInfo.ClassifPageBannerListBean> classIfPageBannerList = new ArrayList<>();
    private static final String[] CHANNELS = new String[]{"水乳", "面霜", "精华", "面膜", "口红"};
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<ClassificationCommodity> classificationCommodityList = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper = new FragmentContainerHelper();

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
    }

    /**
     * 获取banner数据
     */
    private void getClassifyPage() {
        HttpManager.getInstance().getBanner(
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
                .setIndicator(new CircleIndicator(mActivity));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {

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
                            initFragments();
                            initMagicIndicator();
                            mFragmentContainerHelper.handlePageSelected(0, false);
                            switchPages(0);
                        } else {
                            ToastUtils.show(message);
                        }
                    }

                });
    }

    private void switchPages(int index) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        for (int i = 0, j = mFragments.size(); i < j; i++) {
            if (i == index) {
                continue;
            }
            fragment = mFragments.get(i);
            if (fragment.isAdded()) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragment = mFragments.get(index);
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void initFragments() {
        for (int i = 0; i < classificationCommodityList.size(); i++) {
            mFragments.add(ClassificationSubpageFragment.newInstance(classificationCommodityList.get(i).getId()));
        }
    }

    private void initMagicIndicator() {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return CHANNELS.length;
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
                view.setVisibility(index + 1 == CHANNELS.length ? View.GONE : View.VISIBLE);
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
                        mFragmentContainerHelper.handlePageSelected(index, false);
                        switchPages(index);
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
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
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
                break;
            case R.id.tv_search:
                SearchActivity.startActivity(mActivity);
                break;
        }
    }
}