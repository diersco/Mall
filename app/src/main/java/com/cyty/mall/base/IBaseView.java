package com.cyty.mall.base;

/**
 * @创建者 misJackLee
 * @创建时间 2021/12/9 16:08
 * @描述 界面UI显示切换
 */
public interface IBaseView {
    /**
     * 显示内容
     */
    void showContent();

    /**
     * 显示加载提示
     */
    void showLoading();

    /**
     * 显示空页面
     */
    void showEmpty();

    /**
     * 刷新失败
     */
    void showFailure(String message);

}
