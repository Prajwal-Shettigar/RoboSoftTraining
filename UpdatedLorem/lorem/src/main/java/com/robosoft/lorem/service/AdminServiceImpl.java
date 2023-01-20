package com.robosoft.lorem.service;
import com.robosoft.lorem.entity.*;
import com.robosoft.lorem.model.Brand;
import com.robosoft.lorem.model.Orders;
import com.robosoft.lorem.model.Role;
import com.robosoft.lorem.model.OfferRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService
{

    private static final String ADD_BRANDED_OFFERS="insert into offer(offerId,discount,maxCashBack,validUpto,validPerMonth,photo,description,brandId,codEnabled,superCashPercent,maxSuperCash) values(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String ADD_OFFERS="insert into offer(offerId,discount,maxCashBack,validUpto,validPerMonth,photo,description,codEnabled,superCashPercent,maxSuperCash) values(?,?,?,?,?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String query;

    @Override
    public boolean addBrand(Brand brand)
    {
        try
        {
            jdbcTemplate.update("insert into brand(brandName, description, logo, profilePic, brandOrigin) values (?,?,?,?,?)", brand.getBrandName(), brand.getDescription(), brand.getLogoLink(), brand.getProfileLink(), brand.getBrandOrigin());
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    @Override
    public boolean changeRole(int userId, String role) {

        String userRole = getRole(role.toUpperCase());

        if(userRole.equalsIgnoreCase("none"))
            return false;

        query = "update user set role='"+userRole+"' where userId="+userId;

        if(jdbcTemplate.update(query)>0)
            return true;

        return false;

    }


    public String getRole(String role){
        switch (role){
            case "ADMIN": {return Role.ROLE_ADMIN.toString();}

            case "USER":{return Role.ROLE_USER.toString();}

            case "MERCHANT":{return Role.ROLE_MERCHANT.toString();}

            default : {return "None";}
        }
    }


    @Override
    public boolean addRestaurant(Restaurant restaurant) throws IOException {

        try {
            String insertQueryForBrand = "insert into restaurant(restaurantName,userId,addressId,profilePic,workingHours,cardAccepted,Description,restaurantType,brandId) values(?,?,?,?,?,?,?,?,?)";
            String insertQueryForNonBrand = "insert into restaurant(restaurantName,userId,addressId,profilePic,workingHours,cardAccepted,Description,restaurantType) values(?,?,?,?,?,?,?,?)";

            query = insertQueryForBrand;

            if (restaurant.getBrandId() == null) {
                query = insertQueryForNonBrand;
            }
            jdbcTemplate.update(query, (preparedStatement) -> {
                preparedStatement.setString(1, restaurant.getRestaurantName());
                preparedStatement.setInt(2, restaurant.getUserId());
                preparedStatement.setInt(3, restaurant.getAddressId());
                if (restaurant.getProfilePicLink() != null) {
                    preparedStatement.setString(4, restaurant.getProfilePicLink());
                }
                else {
                    preparedStatement.setString(4, null);
                }
                preparedStatement.setString(5, restaurant.getWorkingHours());
                preparedStatement.setBoolean(6, restaurant.isCardAccepted());
                preparedStatement.setString(7, restaurant.getDescription());
                preparedStatement.setString(8, restaurant.getRestaurantType());
                if (restaurant.getBrandId() != null) {
                    preparedStatement.setInt(9, restaurant.getBrandId());
                }
            });
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean addDish(Dish dish) throws IOException {
        query = "insert into dish(dishName,description,dishType,veg) values(?,?,?,?)";
        try {
            jdbcTemplate.update(query, dish.getDishName(), dish.getDescription(), dish.getDishType(), dish.isVeg());
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double minPrice(int restaurantId) {
        String minQuery = "select min(price) from menu where restaurantId=" + restaurantId;
        return jdbcTemplate.queryForObject(minQuery, double.class);
    }

    public void updateMinCost(int restaurantId, double minCost) {
        query = "update restaurant set minimumCost=" + minCost + " where restaurantId=" + restaurantId;
        jdbcTemplate.update(query);
    }

    public double averageCost(int restaurantId) {
        query = "select round(avg(price)) from menu where restaurantId=" + restaurantId;
        return jdbcTemplate.queryForObject(query, double.class);

    }

    public void updateAverageCost(int restaurantId, double averageCost) {
        query = "update restaurant set averageCost=" + averageCost + " where restaurantId=" + restaurantId;
        jdbcTemplate.update(query);
    }

    @Override
    public boolean addMenu(Menu menu) throws IOException {
        query = "insert into menu(restaurantId,dishId,price,customizable,dishPhoto,foodType) values(?,?,?,?,?,?)";
        String addonQuery="insert into addonmapping(addonId,dishId,restaurantId) values(?,?,?)";
        try {
            jdbcTemplate.update(query, (preparedStatement) -> {
                preparedStatement.setInt(1, menu.getRestaurantId());
                preparedStatement.setInt(2, menu.getDishId());
                preparedStatement.setFloat(3, menu.getPrice());
                preparedStatement.setBoolean(4, menu.isCustomizable());
                preparedStatement.setString(5, null);
                if (menu.getDishPhotoLink() != null) {
                    preparedStatement.setString(5, menu.getDishPhotoLink());
                }
                preparedStatement.setString(6, menu.getFoodType());

            });
            Double minCost = minPrice(menu.getRestaurantId());
            if (menu.getPrice() <= minCost) {
                updateMinCost(menu.getRestaurantId(), minCost);
            }

            for(String addOnId: menu.getAddonIds()){
                jdbcTemplate.update(addonQuery, (preparedStatement) -> {

                    if(addOnId!=null) {
                        preparedStatement.setInt(1, Integer.valueOf(addOnId));
                        preparedStatement.setInt(2, menu.getDishId());
                        preparedStatement.setInt(3, menu.getRestaurantId());
                    }
                });
            }


            Double avgCost = averageCost(menu.getRestaurantId());
            updateAverageCost(menu.getRestaurantId(), avgCost);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean addon(Addon addon) throws IOException {
        query = "insert into addon(addon,price) values(?,?)";
        try {
            jdbcTemplate.update(query, (preparedStatement) -> {
                preparedStatement.setString(1, addon.getAddon());
                preparedStatement.setFloat(2, addon.getPrice());
            });

            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    //    updating order status by marchent



    @Override
    public boolean addOffers(OfferRequestBody offer) throws IOException {

        char[] chars = "abcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < 5; i++)
        {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String offerCode = sb.toString();


        if(offer.getBrandId()!=0)
        {
            jdbcTemplate.update(ADD_BRANDED_OFFERS,offerCode,offer.getDiscount(),offer.getMaxCashBack(),offer.getValidUpto(),offer.getValidPerMonth(),offer.getPhoto_url(),offer.getDescription(),offer.getBrandId(),offer.isCodEnabled(),offer.getSuperCashPercent(),offer.getMaxSuperCash());
        }
        else
        {
            jdbcTemplate.update(ADD_OFFERS,offerCode,offer.getDiscount(),offer.getMaxCashBack(),offer.getValidUpto(),offer.getValidPerMonth(),offer.getPhoto_url(),offer.getDescription(),offer.isCodEnabled(),offer.getSuperCashPercent(),offer.getMaxSuperCash());
        }

        return true;
    }
}









