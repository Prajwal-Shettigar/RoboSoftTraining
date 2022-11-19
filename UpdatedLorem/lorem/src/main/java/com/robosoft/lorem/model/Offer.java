package com.robosoft.lorem.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Date;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Offer
{
    private String offerId;
    private float discount;
    private int maxCashBack;
    private Date validUpto;
    private int validPerMonth;
    //store url in db
    private String photo;
    private String description;
    private int brandId;
    private boolean codEnabled;
    private float superCashPercent;
    private int maxSuperCash;

    //private String photo_url;

}
