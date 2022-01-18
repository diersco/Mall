package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/18 16:09
 * @描述
 */
public class SignInfo implements Parcelable {

    /**
     * package : Sign=WXPay
     * appid : wx6495a900821f9933
     * sign : 0AB1D705AB31A30F8198C0FA4066DED2
     * partnerid : 1611424221
     * prepayid : wx18160818809756a1aab1eb02b8a5b80000
     * noncestr : 1642493298907
     * timestamp : 1642493298
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    protected SignInfo(Parcel in) {
        packageX = in.readString();
        appid = in.readString();
        sign = in.readString();
        partnerid = in.readString();
        prepayid = in.readString();
        noncestr = in.readString();
        timestamp = in.readString();
    }

    public static final Creator<SignInfo> CREATOR = new Creator<SignInfo>() {
        @Override
        public SignInfo createFromParcel(Parcel in) {
            return new SignInfo(in);
        }

        @Override
        public SignInfo[] newArray(int size) {
            return new SignInfo[size];
        }
    };

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(packageX);
        parcel.writeString(appid);
        parcel.writeString(sign);
        parcel.writeString(partnerid);
        parcel.writeString(prepayid);
        parcel.writeString(noncestr);
        parcel.writeString(timestamp);
    }
}
