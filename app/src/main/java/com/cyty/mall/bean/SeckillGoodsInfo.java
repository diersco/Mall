package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/24 11:56
 * @描述
 */
public class SeckillGoodsInfo implements Parcelable {

    /**
     * specId : 10
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png
     * priceSpike : 0.01
     * salesVolume : 0
     * goodsId : 3
     * price : 0.01
     * id : 10
     * title : 测试秒杀水乳
     * stock : 999
     * spec : 红瓶-5ml/瓶
     */

    private int specId;
    private String thumbnail;
    private double priceSpike;
    private int salesVolume;
    private int goodsId;
    private double price;
    private int id;
    private String title;
    private int stock;
    private String spec;

    protected SeckillGoodsInfo(Parcel in) {
        specId = in.readInt();
        thumbnail = in.readString();
        priceSpike = in.readDouble();
        salesVolume = in.readInt();
        goodsId = in.readInt();
        price = in.readDouble();
        id = in.readInt();
        title = in.readString();
        stock = in.readInt();
        spec = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(specId);
        dest.writeString(thumbnail);
        dest.writeDouble(priceSpike);
        dest.writeInt(salesVolume);
        dest.writeInt(goodsId);
        dest.writeDouble(price);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(stock);
        dest.writeString(spec);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SeckillGoodsInfo> CREATOR = new Creator<SeckillGoodsInfo>() {
        @Override
        public SeckillGoodsInfo createFromParcel(Parcel in) {
            return new SeckillGoodsInfo(in);
        }

        @Override
        public SeckillGoodsInfo[] newArray(int size) {
            return new SeckillGoodsInfo[size];
        }
    };

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPriceSpike() {
        return priceSpike;
    }

    public void setPriceSpike(double priceSpike) {
        this.priceSpike = priceSpike;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
