package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreGoodsInfo implements Parcelable {

    /**
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * salesVolume : 1044
     * goodsId : 2
     * integral : 100
     * id : 7
     * title : 测试积分水乳
     */

    private String thumbnail;
    private int salesVolume;
    private int goodsId;
    private int integral;
    private int id;
    private String title;

    protected ScoreGoodsInfo(Parcel in) {
        thumbnail = in.readString();
        salesVolume = in.readInt();
        goodsId = in.readInt();
        integral = in.readInt();
        id = in.readInt();
        title = in.readString();
    }

    public static final Creator<ScoreGoodsInfo> CREATOR = new Creator<ScoreGoodsInfo>() {
        @Override
        public ScoreGoodsInfo createFromParcel(Parcel in) {
            return new ScoreGoodsInfo(in);
        }

        @Override
        public ScoreGoodsInfo[] newArray(int size) {
            return new ScoreGoodsInfo[size];
        }
    };

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeInt(salesVolume);
        dest.writeInt(goodsId);
        dest.writeInt(integral);
        dest.writeInt(id);
        dest.writeString(title);
    }
}
