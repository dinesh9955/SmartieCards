package com.smartiecards.dashboard;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/2/2018.
 */

public class ItemPaymentHistory implements Serializable{

//    String subjectid = "";
    String amount = "";
    String transid = "";
    String date = "";
    String coupon = "";
//    String status = "";
    String sname = "";
//    String photo = "";
//    String stime = "";


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransid() {
        return transid;
    }

    public void setTransid(String transid) {
        this.transid = transid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
