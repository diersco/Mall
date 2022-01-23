package com.cyty.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.cyty.mall.R;
import com.cyty.mall.base.ActivityCollector;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.util.DataCleanManager;
import com.cyty.mall.util.MkUtils;
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
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;

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
        try {
            tvCacheSize.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @OnClick({R.id.layout_personal_info, R.id.tv_user_agreement, R.id.tv_privacy_agreement, R.id.tv_mine_about_us, R.id.layout_clear_cache, R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_personal_info:
                PersonalInformationActivity.startActivity(mContext);
                break;
            case R.id.tv_user_agreement:
                CommonActivity.startActivity(mContext, 2);
                break;
            case R.id.tv_privacy_agreement:
                CommonActivity.startActivity(mContext, 3);
                break;
            case R.id.tv_mine_about_us:
                CommonActivity.startActivity(mContext, 1);
                break;
            case R.id.layout_clear_cache:
                AlertView alertView = new AlertView("提示", "确定清除缓存～", null, null, new String[]{"取消", "确定"}, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 1) {
                            DataCleanManager.clearAllCache(mContext);
                            try {
                                tvCacheSize.setText(DataCleanManager.getTotalCacheSize(mContext));
                            } catch (Exception e) {
                                tvCacheSize.setText("0k");
                            }
                        }
                    }
                });
                alertView.show();
                break;
            case R.id.tv_quit:
                AlertView alertQuitView = new AlertView("提示", "是否退出登录？", null, null, new String[]{"取消", "确定"}, this, AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        if (position == 1) {
                            ActivityCollector.finishAll();
                            MkUtils.clearAll();
                            LoginActivity.startActivity(mContext);
                            finish();
                        }
                    }
                });
                alertQuitView.show();

                break;
        }
    }
}