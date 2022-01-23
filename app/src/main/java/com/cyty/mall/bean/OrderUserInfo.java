package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderUserInfo implements Parcelable {

    /**
     * id : null
     * nickname : 18380438130
     * headPortrait : null
     * orderTime : 2022-01-11 14:49:25
     */

    private String id;
    private String nickname;
    private String headPortrait;
    private String orderTime;

    protected OrderUserInfo(Parcel in) {
        id = in.readString();
        nickname = in.readString();
        headPortrait = in.readString();
        orderTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nickname);
        dest.writeString(headPortrait);
        dest.writeString(orderTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderUserInfo> CREATOR = new Creator<OrderUserInfo>() {
        @Override
        public OrderUserInfo createFromParcel(Parcel in) {
            return new OrderUserInfo(in);
        }

        @Override
        public OrderUserInfo[] newArray(int size) {
            return new OrderUserInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
