package com.smartiecards.settings;

import java.io.Serializable;

/**
 * Created by AnaadIT on 11/14/2017.
 */

public class ItemSettings implements Serializable{

    String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
