package com.smartiecards.home;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by AnaadIT on 1/29/2018.
 */

public class ItemSubject implements Serializable{
int id_count;
    String userid = "";
    String id = "";
    String subjectname = "";
    String photo = "";
    String subjecttime = "";
    String amount = "";
    String purchase_status = "";
    String total_count = "";
    String payable_amount = "";
    String type = "";
    String gameDescription = "";
    String discription = "";

    public int getId_count() {
        return id_count;
    }

    public void setId_count(int id_count) {
        this.id_count = id_count;
    }

    boolean isSelected = false;

    String value = "";

    ArrayList<String> categories = new ArrayList<String>();


    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    int image = 0;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPayable_amount() {
        return payable_amount;
    }

    public void setPayable_amount(String payable_amount) {
        this.payable_amount = payable_amount;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSubjecttime() {
        return subjecttime;
    }

    public void setSubjecttime(String subjecttime) {
        this.subjecttime = subjecttime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getPurchase_status() {
        return purchase_status;
    }

    public void setPurchase_status(String purchase_status) {
        this.purchase_status = purchase_status;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
