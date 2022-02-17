package com.cyty.mall.fragment;

import android.view.View;
import android.widget.ImageView;

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
import com.cyty.mall.util.GlideUtil;
import com.hjq.toast.ToastUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.util.BannerUtils;

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
    StandardGSYVideoPlayer videoPlayer;
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




    @Override
    public void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            videoPlayer.onVideoResume();
        }
    }

    /**
     * 加载banner
     */
    private void initBanner() {
        mAdapter = new FrontBannerAdapter(pictureListBeanList, mActivity);
        banner.setAdapter(mAdapter).addBannerLifecycleObserver(this)//添加生命周期观察者
                .isAutoLoop(true)
                .setIndicator(new RoundLinesIndicator(mActivity));
        banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                if (pictureListBeanList.get(position).getGoodsId()>0) {
                    GoodsDetailActivity.startActivity(mActivity, pictureListBeanList.get(position).getGoodsId());
                }
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
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //全屏按钮
        videoPlayer.getFullscreenButton().setVisibility(View.GONE);
        //小屏时不触摸滑动
        videoPlayer.setIsTouchWiget(false);
        //增加封面
        ImageView imageView = new ImageView(mActivity);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        GlideUtil.with(mActivity).displayImage(videoBean.getCover(), imageView);
        videoPlayer.setThumbImageView(imageView);
        videoPlayer.setUp(videoBean.getResourceLink(), true, "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoPlayer != null) {
            videoPlayer.release();
            //释放所有
            videoPlayer.setVideoAllCallBack(null);
        }

        GSYVideoManager.releaseAllVideos();
    }

    /**
     * 判断当前fragment 是否可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (videoPlayer == null) return;
        if (isVisibleToUser) {
            videoPlayer.onVideoResume();
        } else {
            videoPlayer.onVideoPause();
        }
    }
}