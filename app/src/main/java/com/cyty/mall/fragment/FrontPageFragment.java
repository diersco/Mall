package com.cyty.mall.fragment;

import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyty.mall.R;
import com.cyty.mall.activity.GoodsDetailActivity;
import com.cyty.mall.adapter.FrontBannerAdapter;
import com.cyty.mall.adapter.FrontImageAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.HomeDataInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.view.EmptyControlVideo;
import com.hjq.toast.ToastUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * main-首页
 */
public class FrontPageFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.video_player)
    EmptyControlVideo videoPlayer;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private FrontBannerAdapter mAdapter;
    private FrontImageAdapter frontImageAdapter;
    private List<HomeDataInfo.BigPictureListBean> pictureListBeanList = new ArrayList<>();
    private List<HomeDataInfo.PosterListBean> posterListBeanList = new ArrayList<>();
    private HomeDataInfo.VideoBean videoBean;

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_front_page;
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {
        getHomePageData();
    }

    /**
     * 获取首页数据
     */
    private void getHomePageData() {
        HttpManager.getInstance().getHomePageData(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getHomePageDataResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getHomePageDataResponse data) {
                        if (result) {
                            pictureListBeanList = data.data.getBigPictureList();
                            posterListBeanList = data.data.getPosterList();
                            videoBean = data.data.getVideo();
                            initBanner();
                            initBigImg();
                            initVideo();
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
        mAdapter = new FrontBannerAdapter(pictureListBeanList, mActivity);
        banner.setAdapter(mAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(true)
                .setIndicator(new CircleIndicator(mActivity));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                GoodsDetailActivity.startActivity(mActivity, pictureListBeanList.get(position).getGoodsId());
            }
        });
    }

    /**
     * 加载最下面的大图
     */
    private void initBigImg() {
        frontImageAdapter = new FrontImageAdapter(posterListBeanList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(frontImageAdapter);

    }

    /**
     * 加载视频
     */
    private void initVideo() {
        videoPlayer.setUp(videoBean.getResourceLink(), true, "1111111");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPlayer.release();
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
    }

}