package com.cyty.mall.bean;

public class SkillTypeInfo {

    /**
     * id : 2
     * startTime : 12:00
     * endTime : 13:00
     * seckillState : 3
     * deletes : 1
     */

    private int id;
    private String startTime;
    private String endTime;
    private int seckillState;
    private int deletes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSeckillState() {
        return seckillState;
    }

    public void setSeckillState(int seckillState) {
        this.seckillState = seckillState;
    }

    public int getDeletes() {
        return deletes;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }
}
