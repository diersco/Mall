package com.cyty.mall.callback;

import android.content.Context;
import android.view.View;
import android.widget.Toast;


import com.cyty.mall.R;
import com.kingja.loadsir.callback.Callback;


/**
 * @创建者 misJackLee
 * @创建时间 2020/11/9 9:56
 * @描述 超时回调
 */

public class TimeoutCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_timeout;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        Toast.makeText(context.getApplicationContext(), "Connecting to the network again!", Toast.LENGTH_SHORT).show();
        return false;
    }

}
