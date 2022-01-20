package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HomeSecKillGoodsInfo implements Parcelable {

    /**
     * startTime : 2022-01-18T16:00:00.000+08:00
     * seckillState : 2
     * list : [{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","priceSpike":0.01,"goodsId":4,"id":12}]
     */

    private String startTime;
    private int seckillState;
    private List<ListBean> list;

    protected HomeSecKillGoodsInfo(Parcel in) {
        startTime = in.readString();
        seckillState = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startTime);
        dest.writeInt(seckillState);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeSecKillGoodsInfo> CREATOR = new Creator<HomeSecKillGoodsInfo>() {
        @Override
        public HomeSecKillGoodsInfo createFromParcel(Parcel in) {
            return new HomeSecKillGoodsInfo(in);
        }

        @Override
        public HomeSecKillGoodsInfo[] newArray(int size) {
            return new HomeSecKillGoodsInfo[size];
        }
    };

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getSeckillState() {
        return seckillState;
    }

    public void setSeckillState(int seckillState) {
        this.seckillState = seckillState;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png
         * priceSpike : 0.01
         * goodsId : 4
         * id : 12
         */

        private String thumbnail;
        private String priceSpike;
        private int goodsId;
        private int id;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getPriceSpike() {
            return priceSpike;
        }

        public void setPriceSpike(String priceSpike) {
            this.priceSpike = priceSpike;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
