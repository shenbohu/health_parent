package com.itheima.daomin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 检查组
 */
public class Succeed implements Serializable {
    private String orderType;//预约类型
    private Date orderDate;//预约时间
    private String member;//体检人
    private String setmeal;//体检套餐

    @Override
    public String toString() {
        return "Succeed{" +
                "orderType='" + orderType + '\'' +
                ", orderDate=" + orderDate +
                ", member='" + member + '\'' +
                ", setmeal='" + setmeal + '\'' +
                '}';
    }

    public Succeed() {
    }

    public Succeed(String orderType, Date orderDate, String member, String setmeal) {
        this.orderType = orderType;
        this.orderDate = orderDate;
        this.member = member;
        this.setmeal = setmeal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSetmeal() {
        return setmeal;
    }

    public void setSetmeal(String setmeal) {
        this.setmeal = setmeal;
    }
}
