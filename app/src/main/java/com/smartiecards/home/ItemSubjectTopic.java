package com.smartiecards.home;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/6/2018.
 */

public class ItemSubjectTopic implements Serializable{
    String id = "";
    String subjectid = "";
    String ftopic = "";
    String image = "";
    String color = "";
    String sname = "";



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getFtopic() {
        return ftopic;
    }

    public void setFtopic(String ftopic) {
        this.ftopic = ftopic;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
}
