package com.cyty.mall.event;

import com.cyty.mall.bean.AddressInfo;

public class GetAddressEvent {
    private AddressInfo addressInfo;

    public GetAddressEvent(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }
}
