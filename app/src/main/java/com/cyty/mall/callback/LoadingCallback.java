package com.cyty.mall.callback;

import android.content.Context;
import android.view.View;


import com.cyty.mall.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @创建者 misJackLee
 * @创建时间 2020/11/9 9:56
 * @描述 加载回调
 */

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }


    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
