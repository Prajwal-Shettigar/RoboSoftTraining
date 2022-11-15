package com.robosoft.lorem.model;


import com.robosoft.lorem.entity.Addon;
import lombok.Data;

import java.util.List;

@Data
public class ItemModel {

    private int dishId;
    private String dishName;
    private String customizationInfo;
    private boolean customizable;
    private int addOnCount;
    private int itemCount;
    private boolean veg;
    private double price;
    private List<Addon> addOns;

}
