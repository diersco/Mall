package com.cyty.mall.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyIntegralInfo implements Parcelable {

    /**
     * id : 7
     * serialNumber : 1642658458347
     * userId : 3
     * changeFront : 10000
     * changeType : 1
     * changes : 10000
     * changeAfter : 20000
     * changeTime : 2022-01-20 14:00:58
     * changeReason : 签到获取积分
     * deletes : 1
     */

    private int id;
    private long serialNumber;
    private int userId;
    private int changeFront;
    private int changeType;
    private int changes;
    private int changeAfter;
    private String changeTime;
    private String changeReason;
    private int deletes;

    protected MyIntegralInfo(Parcel in) {
        id = in.readInt();
        serialNumber = in.readLong();
        userId = in.readInt();
        changeFront = in.readInt();
        changeType = in.readInt();
        changes = in.readInt();
        changeAfter = in.readInt();
        changeTime = in.readString();
        changeReason = in.readString();
        deletes = in.readInt();
    }

    public static final Creator<MyIntegralInfo> CREATOR = new Creator<MyIntegralInfo>() {
        @Override
        public MyIntegralInfo createFromParcel(Parcel in) {
            return new MyIntegralInfo(in);
        }

        @Override
        public MyIntegralInfo[] newArray(int size) {
            return new MyIntegralInfo[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChangeFront() {
        return changeFront;
    }

    public void setChangeFront(int changeFront) {
        this.changeFront = changeFront;
    }

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

    public int getChanges() {
        return changes;
    }

    public void setChanges(int changes) {
        this.changes = changes;
    }

    public int getChangeAfter() {
        return changeAfter;
    }

    public void setChangeAfter(int changeAfter) {
        this.changeAfter = changeAfter;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
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
        dest.writeLong(serialNumber);
        dest.writeInt(userId);
        dest.writeInt(changeFront);
        dest.writeInt(changeType);
        dest.writeInt(changes);
        dest.writeInt(changeAfter);
        dest.writeString(changeTime);
        dest.writeString(changeReason);
        dest.writeInt(deletes);
    }
}
