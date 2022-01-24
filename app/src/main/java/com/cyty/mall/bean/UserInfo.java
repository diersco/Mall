package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @创建者 misJackLee
 * @创建时间 2022/1/5 10:34
 * @描述
 */

public class UserInfo implements Parcelable {


    /**
     * gradeName : null
     * cellPhoneNumber : 17396263951
     * collectionNum : 0
     * integral : 0
     * nickname : 17396263951
     * couponsNum : 0
     * id : 3
     * headPortrait : null
     * memberId : 0
     */

    private String gradeName;
    private String cellPhoneNumber;
    private String dateBirth;
    private int collectionNum;
    private int integral;
    private String nickname;
    private int couponsNum;
    private int id;
    private String headPortrait;
    private int memberId;

    protected UserInfo(Parcel in) {
        gradeName = in.readString();
        cellPhoneNumber = in.readString();
        dateBirth = in.readString();
        collectionNum = in.readInt();
        integral = in.readInt();
        nickname = in.readString();
        couponsNum = in.readInt();
        id = in.readInt();
        headPortrait = in.readString();
        memberId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gradeName);
        dest.writeString(cellPhoneNumber);
        dest.writeString(dateBirth);
        dest.writeInt(collectionNum);
        dest.writeInt(integral);
        dest.writeString(nickname);
        dest.writeInt(couponsNum);
        dest.writeInt(id);
        dest.writeString(headPortrait);
        dest.writeInt(memberId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCouponsNum() {
        return couponsNum;
    }

    public void setCouponsNum(int couponsNum) {
        this.couponsNum = couponsNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
