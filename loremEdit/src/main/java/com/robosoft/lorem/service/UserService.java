package com.robosoft.lorem.service;

import com.robosoft.lorem.model.*;
import com.robosoft.lorem.routeResponse.Location;


public interface UserService {

    RestaurantSearchResult searchRestaurant(SearchFilter searchFilter);

    NearByBrandsSearchResult getNearbyBrands(Location location, int pageNumber);

    CartModel saveOrUpdateCart(CartModel cartModel);

    boolean likeAreview(int userId,int reviewId);

    UserProfile getUserProfile(int userId);

    OrderResponseModel getMyOrdersByStatus(String orderStatus, int userId,int pageNumber);

    CartsResponseModel getMyCarts(int userId,int pageNumber);

    boolean removeCart(int userId,int cartId);

    public CartModel getCartById(CartModel cartModel);

}



