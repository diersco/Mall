package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/10 16:51
 * @描述
 */

public class AddressInfo implements Parcelable {
    /**
     * id : 5
     * userId : 3
     * name : 罗香香
     * phone : 18228007920
     * region : null
     * detailedAddress : 华星路四川省成都市锦江区
     * defaults : 2
     * deletes : 1
     */

    private int id;
    private int userId;
    private String name;
    private String phone;
    private String region;
    private String detailedAddress;
    private int defaults;
    private int deletes;

    protected AddressInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        name = in.readString();
        phone = in.readString();
        region = in.readString();
        detailedAddress = in.readString();
        defaults = in.readInt();
        deletes = in.readInt();
    }

    public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        @Override
        public AddressInfo createFromParcel(Parcel in) {
            return new AddressInfo(in);
        }

        @Override
        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public int getDefaults() {
        return defaults;
    }

    public void setDefaults(int defaults) {
        this.defaults = defaults;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(userId);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(region);
        parcel.writeString(detailedAddress);
        parcel.writeInt(defaults);
        parcel.writeInt(deletes);
    }
}
