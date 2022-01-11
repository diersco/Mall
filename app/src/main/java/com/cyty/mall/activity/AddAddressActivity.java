package com.cyty.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.cyty.mall.R;
import com.cyty.mall.base.BaseActivity;
import com.cyty.mall.bean.AddressInfo;
import com.cyty.mall.contants.Constant;
import com.cyty.mall.event.RefreshAddressListEvent;
import com.cyty.mall.http.HttpEngine;
import com.cyty.mall.http.HttpManager;
import com.cyty.mall.http.HttpResponse;
import com.hjq.toast.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.kyleduo.switchbutton.SwitchButton;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityConfig;
import com.lljjcoder.style.cityjd.JDCityPicker;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 新增地址
 */
public class AddAddressActivity extends BaseActivity {

    JDCityPicker cityPicker;
    public JDCityConfig.ShowType mWheelType = JDCityConfig.ShowType.PRO_CITY_DIS;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_details)
    EditText etAddressDetails;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switchButton)
    SwitchButton switchButton;


    private JDCityConfig jdCityConfig = new JDCityConfig.Builder().build();
    //地址
    private String region;
    //详细地址
    private String addressDetail;
    //姓名
    private String name;
    //电话
    private String phone;
    //是否默认
    private int defaults;
    // 1 添加 2编辑
    private int type;
    private int id;
    private AddressInfo addressInfo;

    @Override
    protected void onNetReload(View v) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        addressInfo = (AddressInfo) getIntent().getParcelableExtra(Constant.INTENT_DATA);
        type = getIntent().getIntExtra(Constant.INTENT_TYPE, 0);
        initJD();
    }

    @Override
    protected void initToolBar() {
        // 设置toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    @Override
    protected void initData() {
        if (type == 1) {
            tvTitle.setText("新增地址");
        } else {
            tvTitle.setText("修改地址");
            initAddress();
        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    defaults = 1;

                } else {
                    defaults = 2;
                }
            }
        });
    }

    /**
     * 修改地址初始化地址数据
     */
    private void initAddress() {

        id = addressInfo.getId();
        if (!TextUtils.isEmpty(addressInfo.getName())) {
            name = addressInfo.getName();
            etName.setText(name);
        }
        if (!TextUtils.isEmpty(addressInfo.getPhone())) {
            phone = addressInfo.getPhone();
            etPhoneNum.setText(phone);
        }
        if (!TextUtils.isEmpty(addressInfo.getRegion())) {
            region = addressInfo.getRegion();
            tvAddress.setText(region);
            tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.default_font_gray_333));
        }
        if (!TextUtils.isEmpty(addressInfo.getDetailedAddress())) {
            addressDetail = addressInfo.getDetailedAddress();
            etAddressDetails.setText(addressDetail);
        }
        defaults = addressInfo.getDefaults();
        if (defaults == 1) {
            switchButton.setChecked(true);
        } else {
            switchButton.setChecked(false);
        }
    }

    /**
     * 初始化城市选择器
     */
    private void initJD() {
        jdCityConfig.setShowType(mWheelType);
        cityPicker = new JDCityPicker();
        //初始化数据
        cityPicker.init(this);
        //设置JD选择器样式位只显示省份和城市两级
        cityPicker.setConfig(jdCityConfig);
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                String proData = null;
                if (province != null) {
                    proData = province.getName();
                }
                String citData = null;
                if (city != null) {
                    citData = city.getName();
                }
                String districtData = null;
                if (district != null) {
                    districtData = district.getName();
                }
                region = proData + citData + districtData;
                tvAddress.setText(region);
                tvAddress.setTextColor(ContextCompat.getColor(mContext, R.color.default_font_gray_333));
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 新增或修改地址
     */
    private void reviseDefaultsAddress(int defaults, String detailedAddress, int id, String name, String phone, String region) {
        HttpManager.getInstance().addOrReviseAddressList(defaults, detailedAddress, id, name, phone, region,
                new HttpEngine.HttpResponseResultCallback<HttpResponse.addOrReviseAddressResponse>() {
                    @Override
                    public void onResponse(boolean result, String message, HttpResponse.addOrReviseAddressResponse data) {
                        if (result) {
                            EventBus.getDefault().post(new RefreshAddressListEvent());
                            if (type == 1) {
                                ToastUtils.show("新增地址成功！");
                            } else {
                                ToastUtils.show("修改地址成功！");
                            }
                            finish();
                        } else {
                            if (type == 1) {
                                ToastUtils.show("新增地址失败！");
                            } else {
                                ToastUtils.show("修改地址失败！");
                            }
                        }
                    }
                });
    }

    /**
     * 展示地址
     */
    private void showJD() {
        cityPicker.showCityPicker();
    }

    public static void startActivity(Context mContext, int type) {
        Intent mIntent = new Intent(mContext, AddAddressActivity.class);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    public static void startActivity(Context mContext, AddressInfo addressInfo, int type) {
        Intent mIntent = new Intent(mContext, AddAddressActivity.class);
        mIntent.putExtra(Constant.INTENT_DATA, addressInfo);
        mIntent.putExtra(Constant.INTENT_TYPE, type);
        mContext.startActivity(mIntent);
    }

    @OnClick({R.id.tv_address, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                showJD();
                break;
            case R.id.tv_sure:

                addressDetail = etAddressDetails.getText().toString().trim();
                name = etName.getText().toString().trim();
                phone = etPhoneNum.getText().toString().trim();
                region = tvAddress.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.show("请先填写姓名！");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.show("请先填写电话号码！");
                    return;
                }
                if (TextUtils.isEmpty(region)) {
                    ToastUtils.show("请先选择省市区！");
                    return;
                }
                if (TextUtils.isEmpty(addressDetail)) {
                    ToastUtils.show("请先填写详细地址！");
                    return;
                }
                reviseDefaultsAddress(defaults, addressDetail, id, name, phone, region);
                break;
        }
    }
    @Override
    protected void setStatusBar() {
        setLightStatusBarForM(this, true);
        StatusBarUtil.setColor(this, Color.WHITE, 0);
    }
}