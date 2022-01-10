package com.cyty.mall.callback;



import com.cyty.mall.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @创建者 misJackLee
 * @创建时间 2020/11/9 9:56
 * @描述 错误回调
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
