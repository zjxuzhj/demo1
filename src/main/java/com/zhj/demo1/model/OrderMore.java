package com.zhj.demo1.model;
/**
 * 用于构建xorpay中more字段
 */
public class OrderMore {
    private int monthNum;
    private String objectId;

    public OrderMore(int monthNum, String objectId) {
        this.monthNum = monthNum;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }
}
