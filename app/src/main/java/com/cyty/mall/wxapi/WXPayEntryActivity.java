package com.cyty.mall.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cyty.mall.contants.Constant;
import com.cyty.mall.event.WeChartPayEvent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Dest  微信支付
 */

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private String Tag="WXPayEntryActivity";
    private IWXAPI mIWXAPI;
    private String mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mData = intent.getStringExtra("_wxapi_payresp_extdata");
        mIWXAPI = WXAPIFactory.createWXAPI(this, Constant.WEI_XIN_APP_ID,true);
        mIWXAPI.handleIntent(intent,this);
    }


    @Override
    public void onReq(BaseReq baseReq) {


    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mIWXAPI.handleIntent(intent, this);
    }

    @Override
    public void onResp(BaseResp resp) {
        EventBus.getDefault().post(new WeChartPayEvent(resp.errCode));
        finish();
    }
}
