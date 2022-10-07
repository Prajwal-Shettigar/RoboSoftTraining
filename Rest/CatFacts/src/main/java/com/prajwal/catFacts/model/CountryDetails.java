package com.prajwal.catFacts.model;

import java.util.ArrayList;

/*
{"country":[{"country_id":"GH","probability":0.224},{"country_id":"PH","probability":0.084},{"country_id":"NG","probability":0.073},{"country_id":"US","probability":0.061},{"country_id":"NE","probability":0.034}],"name":"nathaniel"}
 */
public class CountryDetails {

    private ArrayList<Country> country;
    private String name;

    public ArrayList<Country> getCountry() {
        return country;
    }

    public void setCountry(ArrayList<Country> country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
