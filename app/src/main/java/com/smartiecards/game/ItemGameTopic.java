package com.smartiecards.game;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/16/2018.
 */

public class ItemGameTopic implements Serializable{

    String id = "";
    String gsubid = "";
    String gtopic_name = "";
    String image = "";
    String dat = "";
    String type = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGsubid() {
        return gsubid;
    }

    public void setGsubid(String gsubid) {
        this.gsubid = gsubid;
    }

    public String getGtopic_name() {
        return gtopic_name;
    }

    public void setGtopic_name(String gtopic_name) {
        this.gtopic_name = gtopic_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
