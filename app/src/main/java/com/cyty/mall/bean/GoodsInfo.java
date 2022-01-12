package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/4 16:17
 * @描述
 */
public class GoodsInfo implements Parcelable {

    /**
     * salesVolume : 1043
     * goodsId : 1
     * atlas : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png,https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png
     * price : 0.01
     * specList : [{"specTwoList":[{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"130.00","defaultDisplay":"2","specTwo":"120ml/瓶","id":"1","stock":"114"},{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"100.00","defaultDisplay":"2","specTwo":"100ml/瓶","id":"2","stock":"135"},{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"50.00","defaultDisplay":"2","specTwo":"50ml/瓶","id":"3","stock":"5000"}],"specOne":"红瓶"},{"specTwoList":[{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"0.01","defaultDisplay":"1","specTwo":"5ml/瓶","id":"6","stock":"966"}],"specOne":"绿瓶"},{"specTwoList":[{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"100.00","defaultDisplay":"2","specTwo":"120ml/瓶","id":"4","stock":"200"},{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"120.00","defaultDisplay":"2","specTwo":"100ml/瓶","id":"5","stock":"100"}],"specOne":"蓝瓶"}]
     * totalStock : 9955
     * details : 测试水乳测试水乳测试水乳
     * id : 6
     * collection : 2
     * title : 测试水乳
     */

    private int salesVolume;
    private int goodsId;
    private String atlas;
    private double price;
    private int totalStock;
    private String details;
    private int id;
    private int collection;
    private String title;
    private List<SpecListBean> specList;

    protected GoodsInfo(Parcel in) {
        salesVolume = in.readInt();
        goodsId = in.readInt();
        atlas = in.readString();
        price = in.readDouble();
        totalStock = in.readInt();
        details = in.readString();
        id = in.readInt();
        collection = in.readInt();
        title = in.readString();
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel in) {
            return new GoodsInfo(in);
        }

        @Override
        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };

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

    public String getAtlas() {
        return atlas;
    }

    public void setAtlas(String atlas) {
        this.atlas = atlas;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SpecListBean> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecListBean> specList) {
        this.specList = specList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(salesVolume);
        parcel.writeInt(goodsId);
        parcel.writeString(atlas);
        parcel.writeDouble(price);
        parcel.writeInt(totalStock);
        parcel.writeString(details);
        parcel.writeInt(id);
        parcel.writeInt(collection);
        parcel.writeString(title);
    }

    public static class SpecListBean {
        /**
         * specTwoList : [{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"130.00","defaultDisplay":"2","specTwo":"120ml/瓶","id":"1","stock":"114"},{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"100.00","defaultDisplay":"2","specTwo":"100ml/瓶","id":"2","stock":"135"},{"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png","price":"50.00","defaultDisplay":"2","specTwo":"50ml/瓶","id":"3","stock":"5000"}]
         * specOne : 红瓶
         */

        private String specOne;
        private List<SpecTwoListBean> specTwoList;

        public String getSpecOne() {
            return specOne;
        }

        public void setSpecOne(String specOne) {
            this.specOne = specOne;
        }

        public List<SpecTwoListBean> getSpecTwoList() {
            return specTwoList;
        }

        public void setSpecTwoList(List<SpecTwoListBean> specTwoList) {
            this.specTwoList = specTwoList;
        }

        @Override
        public String toString() {
            return "SpecListBean{" +
                    "specOne='" + specOne + '\'' +
                    ", specTwoList=" + specTwoList +
                    '}';
        }

        public static class SpecTwoListBean {
            /**
             * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311539247787024.png
             * price : 130.00
             * defaultDisplay : 2
             * specTwo : 120ml/瓶
             * id : 1
             * stock : 114
             */

            private String thumbnail;
            private String price;
            private String defaultDisplay;
            private String specTwo;
            private String id;
            private String stock;

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

            public String getDefaultDisplay() {
                return defaultDisplay;
            }

            public void setDefaultDisplay(String defaultDisplay) {
                this.defaultDisplay = defaultDisplay;
            }

            public String getSpecTwo() {
                return specTwo;
            }

            public void setSpecTwo(String specTwo) {
                this.specTwo = specTwo;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            @Override
            public String toString() {
                return "SpecTwoListBean{" +
                        "thumbnail='" + thumbnail + '\'' +
                        ", price='" + price + '\'' +
                        ", defaultDisplay='" + defaultDisplay + '\'' +
                        ", specTwo='" + specTwo + '\'' +
                        ", id='" + id + '\'' +
                        ", stock='" + stock + '\'' +
                        '}';
            }
        }
    }
}
