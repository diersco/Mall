package com.cyty.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cyty.mall.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/19 10:13
 * @描述
 */
public class EmptyControlVideo extends StandardGSYVideoPlayer {

    public EmptyControlVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EmptyControlVideo(Context context) {
        super(context);
    }

    public EmptyControlVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.empty_control_video;
    }

    @Override
    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
        //不给触摸快进，如果需要，屏蔽下方代码即可
//        mChangePosition = false;

        //不给触摸音量，如果需要，屏蔽下方代码即可
//        mChangeVolume = false;

        //不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false;
    }

    @Override
    protected void touchDoubleUp() {
        super.touchDoubleUp();
    }
}

