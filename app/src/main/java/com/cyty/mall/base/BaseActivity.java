package com.cyty.mall.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
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
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.util.AppUtils;
import com.cyty.mall.util.LoadingDialogUtils;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zsml.dialoglibrary.widget.CustomDialog;

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
        HttpManager.getInstance().init(mContext);
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

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private CustomDialog shareDialog;

    public void share(String title, String desc, String id) {

        if (shareDialog == null) {

            shareDialog = new CustomDialog.Builder(this)
                    .view(R.layout.share_dialog)
                    .setWidthPX(AppUtils.getScreenWidth(mContext))
                    .setHeightDP(180)
                    .setDialogPosition(Gravity.BOTTOM)
                    .build();
        }
        View dialogView = shareDialog.getDialogView();
        /****微信好友***/
        dialogView.findViewById(R.id.ll_share_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTwo(title, desc, id, SHARE_MEDIA.WEIXIN);
                shareDialog.dismiss();
            }
        });
        /****微信朋友圈***/
        dialogView.findViewById(R.id.ll_share_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTwo(title, desc, id, SHARE_MEDIA.WEIXIN_CIRCLE);
                shareDialog.dismiss();
            }
        });
        /****取消***/
        dialogView.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.dismiss();
            }
        });
        shareDialog.show();
    }

    private final String url = "https://appmall.ciyuantiaoyue.com/h5/index.html?goodsId=%1s";

    public void shareTwo(String title, String desc, String id, SHARE_MEDIA shareMedia) {
        //通用分享
        UMImage image = new UMImage(mContext, R.mipmap.ic_logo);//资源文件
        UMWeb web = new UMWeb(String.format(url, id));
        web.setTitle(title);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(desc);//描述
        new ShareAction(this)
                .withMedia(web)
                .setCallback(shareListener)
                .setPlatform(shareMedia)
                .share();

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @param platform 平台类型
         * @descrption 分享开始的回调
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.show("分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtils.show("分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtils.show("分享取消");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(mContext).onActivityResult(requestCode, resultCode, data);
    }
}

