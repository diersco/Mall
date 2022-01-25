package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.MainActivity;
import com.cyty.mall.R;
import com.cyty.mall.base.ActivityCollector;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.event.MainJumpEvent;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 支付成功
 */
public class PaymentSuccessfulActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onNetReload(View v) {

    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, PaymentSuccessfulActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_payment_successful;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        tvTitle.setText("支付成功");
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        finish();
        EventBus.getDefault().post(new MainJumpEvent(3));
        ActivityCollector.finishAllActivityExcept(MainActivity.class);
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}