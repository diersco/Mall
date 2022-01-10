package com.cyty.mall.callback;


import com.cyty.mall.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @创建者 misJackLee
 * @创建时间 2020/11/9 9:56
 * @描述  空值回调
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
