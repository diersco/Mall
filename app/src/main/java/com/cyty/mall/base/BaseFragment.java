package com.cyty.mall.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cyty.mall.callback.EmptyCallback;
import com.cyty.mall.callback.ErrorCallback;
import com.cyty.mall.callback.LoadingCallback;
import com.hjq.toast.ToastUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/10 9:13
 * @描述
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    protected String mTag = this.getClass().getSimpleName();
    protected Activity mActivity;
    protected View mRootView;
    protected LoadService mBaseLoadService;
    protected Unbinder unbinder;
    protected MMKV kv;
    //是否使用EventBus
    private boolean isUseEventBus = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(onCreateFragmentView(), container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        kv = MMKV.defaultMMKV();
        initView();
        if (isUseEventBus) {
            EventBus.getDefault().register(this);
        }
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

    /**
     * 是否使用 EventBus
     *
     * @param useEventBus true or false
     */
    public void isUseEventBus(boolean useEventBus) {
        isUseEventBus = useEventBus;
    }


    protected abstract int onCreateFragmentView();


    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    protected abstract void onNetReload(View v);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isUseEventBus) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}

