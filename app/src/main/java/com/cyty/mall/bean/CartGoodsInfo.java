package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CartGoodsInfo  implements Parcelable {

    /**
     * id : 17
     * userId : 3
     * nickname : 17396263951
     * goodsId : 1
     * goodsTitle : 测试水乳
     * specId : 1
     * spec : 红瓶-120ml/瓶
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * price : 130
     * quantity : 1
     * deletes : 1
     */

    private int id;
    private int userId;
    private String nickname;
    private int goodsId;
    private String goodsTitle;
    private int specId;
    private String spec;
    private String thumbnail;
    private String price;
    private int quantity;
    private int deletes;

    protected CartGoodsInfo(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        nickname = in.readString();
        goodsId = in.readInt();
        goodsTitle = in.readString();
        specId = in.readInt();
        spec = in.readString();
        thumbnail = in.readString();
        price = in.readString();
        quantity = in.readInt();
        deletes = in.readInt();
    }

    public static final Creator<CartGoodsInfo> CREATOR = new Creator<CartGoodsInfo>() {
        @Override
        public CartGoodsInfo createFromParcel(Parcel in) {
            return new CartGoodsInfo(in);
        }

        @Override
        public CartGoodsInfo[] newArray(int size) {
            return new CartGoodsInfo[size];
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(nickname);
        dest.writeInt(goodsId);
        dest.writeString(goodsTitle);
        dest.writeInt(specId);
        dest.writeString(spec);
        dest.writeString(thumbnail);
        dest.writeString(price);
        dest.writeInt(quantity);
        dest.writeInt(deletes);
    }
}
