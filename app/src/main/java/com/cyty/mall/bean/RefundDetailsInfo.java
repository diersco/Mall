package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RefundDetailsInfo implements Parcelable {

    /**
     * searchValue : null
     * createBy :
     * createTime : 2022-01-14 17:15:00
     * updateBy :
     * updateTime : 2022-01-15 16:27:41
     * remark : null
     * params : {}
     * id : 2
     * orderNumber : 7892bb611c3444bf89e2ce5d50882249
     * orderDetailsId : 3
     * userId : 2
     * cellPhoneNumber : 18380438130
     * goodsId : 1
     * goodsTitle : 测试水乳
     * thumbnail : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202112311538568161529.png
     * specId : 6
     * spec : 绿瓶-5ml/瓶
     * appeal : 快快乐乐了
     * appealPicture : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201141715001219818.jpeg
     * feedback : 回复
     * salesType : 1
     * refund : 1
     * afterSale : 4
     * returnWaybill : 兔兔
     * deletes : 1
     */

    private Object searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private Object remark;
    private ParamsBean params;
    private int id;
    private String orderNumber;
    private int orderDetailsId;
    private int userId;
    private String cellPhoneNumber;
    private int goodsId;
    private String goodsTitle;
    private String thumbnail;
    private int specId;
    private String spec;
    private String appeal;
    private String appealPicture;
    private String feedback;
    private int salesType;
    private int refund;
    private int afterSale;
    private String returnWaybill;
    private int deletes;

    protected RefundDetailsInfo(Parcel in) {
        createBy = in.readString();
        createTime = in.readString();
        updateBy = in.readString();
        updateTime = in.readString();
        id = in.readInt();
        orderNumber = in.readString();
        orderDetailsId = in.readInt();
        userId = in.readInt();
        cellPhoneNumber = in.readString();
        goodsId = in.readInt();
        goodsTitle = in.readString();
        thumbnail = in.readString();
        specId = in.readInt();
        spec = in.readString();
        appeal = in.readString();
        appealPicture = in.readString();
        feedback = in.readString();
        salesType = in.readInt();
        refund = in.readInt();
        afterSale = in.readInt();
        returnWaybill = in.readString();
        deletes = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeString(updateBy);
        dest.writeString(updateTime);
        dest.writeInt(id);
        dest.writeString(orderNumber);
        dest.writeInt(orderDetailsId);
        dest.writeInt(userId);
        dest.writeString(cellPhoneNumber);
        dest.writeInt(goodsId);
        dest.writeString(goodsTitle);
        dest.writeString(thumbnail);
        dest.writeInt(specId);
        dest.writeString(spec);
        dest.writeString(appeal);
        dest.writeString(appealPicture);
        dest.writeString(feedback);
        dest.writeInt(salesType);
        dest.writeInt(refund);
        dest.writeInt(afterSale);
        dest.writeString(returnWaybill);
        dest.writeInt(deletes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RefundDetailsInfo> CREATOR = new Creator<RefundDetailsInfo>() {
        @Override
        public RefundDetailsInfo createFromParcel(Parcel in) {
            return new RefundDetailsInfo(in);
        }

        @Override
        public RefundDetailsInfo[] newArray(int size) {
            return new RefundDetailsInfo[size];
        }
    };

    public Object getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(Object searchValue) {
        this.searchValue = searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

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

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
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

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    public String getAppealPicture() {
        return appealPicture;
    }

    public void setAppealPicture(String appealPicture) {
        this.appealPicture = appealPicture;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getSalesType() {
        return salesType;
    }

    public void setSalesType(int salesType) {
        this.salesType = salesType;
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

    public String getReturnWaybill() {
        return returnWaybill;
    }

    public void setReturnWaybill(String returnWaybill) {
        this.returnWaybill = returnWaybill;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    public static class ParamsBean {
    }
}
