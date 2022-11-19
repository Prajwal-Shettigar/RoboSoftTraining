package com.robosoft.lorem.service;

import com.robosoft.lorem.entity.Addon;
import com.robosoft.lorem.entity.Dish;
import com.robosoft.lorem.entity.Menu;
import com.robosoft.lorem.entity.Restaurant;
import com.robosoft.lorem.model.Brand;
import com.robosoft.lorem.model.Orders;
import com.robosoft.lorem.model.OfferRequestBody;

import java.io.IOException;

public interface AdminService {
    boolean addBrand(Brand brand);

    boolean changeRole(int userId, String role);

    boolean addRestaurant(Restaurant newrestaurant) throws IOException;

    boolean addDish(Dish dish) throws IOException;

    boolean addMenu(Menu menu) throws IOException;

    boolean addon(Addon addon) throws IOException;

    boolean updateOrderStatus(Orders orders);

    boolean addOffers(OfferRequestBody offer) throws IOException;
}
