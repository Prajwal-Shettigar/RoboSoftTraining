package com.robosoft.lorem.controller;


import com.robosoft.lorem.model.*;
import com.robosoft.lorem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    //search with filter
    @GetMapping("/Search")
    public ResponseEntity<?> searchRestaurants(@RequestBody SearchFilter searchFilter) {
        RestaurantSearchResult restaurantSearchResult = userService.searchRestaurant(searchFilter);
        if (searchFilter.getPageNumber()==1){
            if(restaurantSearchResult.getTotalRocordsCount()==0)
                return new ResponseEntity<>("No Restaurants matching the search query found..",HttpStatus.NO_CONTENT);
        }

        if(restaurantSearchResult.getPerPageRecordsCount()==0)
            return new ResponseEntity<>("No Content in this page..",HttpStatus.NO_CONTENT);


        return new ResponseEntity<>(restaurantSearchResult,HttpStatus.OK);
    }



    //get nearby brands
    @GetMapping("/Brands/{address}/{limit}")
    public NearByBrandsSearchResult getNearByBrands(@PathVariable String address, @PathVariable int limit){
        return userService.getNearbyBrands(address,limit);
    }


    //create and update cart
    @PostMapping("/Cart")
    public CartModel createOrUpdateCart(@RequestBody CartModel cartModel){
        return userService.saveOrUpdateCart(cartModel);
    }


    //like and unlike review
    @PostMapping("/Review/Like/{reviewId}/{userId}")
    public ResponseEntity<?> likeAReview(@PathVariable int reviewId, @PathVariable int userId){
        if(userService.likeAreview(userId,reviewId))
            return new ResponseEntity<>("Liked Review Successfully...",HttpStatus.OK);

        return new ResponseEntity<>("UnLiked Review Successfully...",HttpStatus.EXPECTATION_FAILED);
    }


    //get My Profile
    @GetMapping("/Profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userId){
        UserProfile userProfile = userService.getUserProfile(userId);

        if(userProfile!=null)
            return new ResponseEntity<>(userProfile,HttpStatus.OK);

        return new ResponseEntity<>(userProfile,HttpStatus.NO_CONTENT);
    }


    //get my orders based on status
    @GetMapping("/Orders/{orderStatus}/{pageNumber}/{userId}")
    public ResponseEntity<?> getMyOrdersByStatus(@PathVariable String orderStatus,@PathVariable int pageNumber,@PathVariable int userId){

        OrderResponseModel orderResponseModel = userService.getMyOrdersByStatus(orderStatus,userId,pageNumber);

        if(orderResponseModel==null)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        if(pageNumber==1){
            if(orderResponseModel.getTotalRecordsCount()==0)
                return new ResponseEntity<>("No Orders Found under this user id..",HttpStatus.NO_CONTENT);
        }

        if(orderResponseModel.getTotalRecordsInPage()==0)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(orderResponseModel,HttpStatus.OK);
    }


    //get my carts
    @GetMapping("/Carts/{pageNumber}/{userId}")
    public ResponseEntity<?> getMyCarts(@PathVariable int pageNumber,@PathVariable int userId){
        CartsResponseModel cartsResponseModel =  userService.getMyCarts(userId,pageNumber);

        if(pageNumber==1){
            if(cartsResponseModel.getTotalResultCount()==0)
                return new ResponseEntity<>("No Carts found under this user id..",HttpStatus.NO_CONTENT);
        }

        if(cartsResponseModel.getPerPageCount()==0)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cartsResponseModel,HttpStatus.OK);
    }


    //remove a cart
    @DeleteMapping("/Cart/{cartId}/{userId}")
    public HttpStatus deleteMyCart(@PathVariable int cartId,@PathVariable int userId){
        if(userService.removeCart(userId,cartId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //get cart by cart id and user id
    @GetMapping("/Cart")
    public ResponseEntity<?> getCartById(@RequestBody CartModel cartModel)
    {
        CartModel returningCartModel = userService.getCartById(cartModel);

        if(returningCartModel==null)
            return new ResponseEntity<String>("No Cart matching given details found..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(returningCartModel,HttpStatus.OK);
    }




}
