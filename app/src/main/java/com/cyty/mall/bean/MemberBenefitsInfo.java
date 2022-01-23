package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MemberBenefitsInfo implements Parcelable {

    /**
     * gradeName : 普通用户
     * consumptionAmount : 0
     * gradeName2 : 再消费￥1000元成为vip1
     * ReConsumptionAmount : 1000
     * list : [{"searchValue":null,"createBy":"","createTime":null,"updateBy":"","updateTime":"2022-01-20 18:00:17","remark":null,"params":{},"id":1,"icon":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151009209691291.jpg","gradeName":"vip1","consumptionAmount":1000,"equitySwitch":1,"benefitDescription":"<p>权益说明权益说明权益说明权益说明权益说明权益说明权益说明<\/p>","deletes":1},{"searchValue":null,"createBy":"","createTime":"2022-01-20 18:01:33","updateBy":"","updateTime":"2022-01-20 18:04:20","remark":null,"params":{},"id":3,"icon":null,"gradeName":"VIP2","consumptionAmount":5000,"equitySwitch":1,"benefitDescription":"<p><strong>权益说明权益说明权益说明权益说明权益说明权益说明<\/strong><img src=\"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201201804184122438.jpg\"><\/p>","deletes":1},{"searchValue":null,"createBy":"","createTime":null,"updateBy":"","updateTime":"2022-01-20 18:00:22","remark":null,"params":{},"id":2,"icon":"https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151009209691291.jpg","gradeName":"vip3","consumptionAmount":10000,"equitySwitch":1,"benefitDescription":"<p>权益说明权益说明权益说明权益说明权益说明<\/p>","deletes":1}]
     * upgradeAmount : 1000
     */

    private String gradeName;
    private int consumptionAmount;
    private String gradeName2;
    private int ReConsumptionAmount;
    private int upgradeAmount;
    private List<ListBean> list;

    protected MemberBenefitsInfo(Parcel in) {
        gradeName = in.readString();
        consumptionAmount = in.readInt();
        gradeName2 = in.readString();
        ReConsumptionAmount = in.readInt();
        upgradeAmount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gradeName);
        dest.writeInt(consumptionAmount);
        dest.writeString(gradeName2);
        dest.writeInt(ReConsumptionAmount);
        dest.writeInt(upgradeAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemberBenefitsInfo> CREATOR = new Creator<MemberBenefitsInfo>() {
        @Override
        public MemberBenefitsInfo createFromParcel(Parcel in) {
            return new MemberBenefitsInfo(in);
        }

        @Override
        public MemberBenefitsInfo[] newArray(int size) {
            return new MemberBenefitsInfo[size];
        }
    };

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getConsumptionAmount() {
        return consumptionAmount;
    }

    public void setConsumptionAmount(int consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    public String getGradeName2() {
        return gradeName2;
    }

    public void setGradeName2(String gradeName2) {
        this.gradeName2 = gradeName2;
    }

    public int getReConsumptionAmount() {
        return ReConsumptionAmount;
    }

    public void setReConsumptionAmount(int ReConsumptionAmount) {
        this.ReConsumptionAmount = ReConsumptionAmount;
    }

    public int getUpgradeAmount() {
        return upgradeAmount;
    }

    public void setUpgradeAmount(int upgradeAmount) {
        this.upgradeAmount = upgradeAmount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * searchValue : null
         * createBy :
         * createTime : null
         * updateBy :
         * updateTime : 2022-01-20 18:00:17
         * remark : null
         * params : {}
         * id : 1
         * icon : https://app-malll.oss-cn-chengdu.aliyuncs.com/upload/202201151009209691291.jpg
         * gradeName : vip1
         * consumptionAmount : 1000
         * equitySwitch : 1
         * benefitDescription : <p>权益说明权益说明权益说明权益说明权益说明权益说明权益说明</p>
         * deletes : 1
         */

        private Object searchValue;
        private String createBy;
        private Object createTime;
        private String updateBy;
        private String updateTime;
        private Object remark;
        private ParamsBean params;
        private int id;
        private String icon;
        private String gradeName;
        private int consumptionAmount;
        private int equitySwitch;
        private String benefitDescription;
        private int deletes;

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public int getConsumptionAmount() {
            return consumptionAmount;
        }

        public void setConsumptionAmount(int consumptionAmount) {
            this.consumptionAmount = consumptionAmount;
        }

        public int getEquitySwitch() {
            return equitySwitch;
        }

        public void setEquitySwitch(int equitySwitch) {
            this.equitySwitch = equitySwitch;
        }

        public String getBenefitDescription() {
            return benefitDescription;
        }

        public void setBenefitDescription(String benefitDescription) {
            this.benefitDescription = benefitDescription;
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
}
