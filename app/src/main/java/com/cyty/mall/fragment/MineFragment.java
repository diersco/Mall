package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.cyty.mall.R;
import com.cyty.mall.activity.AddressManagementActivity;
import com.cyty.mall.activity.CollectionActivity;
import com.cyty.mall.activity.CommonActivity;
import com.cyty.mall.activity.ContactCustomerServiceActivity;
import com.cyty.mall.activity.CouponActivity;
import com.cyty.mall.activity.MemberBenefitsActivity;
import com.cyty.mall.activity.MyScoresActivity;
import com.cyty.mall.activity.NotificationListActivity;
import com.cyty.mall.activity.OrderActivity;
import com.cyty.mall.activity.PersonalSettingsActivity;
import com.cyty.mall.activity.SelectAftermarketTypeActivity;
import com.cyty.mall.activity.SignInActivity;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.UserInfo;
import com.cyty.mall.contants.MKParameter;
import com.cyty.mall.event.RefreshNewsListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.GlideUtil;
import com.cyty.mall.util.MkUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * main-我的
 */
public class MineFragment extends BaseFragment {


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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int messageNum;
    private int userID;
    private int myIntegral;

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
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getUserInfo();
                selectReadNews();
                refreshLayout.finishRefresh();
            }
        });
        if (!StringUtils.isEmpty(MkUtils.decodeString(MKParameter.TOKEN))) {
            getUserInfo();
            selectReadNews();
        }
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
        userID = userInfo.getId();
        if (!TextUtils.isEmpty(userInfo.getNickname())) tvNickname.setText(userInfo.getNickname());
        if (!TextUtils.isEmpty(userInfo.getGradeName())) tvVip.setText(userInfo.getGradeName());
        if (userInfo.getCouponsNum() >= 0) tvCouponNum.setText(userInfo.getCouponsNum() + "");
        if (userInfo.getCollectionNum() >= 0)
            tvCollectNum.setText(userInfo.getCollectionNum() + "");
        if (userInfo.getIntegral() >= 0) {
            myIntegral = userInfo.getIntegral();
            tvMyScoresNum.setText(userInfo.getIntegral() + "");
        }
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


    @OnClick({R.id.layout_collect, R.id.layout_my_scores, R.id.layout_coupon, R.id.img_avatar, R.id.tv_sign_in, R.id.tv_share, R.id.tv_mine_pending_payment, R.id.tv_mine_to_be_delivered, R.id.tv_mine_to_be_received, R.id.tv_mine_receipt, R.id.tv_mine_refund, R.id.tv_mine_member_permissions, R.id.tv_mine_address_management, R.id.tv_mine_notification, R.id.tv_mine_contact_customer_service, R.id.tv_mine_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                PersonalSettingsActivity.startActivity(mActivity);
                break;
            case R.id.layout_my_scores:
                MyScoresActivity.startActivity(mActivity, myIntegral + "");
                break;
            case R.id.layout_coupon:
                CouponActivity.startActivity(mActivity);
                break;
            case R.id.layout_collect:
                CollectionActivity.startActivity(mActivity, userID);
                break;
            case R.id.tv_sign_in:
                SignInActivity.startActivity(mActivity);
                break;
            case R.id.tv_share:
                break;
            case R.id.tv_mine_pending_payment:
                OrderActivity.startActivity(mActivity, 1);
                break;
            case R.id.tv_mine_to_be_delivered:
                OrderActivity.startActivity(mActivity, 2);
                break;
            case R.id.tv_mine_to_be_received:
                OrderActivity.startActivity(mActivity, 3);
                break;
            case R.id.tv_mine_receipt:
                OrderActivity.startActivity(mActivity, 4);
                break;
            case R.id.tv_mine_refund:

                break;
            case R.id.tv_mine_member_permissions:
                MemberBenefitsActivity.startActivity(mActivity);
                break;
            case R.id.tv_mine_address_management:
                AddressManagementActivity.startActivity(mActivity, 1);
                break;
            case R.id.tv_mine_notification:
                NotificationListActivity.startActivity(mActivity, userID);
                break;
            case R.id.tv_mine_contact_customer_service:
                ContactCustomerServiceActivity.startActivity(mActivity);
                break;
            case R.id.tv_mine_about_us:
                CommonActivity.startActivity(mActivity, 1);
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