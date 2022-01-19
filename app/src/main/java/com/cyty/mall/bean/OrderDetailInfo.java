package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class OrderDetailInfo implements Parcelable {
    /**
     * id : 56
     * orderNumber : c6ab834da5374da5a41fded62f9ef575
     * paymentNo : 4200001345202201180068410898
     * discountAmount : 0.0
     * freight : 0.01
     * paymentAmount : 0.02
     * orderTime : 2022-01-18 16:22:24
     * paymentType : 1
     * paymentTime : 2022-01-18 16:22:37
     * orderStatus : 2
     * addressesDTO : {"id":null,"userId":null,"name":"李xx","phone":"17396263951","region":"四川省成都市新都区","detailedAddress":"808姐姐"}
     * orderDetailsList : [{"id":7,"goodsId":1,"goodsTitle":"测试水乳","thumbnail":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png","specId":6,"spec":"绿瓶-5ml/瓶","price":0.01,"freight":0.01,"quantity":1,"evaluateStatus":1,"refund":2,"afterSale":1,"deletes":1}]
     */

    private int id;
    private String orderNumber;
    private String paymentNo;
    private String discountAmount;
    private String freight;
    private String paymentAmount;
    private String orderTime;
    private int paymentType;
    private String paymentTime;
    private int orderStatus;
    private AddressesDTOBean addressesDTO;
    private List<OrderDetailsListBean> orderDetailsList;

    protected OrderDetailInfo(Parcel in) {
        id = in.readInt();
        orderNumber = in.readString();
        paymentNo = in.readString();
        discountAmount = in.readString();
        freight = in.readString();
        paymentAmount = in.readString();
        orderTime = in.readString();
        paymentType = in.readInt();
        paymentTime = in.readString();
        orderStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(orderNumber);
        dest.writeString(paymentNo);
        dest.writeString(discountAmount);
        dest.writeString(freight);
        dest.writeString(paymentAmount);
        dest.writeString(orderTime);
        dest.writeInt(paymentType);
        dest.writeString(paymentTime);
        dest.writeInt(orderStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetailInfo> CREATOR = new Creator<OrderDetailInfo>() {
        @Override
        public OrderDetailInfo createFromParcel(Parcel in) {
            return new OrderDetailInfo(in);
        }

        @Override
        public OrderDetailInfo[] newArray(int size) {
            return new OrderDetailInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public AddressesDTOBean getAddressesDTO() {
        return addressesDTO;
    }

    public void setAddressesDTO(AddressesDTOBean addressesDTO) {
        this.addressesDTO = addressesDTO;
    }

    public List<OrderDetailsListBean> getOrderDetailsList() {
        return orderDetailsList;
    }

    public void setOrderDetailsList(List<OrderDetailsListBean> orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public static class AddressesDTOBean {
        /**
         * id : null
         * userId : null
         * name : 李xx
         * phone : 17396263951
         * region : 四川省成都市新都区
         * detailedAddress : 808姐姐
         */

        private Object id;
        private Object userId;
        private String name;
        private String phone;
        private String region;
        private String detailedAddress;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
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
    }

    public static class OrderDetailsListBean {
        /**
         * id : 7
         * goodsId : 1
         * goodsTitle : 测试水乳
         * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
         * specId : 6
         * spec : 绿瓶-5ml/瓶
         * price : 0.01
         * freight : 0.01
         * quantity : 1
         * evaluateStatus : 1
         * refund : 2
         * afterSale : 1
         * deletes : 1
         */

        private int id;
        private int goodsId;
        private String goodsTitle;
        private String thumbnail;
        private int specId;
        private String spec;
        private String price;
        private String freight;
        private int quantity;
        private int evaluateStatus;
        private int refund;
        private int afterSale;
        private int deletes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
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

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getEvaluateStatus() {
            return evaluateStatus;
        }

        public void setEvaluateStatus(int evaluateStatus) {
            this.evaluateStatus = evaluateStatus;
        }

        public int getRefund() {
            return refund;
        }

        public void setRefund(int refund) {
            this.refund = refund;
        }

        public int getAfterSale() {
            return afterSale;
        }

        public void setAfterSale(int afterSale) {
            this.afterSale = afterSale;
        }

        public int getDeletes() {
            return deletes;
        }

        public void setDeletes(int deletes) {
            this.deletes = deletes;
        }
    }
}
