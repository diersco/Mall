package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的-个人设置页面
 */
public class PersonalSettingsActivity extends BaseActivity {


    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_mine_about_us)
    TextView tvMineAboutUs;
    @BindView(R.id.tv_mine_contact_customer_service)
    TextView tvMineContactCustomerService;
    @BindView(R.id.tv_quit)
    TextView tvQuit;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_personal_settings;
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
        tvTitle.setText("个人设置");
    }

    @Override
    protected void initData() {

    }


    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, PersonalSettingsActivity.class);
        mContext.startActivity(mIntent);
    }

    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }


    @OnClick({R.id.layout_personal_info, R.id.layout_address, R.id.tv_mine_contact_customer_service, R.id.tv_mine_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_personal_info:
                break;
            case R.id.layout_address:
                AddressManagementActivity.startActivity(mContext);
                break;
            case R.id.tv_mine_contact_customer_service:
                break;
            case R.id.tv_mine_about_us:
                break;
        }
    }
}