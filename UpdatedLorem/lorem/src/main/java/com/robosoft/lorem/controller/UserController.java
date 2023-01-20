package com.robosoft.lorem.controller;
import com.cloudinary.utils.ObjectUtils;
import com.robosoft.lorem.entity.OpeningInfo;
import com.robosoft.lorem.model.*;
import com.robosoft.lorem.response.BrandList;
import com.robosoft.lorem.response.OrderDetails;
import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.service.CloundinaryConfig;
import com.robosoft.lorem.service.UserService;
import com.robosoft.lorem.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/User")
public class UserController
{

    @Autowired
    private AuthenticationManager authenticationManager;

    //for using user services
    @Autowired
    private UserService userService;


    //for using user details services
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    CloundinaryConfig cloudinary;


    //Abhishek

    @PutMapping("/likeBrand")
    public ResponseEntity<String> addToFav(@RequestBody FavTable favTable)
    {
        try
        {
            boolean check= userService.addToFavourite(favTable);
            if(check==true)
            {
                return new ResponseEntity("Added to favorites",HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity("Failed to add favorites",HttpStatus.BAD_GATEWAY);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add favorites",HttpStatus.BAD_GATEWAY);
        }
    }




    @PostMapping("/review")
    public ResponseEntity<String> addReview(@ModelAttribute ReviewInfo reviewInfo)
    {
        try
        {
            try
            {
                List<String> photoLinks = new ArrayList<String>();
                for (int i = 0; i < reviewInfo.getMultipartFileList().size(); i++)
                {
                    Map uploadResult = cloudinary.upload(reviewInfo.getMultipartFileList().get(i).getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
                    photoLinks.add(uploadResult.get("url").toString());
                }
                reviewInfo.setPhotoLinks(photoLinks);
                userService.addReview(reviewInfo);
            }
            catch (Exception e)
            {
                userService.addReview(reviewInfo);
            }
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add review",HttpStatus.BAD_GATEWAY);
        }
    }



    @GetMapping("/getOrderDetails")
    public OrderDetails getOrderDetails(@RequestBody Orders orders)
    {
        return userService.getOrderDetails(orders);
    }

    @PostMapping("/addCard")
    public ResponseEntity<String> addCard(@RequestBody Card card)
    {
        try
        {
            boolean message= userService.addCard(card);
            if(message==true)
            {
                return new ResponseEntity("Card added successfully", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity("Failed to add card", HttpStatus.BAD_GATEWAY);
        }

    }

    @GetMapping("/viewCards")
    public ResponseEntity<?> viewCards(@RequestBody User user)
    {
        try
        {
            Map<Integer, List<Card>> cardLists= userService.viewCards(user);
            if(cardLists.size()==0)
            {
                return new ResponseEntity<>("No Cards Saved Please Add Some Cards",HttpStatus.FORBIDDEN);
            }
           return new ResponseEntity<>(cardLists,HttpStatus.ACCEPTED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("No Cards Saved Please Add Some Cards",HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/editCard")
    public ResponseEntity<String> editCard(@RequestBody Card card)
    {
        try
        {
            if(this.userService.editCard(card)==true)
            {
                return ResponseEntity.ok().body("Card Updated Successfully");
            }
            else
            {
                return ResponseEntity.ok().body("You cant edit this card");
            }
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }

    @PutMapping("/makeCardPrimary")
    public ResponseEntity<String> makeCardPrimary(@RequestBody Card card)
    {
        try
        {
            this.userService.makeCardPrimary(card);
            return ResponseEntity.ok().body(card.getCardNo()+" Selected as primary");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PutMapping("/deleteCard")
    public ResponseEntity<String> deleteCard(@RequestBody Card card)
    {
        try
        {
            this.userService.deleteCard(card);
            return ResponseEntity.ok().body(card.getCardNo()+" Removed successfully");
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
        }
    }

    @PutMapping("/makePayment")
    public ResponseEntity<String> makePayment(@RequestBody Payment payment)
    {
        try
        {
            String message = userService.makePayment(payment);
            return new ResponseEntity(message, HttpStatus.OK);
        }
        catch (Exception e)
        {
            String message= userService.makePayment(payment);
            return new ResponseEntity(message,HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping("/giveFeedback")
    public ResponseEntity<String> giveFeedback(@RequestBody FeedBack feedBack)
    {
      try
      {
          if(userService.giveFeedback(feedBack)==true)
          {
              return new ResponseEntity("Thank you for your feedback", HttpStatus.OK);
          }
          else
          {
              return new ResponseEntity("Something went wrong",HttpStatus.BAD_GATEWAY);
          }
      }
      catch (Exception e)
      {
          return new ResponseEntity("Something went wrong",HttpStatus.BAD_GATEWAY);
      }

    }

    //Prajwal


    //create and update cart
    @PostMapping("/Cart")
    public ResponseEntity<?> createOrUpdateCart(@RequestBody CartModel cartModel){
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returnedCartModel =  userService.saveOrUpdateCart(cartModel);

        if(returnedCartModel==null)
            return new ResponseEntity<>("Cart Not Updaated..",HttpStatus.EXPECTATION_FAILED);

        return ResponseEntity.ok(returnedCartModel);

    }


    //like and unlike review
    @PostMapping("/Review/Like/{reviewId}")
    public ResponseEntity<?> likeAReview(@PathVariable int reviewId){
        if(userService.likeAreview(userService.getUserIdFromEmail(), reviewId))
            return new ResponseEntity<>("Liked Review Successfully...",HttpStatus.OK);

        return new ResponseEntity<>("UnLiked Review Successfully...",HttpStatus.EXPECTATION_FAILED);
    }


    //get My Profile
    @GetMapping("/Profile")
    public ResponseEntity<?> getUserProfile(){
        UserProfile userProfile = userService.getUserProfile(userService.getUserIdFromEmail());

        if(userProfile!=null)
            return new ResponseEntity<>(userProfile,HttpStatus.OK);

        return new ResponseEntity<>(userProfile,HttpStatus.NO_CONTENT);
    }


    //get my orders based on status
    @GetMapping("/Orders/{orderStatus}/{pageNumber}/{limit}")
    public ResponseEntity<?> getMyOrdersByStatus(@PathVariable String orderStatus,@PathVariable int pageNumber,@PathVariable int limit){

        OrderResponseModel orderResponseModel = userService.getMyOrdersByStatus(orderStatus, userService.getUserIdFromEmail(), pageNumber,limit);

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
    @GetMapping("/Carts/{pageNumber}/{limit}")
    public ResponseEntity<?> getMyCarts(@PathVariable int pageNumber,@PathVariable int limit){
        CartsResponseModel cartsResponseModel =  userService.getMyCarts(userService.getUserIdFromEmail(),pageNumber,limit);

        if(pageNumber==1){
            if(cartsResponseModel.getTotalResultCount()==0)
                return new ResponseEntity<>("No Carts found under this user id..",HttpStatus.NO_CONTENT);
        }

        if(cartsResponseModel.getPerPageCount()==0)
            return new ResponseEntity<>("No Content in current page..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cartsResponseModel,HttpStatus.OK);
    }


    //remove a cart
    @DeleteMapping("/Cart/{cartId}")
    public HttpStatus deleteMyCart(@PathVariable int cartId){
        if(userService.removeCart(userService.getUserIdFromEmail(), cartId))
            return HttpStatus.OK;

        return HttpStatus.EXPECTATION_FAILED;
    }


    //get cart by cart id and user id
    @GetMapping("/Cart")
    public ResponseEntity<?> getCartById(@RequestBody CartModel cartModel)
    {
        cartModel.setUserId(userService.getUserIdFromEmail());
        CartModel returningCartModel = userService.getCartById(cartModel);

        if(returningCartModel==null)
            return new ResponseEntity<String>("No Cart matching given details found..",HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(returningCartModel,HttpStatus.OK);
    }

    //nithin


    //adding new address
    @PostMapping("/addAddress")
    public ResponseEntity<String> addAddress(@RequestBody Address address) throws Exception {
        if (userService.addAddress(address)) {
            return ResponseEntity.status(HttpStatus.OK).body("Address added successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    //editing address by user
    @PostMapping("/editAddress/{addressId}")
    public ResponseEntity<String> editAddress(@RequestBody Address address, @PathVariable int addressId) throws Exception {
        if (userService.editAddress(address, addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address updated successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");

    }

    //    deleting the address
    @DeleteMapping("/deleteAddress/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable int addressId) throws Exception {
        if (userService.deleteAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("Address deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("address deletion failed");

    }

    // setting primary address by user
    @PutMapping("/setPrimaryAddress/{addressId}")
    public ResponseEntity<String> setPrimaryAddress(@PathVariable int addressId) throws Exception {
        if (userService.setPrimaryAddress(addressId, userService.getUserIdFromEmail())) {
            return ResponseEntity.status(HttpStatus.OK).body("primary address set successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed");

    }

    @GetMapping("/displayAddress")
    public ResponseEntity<List<AddressDetails>> displayAddress() {
        List<AddressDetails> addressList = userService.displayAddress(userService.getUserIdFromEmail());
        if (addressList.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(addressList));
    }

    @GetMapping("/displayAddresses")
    public ResponseEntity<AddressDesc> displayAddresses()
    {
        AddressDesc addressDesc = userService.displayAddresses(userService.getUserIdFromEmail());
        if(addressDesc==null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(addressDesc));
    }

    @PostMapping("/choosePayment")
    public ResponseEntity<?> chooseAddress(@RequestBody Orders orders) throws Exception {
        OrderInfo orderInfo = userService.chooseAddress(orders, userService.getUserIdFromEmail(), orders.getLocation());
        if(orderInfo==null)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed");
        }
        return ResponseEntity.of(Optional.of(orderInfo));

    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<?>cancelOrder(@RequestBody Orders orders) throws IOException {
        if(userService.cancelOrder(orders))
        {
            return ResponseEntity.status(HttpStatus.OK).body("order canceled");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("failed to cancel order");
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?>updateOrderStatus(@RequestBody Orders orders)
    {
        if(userService.updateOrderStatus(orders))
        {
            return ResponseEntity.status(HttpStatus.OK).body(""+orders.getOrderStatus());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Task failed");
    }



//Akrithi


    @PutMapping("/editProfile")
    public ResponseEntity<String> editProfile(@ModelAttribute UserEditFields user) throws IOException {
        Map uploadResult2 = cloudinary.upload(user.getProfilePic().getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
        String photo_url=uploadResult2.get("url").toString();
        user.setProfileUrl(photo_url);

        boolean reg_user = userService.editProfile(user);
        if(reg_user)
        {
            return new ResponseEntity<String >("updated successfully", HttpStatus.OK);
        }
        //e.printStackTrace();
        return new ResponseEntity<>("Could not update", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/refer")
    public ResponseEntity<Integer> referAFriend()
    {
        try
        {
            int reg_user = userService.referAFriend();
            return new ResponseEntity<Integer>(reg_user, HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // if required
    @GetMapping("/share/referAFriend")
    public ResponseEntity<Map<String,String>> shareReference(String userId)
    {
        try
        {
            Map<String,String> reg_user = userService.onClickShareApp(userId);
            return new ResponseEntity<Map<String,String> >(reg_user, HttpStatus.OK);
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }





    @GetMapping("/viewDetails")
    public ResponseEntity<?> viewDetails(@RequestParam String offerId)
    {
        ResponseEntity<?> offerObj= userService.viewOfferDetails(offerId);
        return offerObj;
    }


    @PutMapping("/redeem")
    public ResponseEntity<?> redeemItNow(@RequestParam int claimedCreditScore)
    {
        ResponseEntity<?> claimed= userService.redeem(claimedCreditScore);
        return claimed;
    }

    @GetMapping("/apply")
    public ResponseEntity<?> applyOffer(@RequestParam String offerId)
    {
        ResponseEntity<?> applied= userService.applyOffer(offerId);
        return applied;
    }



}
