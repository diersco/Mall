package com.cyty.mall;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.cyty.mall.activity.LoginActivity;
import com.cyty.mall.adapter.MainNavigationAdapter;
import com.cyty.mall.base.ActivityCollector;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.event.ExitLoginSuccess;
import com.cyty.mall.util.MkUtils;
import com.cyty.mall.view.ForbidScrollViewpager;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import kotlin.TuplesKt;

/**
 * 主main
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.viewpager_main)
    ForbidScrollViewpager viewpagerMain;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private MainNavigationAdapter navigationAdapter;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        isUseEventBus(true);
        navigationAdapter = new MainNavigationAdapter(getSupportFragmentManager());
        viewpagerMain.setOffscreenPageLimit(4);
        viewpagerMain.setAdapter(navigationAdapter);
        viewpagerMain.setCurrentItem(0);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        adjustNavigationIcoSize(navigation);
        MenuItem item1 = navigation.getMenu().findItem(R.id.navigation_front);
        item1.setIcon(R.drawable.main_tab_front_page_selected);
    }
    @Override
    protected void initToolBar() {

    }
    @Override
    protected void initData() {

    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            refreshItemIcon();
            switch (item.getItemId()) {
                case R.id.navigation_front:
                    viewpagerMain.setCurrentItem(0);
                    item.setIcon(R.drawable.main_tab_front_page_selected);
                    return true;
                case R.id.navigation_classification:
                    viewpagerMain.setCurrentItem(1);
                    item.setIcon(R.drawable.main_tab_classification_selected);
                    return true;
                case R.id.navigation_cart:
                    viewpagerMain.setCurrentItem(2);
                    if (StringUtils.isEmpty(MkUtils.decodeString(MKParameter.TOKEN))){
                        ActivityCollector.finishAll();
                        LoginActivity.startActivity(mContext);
                    }

                    item.setIcon(R.drawable.main_tab_cart_selected);
                    return true;
                case R.id.navigation_mine:
                    if (StringUtils.isEmpty(MkUtils.decodeString(MKParameter.TOKEN))){
                        ActivityCollector.finishAll();
                        LoginActivity.startActivity(mContext);
                    }
                    viewpagerMain.setCurrentItem(3);
                    item.setIcon(R.drawable.main_tab_mine_selected);
                    return true;
            }
            return false;
        }
    };

    /**
     * 未选中时加载默认的图片
     */
    public void refreshItemIcon() {
        MenuItem item1 = navigation.getMenu().findItem(R.id.navigation_front);
        item1.setIcon(R.drawable.main_tab_front_page_unselected);
        MenuItem item2 = navigation.getMenu().findItem(R.id.navigation_classification);
        item2.setIcon(R.drawable.main_tab_classification_unselected);
        MenuItem item3 = navigation.getMenu().findItem(R.id.navigation_cart);
        item3.setIcon(R.drawable.main_tab_cart_unselected);
        MenuItem item4 = navigation.getMenu().findItem(R.id.navigation_mine);
        item4.setIcon(R.drawable.main_tab_mine_unselected);
    }

    private void adjustNavigationIcoSize(BottomNavigationView navigation) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 26, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }

    public static void startActivity(Context mContext) {
        Intent mIntent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(mIntent);
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.show("再点一次退出" + getString(R.string.app_name));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 重新登录
     */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitLoginSuccess(ExitLoginSuccess event) {
        MkUtils.encode(MKParameter.TOKEN, "");
        ToastUtils.show("登陆已失效，请重新登陆");
        LoginActivity.startActivity(mContext);
        ActivityCollector.finishAll();

    }
    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }
}