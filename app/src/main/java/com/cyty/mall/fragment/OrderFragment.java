package com.cyty.mall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.cyty.mall.R;
import com.cyty.mall.activity.LogisticsInformationActivity;
import com.cyty.mall.activity.OrderDetailActivity;
import com.cyty.mall.adapter.OrderListAdapter;
import com.cyty.mall.base.BaseFragment;
import com.cyty.mall.bean.OrderListInfo;
import com.cyty.mall.bean.SignInfo;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.cyty.mall.util.WXPayUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 订单
 */
public class OrderFragment extends BaseFragment {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private static final String ARG_TYPE = "type";
    private int pageIndex = 1;
    private final int pageSize = 10;
    private int total;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;
    private int state = STATE_NORMAL;       //正常情况
    private int type;
    private OrderListAdapter mAdapter;
    private List<OrderListInfo> orderListInfoList = new ArrayList<>();

    public static OrderFragment newInstance(int type) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onNetReload(View v) {
        showLoading();
        selectMallPaymentOrderList();
    }

    @Override
    protected void initView() {
        super.initView();
        setLoadSir(refreshLayout);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE, 0);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                int totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
                if (pageIndex < totalPage) {
                    loadMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
        });
        mAdapter = new OrderListAdapter(orderListInfoList);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
        mAdapter.addChildClickViewIds(R.id.tv_line_gary, R.id.tv_round_purple);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderListInfo orderListInfo = orderListInfoList.get(position);
                int state = orderListInfo.getOrderStatus();
                if (view.getId() == R.id.tv_line_gary) {
                    if (state == 1) {
                        //取消订单
                        cancelOrder(orderListInfo.getId());
                    } else if (state == 3) {
                        //查看物流
                        LogisticsInformationActivity.startActivity(mActivity, orderListInfo.getId());
                    }
                } else if (view.getId() == R.id.tv_round_purple) {
                    if (state == 1) {
                        //立即支付
                        new AlertView("选择支付方式", null, "取消", null, new String[]{"支付宝", "微信"}, mActivity, AlertView.Style.ActionSheet,
                                new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(Object o, int position) {
                                        if (position == 0) {
                                            //支付宝
                                            orderPay(orderListInfo.getOrderNumber());
                                        } else if (position == 1) {
                                            //微信
                                            WXOrderPay(orderListInfo.getOrderNumber());
                                        }
                                    }
                                }).show();
                    } else if (state == 2 || state == 4) {
                        OrderDetailActivity.startActivity(mActivity, orderListInfo.getId());
                        //查看详情
                    } else if (state == 3) {
                        //确认收货
                        confirmReceipts(orderListInfo.getId());
                    }
                }
            }
        });
        selectMallPaymentOrderList();
    }

    /**
     * 确认收货
     */
    private void confirmReceipts(int id) {
        HttpManager.getInstance().confirmReceipts(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.confirmReceiptsResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.confirmReceiptsResponse data) {
                        if (result) {
                            ToastUtils.show("确认收货成功");
                            selectMallPaymentOrderList();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 取消订单
     */
    private void cancelOrder(int id) {
        HttpManager.getInstance().cancelOrder(id,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.cancelOrderResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.cancelOrderResponse data) {
                        if (result) {
                            ToastUtils.show("取消成功");
                            selectMallPaymentOrderList();
                        } else {
                            ToastUtils.show(message);
                        }
                    }
                });
    }

    /**
     * 支付宝支付订单
     */
    private void orderPay(String ids) {
        HttpManager.getInstance().orderPay(ids, 2 + "",
                new HttpEngine.HttpResponseResultCallback<HttpResponse.OrderPayResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.OrderPayResponse data) {
                        if (result) {
                            String sign = data.data;
                            payForAli(sign);
                        } else {
                            ToastUtils.show(message);

                        }
                    }
                });
    }

    /**
     * 微信支付订单
     */
    private void WXOrderPay(String ids) {
        HttpManager.getInstance().WXOrderPay(ids, 1 + "",
                new HttpEngine.HttpResponseResultCallback<HttpResponse.WXOrderPayResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.WXOrderPayResponse data) {
                        if (result) {
                            SignInfo signInfo = data.data;
                            WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
                            builder.setAppId(signInfo.getAppid())
                                    .setPartnerId(signInfo.getPartnerid())
                                    .setPrepayId(signInfo.getPrepayid())
                                    .setPackageValue("Sign=WXPay")
                                    .setNonceStr(signInfo.getNoncestr())
                                    .setTimeStamp(signInfo.getTimestamp())
                                    .setSign(signInfo.getSign())
                                    .build().toWXPayNotSign(mActivity);
                        } else {
                            ToastUtils.show(message);

                        }
                    }
                });
    }

    private void payForAli(final String orderinfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(orderinfo, true);
                Message msg = new Message();
                msg.what = 101;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Map<String, String> result = ((Map<String, String>) msg.obj);
            String requestcode = result.get("resultStatus");
            if (!TextUtils.isEmpty(requestcode)) {
                try {
                    switch (Integer.parseInt(requestcode)) {
                        case 9000:

                            new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        Thread.sleep(200);//
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
//                                    CoursePaySuccessActivity.startActivity(mContext, orderId);
                                    ToastUtils.show("支付成功");
                                }

                            }.start();
                            break;
                        case 6001:
                            ToastUtils.show("取消支付");
                            break;
                        default:
//                            isCanclePay = false;
                            ToastUtils.show("支付宝支付失败");
                            break;
                    }

                } catch (Exception e) {
                }
            }
        }
    };

    /**
     * 订单列表
     */
    private void selectMallPaymentOrderList() {
        HttpManager.getInstance().selectMallPaymentOrderList(type, pageIndex, pageSize,
                new HttpEngine.HttpResponseResultListCallback<HttpResponse.selectMallPaymentOrderListResponse>() {
                    @Override
                    public void onResponse(boolean result, int totalNum, String message, HttpResponse.selectMallPaymentOrderListResponse data) {
                        if (state != STATE_MORE) {
                            orderListInfoList.clear();
                        }
                        if (result) {
                            if (data.rows != null) {
                                orderListInfoList.addAll(data.rows);
                                total = totalNum;
                                if (data.rows.size() > 0) {
                                    showContent();
                                } else {
                                    showEmpty();
                                }
                            }
                            showOrderList();
                        } else {
                            showOrderList();
                            if (state != STATE_MORE) {
                                orderListInfoList.clear();
                            }
                            ToastUtils.show(message);
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }

                });
    }

    /**
     * 展示数据
     */
    private void showOrderList() {
        switch (state) {
            case STATE_NORMAL:
            case STATE_MORE:
                mAdapter.notifyDataSetChanged();
                break;
            case STATE_REFRESH:
                mAdapter.notifyDataSetChanged();
                recyclerview.scrollToPosition(0);
                break;
        }
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        state = STATE_MORE;
        pageIndex = ++pageIndex;
        selectMallPaymentOrderList();
    }

    /**
     * 刷新
     */
    private void refreshData() {
        state = STATE_REFRESH;
        pageIndex = 1;
        selectMallPaymentOrderList();
    }
}