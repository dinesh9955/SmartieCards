package com.smartiecards.account;

import java.io.Serializable;

/**
 * Created by AnaadIT on 2/19/2018.
 */

public class ItemCountry implements Serializable{

    String id = "";
    String country_code = "";
    String country_name = "";


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
