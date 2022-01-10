package com.cyty.mall.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cyty.mall.R;
import com.cyty.mall.callback.EmptyCallback;
import com.cyty.mall.callback.ErrorCallback;
import com.cyty.mall.callback.LoadingCallback;
import com.cyty.mall.util.LoadingDialogUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/9 15:49
 * @描述 Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {


    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    protected Unbinder unbinder;
    //是否使用EventBus
    private boolean isUseEventBus = false;
    public Context mContext;
    protected MMKV kv;
    protected LoadService mBaseLoadService;
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activity管理
        ActivityCollector.addActivity(this);
        mContext = this;
        kv = MMKV.defaultMMKV();
        if (bindLayout() > 0) setContentView(bindLayout());
        unbinder = ButterKnife.bind(this);
        //初始化控件
        initView();
        setStatusBar();
        if (isUseEventBus) {
            EventBus.getDefault().register(this);
        }
        initToolBar();
        //设置数据
        initData();
    }

    /**
     * 注册LoadSir
     *
     * @param view 替换视图
     */
    public void setLoadSir(View view) {
        if (mBaseLoadService == null) {
            mBaseLoadService = LoadSir.getDefault()
                    .register(view, (Callback.OnReloadListener) v -> onNetReload(view));
        }

    }

    private boolean isShowedContent = false;

    @Override
    public void showContent() {
        if (null != mBaseLoadService) {
            isShowedContent = true;
            mBaseLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showEmpty() {
        if (null != mBaseLoadService) {
            mBaseLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void showFailure(String message) {
        if (null != mBaseLoadService) {
            if (!isShowedContent) {
                mBaseLoadService.showCallback(ErrorCallback.class);
            } else {
                ToastUtils.show(message);
            }
        }
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    /**
     * 失败重试,重新加载事件
     */
    protected abstract void onNetReload(View v);

    /**
     * 初始化布局
     *
     * @return 布局id
     */
    protected abstract int bindLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 设置数据
     */
    protected abstract void initData();

    /**
     * 设置ToolBar
     */
    protected abstract void initToolBar();

    /**
     * 是否使用 EventBus
     *
     * @param useEventBus true or false
     */
    public void isUseEventBus(boolean useEventBus) {
        isUseEventBus = useEventBus;
    }

    /**
     * 保持不息屏
     */
    protected void keepScreenOn() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Activity退出动画
     */
    protected void setExitAnimation(int animId) {
        overridePendingTransition(0, animId);
    }


    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isUseEventBus) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;
        }

        //activity管理
        ActivityCollector.removeActivity(this);
    }


    /**
     * 展示加载弹框
     *
     * @author wangshifu
     * @version v1.0, 2018/12/29
     */
    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialogUtils.createLoadingDialog(this, "");
        }
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 展示加载弹框
     *
     * @author wangshifu
     * @version v1.0, 2018/12/29
     */
    protected void showLoadingDialog(String msg) {
        mLoadingDialog = LoadingDialogUtils.createLoadingDialog(this, msg);

        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    //设置监听事件,点击返回按钮则退出当前页面
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    public void onBackPressed() {
        finish();
    }

    /**
     * 隐藏加载弹框
     *
     * @author wangshifu
     * @version v1.0, 2018/12/29
     */
    protected void dismissLoadingDialog() {
        LoadingDialogUtils.closeDialog(mLoadingDialog);
    }

    /**
     * 设置改变手机字体大小，不改变app内部字体大小
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setLightStatusBarForM(@NonNull Activity activity, boolean dark) {
        Window window = activity.getWindow();
        if (window != null) {
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (dark) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }

}

