package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cyty.mall.R;
import com.cyty.mall.activity.NotificationListActivity;
import com.cyty.mall.activity.PersonalSettingsActivity;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.UserInfo;
import com.cyty.mall.event.RefreshAddressListEvent;
import com.cyty.mall.event.RefreshNewsListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.GlideUtil;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * main-我的
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.status_bar)
    View statusBarView;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    @BindView(R.id.tv_my_scores_num)
    TextView tvMyScoresNum;
    @BindView(R.id.tv_coupon_num)
    TextView tvCouponNum;
    @BindView(R.id.tv_message)
    TextView tvMessage;

    private int messageNum;
    private int userID;
    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected void initView() {
        super.initView();
        isUseEventBus(true);
    }

    @Override
    protected void initData() {
        super.initData();
        getUserInfo();
        selectReadNews();
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpManager.getInstance().getUserInfo(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.getUserInfoResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.getUserInfoResponse data) {
                        if (result) {
                            setUserInfo(data.data);
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 设置我的页面信息
     *
     * @param userInfo 数据
     */
    @SuppressLint("SetTextI18n")
    private void setUserInfo(UserInfo userInfo) {
        userID =userInfo.getId();
        if (!TextUtils.isEmpty(userInfo.getNickname())) tvNickname.setText(userInfo.getNickname());
        if (!TextUtils.isEmpty(userInfo.getGradeName())) tvVip.setText(userInfo.getGradeName());
        if (userInfo.getCouponsNum() >= 0) tvCouponNum.setText(userInfo.getCouponsNum() + "");
        if (userInfo.getCollectionNum() >= 0)
            tvCollectNum.setText(userInfo.getCollectionNum() + "");
        if (userInfo.getIntegral() >= 0) tvMyScoresNum.setText(userInfo.getIntegral() + "");
        if (!TextUtils.isEmpty(userInfo.getHeadPortrait()))
            GlideUtil.with(mActivity).displayImage(userInfo.getHeadPortrait(), imgAvatar);
    }

    /**
     * 个人中心未读标志数量
     */
    private void selectReadNews() {
        HttpManager.getInstance().selectReadNews(
                new HttpEngine.HttpResponseResultCallback<HttpResponse.selectReadNewsResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.selectReadNewsResponse data) {
                        if (result) {
                            messageNum = data.data;
                            if (messageNum > 0) {
                                tvMessage.setVisibility(View.VISIBLE);
                                tvMessage.setText(messageNum + "");
                            } else {
                                tvMessage.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }


    @OnClick({R.id.img_avatar, R.id.tv_sign_in, R.id.tv_share, R.id.tv_mine_pending_payment, R.id.tv_mine_to_be_delivered, R.id.tv_mine_to_be_received, R.id.tv_mine_receipt, R.id.tv_mine_refund, R.id.tv_mine_member_permissions, R.id.tv_mine_address_management, R.id.tv_mine_notification, R.id.tv_mine_contact_customer_service, R.id.tv_mine_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                PersonalSettingsActivity.startActivity(mActivity);
                break;
            case R.id.tv_sign_in:
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_mine_pending_payment:
                break;
            case R.id.tv_mine_to_be_delivered:
                break;
            case R.id.tv_mine_to_be_received:
                break;
            case R.id.tv_mine_receipt:
                break;
            case R.id.tv_mine_refund:
                break;
            case R.id.tv_mine_member_permissions:
                break;
            case R.id.tv_mine_address_management:
                break;
            case R.id.tv_mine_notification:
                NotificationListActivity.startActivity(mActivity,userID);
                break;
            case R.id.tv_mine_contact_customer_service:
                break;
            case R.id.tv_mine_about_us:
                break;
        }
    }

    /**
     * 刷新列表
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshNewsListEvent(RefreshNewsListEvent event) {
        selectReadNews();
    }
}