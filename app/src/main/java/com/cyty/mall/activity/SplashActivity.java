package com.cyty.mall.activity;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.layout)
    FrameLayout layout;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        startAnim();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar() {

    }

    /**
     * 启动动画
     */
    private void startAnim() {
        // 渐变动画,从完全透明到完全不透明
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        // 持续时间 2 秒
//        alpha.setDuration(2000);
        // 动画结束后，保持动画状态
        alpha.setFillAfter(true);

        // 设置动画监听器
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            // 动画结束时回调此方法
            @Override
            public void onAnimationEnd(Animation animation) {
                //已登录
                MainActivity.startActivity(mContext);
                finish();
            }
        });
        // 启动动画
        layout.startAnimation(alpha);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageView(this, null);
        setLightStatusBarForM(this, true);
    }
}