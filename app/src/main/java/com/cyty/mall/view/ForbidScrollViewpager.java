package com.cyty.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/7 10:01
 * @描述 禁止左右滑动
 */
public class ForbidScrollViewpager extends ViewPager {
    private boolean result=false;
    public ForbidScrollViewpager(@NonNull Context context) {
        super(context);
    }

    public ForbidScrollViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (result)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (result)
            return super.onTouchEvent(arg0);
        else
            return false;
    }
}