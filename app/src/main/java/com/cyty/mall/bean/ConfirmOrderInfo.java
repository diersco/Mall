package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ConfirmOrderInfo  implements Parcelable {

    /**
     * totalPrice : 340
     * freight : 0
     * ids : 5|2,4|1
     * list : [{"specId":5,"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png","quantity":2,"price":120,"freight":0.01,"goodsTitle":"测试水乳","id":1,"spec":"蓝瓶-100ml/瓶"},{"specId":4,"thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png","quantity":1,"price":100,"freight":0.01,"goodsTitle":"测试水乳","id":1,"spec":"蓝瓶-120ml/瓶"}]
     * couponsList : [{"id":6,"userId":3,"couponTitle":"5折券","couponType":2,"discount":0.5,"conditions":0,"startTime":"2021-12-01 16:10:43","endTime":"2021-12-29 16:10:47","validStatus":1,"receiveRemark":null,"deletes":1},{"id":7,"userId":3,"couponTitle":"5折券","couponType":2,"discount":0.5,"conditions":0,"startTime":"2021-12-01 16:10:43","endTime":"2021-12-29 16:10:47","validStatus":1,"receiveRemark":null,"deletes":1},{"id":9,"userId":3,"couponTitle":"满100减20","couponType":1,"discount":20,"conditions":100,"startTime":"2021-12-01 16:10:13","endTime":"2022-01-20 16:10:17","validStatus":1,"receiveRemark":null,"deletes":1},{"id":10,"userId":3,"couponTitle":"满100减10","couponType":1,"discount":10,"conditions":100,"startTime":"2021-12-01 16:09:42","endTime":"2022-01-30 16:09:53","validStatus":1,"receiveRemark":null,"deletes":1}]
     */

    private String totalPrice;
    private String freight;
    private String ids;
    private List<ListBean> list;
    private List<CouponsListBean> couponsList;

    protected ConfirmOrderInfo(Parcel in) {
        totalPrice = in.readString();
        freight = in.readString();
        ids = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(totalPrice);
        dest.writeString(freight);
        dest.writeString(ids);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ConfirmOrderInfo> CREATOR = new Creator<ConfirmOrderInfo>() {
        @Override
        public ConfirmOrderInfo createFromParcel(Parcel in) {
            return new ConfirmOrderInfo(in);
        }

        @Override
        public ConfirmOrderInfo[] newArray(int size) {
            return new ConfirmOrderInfo[size];
        }
    };

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<CouponsListBean> getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(List<CouponsListBean> couponsList) {
        this.couponsList = couponsList;
    }

    public static class ListBean {
        /**
         * specId : 5
         * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
         * quantity : 2
         * price : 120
         * freight : 0.01
         * goodsTitle : 测试水乳
         * id : 1
         * spec : 蓝瓶-100ml/瓶
         */

        private int specId;
        private String thumbnail;
        private int quantity;
        private String price;
        private String freight;
        private String goodsTitle;
        private int id;
        private String spec;

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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getGoodsTitle() {
            return goodsTitle;
        }

        public void setGoodsTitle(String goodsTitle) {
            this.goodsTitle = goodsTitle;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }

    public static class CouponsListBean {
        /**
         * id : 6
         * userId : 3
         * couponTitle : 5折券
         * couponType : 2
         * discount : 0.5
         * conditions : 0
         * startTime : 2021-12-01 16:10:43
         * endTime : 2021-12-29 16:10:47
         * validStatus : 1
         * receiveRemark : null
         * deletes : 1
         */

        private int id;
        private int userId;
        private String couponTitle;
        private int couponType;
        private String discount;
        private String conditions;
        private String startTime;
        private String endTime;
        private int validStatus;
        private String receiveRemark;
        private int deletes;

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

        public String getCouponTitle() {
            return couponTitle;
        }

        public void setCouponTitle(String couponTitle) {
            this.couponTitle = couponTitle;
        }

        public int getCouponType() {
            return couponType;
        }

        public void setCouponType(int couponType) {
            this.couponType = couponType;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getValidStatus() {
            return validStatus;
        }

        public void setValidStatus(int validStatus) {
            this.validStatus = validStatus;
        }

        public String getReceiveRemark() {
            return receiveRemark;
        }

        public void setReceiveRemark(String receiveRemark) {
            this.receiveRemark = receiveRemark;
        }

        public int getDeletes() {
            return deletes;
        }

        public void setDeletes(int deletes) {
            this.deletes = deletes;
        }
    }
}
